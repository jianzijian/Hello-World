package algorithm_test.datastructure_test;

import java.util.Arrays;

/**
 * 有序数组，查找插入时间复杂度看具体算法
 * 
 * @author jianzijian
 *
 */
class OrderArrayStructure {

	private String[] keys;
	private String[] values;
	private int length;

	private OrderArrayStructure(int initialCapacity) {
		keys = new String[initialCapacity];
		values = new String[initialCapacity];
		length = 0;
	}

	// 二分查找
	
	String binarySearchGet(String key) {
		int low = 0, high = length - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (keys[mid].compareTo(key) < 0) {
				low = mid + 1;
			} else if (keys[mid].compareTo(key) > 0) {
				high = mid - 1;
			} else {
				return values[mid];
			}
		}
		return null;
	}

	void binarySearchPut(String key, String value) {
		// 查找插入点
		int low = 0, high = length - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (keys[mid].compareTo(key) < 0) {
				low = mid + 1;
			} else if (keys[mid].compareTo(key) > 0) {
				high = mid - 1;
			} else {
				low = mid;
				break;
			}
		}
		// key存在则更新value
		if (keys[low].compareTo(key) == 0) {
			values[low] = value;
			return;
		}
		// 是否需要扩容
		if (length == keys.length) {
			keys = Arrays.copyOf(keys, length * 2);
			values = Arrays.copyOf(values, length * 2);
		}
		// 插入并把插入点后的元素后移
		int tarIndex = low;
		for (int index = length - 1; length >= tarIndex; index--) {
			keys[index + 1] = keys[index];
			values[index + 1] = values[index];
		}
		keys[tarIndex] = key;
		values[tarIndex] = value;
	}

}
