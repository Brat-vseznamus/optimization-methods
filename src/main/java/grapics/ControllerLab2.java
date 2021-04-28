package grapics;

import grapics.contour.Contour2DMap;
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
import methods.dimensional.one.*;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class ControllerLab2 implements Initializable {

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

    private final BinaryOperator<Double> function = (x, y) -> x * x + 2 * y * y;
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


    private final double left = -10d;
    private final double right = 10d;
    double ACTUAL_MINIMUM = 0.1651701916490658914488911;
    private XYChart.Series<Number, Number> currentSeries;
    private List<AbstractOneDimensionalMethod.Info> currentIterations;
    private int currentIteration = 0;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        Exit.setOnMouseClicked(event -> System.exit(0));
        slider.setTranslateX(-176);

        Menu.setOnMouseClicked(this::openMenu);
        MenuClose.setOnMouseClicked(this::closeMenu);

        openMenu(null);

        final ChartPanManager panner = new ChartPanManager(lineChart);
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

        final boolean animations = false;

        lineChart.setAnimated(animations);
        lineChart.getXAxis().setAnimated(animations);
        lineChart.getYAxis().setAnimated(animations);


        lineChart.getXAxis().autoRangingProperty().setValue(true);
        lineChart.getYAxis().autoRangingProperty().setValue(true);

        initializeLineChart();
    }

    private void initializeLineChart() {
        lineChart.getData().clear();
        getFunctionLevels(function);


    }


    private void updateCurrentSeries() {
        final AbstractOneDimensionalMethod.Info currentInfo = currentIterations.get(currentIteration);
        final double x = currentInfo.getValue();
        final double y = 0;//functionPositive.apply(x, 0d);
        final double l = currentInfo.getLeft();
        final double r = currentInfo.getRight();
        addPoint(currentSeries, x, y);
    }

    private void clearChart() {
        currentSeries.getData().clear();
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

    private void addPoint(final XYChart.Series<Number, Number> series, final Number x, final Number y) {
        series.getData().add(new XYChart.Data<>(x, y));
    }


    private void createDataSeriesFromDrawableOptimizationAlgorithm(final DrawableMethod algorithm, final boolean drawParabolas) {
        clearChart();
        currentSeries.setName(algorithm.getName());
        currentIteration = 0;
        currentIterationText.setText(Integer.toString(currentIteration));
        algorithm.findMin(left, right);
        currentIterations = algorithm.getTable();
        sizeIterationsText.setText(Integer.toString(currentIterations.size() - 1));
        updateCurrentSeries();
    }

    private String getFormattedDouble(final double x) {
        return String.format("%.9f", x);
    }

    private void openMenu(final MouseEvent event) {
        final TranslateTransition slide = new TranslateTransition();
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

    private void closeMenu(final MouseEvent event) {
        final TranslateTransition slide = new TranslateTransition();
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


    private void getFunctionLevels(final BinaryOperator<Double> function) {
        final double minX = -1, maxX = 1, minY = -1, maxY = 1, step = 0.5;
        final int xSize = (int) ((maxX - minX) / step), ySize = (int) ((maxY - minY) / step);
        final double[][] values = new double[xSize][ySize];
        for (double x = minX; x < maxX; x += step) {
            for (double y = minY; y < maxY; y += step) {
                final int xi = (int) ((x - minX) / step);
                final int yi = (int) ((y - minY) / step);
                values[xi][yi] = function.apply(x, y);
            }
        }
        Contour2DMap contour2DMap = new Contour2DMap(xSize, ySize);
        contour2DMap.setData(values);
        contour2DMap.setIsoFactor(1.0);
        contour2DMap.setInterpolationFactor(1);
        contour2DMap.setMapColorScale("Color");
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        contour2DMap.draw(lineChart, series);
        //lineChart.getData().add(series);
        //series.getNode().setStyle("-fx-stroke:#0000ff;-fx-stroke-width:1");
    }

}
