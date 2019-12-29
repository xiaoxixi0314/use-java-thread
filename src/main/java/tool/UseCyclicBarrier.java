
package tool;


import base.util.SleepTool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 使用演示
 */
public class UseCyclicBarrier {

    private static final int THREAD_NUMS = 4;

    // parties 必须保持和线程数一致，barrierAction在所有子线程wait后执行，再执行各子线程wait后的代码
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUMS, new CollectThread());
//    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_NUMS);
    private static final ConcurrentHashMap<String, Long> sumResult = new ConcurrentHashMap<>();

    private static class CollectThread implements Runnable {
        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            for (Map.Entry entry : sumResult.entrySet()) {
                result.append("[" + entry.getValue() + "]");
            }
            System.out.println(result);
        }
    }

    private static class SubThread implements Runnable {
        @Override
        public void run() {
            try {
                long threadId = Thread.currentThread().getId();
                System.out.println("Thread_" + threadId + "started.");
                SleepTool.sleepMills(500);
                sumResult.put(String.valueOf(threadId), threadId);
                // await
                cyclicBarrier.await();
                SleepTool.sleepMills(200);
                System.out.println("Thread_" + threadId + "do something.");
                // 可以多次await
                cyclicBarrier.await();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            for(int i = 0; i < THREAD_NUMS; i ++) {
                new Thread(new SubThread()).start();
            }
            Thread.sleep(2000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}