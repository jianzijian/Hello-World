package forkjoinpool_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class Test1 {

	public static void main(String[] args) {
		new Test1();
	}

	public Test1() {
		int[] array = new int[100000];
		Arrays.fill(array, 1);
		MyRecursiveTask task = new MyRecursiveTask(array, 0, array.length);
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			return task.compute();
		});
		future.thenAccept(System.out::println);
		try {
			Thread.sleep(100000);
		} catch (Exception e2) {
			// TODO: handle exception
		}
		// ForkJoinPool pool = ForkJoinPool.commonPool();
		// pool.submit(task);
		// System.out.println(task.join());
		// pool.shutdown();
	}

	private class MyRecursiveTask extends RecursiveTask<Integer> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4791986149912565596L;

		private static final int CALCULATE_SIZE = 10000;
		private final int[] array;
		private final int beginIndex;
		private final int endIndex;

		public MyRecursiveTask(int[] array, int beginIndex, int endIndex) {
			super();
			this.array = array;
			this.beginIndex = beginIndex;
			this.endIndex = endIndex;
		}

		@Override
		protected Integer compute() {
			if (endIndex - beginIndex <= CALCULATE_SIZE) {// �������֧�������ļ�����
				System.out.println("Child Thread In");
				return Arrays.stream(array, beginIndex, endIndex).sum();
			} else {// ������ִ�з�֧���������ְ�ܾ���1���ָ�������2������������Ľ������ν��һ���߳��ռ�������Ľ�������߳���ʵ���Ǵ�������̣߳�
				System.out.println("Main Thread In");
				// ������ָ�
				List<MyRecursiveTask> allChildTasks = new ArrayList<>();
				for (int bein = beginIndex; bein < endIndex; bein += CALCULATE_SIZE) {
					int end = bein + CALCULATE_SIZE;
					end = end >= endIndex ? endIndex : end;
					allChildTasks.add(new MyRecursiveTask(array, bein, end));
				}
				// �������ύ
				allChildTasks.stream().forEach(ForkJoinTask::fork);
				// ��ȡ������Ľ���㱾����Ľ�
				return allChildTasks.stream().reduce(0, (total, task) -> total + task.join(),
						(total1, total2) -> total1 + total2);
			}
		}

	}

}
