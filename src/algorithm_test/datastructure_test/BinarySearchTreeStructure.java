package algorithm_test.datastructure_test;

import algorithm_test.datastructure_test.CommonBinaryTreeStructure.Node;

/**
 * ���������
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
				// �滻ֵ
				return;
			}
			if (tmpNode.key < key) { // ������
				if (tmpNode.right != null) {
					tmpNode = tmpNode.right;
					continue;
				}
				tmpNode.right = new Node(key, null, null);
			} else { // ������
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
			if (child.left == null && child.right == null) { // û����������
				this.deleteNoChild(parent, child);
			} else if (child.left != null && child.right != null) { // ������������
				this.deleteWithTwoChild(key, parent, child);
			} else { // ֻ������������ֻ��������
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

	private void deleteWithTwoChild(int key, Node parent, Node child) { // �Զ���������ķ��������������ҽڵ������ɾ���ڵ�
		// ���������������ҽڵ㣬���Ͽ��ýڵ�
		Node replaceNode = child.left;
		while (replaceNode.right != null) {
			Node tmpNode = replaceNode;
			replaceNode = replaceNode.right;
			if (replaceNode.right == null) {
				tmpNode.right = null;
			}
		}
		// �ӹܱ�ɾ���ڵ����������
		if (replaceNode != child.left) { // ���ҽڵ��п���ֱ������ڵ�
			replaceNode.left = child.left;
		}
		replaceNode.right = child.right;
		// �޸ı�ɾ���ڵ�ĸ��ڵ���ӽڵ�����Ϊ����
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
		if (parent == null) { // ���ڵ�ɾ��
			// ���ǵ���ģ�Ҳ����Ҫ������
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
		if (parent == null) { // ���ڵ�ɾ��
			// ֻ��һ���ڵ��������������
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
