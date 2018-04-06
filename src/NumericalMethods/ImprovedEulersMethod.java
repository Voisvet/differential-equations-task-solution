package NumericalMethods;

import javafx.scene.chart.XYChart;

/**
 * Implementation of Improved Euler's method for solving o.d.e.
 */
public class ImprovedEulersMethod implements Method {
    public XYChart.Series<Number, Number> solve(double x0, double X, double y0, int N){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        double stepSize = (X - x0) / N;
        double XNth = x0;
        double YNth = y0;
        double YTilda;
        double temp;
        series.getData().add(new XYChart.Data<>(XNth, YNth));
        series.setName("Improved Euler's Method");

        for (int i = 0; i < N; i++) {
            YTilda = YNth + stepSize * Function.calculateDerivative(XNth, YNth);
            temp = Function.calculateDerivative(XNth, YNth);
            XNth += stepSize;

            YNth += (stepSize / 2) * (temp + Function.calculateDerivative(XNth, YTilda));
            series.getData().add(new XYChart.Data<>(XNth, YNth));
        }
        return series;
    }
}
