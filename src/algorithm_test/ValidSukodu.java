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

	// �����㷨

	/**
	 * ˳����ң��������У�ʱ�临�Ӷ�O(n)
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
	 * �۰���ң��������У�ʱ�临�Ӷ�O(logN) �ӷ�+����
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
	 * ��ֵ���ң���������(�ֲ�����)��ʱ�临�Ӷ�O(log 2 (log 2 n))
	 * �۰������ʵ����ֵ���ң�middle=(low+high)/2=low+1/2(high-low)�����ұȹ̶���1/2����ֵ����������Ӧ�Ե�
	 * ����˵���ֵ���ֵ�ʱ��apple���ǻ����ǰ��ҳ��zoo���ǻ���Һ�ҳ��������1/2����ֵ��������Ҫ������֪��a��z��26����ĸ����˳��
	 * ��λ�ñ��� ������ȵģ����ǿ��԰����1/2ϵ�����������Ӧ�Ե�ֵռ��(key-args[low])/(args[high]-args[low])
	 * ��������
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
	 * 쳲��������ң��������У�ʱ�临�ӵ�O(log 2 n) ���۰�������ƣ�ֻ�ǲ��ҵ�ѡ��ͬ���ƽ�ָ�㣩 �ӷ�+����
	 */
	private boolean fbnqSearch(int[] orderedArgs, int target) {
		// �ȹ���һ��쳲���������
		int[] fbnq = new int[orderedArgs.length];
		fbnq[0] = 1;
		fbnq[1] = 1;
		for (int i = 2; i < fbnq.length; i++) {
			fbnq[i] = fbnq[i - 1] + fbnq[i - 2]; // �ӵ�3λ��ʼ��f[n]=f[n-1]+f[n-2]
		}
		// ������������鳤������Ӧ��쳲�������
		int k = 0;
		while (fbnq[k] < orderedArgs.length) {
			k++;
		}
		/*
		 * ��������
		 * ���е�������F(k)-1����ʹ��midֵ���зָ����õ�һ������ôʣ��F(k)-2�������÷ָ����������У�ÿ�������еĸ����ֱ���F(k-1)
		 * -1��F(k-2)-1������ʽ����֮ǰ��ͳһ�ġ���Ȼ�Ļ���ÿ�������е�Ԫ�ظ����п�����F(k-1)��F(k-1)-1��F(k-2)��F(k
		 * -2)-1����д�����ǳ��鷳
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
			int middle = low + fbnq[k - 1] - 1; // ÿ�εĲ��ҵ㶼������ƽ�ָ��
			if (target == array[middle]) {
				System.out.println("fbnqSearch search count:" + count);
				return true;
			} else if (array[middle] < target) {
				low = middle + 1;
				k -= 2; // �ұ�����[mid+1,high]=f[k-2]-1
			} else {
				high = middle - 1;
				k -= 1; // �������[low,mid-1]=f[k-1]-1
			}
		}
		return false;
	}

}
