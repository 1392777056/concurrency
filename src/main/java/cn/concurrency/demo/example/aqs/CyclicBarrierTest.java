package cn.concurrency.demo.example.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

    //设一个值，决定多少个线程一起等待,第二个参数runnable在屏障之后优先执行
    //private static CyclicBarrier barrier = new CyclicBarrier(5);
    private static CyclicBarrier barrier = new CyclicBarrier(5,()->{
        System.out.println("优先执行这里的runnable");
    });

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(()->{
                try {
                    rece(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void rece(int threadNum) throws Exception {
        Thread.sleep(1000);
        System.out.println(threadNum+"===con");
        //到达指定的值次数之后呢就开始执行后面的代码
        barrier.await();
        System.out.println(threadNum+"==fffff");
    }

}
