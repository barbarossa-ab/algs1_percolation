
public class PercolationStats {

    private int N, T;
    private double x[];

    public PercolationStats(int N, int T) {

        if ((N <= 0) || (T <= 0)) {
            throw new java.lang.IllegalArgumentException();
        }

        this.N = N;
        this.T = T;
        this.x = new double[T];

//        StdOut.printf("N = %d\n", N);
//        StdOut.printf("T = %d\n", T);

        for (int k = 0; k < T; k++) {
            int openNr = 0;
            boolean percolates = false;
            Percolation p = new Percolation(this.N);

            while (!percolates && !(openNr == (N * N))) {
                int i = 1, j = 1;
                boolean foundClosed = false;
                
                while (!foundClosed) {
                    i = StdRandom.uniform(N) + 1; // generate nr in 1..N ranges
                    j = StdRandom.uniform(N) + 1;
                    
                    if (!p.isOpen(i, j)) {
                        foundClosed = true;
                    }
                }
                
                p.open(i, j);
                openNr++;

                percolates = p.percolates();
            }

            if (percolates) {
                x[k] = ((double) openNr) / (N * N);
            }
        }
    }

    public double mean() {
        return StdStats.mean(x);
    }

    public double stddev() {
        return StdStats.stddev(x);
    }

    public double confidenceLo() {
        return (mean() - ((1.96 * stddev()) / (java.lang.Math.sqrt(T))));
    }

    public double confidenceHi() {
        return (mean() + ((1.96 * stddev()) / (java.lang.Math.sqrt(T))));
    }

    public static void main(String args[]) {
        if (args.length != 2) {
            throw new java.lang.IllegalArgumentException();
        }

        PercolationStats ps =
                new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        StdOut.printf("mean                    = %f\n", ps.mean());
        StdOut.printf("stddev                  = %f\n", ps.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", ps.confidenceLo(),
                ps.confidenceHi());
    }
}
