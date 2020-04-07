package jvm.heap;

/**
 * 演示对象优先在eden区分配
 * -Xmn10M -Xmx20M -Xms20M -XX:+PrintGCDetails
 */
public class EdenAllocation {

    private static final int _1M = 1024 * 1024;

    public static void main(String[] args) {
        byte[] b1, b2, b3, b4;
        b1 = new byte[_1M];
        b2 = new byte[_1M];
        b3 = new byte[_1M];
    }
}
