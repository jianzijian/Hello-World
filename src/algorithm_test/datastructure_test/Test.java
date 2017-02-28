package algorithm_test.datastructure_test;

import java.util.Arrays;

class Test {

	public static void main(String[] args) {
		AVLTreeStructure structure = new AVLTreeStructure();
		int[] keys = new int[] { 7, 7, 7, 7, 1, 2, 3, 6, 9, -1, 34, 67 };
		Arrays.stream(keys).forEach(key -> structure.insert(key));
		System.out.println(CommonBinaryTreeStructure.loop(structure.getRoot()));
		System.out.println(structure.search(68));
	}
}
