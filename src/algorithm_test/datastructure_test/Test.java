package algorithm_test.datastructure_test;

import java.util.Arrays;

class Test {

	public static void main(String[] args) {
		CommonBinaryTreeStructure structure = new CommonBinaryTreeStructure();
		int[] keys = new int[] { 1, 1, 2, 12, 5, 5, 74, 46 };
		Arrays.stream(keys).forEach(key -> structure.insert(key));
		System.out.println(structure.loop());
		System.out.println(structure.loopPre());
		System.out.println(structure.loopMid());
		structure.delete(1);
		System.out.println(structure.search(1));
		System.out.println(structure.loopLas());
	}
}
