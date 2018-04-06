package NumericalMethods;

import javafx.scene.chart.XYChart;

public interface Method {
    /**
     * Solve equation using some numerical method
     *
     * @param x0 starting value of variable
     * @param X end value of variable
     * @param y0 initial value of function
     * @param N number of steps performed
     * @return series with points on plot
     */
    XYChart.Series<Number, Number> solve(double x0, double X, double y0, int N);
}
