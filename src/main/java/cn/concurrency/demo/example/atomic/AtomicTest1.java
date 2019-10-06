package cn.concurrency.demo.example.atomic;

import cn.concurrency.demo.annoations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class AtomicTest1 {

    public static int clientTotal = 5000;

    public static int threadPool = 200;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadPool);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++){
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("总数："+count.get());
    }

    private static void add(){
        //他两的区别：
        //    一个先做增加操作,之后在获取当前的一个值
        //    先获取当前值，之后再进行增加操作
       count.incrementAndGet();//这个我们在接收返回值的时候有影响，而单独做+1的操作是不影响的。
       //count.getAndIncrement();

    }



}
