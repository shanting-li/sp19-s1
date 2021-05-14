package lab11.graphs;

import java.util.ArrayList;
import java.util.Stack;

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

    private boolean stackContains(Stack<Integer> s, int x) {
        for (int i : s) {
            if (Integer.valueOf(i) == x) {
                return true;
            }
        }
        return false;
    }



    private void cycleEdge(int v, int w) {
        int close = Math.min(v,w);
        int far = Math.max(v,w);

        Stack<Integer> pathFar = new Stack<>();
        Stack<Integer> pathClose = new Stack<>();

        pathFar.add(far);
        pathClose.add(close);

        //far:far to merge, close:close to merge, including other items
        while (!stackContains(pathClose, far)) {
            far = actualEdgeTo[far];
            close = actualEdgeTo[close];
            pathFar.add(far);
            pathClose.add(close);
        }

        //clear other items, close: close to merge (not included)
        int merge = pathFar.peek();
        int temp = pathClose.pop();
        while (temp != merge) {
            temp = pathClose.pop();
        }

        //collect all vertices in the cycle
        while (!pathClose.isEmpty()) {
            pathFar.add(pathClose.pop());
        }

        //connect from close to far
        temp = pathFar.pop();
        while (!pathFar.isEmpty()) {
            edgeTo[temp] = pathFar.peek();
            announce();
            temp = pathFar.pop();
        }
        //from far to close
        edgeTo[Math.max(v,w)] = Math.min(v,w);
        announce();

    }

}

