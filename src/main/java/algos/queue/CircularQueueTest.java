package algos.queue;

/**
 * Created by vivek on 24/09/16.
 */
public class CircularQueueTest {
    @org.junit.Test
    public void testCircularQueue() throws Exception {
        CircularQueue circularQueue = new CircularQueue(10);
        circularQueue.put("hellohell".getBytes());
        byte[] target = new byte[10];
        int read = circularQueue.read(target);
        byte[] data = new byte[read];
        System.arraycopy(target, 0, data, 0, read);
        System.out.println(new String(data));
        circularQueue.put("hi".getBytes());
        read = circularQueue.read(target);
        System.out.println(read);
    }
}