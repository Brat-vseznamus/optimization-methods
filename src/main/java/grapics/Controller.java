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

    private final UnaryOperator<Double>
            function = x -> x * x + Math.exp(-0.35d * x);
    private final double ACTUAL_MINIMUM = 0.1651701916490658914488911;

    private final double left = -2d;
    private final double right = 3d;
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

        initializeLineChart();
    }

    private void initializeLineChart() {
        actualMinimumText.setText(getFormattedDouble(ACTUAL_MINIMUM));

        XYChart.Series<Number, Number> drawSeries = new XYChart.Series<>();
        getFunctionSeries(function, drawSeries);
        drawSeries.setName("Исходная функция");
        lineChart.getData().add(drawSeries);
        drawSeries.getNode().setStyle("-fx-stroke: peachpuff");
        bordersSeries = new ArrayList<>();
        parabola = new XYChart.Series<>();
        lineChart.getData().add(parabola);
        parabola.getNode().setStyle("-fx-stroke-dash-array: 10px; -fx-stroke: cornflowerblue");

        for (int i = 0; i < 2; i++) {
            XYChart.Series<Number, Number> border = new XYChart.Series<>();
            lineChart.getData().add(border);

            bordersSeries.add(border);
            border.getNode().setStyle("-fx-stroke: red");
            border.setName("");
        }
        currentSeries = new XYChart.Series<>();

        lineChart.getData().add(currentSeries);
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
        drawParabolaByThreePoints(l, function.apply(l), r, function.apply(r), x, y);
    }

    private void clearChart() {
        currentSeries.getData().clear();
        parabola.getData().clear();
        bordersSeries.forEach(x -> x.getData().clear());
    }

    public void loadDichotomy() {
        DrawableOptimizationAlgorithm algorithm = new DichotomyMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, false);
    }

    public void loadGoldenRatio() {
        DrawableOptimizationAlgorithm algorithm = new GoldenRatioMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, false);
    }

    public void loadFibonacci() {
        DrawableOptimizationAlgorithm algorithm = new FibonacciMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, false);
    }

    public void loadParabolic() {
        DrawableOptimizationAlgorithm algorithm = new ParabolicMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, true);
    }

    public void loadBrent() {
        DrawableOptimizationAlgorithm algorithm = new BrentsMethod(function);
        createDataSeriesFromDrawableOptimizationAlgorithm(algorithm, false);
    }

    public void prevIteration() {
        if (currentIterations == null || currentIteration == 0) return;
        currentIteration--;
        currentIterationText.setText(Integer.toString(currentIteration));
        deleteLastPoint(currentSeries);
        AbstractMethod.Info currentInfo = currentIterations.get(currentIteration);
        double l = currentInfo.getLeft(), r = currentInfo.getRight();
        leftBorderText.setText(getFormattedDouble(l));
        rightBorderText.setText(getFormattedDouble(r));
        double currentX = currentInfo.getValue();
        double currentValue = function.apply(currentX);
        currentPointText.setText(getFormattedDouble(currentValue));
        drawParabolaByThreePoints(l, function.apply(l), r, function.apply(r), currentX, currentValue);
        createVerticalBorders(l, r);
    }

    public void nextIteration() {
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

    private void deleteLastPoint(XYChart.Series<Number, Number> series) {
        if (series.getData().size() == 0) {
            return;
        }
        series.getData().remove(series.getData().size() - 1);
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
        return String.format("%.6f", x);
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

    private void drawParabolaByThreePoints(double x1, double y1, double x2, double y2, double x3, double y3) {
        parabola.getData().clear();
        if (!drawParabolas) return;
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
        double step = 0.1;
        for (double x = left; x <= right; x += step) {
            drawSeries.getData().add(new XYChart.Data<>(x, function.apply(x)));
        }
    }
}
