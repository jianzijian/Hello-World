package fanxing_test;

class Parent<T> {

	protected T data;

	protected Parent(T data) {
		super();
		this.data = data;
	}

	protected T getData() {
		return data;
	}

	protected void setData(T data) {
		this.data = data;
	}

	public static Parent<Long> parseLParent(Integer index, Long data) {
		switch (index) {
		case 0:
			return new ChildA(data);
		default:
			return new ChildB(data);
		}
	}

}
