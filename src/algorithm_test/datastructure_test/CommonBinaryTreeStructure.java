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
			if (child.left == null && child.right == null) { // û����������
				this.deleteNoChild(parent, child);
			} else if (child.left != null && child.right != null) { // ������������
				this.deleteWithTwoChild(key, parent, child, results);
			} else { // ֻ������������ֻ��������
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

	private void deleteWithTwoChild(int key, Node parent, Node child, List<Node> results) { // �Զ���������ķ��������������ҽڵ������ɾ���ڵ�
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
		// ���������λ��
		allNodes.remove(replaceNode);
		allNodes.add(allNodes.indexOf(child), replaceNode);
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
		this.deleteNodes(key, parent, replaceNode, results);
	}

	private void deleteWithOneChild(int key, Node parent, Node child, List<Node> results) {
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
		this.deleteNodes(key, parent, grandSon, results);
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
