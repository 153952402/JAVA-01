import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test06_CompletableFuture_supplyAsync {

    /**
     * 方式六：通过CompletableFuture.supplyAsync，直接提交任务异步直行，通过get方法获得返回值
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //启动新线程异步执行任务
        Integer result = CompletableFuture.supplyAsync(() -> {
            return fibo(36);
        }).get();

        System.out.println(result);
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
