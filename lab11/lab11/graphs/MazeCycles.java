package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] actualEdgeTo;
    private Maze maze;
    private boolean hasCircle = false;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        actualEdgeTo = new int[maze.V()];
        for (int i = 0; i < maze.V(); i += 1) {
            actualEdgeTo[i] = Integer.MAX_VALUE;
        }
        distTo[0] = 0;
        edgeTo[0] = 0;
    }

    @Override
    public void solve() {
        findCircle(0);
    }

    // Helper methods go here
    private void findCircle(int v) {
        marked[v] = true;
        announce();

        for (int w : maze.adj(v)) {
            if (!hasCircle) {
                if (marked[w] && actualEdgeTo[v] != w) {
                    hasCircle = true;
                    cycleEdge(v, w);
                    return;
                } else if (!marked[w]) {
                    actualEdgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    findCircle(w);
                }
            }

        }
    }
    private void cycleEdge(int v, int w) {
        int temp = v;

        while (w != temp) {
            edgeTo[temp] = actualEdgeTo[temp];//w-x
            temp = edgeTo[temp];
        }
        edgeTo[w] = v;
        announce();

    }

}

