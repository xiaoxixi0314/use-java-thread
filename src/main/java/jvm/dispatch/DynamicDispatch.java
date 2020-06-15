package jvm.dispatch;

/**
 * 动态分派
 */
public class DynamicDispatch {
    static abstract class Human{
        abstract void sayHello();
    }
    static class Women extends Human {
        @Override
        void sayHello() {
            System.out.println("hello, gentleman!");
        }
    }
    static class Man extends Human {
        @Override
        void sayHello() {
            System.out.println("hello, lady!");
        }
    }


    public static void main(String[] args) {

        Human h1 = new Man();
        Human h2 = new Women();
        h1.sayHello();
        h2.sayHello();
        System.out.println("================================");

        // 静态类型变化
        h1 = new Women();
        h1.sayHello();

    }
}
