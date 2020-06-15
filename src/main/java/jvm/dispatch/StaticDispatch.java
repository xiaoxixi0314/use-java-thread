package jvm.dispatch;

/**
 * 静态分派
 */
public class StaticDispatch {

    static abstract class Human{}
    static class Women extends Human{}
    static class Man extends Human{}

    public void sayHello(Human guy){
        System.out.println("hello, guy!");
    }

    public void sayHello(Women guy) {
        System.out.println("hello, lady!");
    }

    public void sayHello(Man guy) {
        System.out.println("hello, gentleman!");
    }

    public static void main(String[] args) {
        StaticDispatch dispatch = new StaticDispatch();
        Human h1 = new Women();
        Human h2 = new Man();
        // 静态分派
        dispatch.sayHello(h1);
        dispatch.sayHello(h2);
        System.out.println("================================");

        // 静态类型变化
        dispatch.sayHello((Women)h1);
        dispatch.sayHello((Man)h2);

    }
}
