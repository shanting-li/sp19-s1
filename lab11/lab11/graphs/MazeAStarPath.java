package lab11.graphs;


/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private int[] priority;
    private int unmarked;
    private int minItem;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;

        priority = new int[maze.V()];
        for (int i = 0; i < maze.V(); i++) {
            priority[i] = Integer.MAX_VALUE;
        }
        priority[s]= distTo[s] + h(s);
        unmarked = maze.V();
        minItem = s;
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

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        //v = min.poll,mark v, 看v和target是否相等
        //如果相等，结束
        //如果不相等，看 v 所有的邻居 w
        //v-w，如果sv + 1 <sw,更新sw = sv + 1,更新dw = sw + wt,修改distTo
        //全部改完之后，astar(min.poll)

        int v = s;
        marked[v] = true;
        priority[v] = Integer.MAX_VALUE;
        unmarked -= 1;
        announce();

        if (v == t) {
            distTo[t] = distTo[v];
            edgeTo[t] = edgeTo[v];
            return;
        } else if (unmarked > 0) {
            for (int w : maze.adj(v)) {
                if ((!marked[w]) && (distTo[v] + 1 < distTo[w])) {
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    announce();

                    int disW = distTo[w] + h(w);
                    priority[w] = disW;
                    if (disW < priority[minItem]) {
                        minItem = w;
                    }
                }
            }
            astar(minItem);
        }

    }


    @Override
    public void solve() {
        astar(s);
    }

}

