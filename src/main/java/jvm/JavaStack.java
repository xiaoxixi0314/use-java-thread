package jvm;

public class JavaStack {

    public void money(int money) {
        money = money  - 100;
    }

    public static void main(String[] args) {
        JavaStack stack = new JavaStack();
        stack.money(100);
    }

}
