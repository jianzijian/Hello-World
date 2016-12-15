package algorithm_test.datastructure_test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

	private Random directGenerator = new Random();
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

	void insertRandom(int key) {
		Node node = new Node(key, null, null);
		allNodes.add(node);
		if (allNodes.size() > 1) {
			Node childNotFillNode = allNodes.get(0);
			// 随机方向找到可插入的节点
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

	List<Node> delete(int key) {
		List<Node> results = new ArrayList<>();
		if (!allNodes.isEmpty()) {
			this.deleteNodes(key, null, allNodes.get(0), results);
		}
		return results;
	}

	void deleteNodes(int key, Node parent, Node child, List<Node> results) {
		if (child.key == key) {
			results.add(child);
			if (child.left == null && child.right == null) { // 没有左右子树
				this.deleteNoChild(parent, child);
			} else if (child.left != null && child.right != null) { // 左右子树都有
				this.deleteWithTwoChild(key, parent, child, results);
			} else { // 只有左子树或者只有右子树
				this.deleteWithOneChild(key, parent, child, results);
			}
			allNodes.remove(child);
		} else {
			if (child.left != null) {
				this.deleteNodes(key, child, child.left, results);
			}
			if (child.right != null) {
				this.deleteNodes(key, child, child.right, results);
			}
		}
	}

	private void deleteWithTwoChild(int key, Node parent, Node child, List<Node> results) { // 以二叉查找树的方法，左子树最右节点替代被删除节点
		// 查找左子树的最右节点，并断开该节点
		Node replaceNode = child.left;
		while (replaceNode.right != null) {
			Node tmpNode = replaceNode;
			replaceNode = replaceNode.right;
			if (replaceNode.right == null) {
				tmpNode.right = null;
			}
		}
		// 接管被删除节点的左右子树
		if (replaceNode != child.left) { // 最右节点有可能直接是左节点
			replaceNode.left = child.left;
		}
		replaceNode.right = child.right;
		// 调整自身的位置
		allNodes.remove(replaceNode);
		allNodes.add(allNodes.indexOf(child), replaceNode);
		// 修改被删除节点的父节点的子节点引用为自身
		if (parent != null) {
			if (parent.left == child) {
				parent.left = replaceNode;
				return;
			}
			if (parent.right == child) {
				parent.right = replaceNode;
			}
		}
		this.deleteNodes(key, parent, replaceNode, results);
	}

	private void deleteWithOneChild(int key, Node parent, Node child, List<Node> results) {
		if (parent == null) { // 根节点删除
			// 树是单向的，也不需要做处理
			return;
		}
		Node grandSon = child.left == null ? child.right : child.left;
		if (parent.left == child) {
			parent.left = grandSon;
			return;
		}
		if (parent.right == child) {
			parent.right = grandSon;
		}
		this.deleteNodes(key, parent, grandSon, results);
	}

	private void deleteNoChild(Node parent, Node child) {
		if (parent == null) { // 根节点删除
			// 只有一个节点的树，不做处理
			return;
		}
		if (parent.left == child) {
			parent.left = null;
			return;
		}
		if (parent.right == child) {
			parent.right = null;
		}
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
		this.loopMidNodes(node.left, results);
		results.add(node);
		this.loopMidNodes(node.right, results);
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
		this.loopLasNodes(node.left, results);
		this.loopLasNodes(node.right, results);
		results.add(node);
	}

	List<Node> loopPreNoTraversal() { // 前序遍历非递归算法
		List<Node> results = new ArrayList<>();
		if (allNodes.isEmpty()) {
			return results;
		}
		LinkedList<Node> operandsList = new LinkedList<>();
		Node curNode = allNodes.get(0);
		while (true) {
			results.add(curNode);
			// 有左右子树，一次肯定遍历不完，节点有保存价值（用于寻找右子树）
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
			// 节点重新出栈，其左子树肯定被遍历了，直接找右子树
			if (!operandsList.isEmpty()) {
				// 这里可以直接拿右子树是因为节点入栈的前提是：有左右子树
				curNode = operandsList.removeFirst().right;
				continue;
			}
			break;
		}
		return results;
	}

	List<Node> loopMidNoTraversal() { // 中序遍历非递归算法
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
			// 有左子树，一次肯定遍历不完，节点有保存价值（遍历自己，寻找右子树）
			if (curNode.left != null) {
				operandsList.addFirst(curNode);
				curNode = curNode.left;
				continue;
			}
			// 没有左子树，直接遍历自己
			results.add(curNode);
			if (curNode.right != null) {
				curNode = curNode.right;
				continue;
			}
			// 节点重新出栈，其左子树肯定被遍历了，遍历自己然后直接找右子树
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

	List<Node> loopLasNoTraversal() { // 后序遍历非递归算法
		List<Node> results = new ArrayList<>();
		if (allNodes.isEmpty()) {
			return results;
		}
		LinkedList<Node> operandsList = new LinkedList<>();
		Node curNode = allNodes.get(0);
		while (true) {
			// 先add父节点
			if (curNode.left != null) { // 有左子树add一遍
				operandsList.addFirst(curNode);
			}
			if (curNode.right != null) { // 有右子树再add一遍
				operandsList.addFirst(curNode);
			}
			// 再决定往左往右
			if (curNode.left != null) {
				curNode = curNode.left;
				continue;
			}
			if (curNode.right != null) {
				curNode = curNode.right;
				continue;
			}
			// 叶子节点
			results.add(curNode);
			Node nextNode = null;
			while (!operandsList.isEmpty()) {
				Node parent = operandsList.removeFirst();
				if (!operandsList.isEmpty() && parent == operandsList.getFirst()) { // 本次应该遍历父节点的右子树
					// 节点被add了两遍肯定有右子树
					nextNode = parent.right;
					break;
				} else { // 左右子树都被遍历完了，遍历父节点
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
