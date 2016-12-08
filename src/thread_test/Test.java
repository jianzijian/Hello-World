package thread_test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) {
		new Test();
	}

	public Test() {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleWithFixedDelay(new Task(), 0, 1000, TimeUnit.SECONDS);
	}

	private class Task implements Runnable {
		@Override
		public void run() {
			System.out.println("123");
			throw new RuntimeException("Task fail!");
		}
	}

}
