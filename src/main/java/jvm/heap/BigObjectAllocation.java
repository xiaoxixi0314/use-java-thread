package jvm.heap;

/**
 * 大对象直接进入老年代
 * PretenureSizeThreshold:超过这个值的时候，对象直接在old区分配内存
 * -Xmn10M -Xmx20M -Xms20M -XX:+PrintGCDetails -XX:+UseSerialGC -XX:PretenureSizeThreshold=4M
 */
public class BigObjectAllocation {

    private static final int _1M = 1024 * 1024;

    static final byte[] b5 = new byte[1*_1M];

    public static void main(String[] args) {
        byte[] b1, b2, b3, b4;
        b1 = new byte[1*_1M]; // 对象在eden区
        b2 = new byte[1*_1M]; // 对象在eden区
        b3 = new byte[5*_1M]; // 这个对象直接进入老年代
    }
}
