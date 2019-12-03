package base;

/**
 * 线程的两种创建方式
 */
public class NewThreadWay {

    private static class NewThreadExtendsThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+ ":extends thread way.");
        }
    }

    private static class NewThreadImplRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ":implements runnable way.");
        }
    }


    public static void  main(String[] args) {
        NewThreadExtendsThread threadExtendsThread = new NewThreadExtendsThread();
        threadExtendsThread.setName("extend-thread");
        threadExtendsThread.start();

        Thread implRunnableThread = new Thread(new NewThreadImplRunnable());
        implRunnableThread.setName("impl-runnable");
        implRunnableThread.start();
    }
}
