package task.freamwork;

import task.JobResult;
import task.TaskResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * job，用来存储当前工作下的任务相关信息
 */
public class Job<T> {
    /**
     * 成功任务数
     */
    private AtomicInteger successCount;

    /**
     * 已处理完的任务数
     */
    private AtomicInteger processedCount;

    /**
     * 存放任务的执行结果
     */
    private LinkedBlockingDeque<TaskResult<T>> results;

    /**
     * 保留任务结果可查询的时间,单位:秒
     */
    private Long expireTime;

    /**
     * job名称
     */
    private final String jobName;

    /**
     * 任务数
     */
    private final int taskLength;

    /**
     * 处理任务的具体执行逻辑
     */
    private final ITaskProcess<?, ?> process;


    /**
     * 初始化方法
     * @param taskLength 此工作任务数
     * @param expireTime 多久之后执行结果将清除
     * @param jobName 工作名称
     * @param process 任务处理的具体实现
     */
    public Job(int taskLength,
               long expireTime,
               String jobName,
               ITaskProcess process) {
        this.successCount = new AtomicInteger(0);
        this.processedCount = new AtomicInteger(0);
        this.taskLength = taskLength;
        this.results = new LinkedBlockingDeque<>(taskLength);
        this.process = process;
        this.expireTime = expireTime;
        this.jobName = jobName;
    }

    /**
     * 获取执行成功的任务数量
     * @return
     */
    public int getSuccessCount() {
        return this.successCount.get();
    }

    /**
     * 获取执行失败的任务数量
     * @return
     */
    public int getFailCount() {
        return this.processedCount.get() - this.successCount.get();
    }

    /**
     * 获取已处理的任务数
     * @return
     */
    public int getProcessedCount() {
        return this.processedCount.get();
    }

    /**
     * 任务结果存储
     * @param result
     */
    public void addResult(TaskResult<T> result) {
        // 执行成功， 成功任务数+1
        if (result.isSuccess()) {
            this.successCount.incrementAndGet();
        }
        // 处理完的任务数+1
        this.processedCount.incrementAndGet();
        // 所有任务执行完成的话，将job执行结果保存一段时间，以供查询使用
        // todo
        if (this.taskLength == this.processedCount.get()) {
            JobListener.getInstance().add(jobName, expireTime);
        }

    }

    /**
     * 获取任务详情信息
     * @return
     */
    public JobResult getResult() {
        JobResult result = new JobResult();
        result.setTotal(this.taskLength);
        result.setSuccessCount(this.successCount.get());
        result.setProcessedCount(this.processedCount.get());
        result.setFailCount(getFailCount());
        return result;
    }

    /**
     * 获取任务执行详情
     * @return
     */
    public List<TaskResult<T>> getResults(){
        List<TaskResult<T>> resultList = new ArrayList<>();
        TaskResult<T> result;
        while ((result = results.pollFirst()) != null) {
            resultList.add(result);
        }
        return resultList;
    }

    public ITaskProcess getProcess(){
        return this.getProcess();
    }
}
