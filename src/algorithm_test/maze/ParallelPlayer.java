package algorithm_test.maze;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class ParallelPlayer {

	class ValueLatch<T> {
		private T value;
		private final CountDownLatch done = new CountDownLatch(1);

		public boolean isDone() {
			return done.getCount() == 0;
		}

		public synchronized void setValue(T value) {
			if (this.isDone()) {
				return;
			}
			this.value = value;
			done.countDown();
		}

		public T getValue() throws InterruptedException {
			done.await();
			synchronized (this) { // 保证内存可见性（线程退出同步块时会强制回写主存以保证内存可见性）
				return value;
			}
		}
	}

	class RouteNode {
		public final Position curPos;
		public final Move preMove;
		public final RouteNode preRouteNode;

		public RouteNode(Position curPos, Move preMove, RouteNode preRouteNode) {
			super();
			this.curPos = curPos;
			this.preMove = preMove;
			this.preRouteNode = preRouteNode;
		}
	}

	class Task implements Runnable {
		private final RouteNode routeNode;

		public Task(RouteNode routeNode) {
			this.routeNode = routeNode;
			taskCnt.incrementAndGet();
		}

		@Override
		public void run() {
			try {
				if (result.isDone() || loopedPositions.putIfAbsent(routeNode.curPos, true) != null) {
					return;
				}
				if (maze.getDestination() == routeNode.curPos) {
					result.setValue(routeNode);
				} else {
					for (Move move : routeNode.curPos.getLegalMoves()) {
						Task subTask = new Task(new RouteNode(move.move(routeNode.curPos, maze), move, routeNode));
						service.execute(subTask);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (taskCnt.decrementAndGet() == 0) {
					result.setValue(null);
				}
			}
		}

	}

	private final Maze maze = Player.initMaze();
	private final ExecutorService service = Executors.newFixedThreadPool(10);
	private final ConcurrentHashMap<Position, Boolean> loopedPositions = new ConcurrentHashMap<>();
	private final ValueLatch<RouteNode> result = new ValueLatch<>();
	private final AtomicInteger taskCnt = new AtomicInteger(0);

	public ParallelPlayer() {
		service.execute(new Task(new RouteNode(maze.getCurPos(), null, null)));
		try {
			RouteNode route = result.getValue();
			while (route != null) {
				System.out.println(route.curPos.getDesc());
				route = route.preRouteNode;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		service.shutdown();
	}

	public static void main(String[] args) {
		new ParallelPlayer();
	}

}
