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
import java.util.ArrayList;
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

    QuadraticForm form = new QuadraticForm(
            new DiagonalMatrix(new DoubleVector(60d, 2d)),
            new DoubleVector(-10d, 10d), 2d);


    private XYChart.Series<Number, Number> currentSeries;
    private List<AbstractGradientMethod.State> currentIterations;
    private int currentIteration = 0;


    private final List<XYChart.Series<Number, Number>> levels = new ArrayList<>();
    private final List<QuadraticForm> forms = new ArrayList<>(List.of(
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(60d, 2d)),
                    new DoubleVector(-10d, 10d), 2d),
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(0.5d, 32d)),
                    new DoubleVector(-5d, 15d), 2d)
    ));

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


        loadGradient();
    }

    private void initializeLineChart(final GradientOptimizationMethod method) {

        lineChart.getData().clear();
        levels.clear();
        method.findMin();
        currentSeries = new XYChart.Series<>();
        currentSeries.setName(method.getName());
        currentIterations = method.getTable();
        currentIteration = 0;
        getFunctionLevels();
        lineChart.getData().add(currentSeries);
        sizeIterationsText.setText(Integer.toString(currentIterations.size()));
        updateCurrentSeries();
    }


    private void updateCurrentSeries() {
        final AbstractGradientMethod.State currentInfo = currentIterations.get(currentIteration);
        final double x = currentInfo.getPoint().get(0);
        final double y = currentInfo.getPoint().get(1);
        currentIterationText.setText(Integer.toString(currentIteration));
        addPoint(currentSeries, x, y);
    }

    private void clearChart() {
        currentSeries.getData().clear();
    }


    private void prevIteration(final int cnt) {
        int i = cnt;
        while (i-- > 0) {
            if (currentIterations == null || currentIteration == 0) return;
            currentIteration--;
            currentSeries.getData().remove(currentIteration, currentSeries.getData().size());
            currentIterationText.setText(Integer.toString(currentIteration));
            updateCurrentSeries();
        }
    }


    private void nextIteration(final int cnt) {
        int i = cnt;
        while (i-- > 0) {
            if (currentIterations == null) return;
            if (currentIteration < currentIterations.size() - 1) {
                currentIteration++;
                currentIterationText.setText(Integer.toString(currentIteration));
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
        series.setName(null);
        lineChart.getData().add(series);
        levels.add(series);
        final double startR = 0, startG = 0, startB = 0;
        final double finishR = 106, finishG = 0, finishB = 255;
        final DoubleVector rgb = new DoubleVector(
                finishR - startR,
                finishG - startG,
                finishB - startB)
                .add(new DoubleVector(
                        finishR,
                        finishG,
                        finishB))
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

    @FXML
    private void loadGradient() {
        initializeLineChart(new GradientDescendMethod(form));
    }

    @FXML
    private void loadSteepest() {
        initializeLineChart(new SteepestDescendMethod(form));
    }

    @FXML
    private void loadConjugate() {
        initializeLineChart(new ConjugateGradientMethod(form));
    }

    @FXML
    private void showOrHideLevels() {
        levels.forEach(s -> {
            final StringBuilder oldStyle = new StringBuilder(s.getNode().getStyle());
            final int ind = oldStyle.indexOf("-fx-stroke-width: ");
            if (ind == -1) {
                oldStyle.append("; -fx-stroke-width: 0px");
            } else {
                if (oldStyle.charAt(ind + 18) == '0') {
                    oldStyle.setCharAt(ind + 18, '3');
                } else {
                    oldStyle.setCharAt(ind + 18, '0');
                }
            }
            s.getNode().setStyle(oldStyle.toString());
        });
    }


    @FXML
    private void setFunction1() {
        form = forms.get(0);
        loadGradient();
    }

    @FXML
    private void setFunction2() {
        form = forms.get(1);
        loadGradient();
    }
}
