package task;

/**
 * 任务执行结果
 * @param <T>
 */
public class TaskResult<T> {

    ErrorEnum error;

    private T data;

    private boolean isSuccess;

    private void setData(T data) {
        this.data = data;
    }

    private void setError(ErrorEnum error) {
        this.error = error;
    }

    private void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public T getData() {
        return this.data;
    }

    public ErrorEnum getError() {
        return this.error;
    }

    public boolean isSuccess(){
        return this.isSuccess;
    }

    private TaskResult(){}

    public static <T> TaskResult<T> success(T data) {
        TaskResult<T> result = new TaskResult<>();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    public static <T> TaskResult<T> fail(T data, ErrorEnum error) {
        TaskResult<T> result = new TaskResult<>();
        result.setData(data);
        result.setError(error);
        result.setSuccess(false);
        return result;
    }

    public static <T> TaskResult<T> fail(ErrorEnum error) {
        TaskResult<T> result = new TaskResult<>();
        result.setError(error);
        result.setSuccess(false);
        return result;
    }



}
