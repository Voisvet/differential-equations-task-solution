package NumericalMethods;

import javafx.scene.chart.XYChart;

/**
 * Implementation of Runge-Kutta method for solving o.d.e.
 */
public class RungeKuttaMethod implements Method {
    public XYChart.Series<Number, Number> solve(double x0, double X, double y0, int N){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        double stepSize = (X - x0) / N;
        double XNth = x0;
        double YNth = y0;
        double k1, k2, k3, k4;
        series.getData().add(new XYChart.Data<>(XNth, YNth));
        series.setName("Runge-Kutta Method");

        for (int i = 0; i < N; i++) {
            k1 = Function.calculateDerivative(XNth, YNth);
            k2 = Function.calculateDerivative(XNth + stepSize / 2, YNth + k1 * stepSize / 2);
            k3 = Function.calculateDerivative(XNth + stepSize / 2, YNth + k2 * stepSize / 2);
            k4 = Function.calculateDerivative(XNth + stepSize, YNth + k3 * stepSize);
            YNth += (stepSize / 6) * (k1 + 2*k2 + 2*k3 + k4);
            XNth += stepSize;
            series.getData().add(new XYChart.Data<>(XNth, YNth));
        }
        return series;
    }
}
