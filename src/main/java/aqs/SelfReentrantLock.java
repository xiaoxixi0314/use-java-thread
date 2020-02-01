package aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自己实现一个锁
 * 可重入
 */
public class SelfReentrantLock implements Lock {

    private final class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            if (Thread.currentThread() == getExclusiveOwnerThread()) {
                int state = getState();
                compareAndSetState(state, state+ 1);
                return true;
            }
            if (compareAndSetState(0, 1)) {
                // 设置当前拥有独占访问权限的线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            int state = getState();
            if (state == 0) {
                throw new IllegalMonitorStateException();
            }
            int newState = state - 1;
            if (newState == 0) {
                setExclusiveOwnerThread(null);
            }
            setState(newState);
            System.out.println("["+Thread.currentThread().getName()+"] already released lock");
            return true;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return super.tryAcquireShared(arg);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            return super.tryReleaseShared(arg);
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        System.out.println("["+Thread.currentThread().getName()+"] ready get lock");
        sync.acquire(1);
        System.out.println("["+Thread.currentThread().getName()+"] already got lock");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        System.out.println("[" + Thread.currentThread().getName() + "] ready release lock");
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
