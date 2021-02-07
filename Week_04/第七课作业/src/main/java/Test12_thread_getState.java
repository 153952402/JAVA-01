import java.util.concurrent.ExecutionException;

public class Test12_thread_getState {


    /**
     * 方式十二：while  配合 thread.getState()
     *
     *
     *
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //启动新线程
        MyTask task = new MyTask(Thread.currentThread());
        Thread thread = new Thread(task);
        thread.start();

        while(!thread.getState().equals(Thread.State.TERMINATED)) {
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = Test12_thread_getState.fibo(36);
        }

        public Integer getResult() {
            return result;
        }

        public void setResult(Integer result) {
            this.result = result;
        }
    }

}
