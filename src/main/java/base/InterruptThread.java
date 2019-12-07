package base;

/**
 * 中断线程simple
 */
public class InterruptThread extends Thread{
    @Override
    public void run() {
        String threadName = this.getName();
//        while(true) { // 不理会中标标记
//        while(!isInterrupted()){ // isInterrupted方法不会将标记位重置成false
        while(!Thread.currentThread().interrupted()){ // interrupted方法会将标记位重置成false
            System.out.println("[" + threadName + "] is running");
            System.out.println("[" + threadName + "] interrupt flag is:" + isInterrupted());
        }
        System.out.println("【" + threadName + "】 interrupt flag is:" + isInterrupted());
    }

    public static void main(String[] args) throws Exception{
        InterruptThread thread = new InterruptThread();
        thread.setName("test-interrupt-thread");
        thread.start();
        Thread.sleep(100);
        thread.interrupt();// 中断线程，其实是设置标记位为true
    }
}
