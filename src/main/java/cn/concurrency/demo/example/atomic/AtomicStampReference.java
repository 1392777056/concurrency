package cn.concurrency.demo.example.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 什么是ABA问题？
 *      ABA问题是指它在CAS操作的时候，其他线程将变量的值A改成了B，但是又改回来了，本线程期望值A与当前变量想比较的时候
 * 发现A没有变，于是CAS就将A值进行了交换操作，这个时候呢其实上改值被其他线程改变过，这与设计思想是不符合的。
 *
 * 因此呢ABA问题的解决思路：
 *       每次变量更新的时候，把变量版本号+1,那么之前那个A改成B，再改成A就变成了 A1  B2  C3版本，这个对某一个版本修改过
 * 该变量对应的版本号就会发生递增的变化，从而解决了ABA问题。
 *
 * 主要解决：ABA的问题
 */
public class AtomicStampReference {

    private static AtomicStampedReference<Integer> atomic = new AtomicStampedReference<>(100,0);

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                //和乐观相似，第一个stamp相当于version每次加1，如果别的线程修改过就是1
                boolean success = atomic.compareAndSet(100, 101, atomic.getStamp(), atomic.getStamp() + 1);
                System.out.println("one" + success);
                success = atomic.compareAndSet(101, 100, atomic.getStamp(), atomic.getStamp() + 1);
                System.out.println("one" + success);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {

            try {
                int stamp = atomic.getStamp();
                // 这里获取到的stamp是1
                System.out.println("before: " + stamp);
                TimeUnit.SECONDS.sleep(2);
                int stamp1 = atomic.getStamp();
                //这里的第一个线程stamp已经变成了2了
                System.out.println("第一个线程stamp:" + stamp1);
                //和乐观相似,第一个stamp相当于 version 每次加1 如果别的线程修改过就是1了.第一个线程0就对比不成功
                boolean b = atomic.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println("线程2=" + b);
                atomic.compareAndSet(101, 100, stamp, stamp + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        t1.start();
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
