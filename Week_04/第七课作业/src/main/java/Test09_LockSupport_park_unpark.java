import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

public class Test09_LockSupport_park_unpark {


    /**
     * 方式九： 通过LockSupport.park 和 LockSupport.unpack 进行线程同步
     *          unpark可以先于park之前执行，相当于提前提供一个许可，
     *          当线程被park时，已经有许可，则无需等待，可以直接继续运行
     *          注意：unpark的许可是无法叠加的，且是一次性的
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //启动新线程
        MyTask task = new MyTask(Thread.currentThread());
        Thread thread = new Thread(task);
        thread.start();
        //当前线程放弃CPU，等待继续运行的许可
        LockSupport.park();
        System.out.println(task.getResult());
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

    static class  MyTask implements Runnable {
        private Thread mainThread;
        private Integer result = null;

        public MyTask(Thread thread) {
            this.mainThread = thread;
        }

        @Override
        public void run() {
            result = Test09_LockSupport_park_unpark.fibo(36);
            //
            LockSupport.unpark(mainThread);
            System.out.println(result);
        }

        public Integer getResult() {
            return result;
        }

        public void setResult(Integer result) {
            this.result = result;
        }
    }

}
