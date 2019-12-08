package sync;

import base.util.SleepTool;

/**
 * 类锁和静态变量也是不一样的
 * 两者可以并行
 */
public class StaticAndClass {
    private static class SyncTestStaticInstance extends Thread{

        private StaticAndClass instance;

        public SyncTestStaticInstance(StaticAndClass instance) {
            this.instance = instance;
        }
        @Override
        public void run() {
            System.out.println("[sync instance] sync instance is running....");
            instance.syncStatic();
        }
    }

    private static class SyncTestClass extends Thread{
        @Override
        public void run() {
            System.out.println("[sync class] sync class is running....");
            syncClass();
        }
    }


    private static Object object = new Object();
    private void syncStatic(){
        synchronized (object) {
            System.out.println("[sync static instance] sync static instance is start...");
            SleepTool.sleep(2);
            System.out.println("[sync static instance] sync static instance end...");
        }
    }

    private static synchronized void syncClass(){
        System.out.println("[sync class] sync class is start...");
        SleepTool.sleep(2);
        System.out.println("[sync class] sync class end...");
    }

    public static void main(String[] args) {
        StaticAndClass instance = new StaticAndClass();
        SyncTestStaticInstance threadInstance = new SyncTestStaticInstance(instance);

        SyncTestClass classThread = new SyncTestClass();

        threadInstance.start();
        classThread.start();
    }

}
