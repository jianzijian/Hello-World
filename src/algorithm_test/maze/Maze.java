package algorithm_test.maze;

class Maze {

	private final Position curPos;
	private final Position destination;
	private final Position[][] allPossions;

	public Maze(Position curPos, Position destination, Position[][] allPossions) {
		super();
		this.curPos = curPos;
		this.destination = destination;
		this.allPossions = allPossions;
	}

	public Position getCurPos() {
		return curPos;
	}

	public Position getDestination() {
		return destination;
	}

	public Position getPossion(int x, int y) {
		return allPossions[x][y];
	}

}
