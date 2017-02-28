package algorithm_test.maze;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

class PlayerMul {

	private List<RouteNode> allRouteNodes = new ArrayList<>();
	private Maze maze = Player.initMaze();

	private class RouteNode {
		private final Position curPos;
		private final LinkedHashSet<Position> curRoute;

		public RouteNode(Position curPos, LinkedHashSet<Position> curRoute) {
			super();
			this.curPos = curPos;
			this.curRoute = curRoute;
		}
	}

	private void play(RouteNode curRouteNode) {
		if (curRouteNode.curPos == maze.getDestination()) {
			allRouteNodes.add(curRouteNode);
		} else {
			Position curPos = curRouteNode.curPos;
			for (Move move : curPos.getLegalMoves()) {
				Position nextPos = move.move(curPos, maze);
				if (!curRouteNode.curRoute.contains(nextPos)) {
					LinkedHashSet<Position> nextRoute = new LinkedHashSet<>(curRouteNode.curRoute);
					nextRoute.add(nextPos);
					RouteNode nextRouteNode = new RouteNode(nextPos, nextRoute);
					this.play(nextRouteNode);
				}
			}
		}
	}

	public PlayerMul() {
		LinkedHashSet<Position> curRoute = new LinkedHashSet<>();
		curRoute.add(maze.getCurPos());
		RouteNode curRouteNode = new RouteNode(maze.getCurPos(), curRoute);
		this.play(curRouteNode);
		for (RouteNode routeNode : allRouteNodes) {
			for (Position pos : routeNode.curRoute) {
				System.out.print(pos.getDesc() + ",");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		new PlayerMul();
	}

}
