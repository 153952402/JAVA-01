import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test03_Lock_Conditon {
    private static Integer result = -1;

    public static Lock lock = new ReentrantLock();
    public static Condition finish = lock.newCondition();

    /**
     * 方式三：通过全局变量传值，显示锁来进行线程同步
     */
    public static void main(String[] args) throws InterruptedException {
        //启动新线程
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                result = fibo(36);
                finish.signal();
                lock.unlock();
            }
        };
        Thread thread = new Thread(runnable);

        //wait 等待唤醒
        lock.lock();
            thread.start();
            finish.await();
        lock.unlock();
        System.out.println(result);
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
