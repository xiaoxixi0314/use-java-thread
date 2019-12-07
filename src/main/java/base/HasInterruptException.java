package base;

/**
 * catch interrupt exception 之后
 * 线程中断标记位会重置成false
 * 为什么这么设计？
 */
public class HasInterruptException extends Thread {

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        while(!isInterrupted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("【"+threadName+"】 interrupt flag is " + isInterrupted());
                //interrupt();// 如果确实要中断当前线程，需手动调用interrupt方法
            }
            System.out.println("["+threadName+"] is running!!!!");
        }
        System.out.println("【"+threadName+"】 interrupt flag is " + isInterrupted());
    }

    public static void main(String[] args) throws Exception{
        HasInterruptException thread = new HasInterruptException();
        thread.setName("test-interrupt-ex");
        thread.start();
        Thread.sleep(500);
        thread.interrupt();
    }
}
