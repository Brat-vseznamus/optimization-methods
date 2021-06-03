package graphics.controllers;

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
import linear.DiagonalMatrix;
import linear.DoubleMatrix;
import linear.DoubleVector;
import methods.dimensional.poly.*;
import newton.Iteration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;


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
    PseudoClass hiddenLevelLine = PseudoClass.getPseudoClass("hidden-level-line");

    private XYChart.Series<Number, Number> currentSeries;
    private List<Iteration> currentIterations;
    private int currentIteration = 0;

    private XYChart.Series<Number, Number> tangent = new XYChart.Series<>();
    private XYChart.Series<Number, Number> fromTangentToF1 = new XYChart.Series<>();
    private final List<QuadraticForm> forms = new ArrayList<>(List.of(
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(60d, 2d)),
                    new DoubleVector(-10d, 10d), 2d),
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(1 * 2d, 64 * 2d)),
                    new DoubleVector(-5d, 15d), 2d),
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(51.3d * 2, 27.9d * 2)),
                    new DoubleVector(-23.78d, -0.9d), -0.78d),
            new QuadraticForm(
                    new DoubleMatrix(new DoubleVector(254 * 2d, 506 / 2d),
                            new DoubleVector(506 / 2d, 254 * 2d)),
                    new DoubleVector(50d, 130d), -111d,
                    new DoubleVector(1, 507)),
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(254 * 2d, 254 * 2d)),
                    new DoubleVector(50d, 130d), -111d)
    ));
    QuadraticForm form;

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


        setFunctionN(1);
    }

    private void initializeLineChart(final GradientOptimizationMethod method) {

        lineChart.getData().clear();
        tangent = new XYChart.Series<>();
        fromTangentToF1 = new XYChart.Series<>();

        lineChart.getData().add(tangent);
        lineChart.getData().add(fromTangentToF1);
        Platform.runLater(() -> {
            tangent.getNode().getStyleClass().add("tangent-line");
            fromTangentToF1.getNode().getStyleClass().add("from-tangent-to-f1-line");
        });
        // TODO: generating info
        currentSeries = new XYChart.Series<>();
        lineChart.getData().add(currentSeries);
        currentSeries.setName(method.getName());
        currentIterations = new ArrayList<>(); // TODO: getting iterations;
        for (double x = 0; x < 20; x += 1) {
            final double nextX = x + 1;
            final Iteration iteration = new Iteration(x, Math.sin(x), nextX, Math.sin(nextX), Math.cos(x));
            currentIterations.add(iteration);
        }
        currentIteration = 0;

        currentSeries.getNode().setStyle("-fx-stroke: orange");
        sizeIterationsText.setText(Integer.toString(currentIterations.size()));
        updateCurrentSeries();
    }


    private void updateCurrentSeries() {
        final Iteration currentInfo = currentIterations.get(currentIteration);
        final double x0 = currentInfo.getX0();
        final double f0 = currentInfo.getF0();
        addPoint(currentSeries, x0, f0);
        drawTangent(x0, f0, currentInfo.getX1(), currentInfo.getF1(), currentInfo.getSlope());
        currentIterationText.setText(Integer.toString(currentIteration));
    }

    private void drawTangent(final double x0, final double f0, final double x1, final double f1, final double slope) {
        tangent.getData().clear();
        fromTangentToF1.getData().clear();
        final double step = 0.01;
        final var tangentFunction = getTangentFunction(x0, f0, slope);
        for (double curX = 2 * x0 - x1; curX < x1; curX += step) {
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
            if (currentIterations == null || currentIteration == 1) return;
            currentIteration--;
            currentSeries.getData().remove(currentIteration, currentSeries.getData().size());
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
        loadMethod(new GradientDescendMethod(form), e, 0);
    }

    @FXML
    private void loadSteepest(final ActionEvent e) {
        loadMethod(new SteepestDescendMethod(form), e, 1);
    }

    @FXML
    private void loadConjugate(final ActionEvent e) {
        loadMethod(new ConjugateGradientMethod(form), e, 2);
    }

    private void loadMethod(final GradientOptimizationMethod method, final ActionEvent e, final int n) {
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
        form = forms.get(n);
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
