package task.freamwork;

import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class JobListener {

    /**
     * 存储作业名称队列，一定期间后将任务执行结果删除
     */
    private static DelayQueue<DelayMessage<String>> jobListenerQueue = new DelayQueue<>();

    private static class JobListenerHolder {
        public static JobListener instance = new JobListener();
        public static JobListener getInstance() {
            return JobListenerHolder.instance;
        }
    }

    public static JobListener getInstance() {
        return JobListenerHolder.getInstance();
    }

    /**
     * 清除作业任务
     */
    private static class ClearJobRunnable implements Runnable{

        DelayQueue<DelayMessage<String>> queue = JobListener.jobListenerQueue;

        Map<String, Job<?>> jobMap = TaskPool.getJobMap();

        @Override
        public void run() {
            try {
                while (true) {
                    DelayMessage<String> message = queue.take();
                    String jobName = message.getData();
                    jobMap.remove(jobName);
                    System.out.println("job " + jobName + " removed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void add(String jobName, long expireAfter) {
        DelayMessage<String> delayMessage = new DelayMessage<>(jobName, expireAfter, TimeUnit.SECONDS);
        jobListenerQueue.offer(delayMessage);
        System.out.println("job " + jobName + "offer to delay queue");
    }


    /**
     * 启动作业清除任务
     */
    static {
        Thread thread = new Thread(new ClearJobRunnable());
        thread.setDaemon(true);
        thread.setName("clear-job-thread");
        thread.start();
    }



}
