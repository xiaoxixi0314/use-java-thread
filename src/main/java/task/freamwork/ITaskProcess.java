package task.freamwork;

import task.TaskResult;

/**
 * 任务执行接口
 * @param <T> 任务执行参数
 * @param <R> 返回值
 *            业务方需要实现此接口，提交给任务框架执行
 */
public interface ITaskProcess<T, R> {

    TaskResult<R> process(T bizData);
}
