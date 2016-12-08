package algorithm_test;

class ValidSukodu {

	public static void main(String[] args) {
		new ValidSukodu();
	}

	public ValidSukodu() {
		int[] orderedArgs = new int[] { 1, 2, 4, 1000, 1230, 10000, 200000, 400000 };
		int target = 200000;
		System.out.println(this.orderedSearch(orderedArgs, target));
		System.out.println(this.halfSearch(orderedArgs, target));
		System.out.println(this.insertSearch(orderedArgs, target));
		System.out.println(this.fbnqSearch(orderedArgs, target));
	}

	public boolean isValidSudoku(char[][] board) {

		return false;
	}

	// 查找算法

	/**
	 * 顺序查找，无序序列，时间复杂度O(n)
	 */
	private boolean orderedSearch(int[] args, int target) {
		int count = 0;
		for (int arg : args) {
			count++;
			if (arg == target) {
				System.out.println("orderedSearch search count:" + count);
				return true;
			}
		}
		return false;
	}

	/**
	 * 折半查找，有序序列，时间复杂度O(logN) 加法+除法
	 */
	private boolean halfSearch(int[] orderedArgs, int target) {
		int count = 0;
		int start = 0, end = orderedArgs.length - 1;
		while (start <= end) {
			count++;
			int middle = (start + end) / 2;
			if (orderedArgs[middle] == target) {
				System.out.println("halfSearch search count:" + count);
				return true;
			} else if (orderedArgs[middle] < target) {
				start = middle + 1;
			} else {
				end = middle - 1;
			}
		}
		return false;
	}

	/**
	 * 插值查找，有序序列(分布均匀)，时间复杂度O(log 2 (log 2 n))
	 * 折半查找其实是中值查找，middle=(low+high)/2=low+1/2(high-low)，查找比固定是1/2（中值）不是自适应性的
	 * 比如说，字典查字的时候，apple我们会查找前几页，zoo我们会查找后几页，而不是1/2（中值），这主要是我们知道a、z在26个字母（有顺序）
	 * 的位置比例 所以类比的，我们可以把这个1/2系数改造成自适应性的值占比(key-args[low])/(args[high]-args[low])
	 * 四则运算
	 */
	private boolean insertSearch(int[] orderedArgs, int target) {
		int count = 0;
		int start = 0, end = orderedArgs.length - 1;
		while (start <= end) {
			count++;
			double rate = ((double) target - orderedArgs[start]) / (orderedArgs[end] - orderedArgs[start]);
			int tarIndex = (int) (start + Math.ceil(rate * (end - start)));
			if (orderedArgs[tarIndex] == target) {
				System.out.println("insertSearch search count:" + count);
				return true;
			} else if (orderedArgs[tarIndex] < target) {
				start = tarIndex + 1;
			} else {
				end = tarIndex - 1;
			}
		}
		return false;
	}

	/**
	 * 斐波那契查找，有序序列，时间复杂的O(log 2 n) 跟折半查找类似，只是查找点选择不同（黄金分割点） 加法+减法
	 */
	private boolean fbnqSearch(int[] orderedArgs, int target) {
		// 先构造一个斐波那契数组
		int[] fbnq = new int[orderedArgs.length];
		fbnq[0] = 1;
		fbnq[1] = 1;
		for (int i = 2; i < fbnq.length; i++) {
			fbnq[i] = fbnq[i - 1] + fbnq[i - 2]; // 从第3位开始，f[n]=f[n-1]+f[n-2]
		}
		// 计算待查找数组长度所对应的斐波那契数
		int k = 0;
		while (fbnq[k] < orderedArgs.length) {
			k++;
		}
		/*
		 * 扩容数组
		 * 表中的数据是F(k)-1个，使用mid值进行分割又用掉一个，那么剩下F(k)-2个。正好分给两个子序列，每个子序列的个数分别是F(k-1)
		 * -1与F(k-2)-1个，格式上与之前是统一的。不然的话，每个子序列的元素个数有可能是F(k-1)，F(k-1)-1，F(k-2)，F(k
		 * -2)-1个，写程序会非常麻烦
		 */
		int[] array = new int[fbnq[k] - 1];
		for (int i = 0; i < array.length; i++) {
			if (i >= orderedArgs.length) {
				array[i] = orderedArgs[orderedArgs.length - 1];
				continue;
			}
			array[i] = orderedArgs[i];
		}
		int count = 0;
		int low = 0, high = array.length - 1;
		while (low <= high) {
			count++;
			int middle = low + fbnq[k - 1] - 1; // 每次的查找点都是最靠近黄金分割点
			if (target == array[middle]) {
				System.out.println("fbnqSearch search count:" + count);
				return true;
			} else if (array[middle] < target) {
				low = middle + 1;
				k -= 2; // 右边序列[mid+1,high]=f[k-2]-1
			} else {
				high = middle - 1;
				k -= 1; // 左边序列[low,mid-1]=f[k-1]-1
			}
		}
		return false;
	}

}
