package base;

/**
 * 守护线程
 * 1. finally不一定会执行
 * 2.
 */
public class DaemonThread extends Thread{
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(Thread.currentThread().isDaemon());
        try {
            while(!isInterrupted()) {
                System.out.println("["+threadName+"] is running...");
            }
        } finally {
            // 守护线程finally 不一定会执行，取决于操作系统
            System.out.println(".....finally");
        }
    }

    public static void main(String[] args) throws Exception {
        DaemonThread thread = new DaemonThread();
        thread.setName("deamon-thread");
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(1);
//        thread.interrupt();

    }
}
