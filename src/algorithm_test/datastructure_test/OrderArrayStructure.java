package algorithm_test.datastructure_test;

import java.util.Arrays;

/**
 * �������飬���Ҳ���ʱ�临�Ӷȿ������㷨
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

	// ���ֲ���
	
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
		// ���Ҳ����
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
		// key���������value
		if (keys[low].compareTo(key) == 0) {
			values[low] = value;
			return;
		}
		// �Ƿ���Ҫ����
		if (length == keys.length) {
			keys = Arrays.copyOf(keys, length * 2);
			values = Arrays.copyOf(values, length * 2);
		}
		// ���벢�Ѳ������Ԫ�غ���
		int tarIndex = low;
		for (int index = length - 1; length >= tarIndex; index--) {
			keys[index + 1] = keys[index];
			values[index + 1] = values[index];
		}
		keys[tarIndex] = key;
		values[tarIndex] = value;
	}

}
