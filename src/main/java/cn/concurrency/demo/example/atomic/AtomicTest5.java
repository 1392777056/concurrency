package cn.concurrency.demo.example.atomic;

import cn.concurrency.demo.annoations.ThreadSafe;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;


@ThreadSafe
public class AtomicTest5 {

    /**
     * 原子性去更新某一个类的实例的字段时，而且这个字段必要求通过volatile修饰，并且不能为static才可以，不过这个用的少。
     */
    private static AtomicIntegerFieldUpdater<AtomicTest5> cout = AtomicIntegerFieldUpdater.newUpdater(AtomicTest5.class,"ceshi");

    //首先对象的字段必须要用volatile关键字修饰
    private volatile int ceshi = 10;

    public int getCeshi() {
        return ceshi;
    }

    private static AtomicTest5 atomicTest5 = new AtomicTest5();

    public static void main(String[] args) {
        if (cout.compareAndSet(atomicTest5,10,20)){
            System.out.println("结果1："+ atomicTest5.getCeshi());
        }
        if (cout.compareAndSet(atomicTest5,10,80)){
            System.out.println("结果2："+ atomicTest5.getCeshi());
        } else{
            System.out.println(atomicTest5.getCeshi());
        }

    }

}
