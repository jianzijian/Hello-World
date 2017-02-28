package algorithm_test.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class LT_3Sum {

	public List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> results = new ArrayList<>();
		// ����һ��Ϊ�˹̶���1��ֵ����ͷβ�ƽ���(���ֲ��ұ���)����2��3ֵ��ͬʱҲ��Ϊ�˷����ų��ظ���
		Arrays.sort(nums);
		// i + 2 < nums.length,������Ϊ����3������3Sum��
		for (int i = 0; i + 2 < nums.length; i++) {
			// ������ֻ�е�һ������ǲ��������ظ��ģ�i>0����������ÿһ���ⶼ�п�����֮ǰ�Ľ��ظ�</BR>
			// ���֤��Ч����ͬ�����Ե�����֤����
			if (i > 0 && nums[i] == nums[i - 1]) {
				continue;
			}
			int j = i + 1, k = nums.length - 1;
			while (j < k) {
				if (nums[j] + nums[k] == -nums[i]) {
					results.add(Arrays.asList(nums[i], nums[j], nums[k]));
					// ��һ��2��3ֵ
					j++;
					k--;
					// ���֤��Ч����ͬ�����Ե�����֤����
					while (j < k && nums[j] == nums[j - 1])
						j++;
					// ���֤��Ч����ͬ�����Ե�����֤����
					while (j < k && nums[k] == nums[k + 1])
						k--;
				} else if (nums[j] + nums[k] < -nums[i]) {
					j++;
				} else {
					k--;
				}
			}
		}
		return results;
	}

	private String asString(List<Integer> nums) {
		if (nums.isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		nums.forEach(num -> builder.append(num + ","));
		return builder.substring(0, builder.length() - 1);
	}

	public LT_3Sum() {
		int[] nums = new int[] { -1, 0, 1, 2, -1, -4 };
		List<List<Integer>> results = this.threeSum(nums);
		for (List<Integer> result : results) {
			System.err.println(this.asString(result));
		}
	}

	public static void main(String[] args) {
		new LT_3Sum();
	}

}
