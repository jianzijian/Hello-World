package algorithm_test.maze;

import java.util.Arrays;

class Position {

	private final int x;
	private final int y;
	private final Move[] legalMoves;
	private final String desc;

	public Position(int x, int y, Move[] legalMoves, String desc) {
		super();
		this.x = x;
		this.y = y;
		this.legalMoves = legalMoves;
		this.desc = desc;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Move[] getLegalMoves() {
		return Arrays.copyOf(legalMoves, legalMoves.length);
	}

	public String getDesc() {
		return desc;
	}
}
