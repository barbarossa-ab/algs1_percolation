
public class Percolation {

    private boolean grid[][];	// false = closed; true = open
    private WeightedQuickUnionUF uf;
    private int N;

    public Percolation(int N) {
        this.N = N;
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);  // N*N cells + virtual top and bottom
    }

    private void checkBoundaries(int i, int j) {
        if ((i < 1) || (i > N) || (j < 1) || (j > N)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }
    
    private int getTopIndex(){
        return (N * N);
    }
    
    private int getBottomIndex() {
        return (N * N + 1);
    }
    
    // i, j are 0-based
    private int getIndex(int i, int j) {
        return (i * N + j);
    }

    public void open(int i, int j) {
        checkBoundaries(i, j);

        // get into 0-based logic
        i = i-1;
        j = j-1;
        
        grid[i][j] = true;

        // check union with layer above
        if(i == 0) {
            uf.union(getIndex(i, j), getTopIndex());
        } else {
            // i > 0
            if (grid[i - 1][j]) {
                uf.union(getIndex(i, j), getIndex(i-1, j));
            }
        } 

        // check union with layer below
        if(i == (N - 1)) {
            uf.union(getIndex(i, j), getBottomIndex());
        } else {
            if (grid[i + 1][j]) {
                uf.union(getIndex(i, j), getIndex(i+1, j));
            }
        }

        // check union with layer on left
        if (j > 0) {
            if (grid[i][j - 1]) {
                uf.union(getIndex(i, j), getIndex(i, j-1));
            }
        }

        // check union with layer on right
        if (j < (N - 1)) {
            if (grid[i][j + 1]) {
                uf.union(getIndex(i, j), getIndex(i, j+1));
            }
        }


    }

    public boolean isOpen(int i, int j) {
        checkBoundaries(i, j);
        return grid[i-1][j-1];
    }

    public boolean isFull(int i, int j) {
        checkBoundaries(i, j);
        return(uf.connected(getTopIndex(), getIndex(i-1, j-1)));
    }

    public boolean percolates() {
        return (uf.connected(getTopIndex(), getBottomIndex()));
    }

}
