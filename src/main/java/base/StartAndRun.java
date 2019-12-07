package base;

/**
 * start and run 的区别
 */
public class StartAndRun extends Thread {

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        int i = 10;
        while (i > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("["+threadName+"] i = " + --i);
        }
    }

    public static void main(String[] args) {
        StartAndRun startAndRun = new StartAndRun();
        startAndRun.setName("start-and-run");
//        startAndRun.run();// 执行线程是主线程
        startAndRun.start();// 执行的是当前线程
    }
}
