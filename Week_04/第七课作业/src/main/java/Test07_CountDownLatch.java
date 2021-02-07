import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class Test07_CountDownLatch {


    /**
     * 方式七： 通过CountDownLatch.await进行线程同步
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CountDownLatch cdl = new CountDownLatch(1);
        //启动新线程
        MyTask task = new MyTask(cdl);
        Thread thread = new Thread(task);
        thread.start();
        cdl.await();
        System.out.println(task.getResult());
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    static class  MyTask implements Runnable {
        private CountDownLatch cdl;
        private Integer result = null;

        public MyTask(CountDownLatch cdl) {
            this.cdl = cdl;
        }

        @Override
        public void run() {
            result = Test07_CountDownLatch.fibo(36);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cdl.countDown();
        }

        public Integer getResult() {
            return result;
        }

        public void setResult(Integer result) {
            this.result = result;
        }
    }

}
