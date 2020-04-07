package jvm;

public class JavaStackTmp {

    public static final String HELLO_WORD = "hello world!";

    public void money(int money) {
        System.out.println(HELLO_WORD);
        money = money  - 100;
    }

    public static void main(String[] args) {
        JavaStackTmp stack = new JavaStackTmp();
        stack.money(100);
    }

}
