package algorithm_test.datastructure_test;

/**
 * �����������Ҹ����붼��Ҫ������ʱ�临�Ӷ�O(n)
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
	 * �Ȳ��ң���key���������value�����������ͷ
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
