import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

public class Test10_Thread_isAlive {


    /**
     * 方式十： while 配合 Thread.isAlive判断是否计算完成
     *
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //启动新线程
        MyTask task = new MyTask(Thread.currentThread());
        Thread thread = new Thread(task);
        thread.start();

        while(thread.isAlive()) {
            Thread.yield();
        }
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
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = Test10_Thread_isAlive.fibo(36);
        }

        public Integer getResult() {
            return result;
        }

        public void setResult(Integer result) {
            this.result = result;
        }
    }

}
