package cn.concurrency.demo.example.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join框架：是java 7提出的并行执行任务的框架。它是把大任务分割成若干个小任务的，最后将若干个小任务的结果汇总后给大任务返回。
 *
 * 主要采取的工作窃取算法，是指某个线程从其他队列里窃取任务来执行。
 *
 *
 * BlockingQueue 阻塞队列    线程安全的
 *  第一种情况 当队列满了的情况，进行入队列操作
 *  第二种情况 当队列空的时候，进行出队列操作
 *
 * ArrayBlockingQueue：有界的阻塞队列，底层数组,指定容量大小。先进先出
 * DelayQueue：存的是内部元素，去实现一个接口。  定时关闭连接  缓存对象啊 超时处理
 * LinkedBlockingQueue: 如果指定就是有边界的，不指定无边界的。  内部：链表
 *
 *PriorityBlockingQueue：带有优先级的阻塞队列，无界的。但是有排序规则的  允许插入空对象，必须实现Compare 比较接口。
 *synchronousQueue ：只允许插入一个元素。无界非缓存的队列。
 *
 *
 */
public class ForkJoinTaskTest  extends RecursiveTask<Integer> {

    public static final int threshold = 2;

    private int start;
    private int end;

    public ForkJoinTaskTest(int start,int end){
        this.start = start;
        this.end = end;
    }


    @Override
    protected Integer compute() {
        int sum = 0;

        //如果任务足够小就计算任务
        boolean canCompute = (end -start) <= threshold;
        if (canCompute){
            for (int i = start; i<=end; i++){
                sum +=i;
            }
        } else {
            int middle = (start+end) / 2;
            ForkJoinTaskTest left = new ForkJoinTaskTest(start, middle);
            ForkJoinTaskTest reight = new ForkJoinTaskTest(middle+1, end);

            //执行子任务
            left.fork();
            reight.fork();

            //等待任务执行结束合并其结果
            Integer leftres = left.join();
            Integer re = reight.join();
            //合并子任务
            sum = leftres+re;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool joinPool = new ForkJoinPool();
        ForkJoinTaskTest taskTest = new ForkJoinTaskTest(1, 100);

        ForkJoinTask<Integer> result = joinPool.submit(taskTest);

        try {
            Integer integer = result.get();
            System.out.println("结果："+integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
