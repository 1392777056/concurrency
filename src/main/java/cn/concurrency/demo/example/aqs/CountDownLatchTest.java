package cn.concurrency.demo.example.aqs;

import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

    private final static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService service = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            service.execute(()->{
                try {
                    test(threadNum);
                } catch (Exception e) {
                    System.out.println("出现异常");
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        //也可以设置时间，超过时间就不等待了，直接先执行下面的代码
        //countDownLatch.await(10, TimeUnit.MILLISECONDS);
        System.out.println("finish");
        service.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        System.out.println(threadNum);
        Thread.sleep(100);
    }

}
