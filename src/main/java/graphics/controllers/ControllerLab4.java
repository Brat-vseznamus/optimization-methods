package graphics.controllers;

import expression.*;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import linear.DoubleVector;
import newton.ClassicNewtonMethod;
import newton.DescentDirectionNewtonMethod;
import newton.NewtonMethod;
import newton.OneDimOptimizedNewtonMethod;
import newton.utils.FunctionExpression;
import newton.utils.Iteration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;


public class ControllerLab4 implements Initializable {

    @FXML
    private ImageView Exit;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private AnchorPane slider;

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private Text currentIterationText;

    @FXML
    private Text sizeIterationsText;

    @FXML
    private VBox methodsButtons;

    @FXML
    private VBox functionsButtons;

    @FXML
    private ImageView FullScreenButton;

    PseudoClass nowSelected = PseudoClass.getPseudoClass("now-selected");

    private XYChart.Series<Number, Number> currentSeries;
    private List<Iteration> currentIterations;
    private int currentIteration = 0;

    private final XYChart.Series<Number, Number> tangent = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> fromTangentToF1 = new XYChart.Series<>();
    private final List<XYChart.Series<Number, Number>> points = List.of(
            new XYChart.Series<>(),
            new XYChart.Series<>()
    );

    public static final Expression x1 = new X(0);
    public static final Expression x2 = new X(1);
    public static final Expression x3 = new X(2);
    public static final FunctionExpression[] functions = {
            new FunctionExpression(
                    new Sub(
                            new Add(
                                    new Square(x1),
                                    new Square(x2)
                            ),
                            new Mul(new Const(1.2d), new Mul(x1, x2))
                    ),
                    2,
                    true
            ),
            new FunctionExpression(
                    new Add(
                            new Mul(
                                    new Const(100),
                                    new Square(new Sub(x2, new Square(x1)))),
                            new Square(new Sub(Const.ONE, x1))
                    ),
                    2,
                    true
            ),
            new FunctionExpression(
                    new Mul(
                            new Cos(
                                    new Mul(
                                            new Add(
                                                    new Square(x1),
                                                    new Add(
                                                            Const.ONE,
                                                            new Power(x2, 4)
                                                    )
                                            ),
                                            new Const(0.04)
                                    )
                            ),
                            new Const(-4)
                    ),
                    2,
                    true
            ),
            new FunctionExpression(
                    new Add(
                            new Square(x1),
                            new Add(
                                    new Square(x2),
                                    new Mul(
                                            new Const(2),
                                            new Mul(x1, x2)
                                    )
                            )
                    ),
                    2,
                    true
            )
    };
    public static final List<DoubleVector> startVectors = List.of(
            new DoubleVector(4, 1),
            new DoubleVector(-1.2, 1),
            new DoubleVector(4, 0),
            new DoubleVector(1, -5)
    );
    FunctionExpression functionExpression;
    DoubleVector startVector;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        FullScreenButton.setOnMouseClicked(event -> ((Stage) (((ImageView) event.getSource()).getScene().getWindow())).setFullScreen(true));
        ControllerLab1.initScene(Exit, slider, Menu, MenuClose, lineChart);

        final boolean animations = false;

