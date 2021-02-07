public class Test02_Synchronized_wait_nofity {
    private static Object lock = new Object();
    private static Integer result = -1;

    /**
     * 方式二：通过全局变量传值，wait来进行线程同步
     */
    public static void main(String[] args) throws InterruptedException {
        //启动新线程
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Test02_Synchronized_wait_nofity test1 = new Test02_Synchronized_wait_nofity();
                synchronized (lock) {
                    Test02_Synchronized_wait_nofity.result = test1.fibo(36);
                    lock.notifyAll();
                }
            }
        };
        Thread thread = new Thread(runnable);

        synchronized (lock) {
            thread.start();
            //wait 释放锁，等待子线程执行完成，进行唤醒
            lock.wait();
        }
        System.out.println(result);
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
