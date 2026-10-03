import java.util.ArrayList;
import java.util.List;

public class GarsiaWachsAlgorithm {

    static class Node {
        int weight;
        int depth;
        char symbol;
        Node left, right;

        Node(int weight, char symbol) {
            this.weight = weight;
            this.symbol = symbol;
        }

        Node(Node left, Node right) {
            this.weight = left.weight + right.weight;
            this.left = left;
            this.right = right;
        }
    }

    public static List<Node> buildAlphabeticTree(char[] symbols, int[] weights) {
        List<Node> list = new ArrayList<>();
        for (int i = 0; i < symbols.length; i++) {
            list.add(new Node(weights[i], symbols[i]));
        }

        // Phase 1: Combination & Leftward Relocation
        while (list.size() > 1) {
            int k = 1;
            // Find first k such that w[k-1] <= w[k+1]
            while (k < list.size() - 1) {
                if (list.get(k - 1).weight <= list.get(k + 1).weight) {
                    break;
                }
                k++;
            }

            // Combine list[k-1] and list[k]
            Node combined = new Node(list.get(k - 1), list.get(k));
            list.remove(k);
            list.remove(k - 1);

            // Shift left until list[j-1].weight >= combined.weight
            int j = k - 1;
            while (j > 0 && list.get(j - 1).weight < combined.weight) {
                j--;
            }
            list.add(j, combined);
        }

        return list;
    }

    public static void computeDepths(Node root, int currentDepth) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            root.depth = currentDepth;
            return;
        }
        computeDepths(root.left, currentDepth + 1);
        computeDepths(root.right, currentDepth + 1);
    }

    public static void printAlphabeticCodes(Node root, String code) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            System.out.println(root.symbol + " (Weight " + root.weight + "): " + code);
            return;
        }
        printAlphabeticCodes(root.left, code + "0");
        printAlphabeticCodes(root.right, code + "1");
    }

    public static void main(String[] args) {
        // Symbols MUST maintain alphabetic / sequence order
        char[] symbols = { 'A', 'B', 'C', 'D', 'E' };
        int[] weights = { 10, 15, 20, 25, 30 };

        List<Node> result = buildAlphabeticTree(symbols, weights);
        Node root = result.get(0);

        computeDepths(root, 0);

        System.out.println("Optimal Alphabetic Codes (Order Preserved):");
        printAlphabeticCodes(root, "");
    }
}
