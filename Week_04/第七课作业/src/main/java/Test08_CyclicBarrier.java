import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;

public class Test08_CyclicBarrier {
    private static Integer result;

    /**
     * 方式八： 通过 静态字段传值，CyclicBarrier 进行线程同步
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, BrokenBarrierException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("done");
            }
        });
        MyTask task = new MyTask(cyclicBarrier);
        Thread thread = new Thread(task);
        thread.start();
        cyclicBarrier.await();
        System.out.println(result);
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    static class  MyTask implements Runnable {
        private CyclicBarrier cb;

        public MyTask(CyclicBarrier cb) {
            this.cb = cb;
        }

        @Override
        public void run() {
            Test08_CyclicBarrier.result = Test08_CyclicBarrier.fibo(36);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

}
