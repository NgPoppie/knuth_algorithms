import java.util.Arrays;

public class LinearProbingHashTable<K, V> {

    private int capacity;
    private int size;
    private K[] keys;
    private V[] values;
    private int totalDisplacement; // Total extra probes across all insertions

    @SuppressWarnings("unchecked")
    public LinearProbingHashTable(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.keys = (K[]) new Object[capacity];
        this.values = (V[]) new Object[capacity];
        this.totalDisplacement = 0;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    /**
     * Inserts key-value pair using linear probing.
     * Returns the displacement (number of extra slots probed beyond home position).
     */
    public int put(K key, V val) {
        if (size >= capacity) {
            throw new IllegalStateException("Hash table is full");
        }

        int home = hash(key);
        int probes = 0;
        int i = home;

        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                values[i] = val; // Update existing key
                return probes;
            }
            probes++;
            i = (i + 1) % capacity; // Linear probe step
        }

        keys[i] = key;
        values[i] = val;
        size++;
        totalDisplacement += probes;

        return probes;
    }

    public V get(K key) {
        int home = hash(key);
        int i = home;

        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                return values[i];
            }
            i = (i + 1) % capacity;
        }

        return null;
    }

    public double getLoadFactor() {
        return (double) size / capacity;
    }

    public double getAverageDisplacement() {
        return size == 0 ? 0.0 : (double) totalDisplacement / size;
    }

    public int getTotalDisplacement() {
        return totalDisplacement;
    }

    public void printTableState() {
        System.out.println("Table Capacity: " + capacity + ", Size: " + size + 
                           " (Load Factor α = " + String.format("%.2f", getLoadFactor()) + ")");
        System.out.println("Keys: " + Arrays.toString(keys));
        System.out.println("Total Displacement (Probes): " + totalDisplacement);
        System.out.println("Average Displacement per Key: " + String.format("%.3f", getAverageDisplacement()));
    }

    public static void main(String[] args) {
        int M = 10; // Table size
        LinearProbingHashTable<String, Integer> table = new LinearProbingHashTable<>(M);

        String[] keysToInsert = { "Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta" };

        System.out.println("--- Linear Probing Insertion Simulation ---");
        for (String key : keysToInsert) {
            int probes = table.put(key, key.length());
            System.out.printf("Inserted '%s' (Home: %d) -> Probes/Displacement: %d%n", 
                              key, (key.hashCode() & 0x7fffffff) % M, probes);
        }

        System.out.println("\n--- Final Hash Table Summary ---");
        table.printTableState();

        double alpha = table.getLoadFactor();
        double theoreticalAvgProbes = 0.5 * (1.0 + (1.0 / (1.0 - alpha)));
        System.out.println("Theoretical Expected Probes for α=" + String.format("%.2f", alpha) + 
                           " is ≈ " + String.format("%.3f", theoreticalAvgProbes));
    }
}
