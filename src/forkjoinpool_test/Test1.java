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
			if (endIndex - beginIndex <= CALCULATE_SIZE) {// 子任务分支，真正的计算者
				System.out.println("Child Thread In");
				return Arrays.stream(array, beginIndex, endIndex).sum();
			} else {// 大任务执行分支，大任务的职能就是1、分割子任务；2、汇总子任务的结果（所谓开一个线程收集子任务的结果，该线程其实就是大任务的线程）
				System.out.println("Main Thread In");
				// 子任务分割
				List<MyRecursiveTask> allChildTasks = new ArrayList<>();
				for (int bein = beginIndex; bein < endIndex; bein += CALCULATE_SIZE) {
					int end = bein + CALCULATE_SIZE;
					end = end >= endIndex ? endIndex : end;
					allChildTasks.add(new MyRecursiveTask(array, bein, end));
				}
				// 子任务提交
				allChildTasks.stream().forEach(ForkJoinTask::fork);
				// 提取子任务的解计算本任务的解
				return allChildTasks.stream().reduce(0, (total, task) -> total + task.join(),
						(total1, total2) -> total1 + total2);
			}
		}

	}

}
