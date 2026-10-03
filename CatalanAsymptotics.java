import java.math.BigInteger;

public class CatalanAsymptotics {

    /**
     * Calculates the exact N-th Catalan number C_N = (2N)! / ((N+1)! * N!)
     */
    public static BigInteger getExactCatalan(int n) {
        BigInteger num = factorial(2 * n);
        BigInteger den = factorial(n + 1).multiply(factorial(n));
        return num.divide(den);
    }

    private static BigInteger factorial(int k) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= k; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    /**
     * Computes the asymptotic approximation C_N ~ 4^N / (sqrt(pi) * N^(3/2))
     */
    public static double getApproximateCatalan(int n) {
        double numerator = Math.pow(4, n);
        double denominator = Math.sqrt(Math.PI) * Math.pow(n, 1.5);
        return numerator / denominator;
    }

    public static void main(String[] args) {
        int[] testCases = { 10, 50, 100, 500 };

        System.out.println("--- Catalan Numbers & Asymptotic Limits (Pi Convergence) ---");
        for (int n : testCases) {
            BigInteger exact = getExactCatalan(n);
            double approx = getApproximateCatalan(n);

            System.out.println("N = " + n);
            System.out.println("  Exact C_N      : " + exact);
            System.out.println("  Approx (via π) : " + String.format("%.6e", approx));
            System.out.println("  Log10 Ratio    : " + String.format("%.4f", 
                Math.log10(exact.doubleValue()) - Math.log10(approx)));
            System.out.println();
        }
    }
}
