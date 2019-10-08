package cn.concurrency.demo.example.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 等待线程，唤醒
 *
 */
public class LockTest2 {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(()->{
            try {
                reentrantLock.lock();
                System.out.println("等待信号");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("得到信号");
            reentrantLock.unlock();

        }).start();

        new Thread(()->{
            reentrantLock.lock();
            System.out.println("get lock");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            condition.signalAll();
            System.out.println("发送信号");
            reentrantLock.unlock();

        }).start();


    }

}
