package cn.concurrency.demo.example.atomic;

import cn.concurrency.demo.annoations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder优点：
 *      对于普通类型的long double变量，JVM将64位操作拆成32位的操作，那么LongAdder这个实现是基于的思想：它核心的是将
 * 热点数据分离，比如说将atomicLong的内部核心数据value分成一个数组，每个线程访问时通过Hash等算法预测到，其中一个数字
 * 进行计数，而最终的计数结果呢则为这个数组的求和累加，其中热点数据他会分成多个单元的sel,每个sel独自维护内部的值，当前
 * 对象的实际值由所有的sel的累计合成，这个的话热点就进行了有效的分离，并提高了并行度，这样一来呢LongAdder它相当于是在
 * atomicLong基础上将单点的更新压力分散到各个节点上，在低并发的时候呢通过对bytes的直接更新可以很好的和Atomic的性能基本
 * 一致，而在高并发的时候呢会通过分散提高性能。
 *
 * 缺点：在统计的时候，如果有并发更新，可能会导致统计的数据有些误差。
 *
 * 实际使用：
 *    在处理高并发的时候，我们可以优先使用longAdder，而不是继续使用AtomicLong,  当然在线程很低的情况进行计数，使用AtomicLong
 * 还是更简单更方便效率更高一些，性能也高。  其他的情况下，序列号的生成呀，需要准确的数值全局唯一的AtomicLong才是正确
 * 的选择，这个时候就不适合LongAdder了
 *
 * AtomicLong 和 LongAdder的区别：
 *
 *
 */
@ThreadSafe
public class AtomicTest3 {

    public static int clientTotal = 5000;

    public static int threadPool = 200;

    public static LongAdder count = new LongAdder();

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
        System.out.println("总数："+count);
    }

    private static void add(){
        count.increment();
    }



}
