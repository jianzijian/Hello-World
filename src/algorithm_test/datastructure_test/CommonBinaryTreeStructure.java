package algorithm_test.datastructure_test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * ��ͨ(��)����������ֵ���ظ�
 * 
 * @author jianzijian
 *
 */
class CommonBinaryTreeStructure {

	// ��̬����

	static class Node {
		int key;
		Node left;
		Node right;

		private Node(int key, Node left, Node right) {
			super();
			this.key = key;
			this.left = left;
			this.right = right;
		}

		@Override
		public String toString() {
			return String.valueOf(key);
		}
	}

	// ʵ������

	private Random directGenerator = new Random();
	private List<Node> allNodes = new ArrayList<>(); // �ڵ��б�

	CommonBinaryTreeStructure() {
	}

	void insert(int key) { // ��ͨ(��)������ÿ�㰴��������˳�����
		Node node = new Node(key, null, null);
		allNodes.add(node);
		if (allNodes.size() > 1) {
			Node parent = null; // �ӽڵ�����ڵ�Ĺ�ϵindexP=(IndexC-1)/2
			if (allNodes.size() % 2 == 0) { // ��Ϊ������
				parent = allNodes.get((allNodes.size() - 1) / 2);
				parent.left = node;
			} else { // ��Ϊ������
				parent = allNodes.get((allNodes.size() - 1) / 2 - 1);
				parent.right = node;
			}
		}
	}

	void insertRandom(int key) {
		Node node = new Node(key, null, null);
		allNodes.add(node);
		if (allNodes.size() > 1) {
			Node childNotFillNode = allNodes.get(0);
			// ��������ҵ��ɲ���Ľڵ�
			while (childNotFillNode.left != null && childNotFillNode.right != null) {
				if (directGenerator.nextBoolean()) {
					childNotFillNode = childNotFillNode.left;
				} else {
					childNotFillNode = childNotFillNode.right;
				}
			}
			if (childNotFillNode.left == null && childNotFillNode.right == null) {
				if (directGenerator.nextBoolean()) {
					childNotFillNode.left = node;
				} else {
					childNotFillNode.right = node;
				}
			} else if (childNotFillNode.left == null) {
				childNotFillNode.left = node;
			} else {
				childNotFillNode.right = node;
			}
		}
	}

	void delete(int key) { // ɾ���ڵ㼰����������
		if (allNodes.isEmpty()) {
			return;
		}
		Node root = allNodes.get(0);
		if (root.key == key) {
			allNodes.clear();
			return;
		}
		this.deleteNode(key, allNodes.get(0));
	}

	private void deleteNode(int key, Node node) {
		if (node == null) { // ����ֱ�ӷ���
			return;
		}
		if (node.left != null && node.left.key == key) {
			node.left = null;
		}
		if (node.right != null && node.right.key == key) {
			node.right = null;
		}
		// ������ң���Ϊ��ֵ�����ظ�����������������Ҫ����
		this.deleteNode(key, node.left);
		this.deleteNode(key, node.right);
	}

	List<Node> search(int key) {
		List<Node> results = new ArrayList<>();
		if (!allNodes.isEmpty()) {
			this.searchNodes(key, allNodes.get(0), results);
		}
		return results;
	}

	private void searchNodes(int key, Node node, List<Node> results) {
		if (node == null) {
			return;
		}
		if (node.key == key) {
			results.add(node);
		}
		this.searchNodes(key, node.left, results);
		this.searchNodes(key, node.right, results);
	}

	List<Node> loop() { // ���ϵ���������˳���������һѹ���������Ȼ����һ�ó�����������ͳ�ƣ�
		List<Node> results = new ArrayList<>();
		LinkedList<Node> tmpNodes = new LinkedList<>();
		if (!allNodes.isEmpty()) {
			tmpNodes.addLast(allNodes.get(0));
			while (!tmpNodes.isEmpty()) {
				Node curNode = tmpNodes.removeFirst();
				if (curNode.left != null) {
					tmpNodes.addLast(curNode.left);
				}
				if (curNode.right != null) {
					tmpNodes.addLast(curNode.right);
				}
				results.add(curNode);
			}
		}
		return results;
	}

	List<Node> loopPre() { // ǰ�����
		List<Node> results = new ArrayList<>();
		if (!allNodes.isEmpty()) {
			this.loopPreNodes(allNodes.get(0), results);
		}
		return results;
	}

	private void loopPreNodes(Node node, List<Node> results) {
		if (node == null) {
			return;
		}
		results.add(node);
		this.loopPreNodes(node.left, results);
		this.loopPreNodes(node.right, results);
	}

