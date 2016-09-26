package algos.queue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by vivek on 24/09/16.
 */
public class CircularQueue {
    private final byte[] data;
    private byte[] extra;

    private int writeStart = 0, readStart = 0, remaining;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.readLock();

    public CircularQueue(int size) {
        this.data = new byte[size];
        remaining = size;
    }

    public void put(byte[] source) throws Exception {
        writeLock.lock();
        try {
            if (source.length > remaining) {
                throw new Exception("OutOfBuffer");
            }

            int i = 0;
            while (i < source.length) {
                if (remaining <= 0) {
                    throw new Exception("OutOfBuffer");
                }
                data[writeStart] = source[i++];
                writeStart++;
                writeStart %= data.length;
                remaining--;
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeLock.unlock();
            throw e;
        }
    }

    private int getRemainingLength() {
        if (writeStart >= readStart)
            return data.length - writeStart + readStart;
        else
            return readStart - writeStart;
    }

    public int read(byte[] target) {
        readLock.lock();
        try {
            int i = 0;
            if (remaining == data.length)
                return -1;

            while (i < target.length) {
                remaining++;
                target[i++] = data[readStart];
                readStart++;
                readStart %= data.length;

                //when do I break?
                if (readStart == writeStart)
                    break;
            }
            return i;

        } catch (Exception e) {
            e.printStackTrace();
            readLock.unlock();
            throw e;
        }
    }
}
