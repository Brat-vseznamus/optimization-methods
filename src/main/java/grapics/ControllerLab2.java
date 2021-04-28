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
import java.util.function.Function;


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
            new Matrix(new DoubleVector(60d, 2d), true),
            new DoubleVector(-10d, 10d), 2d);


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
        final GradientOptimizationMethod steepest = new ConjugateGradientMethod(form);
        steepest.findMin();
        currentSeries = new XYChart.Series<>();
        currentIterations = steepest.getTable();
        currentIteration = 0;
        sizeIterationsText.setText(Integer.toString(currentIterations.size()));
        getFunctionLevels();
        lineChart.getData().add(currentSeries);
        updateCurrentSeries();
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
        final double maxR = 10;
        for (double radius = 0.1; radius < maxR; radius += 0.3) {
            drawFromPoint(radius, maxR);
        }
    }

    private void drawFromPoint(final double radius, final double maxR) {
        final XYChart.Series<Number, Number> series = new XYChart.Series<>();
        lineChart.getData().add(series);
        final DoubleVector rgb = new DoubleVector(0d, 0d, 0d)
                .add(new DoubleVector(106d, 0d, 255d))
                .multiplyBy(radius / maxR);
        final Function<Integer, Integer> getColor = (i) -> (int) ((double) rgb.get(i));
        series.getNode().setStyle(String.format("-fx-opacity: %s; -fx-stroke: rgb(%d,%d,%d)",
                Double.toString(1 - Math.sqrt(radius / maxR)).replace(',', '.'),
                getColor.apply(0),
                getColor.apply(1),
                getColor.apply(2)));
        final AbstractGradientMethod.State state = currentIterations.get(currentIterations.size() - 1);
        final DoubleVector center = state.getPoint();
        final double h = form.apply(center.add(new DoubleVector(0d, radius)));
        final double gradesPerStep = 1;
        for (double angle = 0; angle < Math.PI * 2; angle += Math.PI * 2 / 360 * gradesPerStep) {
            final DoubleVector normal = new DoubleVector(Math.cos(angle), Math.sin(angle));
            final double a = 1 / 2d * form.getA().multiply(normal).scalar(normal);
            final double b = form.getB().scalar(normal) + form.getA().multiply(center).scalar(normal);
            final double c = form.getC() - h
                    + center.scalar(form.getB())
                    + 1 / 2d * (center.scalar(form.getA().multiply(center)));
            final double r = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
            final DoubleVector newPoint = normal.multiplyBy(r).add(center);
            series.getData().add(new XYChart.Data<>(newPoint.get(0), newPoint.get(1)));
        }
    }

}
