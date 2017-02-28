package algorithm_test.maze;

import java.util.HashSet;
import java.util.Set;

class Player {

	private final Maze maze;
	private final Set<Position> loopedPositions = new HashSet<>();

	static class RouteNode {
		private final Position curPos;
		private RouteNode nextRouteNode;

		public RouteNode(Position curPos) {
			this.curPos = curPos;
		}
	}

	static Maze initMaze() {
		Position[][] allPositions = new Position[3][3];
		allPositions[0][0] = new Position(0, 0, new Move[] { Move.RIGHT, Move.DOWN }, "00");
		allPositions[0][1] = new Position(0, 1, new Move[] { Move.LEFT, Move.RIGHT, Move.DOWN }, "01");
		allPositions[0][2] = new Position(0, 2, new Move[] { Move.LEFT, Move.DOWN }, "02");

		allPositions[1][0] = new Position(1, 0, new Move[] { Move.RIGHT, Move.DOWN, Move.UP }, "10");
		allPositions[1][1] = new Position(1, 1, new Move[] { Move.RIGHT, Move.DOWN, Move.LEFT, Move.UP }, "11");
		allPositions[1][2] = new Position(1, 2, new Move[] { Move.LEFT, Move.DOWN, Move.UP }, "12");

		allPositions[2][0] = new Position(2, 0, new Move[] { Move.RIGHT, Move.UP }, "20");
		allPositions[2][1] = new Position(2, 1, new Move[] { Move.RIGHT, Move.LEFT, Move.UP }, "21");
		allPositions[2][2] = new Position(2, 2, new Move[] { Move.LEFT, Move.UP }, "22");

		return new Maze(allPositions[0][0], allPositions[2][2], allPositions);
	}

	RouteNode play(Position curPos) {
		if (!loopedPositions.contains(curPos)) {
			loopedPositions.add(curPos);
			if (maze.getDestination() == curPos) {
				return new RouteNode(curPos);
			}
			RouteNode curRouteNode = new RouteNode(curPos);
			for (Move move : curPos.getLegalMoves()) {
				Position afterMovePos = move.move(curPos, maze);
				RouteNode nextRouteNode = this.play(afterMovePos);
				if (nextRouteNode != null) {
					curRouteNode.nextRouteNode = nextRouteNode;
					return curRouteNode;
				}
			}
		}
		return null;
	}

	public Player() {
		this.maze = initMaze();
		RouteNode routeNode = this.play(maze.getCurPos());
		while (routeNode != null) {
			System.out.println(routeNode.curPos.getDesc());
			routeNode = routeNode.nextRouteNode;
		}
	}

	public static void main(String[] args) {
		new Player();
	}

}
