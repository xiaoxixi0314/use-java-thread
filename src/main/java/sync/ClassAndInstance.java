package sync;

import base.util.SleepTool;

/**
 * 演示类锁和对象锁是不一样的
 * 两者可以并行
 */
public class ClassAndInstance {

    private static class SyncTestInstance extends Thread{

        private ClassAndInstance instance;

        public SyncTestInstance(ClassAndInstance instance) {
            this.instance = instance;
        }
        @Override
        public void run() {
            System.out.println("[sync instance] sync instance is running....");
            instance.syncInstance();
        }
    }

    private static class SyncTestClass extends Thread{
        @Override
        public void run() {
            System.out.println("[sync class] sync class is running....");
            syncClass();
        }
    }

    private synchronized void syncInstance(){
        System.out.println("[sync instance] sync instance is start...");
        SleepTool.sleepSec(2);
        System.out.println("[sync instance] sync instance end...");
    }

    private static synchronized void syncClass(){
        System.out.println("[sync class] sync class is start...");
        SleepTool.sleepSec(2);
        System.out.println("[sync class] sync class end...");
    }

    public static void main(String[] args) {
        ClassAndInstance instance = new ClassAndInstance();
        SyncTestInstance threadInstance = new SyncTestInstance(instance);

        SyncTestClass classThread = new SyncTestClass();

        threadInstance.start();
        classThread.start();

    }
}
