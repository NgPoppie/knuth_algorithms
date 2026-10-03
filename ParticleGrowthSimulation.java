import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ParticleGrowthSimulation {

    /**
     * Simulates the 1D particle system (ones moving past zeros).
     * Tracks the resulting Young diagram row lengths (N1, N2, ...).
     *
     * @param numParticles Number of 1s
     * @param numZeros     Number of 0s
     * @param maxSteps     Total swap steps to perform
     */
    public static void simulate(int numParticles, int numZeros, int maxSteps) {
        int totalLength = numParticles + numZeros;
        int[] bitArray = new int[totalLength];

        // Start with all 1s on left, all 0s on right: [1, 1, ..., 0, 0, ...]
        for (int i = 0; i < numParticles; i++) {
            bitArray[i] = 1;
        }

        Random rng = new Random();
        int steps = 0;

        while (steps < maxSteps) {
            // Pick eligible (1, 0) pairs
            List<Integer> validIndices = new ArrayList<>();
            for (int i = 0; i < totalLength - 1; i++) {
                if (bitArray[i] == 1 && bitArray[i + 1] == 0) {
                    validIndices.add(i);
                }
            }

            if (validIndices.isEmpty()) {
                break; // Fully sorted (all 0s on left, 1s on right)
            }

            // Pick a random valid (1, 0) pair to swap
            int k = validIndices.get(rng.nextInt(validIndices.size()));
            bitArray[k] = 0;
            bitArray[k + 1] = 1;
            steps++;
        }

        // Calculate partition shape (row lengths of Young diagram)
        int[] partition = computePartitionShape(bitArray, numParticles);
        System.out.println("Bit array after " + steps + " steps: " + Arrays.toString(bitArray));
        System.out.println("Partition profile (row lengths): " + Arrays.toString(partition));
    }

    private static int[] computePartitionShape(int[] bitArray, int numParticles) {
        int[] rowLengths = new int[numParticles];
        int zerosEncountered = 0;
        int particleIndex = numParticles - 1;

        // Traverse right to left to compute zeros crossed by each 1
        for (int i = bitArray.length - 1; i >= 0; i--) {
            if (bitArray[i] == 0) {
                zerosEncountered++;
            } else if (particleIndex >= 0) {
                rowLengths[particleIndex--] = zerosEncountered;
            }
        }

        return rowLengths;
    }

    public static void main(String[] args) {
        int particles = 10;
        int zeros = 10;
        int steps = 50;

        System.out.println("Simulating 1D Particle System Growth...");
        simulate(particles, zeros, steps);
    }
}
