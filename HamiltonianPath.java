import java.util.Arrays;

public class HamiltonianPath {

    private final int numVertices;
    private final int[][] graph;
    private final int[] path;

    public HamiltonianPath(int[][] graph) {
        this.graph = graph;
        this.numVertices = graph.length;
        this.path = new int[numVertices];
        Arrays.fill(path, -1);
    }

    /**
     * Checks if vertex v can be added at index 'pos' in the Hamiltonian Path.
     */
    private boolean isSafe(int v, int pos) {
        // Check if this vertex is connected to the previous vertex
        if (graph[path[pos - 1]][v] == 0) {
            return false;
        }

        // Check if the vertex has already been included in the path
        for (int i = 0; i < pos; i++) {
            if (path[i] == v) {
                return false;
            }
        }

        return true;
    }

    private boolean solvePathUtil(int pos) {
        // Base case: If all vertices are in the path
        if (pos == numVertices) {
            return true;
        }

        // Try different vertices as the next candidate in Hamiltonian Path
        for (int v = 0; v < numVertices; v++) {
            if (isSafe(v, pos)) {
                path[pos] = v;

                if (solvePathUtil(pos + 1)) {
                    return true;
                }

                // Backtrack
                path[pos] = -1;
            }
        }

        return false;
    }

    /**
     * Solves the Hamiltonian Path problem starting from every possible vertex.
     */
    public boolean findHamiltonianPath() {
        for (int startVertex = 0; startVertex < numVertices; startVertex++) {
            path[0] = startVertex;
            if (solvePathUtil(1)) {
                System.out.println("Hamiltonian Path found starting at vertex " + startVertex + ":");
                System.out.println(Arrays.toString(path));
                return true;
            }
        }

        System.out.println("No Hamiltonian Path exists.");
        return false;
    }

    public static void main(String[] args) {
        // Example graph represented as an adjacency matrix
        int[][] graph = {
            {0, 1, 0, 1, 0},
            {1, 0, 1, 1, 1},
            {0, 1, 0, 0, 1},
            {1, 1, 0, 0, 1},
            {0, 1, 1, 1, 0}
        };

        HamiltonianPath hp = new HamiltonianPath(graph);
        hp.findHamiltonianPath();
    }
}
