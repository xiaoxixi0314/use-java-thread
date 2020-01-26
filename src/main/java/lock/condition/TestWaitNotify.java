package waitnotify;

import base.util.SleepTool;

public class TestWaitNotify {

    private static Express express = new Express(99, "HangZhou");

    private static class ChangeKmThread extends  Thread {
        @Override
        public void run() {
            express.waitKm();
        }
    }

    private static class ChangeSiteThread extends Thread {
        @Override
        public void run() {
            express.waitSite();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            ChangeKmThread thread = new ChangeKmThread();
            thread.setName("wait-km-" + i);
            thread.start();
        }

        for (int i = 0; i < 3; i++) {
            ChangeSiteThread thread = new ChangeSiteThread();
            thread.setName("wait-site-" + i);
            thread.start();
        }
        SleepTool.sleepSec(2);
        express.changeKm();
    }
}
