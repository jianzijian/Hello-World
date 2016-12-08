package common_test;

class Test1 {

	public static void main(String[] args) {
		String str="12#34";
		str=str.replaceAll("#", "%");
		System.out.println(str);
		str=str.replaceAll("%", "#");
		System.out.println(str);
	}

}
