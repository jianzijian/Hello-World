package algorithm_test.datastructure_test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 普通(满)二叉树，键值可重复
 * 
 * @author jianzijian
 *
 */
class CommonBinaryTreeStructure {

	// 静态区域

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

	// 实例区域

	private List<Node> allNodes = new ArrayList<>(); // 节点列表

	CommonBinaryTreeStructure() {
	}

	void insert(int key) { // 普通(满)二叉树每层按左右子树顺序插入
		Node node = new Node(key, null, null);
		allNodes.add(node);
		if (allNodes.size() > 1) {
			Node parent = null; // 子节点跟父节点的关系indexP=(IndexC-1)/2
			if (allNodes.size() % 2 == 0) { // 成为左子树
				parent = allNodes.get((allNodes.size() - 1) / 2);
				parent.left = node;
			} else { // 成为右子树
				parent = allNodes.get((allNodes.size() - 1) / 2 - 1);
				parent.right = node;
			}
		}
	}

	void delete(int key) { // 删除节点及其左右子树
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
		if (node == null) { // 空树直接返回
			return;
		}
		if (node.left != null && node.left.key == key) {
			node.left = null;
		}
		if (node.right != null && node.right.key == key) {
			node.right = null;
		}
		// 先左后右，因为键值可能重复，所以左右子树都要查找
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

	List<Node> loop() { // 由上到下由左到右顺序遍历（逐一压入操作数，然后逐一拿出操作数进行统计）
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

	List<Node> loopPre() { // 前序遍历
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

	List<Node> loopMid() { // 中序遍历
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

	List<Node> loopLas() { // 后序遍历
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
