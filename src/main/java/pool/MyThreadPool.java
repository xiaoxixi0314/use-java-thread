package pool;


import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 自己手动实现一个线程池
 */
public class MyThreadPool {

    /**
     * 工作线程数
     */
    private static final int DEFAULT_WORKER_NUM = 5;

    /**
     * 任务数
     */
    private static final int DEFAULT_MAX_TASK_NUM = 6;

    /**
     * 任务队列
     */
    private final BlockingQueue<Runnable> taskQueue;

    /**
     * 执行任务的线程
     */
    private final WorkThread[] threadWorkers;

    private int workerNum;

    private int taskNum;

    public MyThreadPool() {
        this(DEFAULT_WORKER_NUM, DEFAULT_MAX_TASK_NUM);
    }

    public MyThreadPool(int workerNum, int taskNum) {
        if (workerNum <= 0) {
            workerNum = DEFAULT_WORKER_NUM;
        }
        if (taskNum <= 0) {
            taskNum = DEFAULT_MAX_TASK_NUM;
        }
        this.workerNum = workerNum;
        this.taskNum = taskNum;
        this.taskQueue = new ArrayBlockingQueue<>(taskNum);
        this.threadWorkers = new WorkThread[workerNum];
        for (int i = 0; i < workerNum; i ++) {
            this.threadWorkers[i] = new WorkThread();
            this.threadWorkers[i].start();
        }

    }

    /**
     * 执行任务
     * @param task
     */
    public void execute(Runnable task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程池销毁
     */
    public void destory(){
        System.out.println("=== ready to stop worker");
        for (int i = 0; i < workerNum; i ++) {
            this.threadWorkers[i].stopWorker();
            this.threadWorkers[i] = null; // help gc
        }
        this.taskQueue.clear();
    }

    /**
     * 打印任务执行状况
     */
    public void printProcess(){
        System.out.println("workers num:" + workerNum + ",waiting task num:"+ taskQueue.size());
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            Runnable task = null;
            while (!isInterrupted()) {
                try {
                    task = taskQueue.take();
                    if (!Objects.isNull(task)) {
                        task.run();
                        printProcess();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopWorker() {
            interrupt();
        }
    }

}
