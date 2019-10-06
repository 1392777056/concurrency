package cn.concurrency.demo.example.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * AtomicBoolean：
 *      因为它是原子性操作，false变成true，只会执行一次，后面的4999次 都是因为它是true都不会执行
 *例子演示了：如何让某次代码只执行一次 并不会重复，在实际中可能会遇到有些流程只执行一遍，就可以参考这样的例子处理。
 */
public class AtomicBoolean6 {

    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);


    public static int clientTotal = 5000;

    public static int threadPool = 200;

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadPool);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++){
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("result:"+atomicBoolean.get());

    }

    private static void test(){
        if (atomicBoolean.compareAndSet(false,true)){
            System.out.println("execute");
        }
    }

}
