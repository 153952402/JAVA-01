public class Test01_Thread_join {
    private static Integer result = -1;

    /**
     * 方式一：通过全局变量传值，thread.join来进行线程协调
     */
    public static void main(String[] args) throws InterruptedException {
        //启动新线程
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                result = fibo(36);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        //阻塞等待子线程计算结束
        thread.join();
        System.out.println(result);
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
