package algorithm_test.datastructure_test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
		this.loopPreNodes(node.left, results);
		results.add(node);
		this.loopPreNodes(node.right, results);
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
		this.loopPreNodes(node.left, results);
		this.loopPreNodes(node.right, results);
		results.add(node);
	}

}
