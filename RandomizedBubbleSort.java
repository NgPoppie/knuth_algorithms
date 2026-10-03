import java.util.Arrays;
import java.util.Random;

public class RandomizedBubbleSort {

    /**
     * Performs randomized bubble sort on an array.
     * Selects a random adjacent index k in [0, N-2] and swaps if arr[k] > arr[k+1].
     * 
     * @param arr The array to sort
     * @return Total number of adjacent comparison/swap steps attempted
     */
    public static long sort(int[] arr) {
        int n = arr.length;
        Random rng = new Random();
        long steps = 0;

        while (!isSorted(arr)) {
            steps++;
            // Choose a random adjacent index k from 0 to n - 2
            int k = rng.nextInt(n - 1);

            // Swap if out of order
            if (arr[k] > arr[k + 1]) {
                int temp = arr[k];
                arr[k] = arr[k + 1];
                arr[k + 1] = temp;
            }
        }

        return steps;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 20;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - i; // Worst case initial order: reverse sorted
        }

        System.out.println("Initial: " + Arrays.toString(arr));
        long totalSteps = sort(arr);
        System.out.println("Sorted:  " + Arrays.toString(arr));
        System.out.println("Total comparison/swap attempts: " + totalSteps);
        System.out.println("Theoretical 4*N^2 baseline: " + (4 * n * n));
    }
}
