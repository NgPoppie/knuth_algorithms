public class MatrixTreeTheorem {

    /**
     * Counts the total number of spanning trees of an undirected graph
     * using Kirchhoff's Matrix Tree Theorem.
     */
    public static long countSpanningTrees(int[][] adjMatrix) {
        int n = adjMatrix.length;
        if (n <= 1) return 1;

        // 1. Construct Laplacian Matrix L = Degree Matrix - Adjacency Matrix
        double[][] laplacian = new double[n][n];
        for (int i = 0; i < n; i++) {
            int degree = 0;
            for (int j = 0; j < n; j++) {
                if (i != j && adjMatrix[i][j] > 0) {
                    laplacian[i][j] = -adjMatrix[i][j];
                    degree += adjMatrix[i][j];
                }
            }
            laplacian[i][i] = degree;
        }

        // 2. Reduce to (N-1) x (N-1) submatrix by deleting the last row & column
        double[][] subMatrix = new double[n - 1][n - 1];
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                subMatrix[i][j] = laplacian[i][j];
            }
        }

        // 3. Compute Determinant of the submatrix
        return Math.round(determinant(subMatrix));
    }

    private static double determinant(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) return matrix[0][0];
        if (n == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        double det = 0;
        for (int col = 0; col < n; col++) {
            double[][] sub = createSubMatrix(matrix, 0, col);
            det += Math.pow(-1, col) * matrix[0][col] * determinant(sub);
        }
        return det;
    }

    private static double[][] createSubMatrix(double[][] matrix, int excludingRow, int excludingCol) {
        int n = matrix.length;
        double[][] result = new double[n - 1][n - 1];
        int r = -1;
        for (int i = 0; i < n; i++) {
            if (i == excludingRow) continue;
            r++;
            int c = -1;
            for (int j = 0; j < n; j++) {
                if (j == excludingCol) continue;
                c++;
                result[r][c] = matrix[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // Complete graph K_4 (4 vertices)
        int[][] K4 = {
            {0, 1, 1, 1},
            {1, 0, 1, 1},
            {1, 1, 0, 1},
            {1, 1, 1, 0}
        };

        long count = countSpanningTrees(K4);
        System.out.println("Spanning trees in K4: " + count);
        System.out.println("Cayley's Formula (4^(4-2)): " + (long) Math.pow(4, 4 - 2));
    }
}
