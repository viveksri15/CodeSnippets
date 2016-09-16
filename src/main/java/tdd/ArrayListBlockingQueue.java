package tdd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by viveksrivastava on 10/05/16.
 */
public class ArrayListBlockingQueue<T> implements BlockingQueue<T> {

	private final List<T> tList = new ArrayList<>();
	private ReentrantLock reentrantLock = new ReentrantLock(true);
	private Condition waitEmpty = reentrantLock.newCondition();
	private Condition waitFull = reentrantLock.newCondition();
	private int size;

	public ArrayListBlockingQueue(int size) {
		this.size = size;
	}

	@Override
	public void put(T o) {
		try {
			reentrantLock.lock();

			if (tList.size() == size)
				waitEmpty.await();

			synchronized (tList) {
				tList.add(o);
			}
			waitFull.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			reentrantLock.unlock();
		}
	}

	@Override
	public T get() throws InterruptedException {
		try {
			reentrantLock.lock();

			if (tList.size() == 0)
				waitFull.await();

			T headObj = null;
			synchronized (tList) {
				headObj = tList.remove(0);
			}
			waitEmpty.signal();
			return headObj;
		} finally {
			reentrantLock.unlock();
		}
	}

	@Override
	public int getSize() {
		return tList.size();
	}
}
