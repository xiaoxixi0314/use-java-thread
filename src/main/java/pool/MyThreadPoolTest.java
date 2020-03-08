package pool;

import org.apache.commons.lang3.RandomUtils;

public class MyThreadPoolTest {


    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(3,3);
        for (int i = 0 ; i < 6; i++) {
            Runnable task = new MyRunnable("task-" + i);
            pool.execute(task);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.destory();
    }

    private static class MyRunnable implements Runnable {

        private String taskName;

        public MyRunnable(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(RandomUtils.nextInt(1, 10) * 300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("[" + Thread.currentThread().getId()+"]["+this.taskName+ "]task execute finished..");
        }
    }
}
