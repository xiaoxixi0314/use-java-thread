package forkjoin.sum;

import forkjoin.MakeArray;

/**
 * 单线程的sum
 */
public class NormalSum {

    public static void main(String[] args) {
        int[] array =  MakeArray.makeRandomArray(10000);
        Long startTime = System.currentTimeMillis();
        int sum = 0;
        for (int i: array) {
            sum = sum + i;
        }
        System.out.println("sum:" + sum + ", cost:" + (System.currentTimeMillis() - startTime) + "ms.");
    }
}
