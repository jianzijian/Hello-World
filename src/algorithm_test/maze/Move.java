package algorithm_test.maze;

enum Move {
	UP {

		@Override
		Position move(Position curPos, Maze maze) {
			int x = curPos.getX();
			int y = curPos.getY();
			x--;
			return maze.getPossion(x, y);
		}

	}, //
	DOWN {

		@Override
		Position move(Position curPos, Maze maze) {
			int x = curPos.getX();
			int y = curPos.getY();
			x++;
			return maze.getPossion(x, y);
		}

	}, //
	LEFT {

		@Override
		Position move(Position curPos, Maze maze) {
			int x = curPos.getX();
			int y = curPos.getY();
			y--;
			return maze.getPossion(x, y);
		}

	}, //
	RIGHT {

		@Override
		Position move(Position curPos, Maze maze) {
			int x = curPos.getX();
			int y = curPos.getY();
			y++;
			return maze.getPossion(x, y);
		}

	}, //
	;
	abstract Position move(Position curPos, Maze maze);
}
