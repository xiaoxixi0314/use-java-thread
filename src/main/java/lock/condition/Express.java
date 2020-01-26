package waitnotify;

import java.util.Objects;

public class Express {

    private int km;
    private String site;

    private final static String CITY = "HangZhou";

    public Express(int km, String site) {
        this.km = km;
        this.site = site;
    }

    public synchronized void changeKm() {
        this.km = 101;
        notifyAll();
//        notify();
    }

    public synchronized  void changeSite() {
        this.site = "Beijing";
        notifyAll();
    }

    public synchronized void waitKm() {
        String threadName = Thread.currentThread().getName();
        try {
            while(this.km < 100) {
                wait();
                System.out.println("[" + threadName + "] wait km be notified.");
            }
            System.out.println("[" + threadName + "]the km is changed to [" + this.km + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void waitSite() {
        String threadName = Thread.currentThread().getName();
        try {
            while(Objects.equals(this.site, CITY)) {
                wait();
                System.out.println("[" + threadName + "] wait site be notified.");
            }
            System.out.println("[" + threadName + "]the site is changed to [" + this.site + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
