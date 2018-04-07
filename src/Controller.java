import NumericalMethods.*;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    //------------------------------------------------------------
    //
    // Elements of interface
    //
    //------------------------------------------------------------

    @FXML
    private TextField X;
    @FXML
    private TextField x0;
    @FXML
    private TextField N;
    @FXML
    private TextField y0;
    @FXML
    private TextField Nmax;
    @FXML
    private TextField Nmin;

    @FXML
    private CheckBox euler;
    @FXML
    private CheckBox improvedEuler;
    @FXML
    private CheckBox rungeKutta;
    @FXML
    private CheckBox original;

    @FXML
    private LineChart<Number, Number> functions;
    @FXML
    private LineChart<Number, Number> errors;
    @FXML
    private LineChart<Number, Number> maxErrors;

    @FXML
    private Label warning;

    //------------------------------------------------------------
    //
    // Different series and methods declaration
    //
    //------------------------------------------------------------

    private XYChart.Series<Number, Number> eulersSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> improvedEulersSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> rungeKuttaSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> originalSeries = new XYChart.Series<>();

    private XYChart.Series<Number, Number> eulersErrorSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> improvedEulersErrorSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> rungeKuttaErrorSeries = new XYChart.Series<>();

    private XYChart.Series<Number, Number> eulersMaxErrorSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> improvedEulersMaxErrorSeries = new XYChart.Series<>();
    private XYChart.Series<Number, Number> rungeKuttaMaxErrorSeries = new XYChart.Series<>();

    private EulersMethod eulersMethod = new EulersMethod();
    private ImprovedEulersMethod improvedEulersMethod = new ImprovedEulersMethod();
    private RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod();
    private OriginalGraph originalGraph = new OriginalGraph();

    //------------------------------------------------------------
    //
    // Functions implementation
    //
    //------------------------------------------------------------

    public Controller() {
    }

    /**
     * Initialization of application
     */
    @FXML
    private void initialize() {
        addListenersToCheckboxes();
        addListenersToTextFields();
        setNamesToSeries();

        maxErrors.getData().add(eulersMaxErrorSeries);
        maxErrors.getData().add(improvedEulersMaxErrorSeries);
        maxErrors.getData().add(rungeKuttaMaxErrorSeries);

        recalculate();
        recalculateMaxErrors();
    }

    /**
     * Add listeners to text fields.
     * Listeners check input values of text fields and recalculates series.
     */
    private void addListenersToTextFields() {
        X.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNumbers(X, newValue, oldValue);
            recalculate();
        });
        x0.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNumbers(x0, newValue, oldValue);
            recalculate();
        });
        y0.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNumbers(y0, newValue, oldValue);
            recalculate();
        });
        N.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNumbers(N, newValue, oldValue);
            recalculate();
        });
        Nmin.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNumbers(Nmin, newValue, oldValue);
            recalculateMaxErrors();
        });
        Nmax.textProperty().addListener((observable, oldValue, newValue) -> {
            checkNumbers(Nmax, newValue, oldValue);
            recalculateMaxErrors();
        });
    }

    /**
     * Add listeners to checkboxes.
     * Listeners add or delete series on plots
     */
    private void addListenersToCheckboxes() {
        euler.selectedProperty().addListener((observable, oldValue, newValue) -> {
            drawGraph(newValue, eulersSeries, functions);
            drawGraph(newValue, eulersErrorSeries, errors);
        });
        improvedEuler.selectedProperty().addListener((observable, oldValue, newValue) -> {
            drawGraph(newValue, improvedEulersSeries, functions);
            drawGraph(newValue, improvedEulersErrorSeries, errors);
        });
        rungeKutta.selectedProperty().addListener((observable, oldValue, newValue) -> {
            drawGraph(newValue, rungeKuttaSeries, functions);
            drawGraph(newValue, rungeKuttaErrorSeries, errors);
        });
        original.selectedProperty().addListener((observable, oldValue, newValue) ->
                drawGraph(newValue, originalSeries, functions));
    }

    /**
     * Set names to different series on plots
     */
    private void setNamesToSeries() {
        eulersSeries.setName("Euler's Method");
        eulersErrorSeries.setName("Euler's Method");
        eulersMaxErrorSeries.setName("Euler's Method");
        improvedEulersSeries.setName("Improved Euler's Method");
        improvedEulersErrorSeries.setName("Improved Euler's Method");
        improvedEulersMaxErrorSeries.setName("Improved Euler's Method");
        rungeKuttaSeries.setName("Runge-Kutta Method");
        rungeKuttaErrorSeries.setName("Runge-Kutta Method");
        rungeKuttaMaxErrorSeries.setName("Runge-Kutta Method");
        originalSeries.setName("Original Function");
    }

    /**
     * Recalculate series on plots from first tab
     */
    private void recalculate() {
        double x0 = Double.parseDouble(this.x0.getText());
        double X = Double.parseDouble(this.X.getText());
        double y0 = Double.parseDouble(this.y0.getText());
        int N = Integer.parseInt(this.N.getText());
        Function.recalculateConstant(x0, y0);

        if (- Function.getConstant() >= x0 && - Function.getConstant() <= X) {
            warning.setVisible(true);
        } else {
            originalSeries.getData().clear();
            eulersSeries.getData().clear();
            improvedEulersSeries.getData().clear();
            rungeKuttaSeries.getData().clear();

            originalSeries.getData().addAll(originalGraph.solve(x0, X, y0, N).getData());
            eulersSeries.getData().addAll(eulersMethod.solve(x0, X, y0, N).getData());
            improvedEulersSeries.getData().addAll(improvedEulersMethod.solve(x0, X, y0, N).getData());
            rungeKuttaSeries.getData().addAll(rungeKuttaMethod.solve(x0, X, y0, N).getData());

            recalculateErrors(eulersSeries, eulersErrorSeries);
            recalculateErrors(improvedEulersSeries, improvedEulersErrorSeries);
            recalculateErrors(rungeKuttaSeries, rungeKuttaErrorSeries);

            warning.setVisible(false);
        }

    }

    /**
     * Recalculate errors series from first tab
     *
     * @param generatedSeries series generated by numerical method
     * @param errorSeries series with errors
     */
    private void recalculateErrors(XYChart.Series<Number, Number> generatedSeries,
                                   XYChart.Series<Number, Number> errorSeries) {
        errorSeries.getData().clear();

        // Take minimal number of entries from both series
        int numberOfEements = originalSeries.getData().size() > generatedSeries.getData().size() ?
                generatedSeries.getData().size() : originalSeries.getData().size();

        for (int i = 0; i < numberOfEements; i++) {
            // Calculate error of generated series
            double temp = Math.abs((double) originalSeries.getData().get(i).getYValue()
                    - (double) generatedSeries.getData().get(i).getYValue());

            // Add error to error series
            errorSeries.getData().add(new XYChart.Data<>(originalSeries.getData().get(i).getXValue(), temp));
        }
    }

    /**
     * Recalculate series with max errors from second tab
     */
    private void recalculateMaxErrors() {
        double temp;
        double x0 = 0;
        double X = 5;
        double y0 = 0;
        int Nmin = Integer.parseInt(this.Nmin.getText());
        int Nmax = Integer.parseInt(this.Nmax.getText());

        eulersMaxErrorSeries.getData().clear();
        improvedEulersMaxErrorSeries.getData().clear();
        rungeKuttaMaxErrorSeries.getData().clear();

        Function.recalculateConstant(x0, y0);
        double originalValue;

        for (int N = Nmin; N <= Nmax; N++) {
            originalValue = Function.calculateFunction(X);
            temp = Math.abs(originalValue - (double) eulersMethod.solve(x0, X, y0, N).getData().get(N).getYValue());
            eulersMaxErrorSeries.getData().add(new XYChart.Data<>(N, temp));
            temp = Math.abs(originalValue - (double) improvedEulersMethod.solve(x0, X, y0, N).getData().get(N).getYValue());
            improvedEulersMaxErrorSeries.getData().add(new XYChart.Data<>(N, temp));
            temp = Math.abs(originalValue - (double) rungeKuttaMethod.solve(x0, X, y0, N).getData().get(N).getYValue());
            rungeKuttaMaxErrorSeries.getData().add(new XYChart.Data<>(N, temp));
        }
    }

    /**
     * Check new value in text field. Only double type is allowed.
     *
     * @param field text field which we check
     * @param value new value
     * @param oldValue old value
     */
    private void checkNumbers(TextField field, String value, String oldValue) {
        if (!value.matches("-?\\d*(\\.\\d*)?")) {
            field.setText(oldValue);
        }
    }

    /**
     * Add or remove graph to/from plot
     *
     * @param checkBoxValue value of checkbox
     * @param series series to add/remove
     * @param chart chart on which add/remove plot
     */
    private void drawGraph(Boolean checkBoxValue,
                           XYChart.Series<Number, Number> series,
                           LineChart<Number, Number> chart) {
        if (checkBoxValue) {
            chart.getData().add(series);
        } else {
            chart.getData().remove(series);
        }
    }
}
