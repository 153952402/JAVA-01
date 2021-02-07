import java.util.concurrent.*;

public class Test05_Callable_FutureTask {

    /**
     * 方式五：通过callable 接口、 FutureTask启动新线程，其get方法拿到返回值
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //启动新线程
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() {
                Test05_Callable_FutureTask test = new Test05_Callable_FutureTask();
                return test.fibo(36);
            }
        };

        FutureTask<Integer> futureTask = new FutureTask<>(task);
        Thread thread = new Thread(futureTask);
        thread.start();
        Integer result = futureTask.get();
        System.out.println(result);
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
