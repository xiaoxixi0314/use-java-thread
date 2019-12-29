package tool.forkjoin;

import org.apache.commons.lang3.RandomUtils;

/**
 * 制作一个随机数组
 */
public class MakeArray {

    public static int[] makeRandomArray(int size) {
        if (size <= 0) {
            size = 10;
        }
        int[] array = new int[size];
        for (int i = 0; i < size; i ++) {
            array[i] = RandomUtils.nextInt();
        }
        return array;
    }
}
