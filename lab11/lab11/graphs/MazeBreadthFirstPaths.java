package lab11.graphs;

import java.util.ArrayDeque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private boolean findTarget = false;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
        queue.addFirst(s);

        while (!(findTarget || queue.isEmpty())) {
            //标记当前点
            //访问当前点的所有未标记邻居，relax distTo，更新edgeTo
            int v = queue.removeFirst();
            marked[v] = true;
            announce();
            if (v == t) {
                findTarget = true;
            } else {
                for (int w : maze.adj(v)) {
                    if (!marked[w]) {
                        distTo[w] = distTo[v] + 1;
                        edgeTo[w] = v;
                        announce();
                        queue.addLast(w);
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

