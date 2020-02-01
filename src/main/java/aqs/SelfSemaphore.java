package aqs;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自己实现一个Semaphore
 */
public class SelfSemaphore implements Lock, Serializable {

    private final class Sync extends AbstractQueuedSynchronizer {

        /**
         * 信号量，信号量为几表示
         * 几个线程可同时获得锁
         * @param acquires
         */
        Sync(int acquires) {
            if(acquires <= 0) {
                throw new IllegalMonitorStateException();
            }
            setState(acquires);
        }

        /**
         *
         * @param reduceCount 扣减个数
         * @return 返回小于0则表示扣减失败
         */
        @Override
        protected int tryAcquireShared(int reduceCount) {
            while (true) {
                int current = getState();
                int newState = current - reduceCount;
                if (newState < 0 || compareAndSetState(current, newState)) {
                    return newState;
                }
            }

        }

        /**
         *
         * @param returnCount 归还个数
         * @return
         */
        @Override
        protected boolean tryReleaseShared(int returnCount) {
            while (true) {
                int current = getState();
                int newState = current + returnCount;
                if (compareAndSetState(current, newState)) {
                    return true;
                }
            }
        }

        Condition newCondition() {
            return new ConditionObject();
        }

    }

    private Sync sync = new Sync(3);

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
}
