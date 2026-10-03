public class EuclideanGCD {

    /**
     * Calculates the Greatest Common Divisor (GCD) of two non-negative integers 
     * using Euclid's Algorithm.
     */
    public static long gcd(long a, long b) {
        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    public static void main(String[] args) {
        long num1 = 1071;
        long num2 = 462;
        System.out.println("GCD of " + num1 + " and " + num2 + " is: " + gcd(num1, num2));
    }
}
