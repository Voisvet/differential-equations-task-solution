package NumericalMethods;

import javafx.scene.chart.XYChart;

/**
 * Implementation of Euler's method for solving o.d.e.
 */
public class EulersMethod implements Method {
    public XYChart.Series<Number, Number> solve(double x0, double X, double y0, int N){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        double stepSize = (X - x0) / N;
        double XNth = x0;
        double YNth = y0;
        series.getData().add(new XYChart.Data<>(XNth, YNth));
        series.setName("Euler's Method");

        for (int i = 0; i < N; i++) {
            YNth += stepSize * Function.calculateDerivative(XNth, YNth);
            XNth += stepSize;
            series.getData().add(new XYChart.Data<>(XNth, YNth));
        }
        return series;
    }
}
