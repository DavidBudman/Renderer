package util;

public class Util {

    // It is binary, equivalent to ~1/1,000,000 in decimal (6 digits)
    private static final int ACCURACY = -20;

    // double store format: seee eeee eeee (1.)mmmm … mmmm
    // 1 bit sign, 11 bits exponent, 53 bits (52 stored) normalized mantissa
    private static int getExp(double num) {
        return (int) ((Double.doubleToRawLongBits(num) >> 52) & 0x7FFL) - 1023;
    }

    public static boolean isZero(double number) {
        return getExp(number) < ACCURACY;
    }

    public static double mult(double lhs, double rhs) { // lhs*rhs
        return isZero(rhs - 1) ? lhs : (isZero(lhs - 1) ? rhs : lhs * rhs);
    }

    public static double div(double numerator, double denominator) { // lhs/rhs
        // ensure quotient not 0
        if (isZero(denominator))
            throw new ArithmeticException("Divide by zero error");

        return isZero(numerator) ? 0.0 : numerator / denominator;
    } // end method div

    public static double subtract(double lhs, double rhs) { // lhs-rhs
        int lhsExp = getExp(lhs);
        int rhsExp = getExp(rhs);

        // if other is too small relatively to our coordinate return the original coordinate
        if (rhsExp - lhsExp < ACCURACY) return lhs;

        // if our coordinate is too small relatively to other return negative of other
        if (lhsExp - rhsExp < ACCURACY) return -rhs;

        double result = lhs - rhs;
        int resExp = getExp(result);

        // if the result is relatively small - tell that it is zero
        return resExp - lhsExp < ACCURACY ? 0.0 : result;
    }

    public static double add(double lhs, double rhs) { // lhs+rhs
        int lhsExp = getExp(lhs);
        int rhsExp = getExp(rhs);

        // if other is too small relatively to our coordinate return the original coordinate
        if (rhsExp - lhsExp < ACCURACY) return lhs;

        // if our coordinate is too small relatively to other return other
        if (lhsExp - rhsExp < ACCURACY) return rhs;

        double result = lhs + rhs;
        int resExp = getExp(result);

        // if the result is relatively small - tell that it is zero
        return resExp - lhsExp < ACCURACY ? 0.0 : result;
    }
}