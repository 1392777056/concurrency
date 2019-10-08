package cn.concurrency.demo.example.aqs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    private final static int threadCount = 20;

    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            service.execute(() -> {
                try {
                    //获取一个许可或者多个许可
                    //semaphore.acquire(2);
                    //semaphore.tryAcquire()  尝试获取一个许可
                    semaphore.acquire();
                    test(threadNum);
                    //释放一个许可或者多个许可
                    semaphore.release();
                    //semaphore.release(2);
                } catch (Exception e) {
                    System.out.println("出现异常");
                    e.printStackTrace();
                }
            });
        }
    }

    private static void test(int threadNum) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        System.out.println(simpleDateFormat.format(new Date())+"----"+threadNum);
        Thread.sleep(1000);
    }


}
