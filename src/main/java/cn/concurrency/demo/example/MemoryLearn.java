package cn.concurrency.demo.example;

/**
 * java内存模型（java Memory Model）：
 *     规范：规定了一个线程如何和核实可以看到由其他线程修改过后的共享变量的值，以及在必要的情况下如何同步的访问共享变量
 *
 * 可见性：
 * 导致共享变量在线程间不可见的原因
 * 1.线程交叉执行
 * 2.重排序结合线程交叉执行
 * 3.共享变量更新后的值没有在工作内存与主内存间及时更新
 *
 * synchronized可见性 Java内存模型规定：
 * 1.线程解锁前，必须把共享变量的最新值刷新到主内存
 * 2.线程加锁时，将清空工作内存中共享变量的值，从而使用共享变量时需要从主内存中重新读取最新的值（加锁和解锁必须是同一把锁）
 *
 * volatile
 * 通过加入内存屏障和禁止重排序优化来实现
 * 1.对volatile变量写操作的时，会在写操作后加入一条store屏障指令，将本地内存中的共享变量值刷新到住内存中
 * 2.对volatile变量读操作的时，会在读操作前加入一条load屏障指令，从主内存读取共享变量。
 *
 * 有序性：
 *      java内存模型中，允许编译器和处理器对指令进行重排序，但重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性.
 *      volatile  synchronized  lock 都能保证有序性
 *
 * happens before原则------ 来自深入了解JAVA虚拟机
 * 1.程序次序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生与书写在后面的操作
 * 2.锁定规则：一个unLock操作先行发生于后面对同一个锁的lock操作
 * 3.volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作
 * 4.传递规则：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C
 * 5.线程启动规则：Thread对象的start()方法先行发生于此线程的每一个动作
 * 6.线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生
 * 7.线程终结规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值
 * 手段检测到线程已经终止执行
 * 8.对象终结规则：一个对象的初始化完成先行发生于他的finalize()方法的开始。
 *
 *
 */
public class MemoryLearn {
}
