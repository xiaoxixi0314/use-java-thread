package tool.forkjoin;

import java.io.File;
import java.util.Objects;

public class NormalFindFile {

    private static int fileCounts;

    public static void findFile(File path) {
        File[] files = path.listFiles();
        if (Objects.isNull(files) || files.length == 0) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                findFile(file);
            } else {
                if (file.getAbsolutePath().endsWith("txt")) {
                    System.out.println(file.getAbsolutePath());
                }
                fileCounts ++;
            }
        }
    }

    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        File path = new File("F:/");
        findFile(path);
        System.out.println("find txt file cost:" + (System.currentTimeMillis() - startTime) +
                "ms,  locate files:" + fileCounts);
    }
}
