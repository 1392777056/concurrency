package cn.concurrency.demo.example.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static void main(String[] args) throws Exception {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("do something in callable...");
                Thread.sleep(5000);
                return "Done";
            }
        });

        new Thread(futureTask).start();

        System.out.println("do something in main...");

        Thread.sleep(1000);

        String result = futureTask.get();
        System.out.println("结果："+result);
    }
}
