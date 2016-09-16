package algos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by viveksrivastava on 15/04/16.
 */
public class BoundedBuffer {
    final ReentrantLock lock = new ReentrantLock();
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[10];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        System.out.println("threadName0 = " + Thread.currentThread().getName() + " " + lock.getHoldCount() + " " + count);
        try {
            while (count == items.length) {
                System.out.println("Waiting...");
                notFull.await();
                System.out.println("Waiting Complete...");
            }
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        System.out.println("threadName = " + Thread.currentThread().getName() + " " + lock.isHeldByCurrentThread());
        try {
            while (count == 0)
                notEmpty.await();
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final BoundedBuffer boundedBuffer = new BoundedBuffer();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 11; i++)
                        boundedBuffer.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread0");
        thread.start();
        Thread.sleep(1000);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("thread1 Lock= " + boundedBuffer.lock.isHeldByCurrentThread());
                    Object take = boundedBuffer.take();
                    System.out.println("take = " + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1");
        thread1.start();
    }
}
