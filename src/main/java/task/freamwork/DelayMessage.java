package task.freamwork;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayMessage<T> implements Delayed {

    /**
     * 业务数据类型
     */
    private T data;

    /**
     * 出队时间
     */
    private Long dequeueTime;

    public DelayMessage(T data, Long expireAfter, TimeUnit unit) {
        this.data = data;
        this.dequeueTime = System.currentTimeMillis() + (expireAfter>0 ? unit.toMillis(expireAfter) : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return dequeueTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        DelayMessage message = (DelayMessage) o;
        long diff = this.dequeueTime - message.dequeueTime;
        if (diff <= 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public T getData() {
        return data;
    }
}
