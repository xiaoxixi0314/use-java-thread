package base;

public class InterruptRunnable implements Runnable {

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        while(!Thread.currentThread().isInterrupted()) {
            System.out.println("[" + threadName + "] is running");
            System.out.println("[" + threadName + "] interrupt flag is:" + Thread.currentThread().isInterrupted());
        }
    }

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new InterruptRunnable());
        thread.setName("test-runnable-thread");
        thread.start();

        Thread.sleep(100);

        thread.interrupt();
    }
}
