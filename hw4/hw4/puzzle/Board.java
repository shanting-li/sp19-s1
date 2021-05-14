package hw4.puzzle;

import java.util.Arrays;
import java.util.Stack;

public class Board implements WorldState{
    private int[][] bd;
    private int N;//N*N square
    private Position emp;

    private class Position {
        int x;
        int y;
        public Position(int a, int b) {
            x = a;
            y = b;
        }
    }
    /**
     * Constructs a board from an N-by-N array of tiles where
     *               tiles[i][j] = tile at row i, column j
     */

    public Board(int[][] tiles) {
        N = tiles.length;
        bd = new int[N][N];

        for (int x = 0; x < bd.length; x++) {
            for (int y = 0; y < bd.length; y++) {
                bd[x][y] = tiles[x][y];
                if ((bd[x][y]) == 0) {
                    emp = new Position(x, y);
                }

            }
        }
    }


    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     * You may assume that the constructor receives an N-by-N array
     * containing the N2 integers between 0 and N2 − 1,
     * where 0 represents the blank square.
     * The tileAt() method should throw a java.lang.IndexOutOfBoundsException
     * unless both i and j are between 0 and N − 1.
     */
    public int tileAt(int i, int j) {
        Position p = new Position(i, j);
        if (isInBound(p)) {
            return bd[i][j];
        } else {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    /**
     * Returns the board size N
     */
    public int size() {
        return N;
    }

    private boolean isInBound(Position i) {
        if (i == null) {
            return false;
        }
        return i.x < N && i.x >= 0 && i.y < N && i.y >= 0;
    }


    private Position leftIndex(Position p) {
        return new Position(p.x - 1, p.y);
    }
    private Position rightIndex(Position p) {
        return new Position(p.x + 1, p.y);
    }
    private Position upIndex(Position p) {
        return new Position(p.x, p.y - 1);

    }
    private Position dnIndex(Position p) {
        return new Position(p.x, p.y + 1);
    }

    private WorldState neighbor(String direction) {
        Board nb = new Board(bd);

        switch(direction) {
            case "l" :
                Position left = leftIndex(nb.emp);
                if (isInBound(left)) {
                    nb.bd[emp.x][emp.y] = nb.bd[left.x][left.y];
                    nb.bd[left.x][left.y] = 0;
                    nb.emp = new Position(left.x, left.y);
                    return nb;
                } else {
                    return null;
                }
            case "r" :
                Position right = rightIndex(nb.emp);
                if (isInBound(right)) {
                    nb.bd[emp.x][emp.y] = nb.bd[right.x][right.y];
                    nb.bd[right.x][right.y] = 0;
                    nb.emp = new Position(right.x, right.y);
                    return nb;
                } else {
                    return null;
                }
            case "u" :
                Position up = upIndex(nb.emp);
                if (isInBound(up)) {
                    nb.bd[emp.x][emp.y] = nb.bd[up.x][up.y];
                    nb.bd[up.x][up.y] = 0;
                    nb.emp = new Position(up.x, up.y);
                    return nb;
                } else {
                    return null;
                }
            case "d" :
                Position dn = dnIndex(nb.emp);
                if (isInBound(dn)) {
                    nb.bd[emp.x][emp.y] = nb.bd[dn.x][dn.y];
                    nb.bd[dn.x][dn.y] = 0;
                    nb.emp = new Position(dn.x, dn.y);
                    return nb;
                } else {
                    return null;
                }
            default:
                return null;
        }
    }
    /**
     * Returns the neighbors of the current board
     */
    public Iterable<WorldState> neighbors() {
        // 把emptyIndex上下左右都移动一遍
        Stack<WorldState> ans = new Stack<>();

        if (neighbor("l") != null) {
            ans.push(neighbor("l"));
        }
        if (neighbor("r") != null) {
            ans.push(neighbor("r"));
        }
        if (neighbor("u") != null) {
            ans.push(neighbor("u"));
        }
        if (neighbor("d") != null) {
            ans.push(neighbor("d"));
        }
        return ans;
    }

    /**
     * Hamming estimate described below
     */
    public int hamming() {
        int ham = 0;
        for (int x = 0; x < bd.length; x++) {
            for (int y = 0; y < bd.length; y++) {
                if (bd[x][y] != 0 && bd[x][y] != x * N + y + 1) {
                        ham += 1;
                }
            }
        }
        return ham;
    }

    /**
     * Manhattan estimate described below
     */
    public int manhattan() {
        int man = 0;
        for (int x = 0; x < bd.length; x++) {
            for (int y = 0; y < bd.length; y++) {
                int temp = bd[x][y];
                if (temp != 0 && temp != x * N + y + 1) {
                    int cX = (temp - 1) / N;
                    int cY = (temp - 1) % N;
                    int minus = Math.abs(cX - x) + Math.abs(cY - y);
                    man += minus;
                }
            }
        }
        return man;
    }

    /**
     * Estimated distance to goal. This method should
     *               simply return the results of manhattan() when submitted to
     *               Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns true if this board's tile values are the same
     *               position as y's
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board) y;
        return Arrays.equals(this.bd, that.bd);

    }


    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
