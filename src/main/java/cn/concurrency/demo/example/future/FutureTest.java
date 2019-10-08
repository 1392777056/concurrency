package cn.concurrency.demo.example.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("do something in callable...");
            Thread.sleep(5000);
            return "Done";
        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(new MyCallable());
        System.out.println("do something in main...");
        Thread.sleep(1000);
        String result = future.get();
        System.out.println("结果："+result);
    }

}
