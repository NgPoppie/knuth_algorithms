import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KnightsTourWarnsdorff {

    private static final int N = 8; // Board size N x N

    // All 8 possible moves for a Knight
    private static final int[] DX = { 2, 1, -1, -2, -2, -1, 1, 2 };
    private static final int[] DY = { 1, 2, 2, 1, -1, -2, -2, -1 };

    static class Cell {
        int x, y;
        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static boolean isValid(int x, int y, int[][] board) {
        return (x >= 0 && x < N && y >= 0 && y < N && board[x][y] == -1);
    }

    /**
     * Counts valid onward moves from position (x, y).
     */
    private static int getDegree(int x, int y, int[][] board) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            if (isValid(x + DX[i], y + DY[i], board)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds a Knight's Tour starting from (startX, startY).
     */
    public static boolean solveKnightsTour(int startX, int startY) {
        int[][] board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = -1;
            }
        }

        int currX = startX;
        int currY = startY;
        board[currX][currY] = 0; // Mark starting square

        for (int move = 1; move < N * N; move++) {
            List<Cell> candidates = new ArrayList<>();

            for (int i = 0; i < 8; i++) {
                int nextX = currX + DX[i];
                int nextY = currY + DY[i];
                if (isValid(nextX, nextY, board)) {
                    candidates.add(new Cell(nextX, nextY));
                }
            }

            if (candidates.isEmpty()) {
                System.out.println("No valid moves left. Tour incomplete.");
                return false;
            }

            // Warnsdorff's Rule: Choose candidate with minimum degree (fewest onward options)
            candidates.sort(Comparator.comparingInt(c -> getDegree(c.x, c.y, board)));

            Cell nextMove = candidates.get(0);
            currX = nextMove.x;
            currY = nextMove.y;
            board[currX][currY] = move;
        }

        printBoard(board);
        return true;
    }

    private static void printBoard(int[][] board) {
        System.out.println("Knight's Tour Matrix:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%2d ", board[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Start from top-left corner (0, 0)
        solveKnightsTour(0, 0);
    }
}
