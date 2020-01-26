package lock.condition;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condition实现等待通知功能
 */
public class Express {

    private int km;
    private String site;

    private Lock kmLock = new ReentrantLock();
    private Lock siteLock = new ReentrantLock();

    private Condition kmCondition = kmLock.newCondition();
    private Condition siteCondition = siteLock.newCondition();

    private final static String CITY = "HangZhou";

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public void changeKm() {
        kmLock.lock();
        try {
            this.km = 101;
            kmCondition.signal();
//            kmCondition.signalAll();
        } finally {
            kmLock.unlock();
        }
    }

    public void changeSite() {
        siteLock.lock();
        try {
            this.site = "Beijing";
            siteCondition.signalAll();
        } finally {
            siteLock.unlock();
        }
    }

    public void waitKm() {
        String threadName = Thread.currentThread().getName();
        kmLock.lock();
        try {
            while(this.km < 100) {
                kmCondition.await();
                System.out.println("[" + threadName + "] wait km be notified.");
            }
            System.out.println("[" + threadName + "]the km is changed to [" + this.km + "]");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kmLock.unlock();
        }
    }

    public void waitSite() {
        String threadName = Thread.currentThread().getName();
        siteLock.lock();
        try {
            while(Objects.equals(this.site, CITY)) {
                siteCondition.await();
                System.out.println("[" + threadName + "] wait site be notified.");
            }
            System.out.println("[" + threadName + "]the site is changed to [" + this.site + "]");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           siteLock.unlock();
        }
    }

}
