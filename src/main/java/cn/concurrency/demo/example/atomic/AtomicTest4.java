package cn.concurrency.demo.example.atomic;

import cn.concurrency.demo.annoations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;


@ThreadSafe
public class AtomicTest4 {

    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(0);

    public static void main(String[] args) {
        atomicReference.compareAndSet(0,2);  // 2
        atomicReference.compareAndSet(0,1);  // no   不执行的
        atomicReference.compareAndSet(1,3);  // no
        atomicReference.compareAndSet(2,4);  // 4
        atomicReference.compareAndSet(3,5);  // no
        //原子性
        System.out.println(atomicReference.get());  //4
    }

}
