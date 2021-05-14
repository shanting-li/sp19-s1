package lab11.graphs;


import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    private PriorityQueue<star> queue;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        distTo[t] = Integer.MAX_VALUE;

        queue = new PriorityQueue<star>();
        queue.add(new star(s, distTo[s] + h(s)));

        // heap按照h(s,v)+h(v,t)排列
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int vX = maze.toX(v);
        int vY = maze.toY(v);
        return  Math.abs(vX - maze.toX(t)) + Math.abs(vY - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        /* You do not have to use this method. */
        return -1;
    }


    private class star implements Comparable {
        int item;
        int priority;

        public star(int i, int p) {
            item = i;
            priority = p;
        }

        @Override
        public int compareTo(Object o) {
            star that = (star) o;
            return this.priority - that.priority;
        }
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        //v = min.poll,mark v, 看v和target是否相等
        //如果相等，结束
        //如果不相等，看 v 所有的邻居 w
        //v-w，如果sv + 1 <sw,更新sw = sv + 1,更新dw = sw + wt,修改distTo
        //全部改完之后，astar(min.poll)

        int v = s;
        marked[v] = true;
        announce();

        if (v == t) {
            distTo[t] = distTo[v];
            edgeTo[t] = v;
            return;
        } else {
            for (int w : maze.adj(v)) {
                if (!marked[w] && distTo[v] + 1 < distTo[w]) {
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    announce();

                    queue.add(new star(w, distTo[w] + h(w)));
                }
            }
            if (!queue.isEmpty()) {
                astar(queue.poll().item);
            }
        }

    }


    @Override
    public void solve() {
        astar(queue.poll().item);
    }

}

