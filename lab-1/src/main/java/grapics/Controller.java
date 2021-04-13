package grapics;

import functions.*;
import javafx.animation.TranslateTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Controller implements Initializable {

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
    private Text leftBorderText;

    @FXML
    private Text rightBorderText;

    @FXML
    private Text currentPointText;

    @FXML
    private Text actualMinimumText;

    @FXML
    private Text currentIterationText;

    @FXML
    private Text sizeIterationsText;

    private UnaryOperator<Double> function = x -> x * x + Math.exp(-0.35d * x);
    private final UnaryOperator<Double> function1 = x -> x * x + Math.exp(-0.35d * x);
    private final UnaryOperator<Double> function2 = x -> 40 * x * x * x * x * x
            - 12 * x * x * x * x
            + x * x * x
            + 6 * x * x
            - 90 * x;
    private final UnaryOperator<Double> function3 = x -> x * x * x - x;
    private final List<UnaryOperator<Double>> functions = List.of(function1, function2, function3);

    private final double lF1 = -2d, rF1 = 3d, lF2 = 0, rF2 = 1.5d, lF3 = 0d, rF3 = 1d;
    private final List<Double> intervals = List.of(lF1, rF1, lF2, rF2, lF3, rF3);

    private final double actualMinimum1 = 0.1651701916490658914488911,
            actualMinimum2 = 0.856619, actualMinimum3 = 0.577350269189;
    private final List<Double> actualMinimums = List.of(actualMinimum1, actualMinimum2, actualMinimum3);


    private double left = -2d;
    private double right = 3d;
    double ACTUAL_MINIMUM = 0.1651701916490658914488911;
    private XYChart.Series<Number, Number> currentSeries;
    private List<AbstractMethod.Info> currentIterations;
    private List<XYChart.Series<Number, Number>> bordersSeries;
    private XYChart.Series<Number, Number> parabola;
    private int currentIteration = 0;
    private boolean drawParabolas = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Exit.setOnMouseClicked(event -> System.exit(0));
        slider.setTranslateX(-176);

        Menu.setOnMouseClicked(this::openMenu);
        MenuClose.setOnMouseClicked(this::closeMenu);

        openMenu(null);

        ChartPanManager panner = new ChartPanManager(lineChart);
        //while pressing the left mouse button, you can drag to navigate
        panner.setMouseFilter(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                mouseEvent.consume();
            }
        });
        panner.start();

        //holding the right mouse button will draw a rectangle to zoom to desired location
        JFXChartUtil.setupZooming(lineChart, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.SECONDARY)
                mouseEvent.consume();
        });

        boolean animations = false;

        lineChart.setAnimated(animations);
        lineChart.getXAxis().setAnimated(animations);
        lineChart.getYAxis().setAnimated(animations);


        lineChart.getXAxis().autoRangingProperty().setValue(true);
        lineChart.getYAxis().autoRangingProperty().setValue(true);

        initializeLineChart();
    }

    private void initializeLineChart() {
        lineChart.getData().clear();


        actualMinimumText.setText(getFormattedDouble(ACTUAL_MINIMUM));

        XYChart.Series<Number, Number> drawSeries = new XYChart.Series<>();
        getFunctionSeries(function, drawSeries);
        drawSeries.setName("Исходная функция");
        lineChart.getData().add(drawSeries);
        drawSeries.getNode().setStyle("-fx-stroke: peachpuff");
        bordersSeries = new ArrayList<>();
        parabola = new XYChart.Series<>();

        for (int i = 0; i < 2; i++) {
            XYChart.Series<Number, Number> border = new XYChart.Series<>();
            lineChart.getData().add(border);

            bordersSeries.add(border);
            border.getNode().setStyle("-fx-stroke: red");
            border.setName("");
        }
        currentSeries = new XYChart.Series<>();
        currentSeries = new XYChart.Series<>();

        lineChart.getData().add(currentSeries);
        lineChart.getData().add(parabola);
        parabola.getNode().setStyle("-fx-stroke-dash-array: 10px; -fx-stroke: cornflowerblue");
    }


    private void updateCurrentSeries() {
        AbstractMethod.Info currentInfo = currentIterations.get(currentIteration);
        double x = currentInfo.getValue();
        double y = function.apply(x);
        double l = currentInfo.getLeft(), r = currentInfo.getRight();
        leftBorderText.setText(getFormattedDouble(l));
        rightBorderText.setText(getFormattedDouble(r));
        currentPointText.setText(getFormattedDouble(x));
        addPoint(currentSeries, x, y);
        createVerticalBorders(l, r);
        parabola.getData().clear();
        boolean drawForOnce = false;
        if (currentInfo instanceof BrentsMethod.BrentInfo) {
            drawForOnce = ((BrentsMethod.BrentInfo) currentInfo).isParabolic;
        }
        if (drawForOnce || drawParabolas) {
            drawParabolaByThreePoints(l, function.apply(l), r, function.apply(r), x, y);
        } else {
            refreshParabola();
        }
    }

    private void clearChart() {
        currentSeries.getData().clear();
        refreshParabola();
        bordersSeries.forEach(x -> x.getData().clear());
    }

    @FXML
    private void loadDichotomy() {
        DrawableOptimizationAlgorithm algorithm = new DichotomyMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, false);
    }

    @FXML
    private void loadGoldenRatio() {
        DrawableOptimizationAlgorithm algorithm = new GoldenRatioMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, false);
    }

    @FXML
    private void loadFibonacci() {
        DrawableOptimizationAlgorithm algorithm = new FibonacciMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, false);
    }

    @FXML
    private void loadParabolic() {
        DrawableOptimizationAlgorithm algorithm = new ParabolicMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, true);
    }

    @FXML
    private void loadBrent() {
        DrawableOptimizationAlgorithm algorithm = new BrentsMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, false);
    }

    @FXML
    private void prevIteration() {
        if (currentIterations == null || currentIteration == 0) return;
        currentIteration--;
        currentSeries.getData().remove(currentIteration, currentSeries.getData().size());
        currentIterationText.setText(Integer.toString(currentIteration));
        updateCurrentSeries();
    }

    @FXML
    private void nextIteration() {
        if (currentIterations == null) return;
        if (currentIteration < currentIterations.size() - 1) {
            currentIteration++;
            currentIterationText.setText(Integer.toString(currentIteration));
            updateCurrentSeries();
        }
    }

    private void addPoint(XYChart.Series<Number, Number> series, Number x, Number y) {
        series.getData().add(new XYChart.Data<>(x, y));
    }

    private void createVerticalBorders(double l, double r) {
        for (XYChart.Series<Number, Number> border : bordersSeries) {
            border.getData().clear();
        }
        drawBorders(l, r);
    }

    private void drawBorders(double l, double r) {
        double[] xForBorder = {l, r};
        for (int i = 0; i < 2; i++) {
            double minY = -10;
            addPoint(bordersSeries.get(i), xForBorder[i], minY);
            double maxY = 10;
            addPoint(bordersSeries.get(i), xForBorder[i], maxY);
        }
    }

    private void createDataSeriesFromDrawableOptimizationAlgorithm(DrawableOptimizationAlgorithm algorithm, boolean drawParabolas) {
        clearChart();
        this.drawParabolas = drawParabolas;
        currentSeries.setName(algorithm.getName());
        currentIteration = 0;
        currentIterationText.setText(Integer.toString(currentIteration));
        algorithm.findMin(left, right);
        currentIterations = algorithm.getTable();
        sizeIterationsText.setText(Integer.toString(currentIterations.size() - 1));
        updateCurrentSeries();
    }

    private String getFormattedDouble(double x) {
        return String.format("%.9f", x);
    }

    private void openMenu(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);

        slide.setToX(0);
        slide.play();

        slider.setTranslateX(-176);

        slide.setOnFinished((ActionEvent e) -> {
            Menu.setVisible(false);
            MenuClose.setVisible(true);
        });
    }

    private void closeMenu(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);

        slide.setToX(-176);
        slide.play();

        slider.setTranslateX(0);

        slide.setOnFinished((ActionEvent e) -> {
            Menu.setVisible(true);
            MenuClose.setVisible(false);
        });
    }

    private void refreshParabola() {
        lineChart.getData().remove(4);
        parabola = new XYChart.Series<>();
        lineChart.getData().add(parabola);
        parabola.getNode().setStyle("-fx-stroke-dash-array: 10px; -fx-stroke: cornflowerblue");
    }

    private void drawParabolaByThreePoints(double x1, double y1, double x2, double y2, double x3, double y3) {
        double d = (x1 - x2) * (x1 - x3) * (x2 - x3);
        if (d == 0) {
            System.err.println("There are some equal or too similar point given");
            return;
        }
        double A = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / d;
        double B = (x3 * x3 * (y1 - y2) + x2 * x2 * (y3 - y1) + x1 * x1 * (y2 - y3)) / d;
        double C = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / d;
        getFunctionSeries((x) -> A * x * x + B * x + C, parabola);
    }

    private void getFunctionSeries(Function<Double, Double> function, XYChart.Series<Number, Number> drawSeries) {
        double step = 0.01;
        for (double x = left; x <= right; x += step) {
            drawSeries.getData().add(new XYChart.Data<>(x, function.apply(x)));
        }
    }

    private void changeFunction(int funcIndex) {
        funcIndex--;
        function = functions.get(funcIndex);
        left = intervals.get(2 * funcIndex);
        right = intervals.get(2 * funcIndex + 1);
        ACTUAL_MINIMUM = actualMinimums.get(funcIndex);
        initializeLineChart();
    }

    @FXML
    private void setFunction1() {
        changeFunction(1);
    }

    @FXML
    private void setFunction2() {
        changeFunction(2);
    }

    @FXML
    private void setFunction3() {
        changeFunction(3);
    }
}