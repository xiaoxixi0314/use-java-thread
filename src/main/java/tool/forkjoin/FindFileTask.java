package tool.forkjoin;

import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * 遍历指定文件夹
 * 找出指定类型的文件并且打印出来
 */
public class FindFileTask extends RecursiveTask<Integer> {

    private File path;

    public FindFileTask(File path) {
        this.path = path;
    }

    @Override
    protected Integer compute() {
        File[] files = path.listFiles();
        if (Objects.isNull(files) || files.length == 0) {
            return 0;
        }
        Integer fileCounts = 0;
        List<FindFileTask> subTasks = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                subTasks.add(new FindFileTask(file));
            } else {
                fileCounts ++;
                if (file.getAbsolutePath().endsWith("txt")) {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
        if (!CollectionUtils.isEmpty(subTasks)) {
            for (FindFileTask task : invokeAll(subTasks)) {
                fileCounts = fileCounts + task.join();
            }
        }
        return fileCounts;
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        Long startTime = System.currentTimeMillis();
        File path = new File("F:/");
        FindFileTask action = new FindFileTask(path);
        Integer fileCounts = pool.invoke(action);
        System.out.println("find txt file cost:" + (System.currentTimeMillis() - startTime) +
                "ms,  locate files:" + fileCounts);

    }
}
