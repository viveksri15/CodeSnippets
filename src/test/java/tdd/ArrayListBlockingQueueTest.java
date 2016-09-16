package tdd;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.concurrent.*;

/**
 * Created by viveksrivastava on 10/05/16.
 */
public class ArrayListBlockingQueueTest extends TestCase {
	public void testPut() throws Exception {
		BlockingQueue<Integer> integerBlockingQueue = new ArrayListBlockingQueue<>(3);
		integerBlockingQueue.put(1);
		integerBlockingQueue.put(2);
		integerBlockingQueue.put(3);
		Assert.assertEquals(3, integerBlockingQueue.getSize());
	}

	public void testGet() throws InterruptedException {
		final BlockingQueue<Integer> integerBlockingQueue = new ArrayListBlockingQueue<>(3);
		integerBlockingQueue.put(1);
		integerBlockingQueue.put(2);
		integerBlockingQueue.put(3);
		integerBlockingQueue.get();
		integerBlockingQueue.get();
		integerBlockingQueue.get();
		Assert.assertEquals(0, integerBlockingQueue.getSize());
	}

	public void testPutTimeout() throws Exception {
		final BlockingQueue<Integer> integerBlockingQueue = new ArrayListBlockingQueue<>(1);
		integerBlockingQueue.put(1);
		final ExecutorService executorService = Executors.newSingleThreadExecutor();
		final long t0 = System.currentTimeMillis();
		Future<?> submit = executorService.submit(new Runnable() {
			@Override
			public void run() {
				integerBlockingQueue.put(2);
			}
		});
		Throwable ex = null;
		try {
			submit.get(1, TimeUnit.SECONDS);
		} catch (ExecutionException | TimeoutException e) {
			ex = e;
		}
		assertTrue(ex instanceof TimeoutException);
	}

	public void testGetTimeOut() throws InterruptedException {
		final BlockingQueue<Integer> integerBlockingQueue = new ArrayListBlockingQueue<>(3);
		final ExecutorService executorService = Executors.newSingleThreadExecutor();
		final long t0 = System.currentTimeMillis();
		Future<?> submit = executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					integerBlockingQueue.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Throwable ex = null;
		try {
			submit.get(1, TimeUnit.SECONDS);
		} catch (ExecutionException | TimeoutException e) {
			ex = e;
		}
		assertTrue(ex instanceof TimeoutException);
	}
}