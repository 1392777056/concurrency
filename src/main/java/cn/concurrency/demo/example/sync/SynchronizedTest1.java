package cn.concurrency.demo.example.sync;

import cn.concurrency.demo.annoations.ThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 原子性呢，提供了互斥访问，同一时刻只能由同一个线程去对它进行操作
 *
 * synchronized：依赖JVM(作用对象的范围内)
 * lock：依赖特殊的CPU指令，代码实现，ReentrantLock (可重入锁)
 *
 * synchronized:
 * 修饰代码块：大括号括起来的代码，作用于调用的对象
 * 修饰方法: 整个方法，作用于调用的对象
 * 修饰静态方法： 整个静态方法， 作用于 这个类的所有对象
 * 修饰类： 括号括起来的部分，  作用于 整个对象
 *
 * 注： 小知识点
 * 如果此类是父类的时候，父类方法带有synchronized关键字 子类重写是没有这个关键字的，因为方法没有声明，需要手动声明。
 *
 * synchronized AND Lock 对比：
 *
 * synchronized: 不可中断锁，适合竞争不激烈，可读性好
 * lock：可中断锁，多样化同步，竞争激烈时，能维持常态
 * Atomic：竞争激烈时能维持常态，比lock性能好；只能同步一个值
 */
@ThreadSafe
public class SynchronizedTest1 {

    // 同步代码块
    /*public void test1(){
        synchronized (this){
            for (int i = 0; i < 10; i++) {
                System.out.println("test1=="+i);
            }
        }
    }

    // 同步方法
    public synchronized void test2(){
        for (int i = 0; i < 10; i++) {
            System.out.println("test2=="+i);
        }
    }*/

    //同步类
    public static void test1(){
        synchronized (SynchronizedTest1.class){
            for (int i = 0; i < 10; i++) {
                System.out.println("test1=="+i);
            }
        }
    }

    // 同步静态方法
    public static synchronized void test2(){
        for (int i = 0; i < 10; i++) {
            System.out.println("test2=="+i);
        }
    }

    public static void main(String[] args) {

        SynchronizedTest1 test1 = new SynchronizedTest1();
        SynchronizedTest1 test2 = new SynchronizedTest1();

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            test1.test2();
        });
        service.execute(()->{
            test2.test2();
        });

        service.shutdown();
    }

}
