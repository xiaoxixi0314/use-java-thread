package sync;

import base.util.SleepTool;

/**
 * synchronized 关键字
 * 演示对象锁
 */
public class SyncTest {

    private int count;

    private Object object = new Object();

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

    public synchronized void incCountSafe() {
        this.incCount();
    }

    public void incCountSafe2() {
        synchronized (object) {
            this.incCount();
        }
    }

    public void incCountSafe3() {
        synchronized (this) {
            this.incCount();
        }
    }
    public static void main(String[] args) {
        SyncTest syncTest = new SyncTest();
        // 不安全的累加
        for (int i = 0 ; i < 10; i ++ ) {
            Thread thread = new Thread(() -> syncTest.incCount());
            thread.start();
        }

//        // 安全的累加方式1
//        for (int i = 0 ; i < 10; i ++ ) {
//            Thread thread = new Thread(() -> syncTest.incCountSafe());
//            thread.start();
//        }

//        // 安全的累加方式2
//        for (int i = 0 ; i < 10; i ++ ) {
//            Thread thread = new Thread(() -> syncTest.incCountSafe2());
//            thread.start();
//        }

//        // 安全的累加方式3
//        for (int i = 0 ; i < 10; i ++ ) {
//            Thread thread = new Thread(() -> syncTest.incCountSafe3());
//            thread.start();
//        }

        SleepTool.sleepSec(2);

        System.out.println(syncTest.getCount());

    }
}