	List<Node> loopMid() { // �������
		List<Node> results = new ArrayList<>();
		if (!allNodes.isEmpty()) {
			this.loopMidNodes(allNodes.get(0), results);
		}
		return results;
	}

	private void loopMidNodes(Node node, List<Node> results) {
		if (node == null) {
			return;
		}
		this.loopMidNodes(node.left, results);
		results.add(node);
		this.loopMidNodes(node.right, results);
	}

	List<Node> loopLas() { // �������
		List<Node> results = new ArrayList<>();
		if (!allNodes.isEmpty()) {
			this.loopLasNodes(allNodes.get(0), results);
		}
		return results;
	}

	private void loopLasNodes(Node node, List<Node> results) {
		if (node == null) {
			return;
		}
		this.loopLasNodes(node.left, results);
		this.loopLasNodes(node.right, results);
		results.add(node);
	}

	List<Node> loopPreNoTraversal() { // ǰ������ǵݹ��㷨
		List<Node> results = new ArrayList<>();
		if (allNodes.isEmpty()) {
			return results;
		}
		LinkedList<Node> operandsList = new LinkedList<>();
		Node curNode = allNodes.get(0);
		while (true) {
			results.add(curNode);
			// ������������һ�ο϶��������꣬�ڵ��б����ֵ������Ѱ����������
			if (curNode.left != null && curNode.right != null) {
				operandsList.addFirst(curNode);
				curNode = curNode.left;
				continue;
			}
			if (curNode.left != null) {
				curNode = curNode.left;
				continue;
			}
			if (curNode.right != null) {
				curNode = curNode.right;
				continue;
			}
			// �ڵ����³�ջ�����������϶��������ˣ�ֱ����������
			if (!operandsList.isEmpty()) {
				// �������ֱ��������������Ϊ�ڵ���ջ��ǰ���ǣ�����������
				curNode = operandsList.removeFirst().right;
				continue;
			}
			break;
		}
		return results;
	}

	List<Node> loopMidNoTraversal() { // ��������ǵݹ��㷨
		List<Node> results = new ArrayList<>();
		if (allNodes.isEmpty()) {
			return results;
		}
		LinkedList<Node> operandsList = new LinkedList<>();
		Node curNode = allNodes.get(0);
		while (curNode != null || !operandsList.isEmpty()) {
			if (curNode == null) {
				Node parent = operandsList.removeFirst();
				results.add(parent);
				curNode = parent.right;
				continue;
			}
			// ����������һ�ο϶��������꣬�ڵ��б����ֵ�������Լ���Ѱ����������
			if (curNode.left != null) {
				operandsList.addFirst(curNode);
				curNode = curNode.left;
				continue;
			}
			// û����������ֱ�ӱ����Լ�
			results.add(curNode);
			if (curNode.right != null) {
				curNode = curNode.right;
				continue;
			}
			// �ڵ����³�ջ�����������϶��������ˣ������Լ�Ȼ��ֱ����������
			if (!operandsList.isEmpty()) {
				Node parent = operandsList.removeFirst();
				results.add(parent);
				curNode = parent.right;
				continue;
			}
			break;
		}
		return results;
	}

	List<Node> loopLasNoTraversal() { // ��������ǵݹ��㷨
		List<Node> results = new ArrayList<>();
		if (allNodes.isEmpty()) {
			return results;
		}
		LinkedList<Node> operandsList = new LinkedList<>();
		Node curNode = allNodes.get(0);
		while (true) {
			// ��add���ڵ�
			if (curNode.left != null) { // ��������addһ��
				operandsList.addFirst(curNode);
			}
			if (curNode.right != null) { // ����������addһ��
				operandsList.addFirst(curNode);
			}
			// �پ�����������
			if (curNode.left != null) {
				curNode = curNode.left;
				continue;
			}
			if (curNode.right != null) {
				curNode = curNode.right;
				continue;
			}
			// Ҷ�ӽڵ�
			results.add(curNode);
			Node nextNode = null;
			while (!operandsList.isEmpty()) {
				Node parent = operandsList.removeFirst();
				if (!operandsList.isEmpty() && parent == operandsList.getFirst()) { // ����Ӧ�ñ������ڵ��������
					// �ڵ㱻add������϶���������
					nextNode = parent.right;
					break;
				} else { // �������������������ˣ��������ڵ�
					results.add(parent);
				}
			}
			if (nextNode != null) {
				curNode = nextNode;
				continue;
			}
			break;
		}
		return results;
	}

}
