package stream_test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

	public static void main(String[] args) {
		new Test();
	}

	public Test() {
		List<Info> infos = new ArrayList<>();
		Map<Integer, Info> id2Infos = infos.stream().collect(Collectors.toMap(Info::getId, MyFunction::identity));
	}

	private class Info {
		public int id;

		public int getId() {
			return id;
		}
	}

	private interface MyFunction {

		public static <T> T identity(T t) {
			return t;
		}
	}

}