        lineChart.setAnimated(animations);
        lineChart.getXAxis().setAnimated(animations);
        lineChart.getYAxis().setAnimated(animations);
        lineChart.getXAxis().autoRangingProperty().setValue(false);
        lineChart.getYAxis().autoRangingProperty().setValue(false);

        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);
        lineChart.setLegendVisible(false);

        setFunctionN(0);
    }

    private void initializeLineChart(final NewtonMethod method) {

        method.findMin(startVector);
        lineChart.getData().clear();
        // TODO: generating info
        currentSeries = new XYChart.Series<>();
        lineChart.getData().add(currentSeries);
        lineChart.getData().add(tangent);
        lineChart.getData().add(fromTangentToF1);

        points.forEach(point -> lineChart.getData().add(point));

        Platform.runLater(() -> {
            tangent.getNode().getStyleClass().add("tangent-line");
            fromTangentToF1.getNode().getStyleClass().add("from-tangent-to-f1-line");
            points.forEach(pnt -> {
                pnt.getNode().getStyleClass().add("special-point");
            });

        });

        currentIterations = method.getTable(); // TODO: getting iterations;

        //PythonUtils.printTwoDimensionalIterationsToFile(currentIterations, "pythonUtils/sample.txt");
        currentIteration = 0;

        currentSeries.getNode().setStyle("-fx-stroke: orange");
        sizeIterationsText.setText(Integer.toString(currentIterations.size()));
        updateCurrentSeries();
    }


    private void updateCurrentSeries() {
        final Iteration currentInfo = currentIterations.get(currentIteration);
        final double x0 = currentInfo.getX0().get(0);
        final double f0 = currentInfo.getF0();
        final double x1 = currentInfo.getX1().get(0);
        final double f1 = currentInfo.getF1();
        addPoint(currentSeries, x0, f0);
        drawTangent(x0, f0, x1, f1, currentInfo.getSlope().get(0));
        currentIterationText.setText(Integer.toString(currentIteration + 1));
    }

    private void drawTangent(final double x0, final double f0, final double x1, final double f1, final double slope) {
        tangent.getData().clear();
        fromTangentToF1.getData().clear();
        points.forEach(point -> point.getData().clear());

        points.get(0).getData().add(new XYChart.Data<>(x0, f0));
        points.get(1).getData().add(new XYChart.Data<>(x1, f1));

        final double step = 0.001;
        final var tangentFunction = getTangentFunction(x0, f0, slope);
        final double minX = Math.min(2 * x0 - x1, x1);
        final double maxX = Math.max(2 * x0 - x1, x1);
        for (double curX = minX; curX < maxX; curX += step) {
            addPoint(tangent, curX, tangentFunction.apply(curX));
        }
        addPoint(fromTangentToF1, x1, tangentFunction.apply(x1));
        addPoint(fromTangentToF1, x1, f1);
    }

    private Function<Double, Double> getTangentFunction(final double x0, final double f0, final double slope) {
        return (x) -> slope * x - slope * x0 + f0;
    }


    private void prevIteration(final int cnt) {
        int i = cnt;
        while (i-- > 0) {
            if (currentIterations == null || currentIteration == 0) return;
            currentSeries.getData().remove(currentIteration, currentSeries.getData().size());
            currentIteration--;
            updateCurrentSeries();
        }
    }


    private void nextIteration(final int cnt) {
        int i = cnt;
        while (i-- > 0) {
            if (currentIterations == null) return;
            if (currentIteration < currentIterations.size() - 1) {
                currentIteration++;
                updateCurrentSeries();
            } else {
                return;
            }
        }
    }

    @FXML
    private void nextIteration() {
        nextIteration(1);
    }

    @FXML
    private void nextIterationTen() {
        nextIteration(10);
    }

    @FXML
    private void prevIteration() {
        prevIteration(1);
    }

    @FXML
    private void prevIterationTen() {
        prevIteration(10);
    }

    private void addPoint(final XYChart.Series<Number, Number> series, final Number x, final Number y) {
        series.getData().add(new XYChart.Data<>(x, y));
    }

    @FXML
    private void loadGradient(final ActionEvent e) {
        loadMethod(new ClassicNewtonMethod(functionExpression), e, 0);
    }

    @FXML
    private void loadSteepest(final ActionEvent e) {
        loadMethod(new DescentDirectionNewtonMethod(functionExpression), e, 1);
    }

    @FXML
    private void loadConjugate(final ActionEvent e) {
        loadMethod(new OneDimOptimizedNewtonMethod(functionExpression), e, 2);
    }

    private void loadMethod(final NewtonMethod method, final ActionEvent e, final int n) {
        clearNowSelected(methodsButtons);
        if (e != null) {
            setNowSelectedToNth(methodsButtons, n);
        } else {
            setNowSelectedToNth(methodsButtons, 2);
        }
        initializeLineChart(method);
    }

    @FXML
    private void setFunction1() {
        setFunctionN(0);
    }

    @FXML
    private void setFunction2() {
        setFunctionN(1);
    }

    @FXML
    private void setFunction3() {
        setFunctionN(2);
    }

    @FXML
    private void setFunction4() {
        setFunctionN(3);
    }

    @FXML
    private void setFunction5() {
        setFunctionN(4);
    }

    private void setFunctionN(final int n) {
        functionExpression = functions[n];
        startVector = startVectors.get(n);
        clearPaneStyle(methodsButtons);
        clearNowSelected(functionsButtons);
        setNowSelectedToNth(functionsButtons, n);
        loadConjugate(null);
    }

    private void setNowSelectedToNth(final Pane pane, final int n) {
        pane.getChildren().get(n).pseudoClassStateChanged(nowSelected, true);
    }

    private void clearNowSelected(final Pane pane) {
        pane.getChildren().forEach(child -> child.pseudoClassStateChanged(nowSelected, false));
    }

    private void clearPaneStyle(final Pane pane) {
        pane.getChildren().forEach(
                button -> button.setStyle("")
        );
    }
}
