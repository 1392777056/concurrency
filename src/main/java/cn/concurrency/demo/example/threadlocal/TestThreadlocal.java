package cn.concurrency.demo.example.threadlocal;

public class TestThreadlocal {

    private final static ThreadLocal<Long> num =  new ThreadLocal<>();

    public static void add(Long id){
        num.set(id);
    }

    public static Long get(){
        return num.get();
    }

    public static void remove(){
        num.remove();
    }

}
