package lock.rw;

import base.util.SleepTool;

/**
 * 模拟修改商品信息和获取商品信息
 * 使用synchronized独占锁
 */
public class UseSyncGoodsServiceImpl implements GoodsService{

    private Goods goods;

    public UseSyncGoodsServiceImpl(Goods goods) {
        this.goods = goods;
    }

    @Override
    public synchronized Goods getGoods() {
        // 模拟业务操作
        SleepTool.sleepMills(5);
        return goods;
    }

    @Override
    public synchronized void setNum() {
        // 模拟业务操作
        SleepTool.sleepMills(5);
        goods.sellGoods(10);
    }
}
