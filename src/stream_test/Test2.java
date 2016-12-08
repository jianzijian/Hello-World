package stream_test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Test2 {

	public static void main(String[] args) {
//		Map<Integer, String> map = new HashMap<>();
//		map.put(0, "A");
//		map.put(1, "A");
//		map.put(2, "B");
//		map.put(3, "B");
//		map.put(4, "C");
//		Map<String, List<Integer>> tempMap = map.keySet().stream().collect(Collectors.groupingBy(key -> map.get(key)));
//		System.out.println(tempMap.get("A").size());
//		System.out.println(tempMap.get("A").get(0));
//		System.out.println(tempMap.get("A").get(1));
		System.out.println(Runtime.getRuntime().availableProcessors());
	}
}
