package NumericalMethods;

import javafx.scene.chart.XYChart;

/**
 * This class calculates series of points for original function
 */
public class OriginalGraph implements Method {
    public XYChart.Series<Number, Number> solve(double x0, double X, double y0, int N){
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        double stepSize = (X - x0) / N;
        double XNth = x0;
        double YNth = y0;
        Function.recalculateConstant(x0, y0);
        series.getData().add(new XYChart.Data<>(XNth, YNth));
        series.setName("Original Function");

        for (int i = 0; i < N; i++) {
            XNth += stepSize;
            YNth = Function.calculateFunction(XNth);
            series.getData().add(new XYChart.Data<>(XNth, YNth));
        }
        return series;
    }
}
