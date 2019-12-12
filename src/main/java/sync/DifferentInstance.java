package sync;

import base.util.SleepTool;

/**
 * 演示对象锁
 * 锁不同对象是可以并行的
 */
public class DifferentInstance {

    private static class Sync1Instance implements Runnable {

        private DifferentInstance instance;

        public Sync1Instance(DifferentInstance instance) {
            this.instance = instance;
        }
        @Override
        public void run() {
            System.out.println("【1】test sync1 is running....");
            instance.sync1Instance();
        }
    }

    private static class Sync2Instance implements Runnable {

        private DifferentInstance instance;

        public Sync2Instance(DifferentInstance instance) {
            this.instance = instance;
        }
        @Override
        public void run() {
            System.out.println("【2】test sync2 is running....");
            instance.sync2Instance();
        }
    }

    private synchronized void sync1Instance() {
        System.out.println("【1】 test sync1 started....");
        SleepTool.sleepSec(2);
        System.out.println("【1】 test sync1 end....");
    }

    private synchronized void sync2Instance() {
        System.out.println("【2】 test sync1 started....");
        SleepTool.sleepSec(2);
        System.out.println("【2】 test sync1 end....");
    }

    public static void main(String[] args) {
        DifferentInstance instance1 = new DifferentInstance();
        Thread instance1Thread = new Thread(new Sync1Instance(instance1));

        DifferentInstance instance2 = new DifferentInstance();
        Thread instance2Thread = new Thread(new Sync2Instance(instance2));

        instance1Thread.start();
        instance2Thread.start();

    }
}
