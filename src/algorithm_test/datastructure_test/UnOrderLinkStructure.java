package algorithm_test.datastructure_test;

/**
 * 无序链表，查找跟插入都需要遍历，时间复杂度O(n)
 * 
 * @author jianzijian
 *
 */
class UnOrderLinkStructure {

	private Node first;
	private int length;

	private class Node {
		private String key;
		private String value;
		private Node next;

		private Node(String key, String value, Node next) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	String get(String key) {
		Node temp = first;
		while (temp != null) {
			if (temp.key.equals(key)) {
				return temp.value;
			}
			temp = temp.next;
		}
		return null;
	}

	/**
	 * 先查找，如key存在则更新value，否则插入链头
	 */
	void put(String key, String value) {
		Node temp = first;
		while (temp != null) {
			if (temp.key.equals(key)) {
				temp.value = value;
				return;
			}
			temp = temp.next;
		}
		first = new Node(key, value, first);
		length++;
	}

	int length() {
		return length;
	}

}
