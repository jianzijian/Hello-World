package algorithm_test.datastructure_test;

import algorithm_test.datastructure_test.CommonBinaryTreeStructure.Node;

/**
 * 二叉查找树
 * 
 * @author jianzijian
 *
 */
class BinarySearchTreeStructure {

	protected Node root;

	protected Node getRoot() {
		return root;
	}

	protected void insert(int key) {
		if (root == null) {
			root = new Node(key, null, null);
			return;
		}
		Node tmpNode = root;
		while (tmpNode != null) {
			if (tmpNode.key == key) {
				// 替换值
				return;
			}
			if (tmpNode.key < key) { // 右子树
				if (tmpNode.right != null) {
					tmpNode = tmpNode.right;
					continue;
				}
				tmpNode.right = new Node(key, null, null);
			} else { // 左子树
				if (tmpNode.left != null) {
					tmpNode = tmpNode.left;
					continue;
				}
				tmpNode.left = new Node(key, null, null);
			}
		}
	}

	protected Node delete(int key) {
		if (root == null) {
			return null;
		}
		return this.deleteNodes(key, null, root);
	}

	private Node deleteNodes(int key, Node parent, Node child) {
		if (child.key == key) {
			if (child.left == null && child.right == null) { // 没有左右子树
				this.deleteNoChild(parent, child);
			} else if (child.left != null && child.right != null) { // 左右子树都有
				this.deleteWithTwoChild(key, parent, child);
			} else { // 只有左子树或者只有右子树
				this.deleteWithOneChild(key, parent, child);
			}
			return child;
		} else {
			Node result = null;
			if (child.left != null) {
				result = this.deleteNodes(key, child, child.left);
			}
			if (result != null) {
				return result;
			}
			if (child.right != null) {
				result = this.deleteNodes(key, child, child.right);
			}
			return result;
		}
	}

	private void deleteWithTwoChild(int key, Node parent, Node child) { // 以二叉查找树的方法，左子树最右节点替代被删除节点
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
	}

	private void deleteWithOneChild(int key, Node parent, Node child) {
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

	protected Node search(int key) {
		if (root == null) {
			return null;
		}
		Node tmpNode = root;
		while (tmpNode != null) {
			if (tmpNode.key == key) {
				return tmpNode;
			} else if (tmpNode.key < key) {
				tmpNode = tmpNode.right;
			} else {
				tmpNode = tmpNode.left;
			}
		}
		return null;
	}

}
