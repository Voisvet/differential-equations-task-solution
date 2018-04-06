package NumericalMethods;

public class Function {
    private static double C = 0;

    /**
     * Calculate value of differential equation
     *
     * @param x value of variable
     * @param y value of function
     * @return value of derivative
     */
    public static double calculateDerivative(double x, double y) {
        return (1 - 2*y)*Math.exp(x) + Math.pow(y, 2) + Math.exp(2*x);
    }

    /**
     * calculate value of function with given value of x
     *
     * @param x value of variable
     * @return value of function at x
     */
    public static double calculateFunction(double x) {
        return Math.exp(x) - 1 / (x + C);
    }

    /**
     * Recalculate constant for function
     *
     * @param x initial value of variable
     * @param y initial value of function at given initial value of variable
     */
    public static void recalculateConstant(double x, double y) {
        C = 1 / (Math.exp(x) - y) - x;
    }

    public static double getConstant() {
        return C;
    }
}
