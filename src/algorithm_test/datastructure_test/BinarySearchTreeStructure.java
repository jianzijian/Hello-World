package algorithm_test.datastructure_test;

/**
 * ���������
 * 
 * @author jianzijian
 *
 */
class BinarySearchTreeStructure {

	private Node root;

	void delete(String key) {
		// �Ȳ���Ŀ��ڵ㼰���ڵ㣬�Ͽ�Ŀ��ڵ��������������������Ѹ���������Ŀ��ڵ�������������ҽڵ��������
		// Ȼ��Ͽ�Ŀ��ڵ�������������Ѹ���������Ŀ��ڵ�����������������������ѶϿ���
		// Ŀ��ڵ����������ᣬ����Ŀ��ڵ�
	}

	private Node insertRecursively(Data data, Node node) {
		if (node == null) {
			return new Node(null, null, data, 1);
		}
		int compare = node.data.key.compareTo(data.key);
		if (compare < 0) {
			node.left = this.insertRecursively(data, node.left);
		} else if (compare > 0) {
			node.right = this.insertRecursively(data, node.right);
		} else {
			node.data = data;
		}
		node.number += 1;
		return node;
	}

	void insert(Data data) {
		this.root = this.insertRecursively(data, root);
	}

	Data search(String key) {
		Node temp = root;
		while (temp != null) {
			if (temp.data.key.compareTo(key) > 0) {
				temp = root.right;
			} else if (temp.data.key.compareTo(key) < 0) {
				temp = root.left;
			} else {
				return temp.data;
			}
		}
		return null;
	}

	private static class Node {
		private Node left;
		private Node right;
		private Data data;
		private int number;

		private Node(Node left, Node right, Data data, int number) {
			super();
			this.left = left;
			this.right = right;
			this.data = data;
			this.number = number;
		}
	}

	private static class Data {
		private String key;
		private String value;

		private Data(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
	}

}
