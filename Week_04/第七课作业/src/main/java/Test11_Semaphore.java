import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class Test11_Semaphore {
    private static Integer result;

    /**
     * 方式十一： 通过 静态字段传值，Semaphore 进行线程同步
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, BrokenBarrierException {
        Semaphore semaphore = new Semaphore(0);
        MyTask task = new MyTask(semaphore);
        Thread thread = new Thread(task);
        thread.start();
        semaphore.acquire();
        System.out.println(result);
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    static class  MyTask implements Runnable {
        private Semaphore semaphore;

        public MyTask(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            Test11_Semaphore.result = Test11_Semaphore.fibo(36);
            try {
                Thread.sleep(2000);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
