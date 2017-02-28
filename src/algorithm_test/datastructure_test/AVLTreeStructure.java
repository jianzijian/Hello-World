package algorithm_test.datastructure_test;

import algorithm_test.datastructure_test.CommonBinaryTreeStructure.Node;

class AVLTreeStructure {

	/* 实例区域 */

	private Node root;

	AVLTreeStructure() {
	}

	Node getRoot() {
		return root;
	}

	Node search(int key) {
		if (root == null) {
			return null;
		}
		return this.searchNode(key, root);
	}

	private Node searchNode(int key, Node node) {
		if (node.key == key) {
			return node;
		} else if (node.key < key && node.right != null) {
			return this.searchNode(key, node.right);
		} else if (node.key > key && node.left != null) {
			return this.searchNode(key, node.left);
		}
		return null;
	}

	void insert(int key) {
		if (root == null) {
			root = new Node(key, null, null, 0);
			return;
		}
		root = this.insertNode(key, root);
	}

	private Node insertNode(int key, Node node) {
		if (node.key == key) {
			return node;
		}
		if (node.key < key) {
			if (node.right == null) {
				node.right = new Node(key, null, null, 0);
				node.balanceCount += -1;
			} else {
				node.right = this.insertNode(key, node.right);
				if (node.right.balanceCount != 0) { // 子树高度有变化
					node.balanceCount += -1;
				}
			}
		} else {
			if (node.left == null) {
				node.left = new Node(key, null, null, 0);
				node.balanceCount += 1;
			} else {
				node.left = this.insertNode(key, node.left);
				if (node.left.balanceCount != 0) { // 子树高度有变化
					node.balanceCount += 1;
				}
			}
		}
		if (node.balanceCount < -1 || node.balanceCount > 1) {
			node = this.spin(node);
		}
		return node;
	}

	private Node spin(Node node) {
		if (node.left != null && node.right == null) {
			if (node.left.left != null) { // 裸LL
				return this.spinOLL(node);
			} else { // 裸LR
				return this.spinOLR(node);
			}
		} else if (node.left == null && node.right != null) {
			if (node.right.right != null) { // 裸RR
				return this.spinORR(node);
			} else { // 裸RL
				return this.spinORL(node);
			}
		} else {
			if (node.right.left == null && node.right.right == null) {
				if (node.left.left.left != null) { // LL
					return this.spinLL(node);
				} else { // LR
					return this.spinLR(node);
				}
			} else {
				if (node.right.right.right != null) { // RR
					return this.spinRR(node);
				} else { // RL
					return this.spinRL(node);
				}
			}
		}
	}

	// 调整了位置要调整平衡因子

	private Node spinOLL(Node node) { // 裸LL
		Node tmp = node.left;
		node.left = null;
		tmp.right = node;
		tmp.balanceCount = 0;
		tmp.right.balanceCount = 0;
		return tmp;
	}

	private Node spinLL(Node node) { // LL
		Node tmpLeft = node.left;
		node.left = tmpLeft.right;
		tmpLeft.right = node;
		tmpLeft.balanceCount = 0;
		tmpLeft.right.balanceCount = 0;
		return tmpLeft;
	}

	private Node spinORR(Node node) { // 裸RR
		Node tmp = node.right;
		node.right = null;
		tmp.left = node;
		tmp.balanceCount = 0;
		tmp.left.balanceCount = 0;
		return tmp;
	}

	private Node spinRR(Node node) { // RR
		Node tmpRight = node.right;
		node.right = tmpRight.left;
		tmpRight.left = node;
		tmpRight.balanceCount = 0;
		tmpRight.left.balanceCount = 0;
		return tmpRight;
	}

	private Node spinOLR(Node node) {
		// 先左转
		Node tmpLeft = node.left;
		node.left = tmpLeft.right;
		tmpLeft.right = null;
		node.left.left = tmpLeft;
		tmpLeft.balanceCount = 0;
		node.left.balanceCount = 1;
		// 再右转
		return this.spinOLL(node);
	}

	private Node spinLR(Node node) {
		Node tmpLeft = node.left;
		node.left = tmpLeft.right;
		tmpLeft.right = null;
		node.left.left = tmpLeft;
		tmpLeft.balanceCount = 1;
		node.left.balanceCount = 1;
		return this.spinLL(node);
	}

	private Node spinORL(Node node) {
		Node tmpRight = node.right;
		node.right = tmpRight.left;
		tmpRight.left = null;
		node.right.right = tmpRight;
		tmpRight.balanceCount = 0;
		node.right.balanceCount = -1;
		return this.spinORR(node);
	}

	private Node spinRL(Node node) {
		Node tmpRight = node.right;
		node.right = tmpRight.left;
		tmpRight.left = null;
		node.right.right = tmpRight;
		tmpRight.balanceCount = -1;
		node.right.balanceCount = -1;
		return this.spinRR(node);
	}
}
