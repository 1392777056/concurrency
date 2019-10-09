package cn.concurrency.demo.example.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池的好处：
 * 1.重用存在的线程，减少对象创建、消亡的开销，性能佳。
 * 2.可有效控制最大并发线程数，提高系统资源利用率，同时可有避免过多资源竞争，避免阻塞。
 * 3.提供定时执行、定期执行、单线程、并发数控制等功能。
 *
 * ThreadPoolExecutor:
 * corePoolSize : 核心线程数量
 * maximumPoolSize: 线程最大线程数
 * workQueue: 阻塞队列，存储等待执行的任务，很重要，会对线程池运行过程产生重大影响。
 * keepAliveTime: 线程没有任务执行时最多保持多久时间终止
 * unit: 时间单位
 *
 * threadFactory:线程工厂，创建线程
 * rejectHandler: 当拒绝处理任务的策略
 *
 * 线程池的状态：
 * Running: 能接受新提交的任务，也能处理阻塞中的任务。
 * shutdown: 关闭状态，不能再接受新提交的任务，但是可以继续处理阻塞队列中已经保存的任务。
 * stop: 关闭状态，也不接受新的任务，也不处理队列中的任务。中断正在处理任务的线程。
 * tidying: 任务已经终止了
 * terminated 最后的状态
 *
 *
 * execute():提交任务，交给线程池执行。
 * submit(): 提交任务，有返回的结果。 相当于execute+Future
 *
 * shutDown(): 关闭线程池，等待任务都执行完。
 * shutDownNow(): 关闭线程池，不在等待任务执行完。
 *
 * getTaskCount(): 线程池已执行和未执行的任务总数
 * getCompletedTaskCount(): 已完成的任务数量
 * getPoolSize(): 线程池当前的线程数量
 * getActiveCount(): 当前线程池中正在执行的任务线程数量
 *
 * 线程池 --- 合理配置：
 * CPU密集型任务，就需要尽量压榨CPU，参考值可以设为NCPU+1
 * IO密集型任务，参考值可以设置为2*NCPU
 *
 */
public class ThreadPoolTest {

}
