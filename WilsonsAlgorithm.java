import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WilsonsAlgorithm {

    static class Edge {
        int u, v;
        Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public String toString() {
            return "(" + u + " - " + v + ")";
        }
    }

    /**
     * Generates a Uniform Random Spanning Tree using Wilson's Algorithm
     * (Loop-Erased Random Walk).
     */
    public static List<Edge> generateRandomSpanningTree(int numVertices, List<List<Integer>> adjList) {
        boolean[] inTree = new boolean[numVertices];
        int[] next = new int[numVertices];
        Arrays.fill(next, -1);

        Random rng = new Random();

        // 1. Root the tree arbitrarily at vertex 0
        inTree[0] = true;

        // 2. Add each vertex to the tree via loop-erased random walks
        for (int i = 1; i < numVertices; i++) {
            int u = i;

            // Perform random walk until reaching a vertex already in the tree
            while (!inTree[u]) {
                List<Integer> neighbors = adjList.get(u);
                int v = neighbors.get(rng.nextInt(neighbors.size()));
                next[u] = v; // Maintains trajectory (overwrites loops implicitly)
                u = v;
            }

            // Add the loop-erased path to the tree
            u = i;
            while (!inTree[u]) {
                inTree[u] = true;
                u = next[u];
            }
        }

        // Reconstruct edges of the spanning tree
        List<Edge> spanningTree = new ArrayList<>();
        for (int v = 1; v < numVertices; v++) {
            spanningTree.add(new Edge(v, next[v]));
        }

        return spanningTree;
    }

    public static void main(String[] args) {
        int N = 5;
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < N; i++) adjList.add(new ArrayList<>());

        // Construct complete graph K_5
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j) adjList.get(i).add(j);
            }
        }

        List<Edge> randomTree = generateRandomSpanningTree(N, adjList);
        System.out.println("Uniform Random Spanning Tree edges: " + randomTree);
    }
}
