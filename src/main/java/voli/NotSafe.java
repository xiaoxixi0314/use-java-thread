package voli;

import base.util.SleepTool;

/**
 * 演示volatile关键字只能保证可见性，并不能保证原子性
 * 适用于一写多读的场景
 */
public class NotSafe {

    private volatile int count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void incCount(){
        for (int i = 0; i < 10000; i++) {
            count ++ ;
        }
    }

    public static void main(String[] args) {
        NotSafe notSafe = new NotSafe();
        // 不安全的累加
        for (int i = 0 ; i < 10; i ++ ) {
            Thread thread = new Thread(() -> notSafe.incCount());
            thread.start();
        }

        SleepTool.sleep(2);

        System.out.println(notSafe.getCount());
    }
}
