package lock.rw;

/**
 * 测试读写锁性能
 * 对比synchronized独占锁和读写锁性能
 */
public class BizLockTest {
    private static final Goods goods = new Goods("apple", 100);
    // 使用synchronized独占锁方式
    private static final GoodsService goodsService = new UseSyncGoodsServiceImpl(goods);
    // 使用读写锁方式
//    private static final GoodsService goodsService = new UseRwGoodsServiceImpl(goods);

    private static final int READ_WEIGHT = 10 * 50;
    private static final int WRITE_WEIGHT = 1 * 50;

    private static class ReadThread extends Thread {

        private GoodsService gdsService;

        public ReadThread(GoodsService gdsService) {
            this.gdsService = gdsService;
        }

        @Override
        public void run() {
            Long startTime = System.currentTimeMillis();
            goodsService.getGoods();
            System.out.println("["+Thread.currentThread().getName()+"] read cost " + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

    private static class WriteThread extends Thread {

        private GoodsService gdsService;

        public WriteThread(GoodsService gdsService) {
            this.gdsService = gdsService;
        }

        @Override
        public void run() {
            Long startTime = System.currentTimeMillis();
            goodsService.setNum();
            System.out.println("["+Thread.currentThread().getName()+"] write cost " + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < READ_WEIGHT; i ++) {
            new ReadThread(goodsService).start();
        }
        for (int i = 0; i < WRITE_WEIGHT; i ++) {
            new WriteThread(goodsService).start();
        }
    }
}
