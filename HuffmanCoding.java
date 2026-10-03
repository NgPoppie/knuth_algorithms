import java.util.PriorityQueue;

public class HuffmanCoding {

    static class Node implements Comparable<Node> {
        int weight;
        char symbol;
        Node left, right;

        Node(int weight, char symbol) {
            this.weight = weight;
            this.symbol = symbol;
        }

        Node(int weight, Node left, Node right) {
            this.weight = weight;
            this.symbol = '\0';
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static Node buildHuffmanTree(char[] symbols, int[] weights) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 0; i < symbols.length; i++) {
            pq.add(new Node(weights[i], symbols[i]));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left.weight + right.weight, left, right);
            pq.add(parent);
        }

        return pq.poll();
    }

    public static void printCodes(Node root, String code) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            System.out.println(root.symbol + ": " + code);
            return;
        }
        printCodes(root.left, code + "0");
        printCodes(root.right, code + "1");
    }

    public static void main(String[] args) {
        char[] symbols = { 'A', 'B', 'C', 'D', 'E' };
        int[] weights = { 10, 15, 20, 25, 30 };

        Node root = buildHuffmanTree(symbols, weights);
        System.out.println("Huffman Codes (Unconstrained):");
        printCodes(root, "");
    }
}
