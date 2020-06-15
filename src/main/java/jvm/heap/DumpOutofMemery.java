package jvm.heap;

import java.util.LinkedList;
import java.util.List;

/**
 * 内存泄露演示
 * vm args -Xms=30m -Xmx=30m -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError
 */
public class DumpOutofMemery {

    public static void main(String[] args) {
        List<Object> list = new LinkedList<>();
        int index = 1;
        while (true) {
            index ++;
            if (index%10000 == 0){
                System.out.println("the index " + index);
            }
            list.add(new Object());
        }
    }
}
