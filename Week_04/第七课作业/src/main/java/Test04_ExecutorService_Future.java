import java.util.concurrent.*;

public class Test04_ExecutorService_Future {

    /**
     * 方式四：通过线程池submit方法提交callable实例，返回Future对象，其get方法拿到返回值
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //启动新线程
        Callable<Integer> task = new Callable<Integer>() {
            @Override
            public Integer call() {
                Test04_ExecutorService_Future test = new Test04_ExecutorService_Future();
                return test.fibo(36);
            }
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(task);
        Integer result = future.get();
        System.out.println(result);
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
