package grapics;

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
import methods.dimensional.poly.*;
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

    final QuadraticForm form = new QuadraticForm(
            new Matrix(new DoubleVector(2d, 128d), true),
            new DoubleVector(-10d, 30d), 2d);


    private XYChart.Series<Number, Number> currentSeries;
    private List<AbstractGradientMethod.State> currentIterations;
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

        lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.NONE);

        initializeLineChart();
    }

    private void initializeLineChart() {
        lineChart.getData().clear();
        currentSeries = new XYChart.Series<>();
        lineChart.getData().add(currentSeries);
        final GradientOptimizationMethod steepest = new SteepestDescendMethod(form);
        steepest.findMin();
        currentIterations = steepest.getTable();
        currentIteration = 0;
        updateCurrentSeries();
        getFunctionLevels();
        sizeIterationsText.setText(Integer.toString(currentIterations.size()));
    }


    private void updateCurrentSeries() {
        final AbstractGradientMethod.State currentInfo = currentIterations.get(currentIteration);
        final double x = currentInfo.getPoint().get(0);
        final double y = currentInfo.getPoint().get(1);
        currentIterationText.setText(getFormattedDouble(currentIteration));
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
        int i = 10;
        while (i-- > 0) {
            if (currentIteration < currentIterations.size() - 1) {
                currentIteration++;
                currentIterationText.setText(Integer.toString(currentIteration));
                updateCurrentSeries();
            }
        }
    }

    private void addPoint(final XYChart.Series<Number, Number> series, final Number x, final Number y) {
        series.getData().add(new XYChart.Data<>(x, y));
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


    private void getFunctionLevels() {
        for (double radius = 0.01; radius < 0.7; radius += 0.05) {
            drawfromPoint(radius);
        }
    }

    private void drawfromPoint(final double radius) {
        final AbstractGradientMethod.State state = currentIterations.get(currentIterations.size() - 1);
        final DoubleVector center = state.getPoint();
        DoubleVector curPoint = center.add(new DoubleVector(0d, radius));
        final double step = radius / 60;//0.005;
        final double maxCnt = radius / step * 33;
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        lineChart.getData().add(series);
        series.getNode().setStyle("-fx-opacity: 0.5");
        double cnt = 0;
        while (cnt < maxCnt) {
            final DoubleVector gradi = form.gradient(curPoint);
            final DoubleVector prGradi = new DoubleVector(gradi.get(1), -gradi.get(0));
            final double length = prGradi.norm();
            final DoubleVector next = curPoint.add(prGradi.multiplyBy(1 / length * step));
            curPoint = next;
            series.getData().add(new XYChart.Data<>(next.get(0), next.get(1)));
            cnt++;
        }
    }

}
