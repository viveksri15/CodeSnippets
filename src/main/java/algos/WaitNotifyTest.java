package algos;

/**
 * Created by viveksrivastava on 05/08/15.
 */
public class WaitNotifyTest {
	public static void main(String[] args) throws InterruptedException {
		A adapter = new A();

		new Thread(adapter).start();

		Thread.sleep(5000);

		adapter.setData("Jhangu");
	}

	public static class A implements Runnable {
		private String data = null;

		public void setData(String data) {
			this.data = data;
			synchronized (this) {
				notify();
			}
		}

		public void run() {
			System.out.println("data = " + data);
			try {
				synchronized (this) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("new data = " + data);
		}
	}
}
