package in.viveksrivastava.concurrent;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vivek on 15-04-2014.
 */
public class CompletionServiceTest<T> {

    private ExecutorService executorService = null;

    private CompletionService<T> completionService = null;

    public CompletionServiceTest() {
        executorService = Executors.newFixedThreadPool(10);
        completionService = new ExecutorCompletionService<T>(executorService);
    }

    public void add(Callable<T> callable) {
        completionService.submit(callable);
    }

    public T get() throws InterruptedException, ExecutionException {
        Future<T> result = completionService.take();
        if (result == null)
            return null;
        return result.get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletionServiceTest<String> stringCompletionService = new CompletionServiceTest<String>();
        final AtomicInteger i = new AtomicInteger(0);
        for (int j = 0; j < 100; j++) {
            stringCompletionService.add(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000));
                    Integer x = i.addAndGet(1);
                    if (x % 2 == 0) {
                        Thread.sleep(5000);
                    }
                    return x.toString();
                }
            });
        }
        String s;
        while ((s = stringCompletionService.get()) != null) {
            System.out.println("s = " + s);
        }
    }
}