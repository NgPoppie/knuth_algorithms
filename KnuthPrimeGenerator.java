import java.util.ArrayList;
import java.util.List;

public class KnuthPrimeGenerator {

    /**
     * Generates all prime numbers up to n using trial division against 
 
     */
    public static List<Integer> generatePrimes(int limit) {
        List<Integer> primes = new ArrayList<>();
        if (limit < 2) return primes;

        // 2 is the only even prime
        primes.add(2);

        // Check odd candidate numbers starting from 3
        for (int candidate = 3; candidate <= limit; candidate += 2) {
            boolean isPrime = true;

            for (int prime : primes) {
                // If the prime divisor exceeds sqrt(candidate), stop testing
                if (prime * prime > candidate) {
                    break;
                }
                // If divisible, it's not prime
                if (candidate % prime == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                primes.add(candidate);
            }
        }

        return primes;
    }

    public static void main(String[] args) {
        int limit = 100;
        List<Integer> primes = generatePrimes(limit);
        System.out.println("Primes up to " + limit + ": " + primes);
    }
}
