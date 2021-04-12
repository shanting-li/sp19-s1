package hw2;
import  edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;


public class PercolationStats {
    private double[] thresholds;
    private int gridSize;
    private int times;

    /**
     * perform T independent experiments on an N-by-N grid
     * throw java.lang.IllegalArgumentException if N<=0 or T<=0
     * @param N
     * @param T
     * @param pf
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("T and N should be positive.");
        } else {
            gridSize = N;
            times = T;
            thresholds = new double[T];
            for (int i = 0; i < T; i++) {
                thresholds[i] = oneTimeExperiment(N, pf);
            }
        }
    }

    /**
     * one experiment, return the threshold of a successfully percolating experiment
     */
    private double oneTimeExperiment(int N, PercolationFactory pf) {
        Percolation grid = pf.make(N);
        double totalGrid = N * N;

        while (true) {
            int indexToOpen = StdRandom.uniform(N * N);
            Position p = numTo2D(indexToOpen);
            grid.open(p.x, p.y);

            if (grid.percolates()) {
                double ans = grid.numberOfOpenSites() / totalGrid;
                return ans;
            }
        }

    }

    private Position numTo2D(int num) {
        //num = row * gridSize + col;
        int col = num % gridSize;
        int row = (num - col) / gridSize;
        Position ans = new Position(row, col);
        return ans;
    }

    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * sample standard deviation of percolation threshold
     * @return
     */
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    /**
     * low endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLow() {
        double ans =  mean() - 1.96 * stddev() / Math.sqrt(times);
        return ans;
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHigh() {
        double ans = mean() + 1.96 * stddev() / Math.sqrt(times);
        return ans;
    }

    /*public static void main(String[] args) {
        //permutation, 输入n，返回0-n-1的随机排列
        int[] num = StdRandom.permutation(4 * 4);
        for(int n : num) {
            System.out.println(n);
        }

        //test uniform
        int totalToOpen = StdRandom.uniform(4 * 4);
        System.out.println(totalToOpen);
        int[] indexToOpen = StdRandom.permutation(4*4, totalToOpen);
        for(int n : indexToOpen) {
            System.out
        PercolationFactory a = new PercolationFactory();
        PercolationStats x = new PercolationStats(20, 10, a);
        System.out.println(x.mean());
        System.out.println(x.confidenceLow());
        System.out.println(x.confidenceHigh());
    }*/



}
