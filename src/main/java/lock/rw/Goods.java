package lock.rw;

public class Goods {

    private String name;

    private double totalMoney;

    private int stock;

    public Goods(String name, int stock) {
        this.name = name;
        this.stock = stock;
        this.totalMoney = 0;
    }

    public int getStock() {
        return this.stock;
    }

    public double getTotalMoney(){
        return this.totalMoney;
    }

    public void sellGoods(int sellNum){
        this.totalMoney = this.totalMoney + sellNum * 30;
        this.stock = this.stock - sellNum;
    }
}
