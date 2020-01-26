package lock.rw;

import base.util.SleepTool;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 模拟修改商品信息和获取商品信息
 * 使用ReadWriteLock读写锁
 */
public class UseRwGoodsServiceImpl implements GoodsService {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private Goods goods;

    public UseRwGoodsServiceImpl(Goods goods) {
        this.goods = goods;
    }

    @Override
    public Goods getGoods() {
        try {
            readLock.lock();
            // 模拟业务操作
            SleepTool.sleepMills(5);
            return goods;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void setNum() {
        try {
            writeLock.lock();
            // 模拟业务操作
            SleepTool.sleepMills(5);
            goods.sellGoods(10);
        } finally {
            writeLock.unlock();
        }
    }
}
