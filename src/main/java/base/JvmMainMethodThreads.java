package base;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * JVM天生是多线程的
 * 打印执行main时会有
 * 几个线程被启动了
 */
public class JvmMainMethodThreads {

    public static void main(String[] args) {
        ThreadMXBean mx = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threads = mx.dumpAllThreads(false, false);
        for (ThreadInfo thread: threads) {
            System.out.println(thread.getThreadId() + ":[" + thread.getThreadName() + "]");
        }
    }
}
