package forkjoin.sum;

import forkjoin.MakeArray;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumArray {

    private static class SumArrayTask extends RecursiveTask<Integer> {

        /**
         * 阈值
         */
        private static final int THRESHOLD = 100;

        private int[] source;
        private int fromIndex;
        private int endIndex;

        public SumArrayTask(int[] source, int fromIndex, int endIndex) {
            this.source = source;
            this.fromIndex = fromIndex;
            this.endIndex = endIndex;
        }


        @Override
        protected Integer compute() {
            // 满足业务计算条件则做真正的业务计算，否则继续拆分任务
            if (endIndex - fromIndex <= THRESHOLD) {
//                System.out.println("from index:" + fromIndex + ",end index:" + endIndex);
                int sum = 0;
                for (int i = fromIndex; i <= endIndex; i ++) {
                    sum = sum + source[i];
                }
                return sum;
            } else {
                int middle = (fromIndex + endIndex) / 2;
                SumArrayTask leftTask = new SumArrayTask(source, fromIndex, middle);
                SumArrayTask rightTask = new SumArrayTask(source, middle+1, endIndex);
                invokeAll(leftTask, rightTask);
                return leftTask.join() + rightTask.join();
            }
        }
    }

    public static void main(String[] args) {
        int[] source = MakeArray.makeRandomArray(10000);
        Long startTime = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        SumArrayTask sumArrayTask = new SumArrayTask(source, 0, source.length - 1);

        pool.invoke(sumArrayTask);
        System.out.println("cost:" + (System.currentTimeMillis() - startTime) + "ms, result:" + sumArrayTask.join());

    }
}
