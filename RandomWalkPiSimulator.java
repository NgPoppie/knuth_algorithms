import java.util.Random;

public class RandomWalkPiSimulator {

    /**
     * Simulates a 2N-step 1D random walk and checks if it lands back at 0.
     */
    public static boolean simulateWalk(int n, Random rng) {
        int position = 0;
        int steps = 2 * n;

        for (int i = 0; i < steps; i++) {
            position += rng.nextBoolean() ? 1 : -1;
        }

        return position == 0;
    }

    public static void main(String[] args) {
        int N = 100; // Walk length 2N = 200
        int trials = 500_000;
        Random rng = new Random();

        int returnCount = 0;
        for (int i = 0; i < trials; i++) {
            if (simulateWalk(N, rng)) {
                returnCount++;
            }
        }

        double empiricalProb = (double) returnCount / trials;
        double theoreticalProb = 1.0 / Math.sqrt(Math.PI * N);

        System.out.println("--- Random Walk Return Probability (2N = " + (2 * N) + " steps) ---");
        System.out.printf("Empirical Probability (%d trials) : %.6f%n", trials, empiricalProb);
        System.out.printf("Theoretical Probability (1/√(πN)) : %.6f%n", theoreticalProb);
        System.out.printf("Relative Difference               : %.4f%%%n", 
                          Math.abs(empiricalProb - theoreticalProb) / theoreticalProb * 100);
    }
}
