package forkjoin;

import forkjoin.sum.SumArray;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * 遍历指定文件夹
 * 找出指定类型的文件并且打印出来
 */
public class FindFileAction extends RecursiveAction {

    private File path;

    public FindFileAction(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {
        File[] files = path.listFiles();
        if (Objects.isNull(files) || files.length == 0) {
            return;
        }
        List<FindFileAction> subActions = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                subActions.add(new FindFileAction(file));
            } else {
                if (file.getAbsolutePath().endsWith("txt")) {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
        if (!CollectionUtils.isEmpty(subActions)) {
            for (FindFileAction action : invokeAll(subActions)) {
                action.join();
            }
        }

    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        Long startTime = System.currentTimeMillis();
        File path = new File("D:/");
        FindFileAction action = new FindFileAction(path);
        pool.invoke(action);
        System.out.println("find txt file cost:" + (System.currentTimeMillis() - startTime) + "ms");

    }
}
