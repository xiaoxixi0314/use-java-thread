package task.freamwork;

import task.TaskResult;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 任务执行池
 */
public class TaskPool<T, R> {

    /**
     * 线程数量，和cpu数量保持一致
     */
    private final int THREAD_COUNT =
            Runtime.getRuntime().availableProcessors();

    /**
     * 任务执行队列
     */
    private final static BlockingQueue<Runnable> taskQueue =
            new ArrayBlockingQueue<>(2048);

    /**
     * 任务执行池
     */
    private final ExecutorService pool =
            new ThreadPoolExecutor(THREAD_COUNT, THREAD_COUNT,
                    60,
                    TimeUnit.SECONDS,
                    taskQueue);

    /**
     * 任务信息容器
     * 用来缓存任务信息
     * 过期后会从此容器中删除
     */
    private static final ConcurrentHashMap<String, Job<?>> jobMap =
            new ConcurrentHashMap<>();


    private TaskPool(){}

    private static class TaskPoolHolder {
        private static final TaskPool pool = new TaskPool<>();
        public static TaskPool getInstance() {
            return pool;
        }
    }

    /**
     * 单例初始化执行池
     * @return
     */
    public TaskPool<T, R> getInstance() {
        return TaskPoolHolder.getInstance();
    }


    private static class Task<T, R> implements Runnable{

        Job<R> job;

        T bizData;

        public Task(Job<R> job, T bizData) {
            this.job = job;
            this.bizData = bizData;
        }

        @Override
        public void run() {
            TaskResult<R> taskResult = job.getProcess().process(bizData);
            // todo 优化result，防止业务调用方乱搞
            job.addResult(taskResult);
        }
    }

    /**
     * 作业提交
     * @param jobName
     * @param process
     * @param taskLength
     * @param expireTime
     */
    public void registerJob(String jobName,
                            ITaskProcess<?, ?> process,
                            int taskLength,
                            long expireTime) {
        Job<?> job = new Job<>(taskLength, expireTime, jobName, process);
        if (!Objects.isNull(jobMap.putIfAbsent(jobName, job))) {
            throw new RuntimeException("job "+ jobName + " is exists, can't duplicate registration!");
        }
    }

    /**
     * 任务执行
     * @param job
     * @param data
     * @param <T>
     * @param <R>
     */
    public <T, R> void executeTask(Job<R> job, T data) {
        Task<T, R> task = new Task<>(job, data);
        pool.execute(task);
    }

    /**
     * 获取job
     * @param jobName
     * @param <R>
     * @return
     */
    public <R> Job<R> getJob(String jobName) {
        Job<R> job =  (Job<R>)jobMap.get(jobName);
        if (Objects.isNull(job)) {
            throw new RuntimeException("illegal job "+ jobName);
        }
        return job;
    }

    /**
     * 获取任务详情
     * @param jobName
     * @param <R>
     * @return
     */
    public <R> List<TaskResult<R>> getTaskResults(String jobName) {
        Job<R> job = getJob(jobName);
        return job.getResults();
    }

    /**
     * 获取所有作业
     * @return
     */
    public static Map<String, Job<?>> getJobMap() {
        return jobMap;
    }

}
