package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Stack;
//union(), find(), connected(), count() 集合的size

public class Percolation {
    private int[][] grid;
    private int gridSize;
    private WeightedQuickUnionUF FullSet;
    private WeightedQuickUnionUF PerSet;
    private int openNum;


    private int XYto1D(int row, int col) {
        return row * gridSize + col;
    }

    /**
     * create N-by-N grid, with all sites initially blocked
     * 0: blocked, 1: open,
     * java.lang.IllegalArgumentException if N <= 0
     */
    public Percolation(int N) {
        if (N > 0) {
            grid = new int[N][N];
            gridSize = N;
            openNum = 0;

            //set all grids as closed
            for (int i = 0; i < N; i++) {
                for (int k = 0; k < N; k++) {
                    grid[i][k] = 0;
                }
            }

            // set the totalSet
            int setSize = N * N + 1;
            FullSet = new WeightedQuickUnionUF(setSize);
            for (int i = 0; i < N - 1; i++) {
                int top = XYto1D(0, i);
                FullSet.union(top, top + 1);
            }
            PerSet = new WeightedQuickUnionUF(setSize);
            for (int i = 0; i < N - 1; i++) {
                int top = XYto1D(0, i);
                PerSet.union(top, top + 1);
                int bt = XYto1D(gridSize - 1, i);
                PerSet.union(bt, bt + 1);
            }


        } else {
            throw new java.lang.IllegalArgumentException
                    ("N should be positive");
        }
    }


    private boolean cornerCase(int row, int col) {
        return (row > gridSize - 1 || row < 0 || col > gridSize - 1 || col < 0);
    }

    private Stack<Position> validNeighbor(int row, int col) {
        Stack<Position> ans = new Stack<>();

        int upX = row - 1;
        int dnX = row + 1;
        int leftY = col - 1;
        int rightY = col + 1;

        if (!cornerCase(upX, col)) {
            ans.push(new Position(upX, col));
        }
        if (!cornerCase(dnX, col)) {
            ans.push(new Position(dnX, col));
        }
        if (!cornerCase(row, leftY)) {
            ans.push(new Position(row, leftY));
        }
        if (!cornerCase(row, rightY)) {
            ans.push(new Position(row, rightY));
        }

        return ans;
    }

    /**
     * open the site (row, col) if it is not open already
     * Throw a java.lang.IndexOutOfBoundsException in corner cases
     */
    public void open(int row, int col) {
        if (cornerCase(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("corner cases");
        } else if (grid[row][col] == 0) {
            grid[row][col] = 1;
            openNum += 1;
            // connect neighbor


            Stack<Position> neighbor = validNeighbor(row, col);
            while (!(neighbor.isEmpty())) {
                Position n = neighbor.pop();
                if (isOpen(n.x, n.y)) {
                    int thisSetNum = XYto1D(row, col);
                    int neighborSetNum = XYto1D(n.x, n.y);
                    FullSet.union(thisSetNum, neighborSetNum);
                    PerSet.union(thisSetNum, neighborSetNum);
                }
            }
        }
    }


    /**
     * is the site (row, col) open?
     * Throw a java.lang.IndexOutOfBoundsException in corner cases
     */
    public boolean isOpen(int row, int col) {
        if (cornerCase(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("corner cases");
        } else {
            return grid[row][col] == 1;
        }
    }


    /**
     * is the site (row, col) full?
     * full = open + last row connected to the first row
     * Throw a java.lang.IndexOutOfBoundsException in corner cases
     */
    public boolean isFull(int row, int col) {
        if (cornerCase(row, col)) {
            throw new java.lang.IndexOutOfBoundsException("corner cases");
        } else {
            int temp = XYto1D(row, col);
            return isOpen(row, col) && FullSet.connected(0, temp);
        }
    }

    /**
     * @return number of open sites
     * big O = c
     */
    public int numberOfOpenSites() {
        return openNum;
    }


    /**
     * @return does the system percolate?
     */
    public boolean percolates() {
        return PerSet.connected(0, gridSize * (gridSize - 1));
    }


    /**
     * use for unit testing (not required)
     */
    public static void main(String[] args) {
        Percolation y = new Percolation(4);
        y.open(3,1);
        y.open(0,1);
        y.open(2,2);
        y.open(2,1);
        y.open(1,1);

        //测试初始化
        /*Percolation x = new Percolation(4);
        System.out.println(x.totalSet.find(0));//0
        System.out.println(x.totalSet.find(1));//0
        System.out.println(x.totalSet.find(2));//0
        System.out.println(x.totalSet.find(3));//0
        System.out.println(x.totalSet.find(15));//12
        System.out.println(x.totalSet.find(14));//12
        System.out.println(x.totalSet.find(12));//12
        System.out.println(x.totalSet.find(13));//12

        System.out.println(x.totalSet.find(6));//6
        System.out.println(x.totalSet.find(11));//11
        System.out.println(x.totalSet.find(7));//7

        //test open
        x.open(2, 2);
        x.open(2, 1);
        x.open(0, 1);
        x.open(1, 1);
        x.open(1, 3);
        System.out.println(x.totalSet.find(10));//0
        System.out.println(x.totalSet.find(9));//0
        System.out.println(x.totalSet.find(5));//0
        System.out.println(x.totalSet.find(1));//0

        System.out.println(x.isFull(2, 2));//true
        System.out.println(x.isFull(1, 3));//false

        // test percolates
        x.open(3, 1);
        System.out.println(x.percolates());//true
        System.out.println(x.totalSet.find(12));//0


        Percolation y = new Percolation(10);
        y.open(9, 5);
        y.open(8, 5);
        y.open(4, 5);
        System.out.println(y.percolates());//false*/





    }
}
