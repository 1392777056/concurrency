package cn.concurrency.demo.example.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 和  synchronized 锁区别：
 * 1.可重入性（必须等状态为0才能释放锁，否则可能还会有重入锁）
 * 2.锁的实现（一个是jdk，一个是jvm）
 * 3.性能的区别（自从synchronized引入了偏向锁，自旋锁，两者的性能都差不多，官方更建议使用synchronized）  使用了CAS技术
 * 4.功能的区别 synchronized比较方便简洁，ReentrantLock需要手动加锁释放锁    锁的细粒度方面 ReentrantLock优于synchronized
 *
 *
 * ReentrantLock独有的功能：
 * 可指定是公平锁还是非公平锁（就是先等待的线程先获得锁，选择公平还是不公平）
 * 提供了一个Condition类，可以分组唤醒需要唤醒的线程
 * 提供能够中断等待锁的线程的机制  lock.lockInterruptibly()  其实是自旋锁，循环调用CAS的操作实现加锁
 */
public class LockTest1 {

    public static int clientTotal = 5000;

    public static int threadPool = 200;

    public static int count = 0;

    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadPool);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        add();
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("总数："+count);
    }


    private static void add(){
        lock.lock();
        try {
            count++;
        }finally {
            lock.unlock();
        }

    }



}
