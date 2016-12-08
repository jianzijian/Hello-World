package enum_test;

enum SetGain implements ISetGain {
	TEST() {

		@Override
		public boolean isGain() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setGain() {
			// TODO Auto-generated method stub

		}

	};

	public static ISetGain parse() {
		return TEST;
	}
}
