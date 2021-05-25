package graphics;

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
import methods.dimensional.poly.*;

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

    @FXML
    private VBox methodsButtons;

    @FXML
    private VBox functionsButtons;

    @FXML
    private ImageView FullScreenButton;

    PseudoClass nowSelected = PseudoClass.getPseudoClass("now-selected");
    PseudoClass hiddenLevelLine = PseudoClass.getPseudoClass("hidden-level-line");

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
                    new DoubleVector(-5d, 15d), 2d),
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(51.3d, 27.9d)),
                    new DoubleVector(-23.78d, -0.9d), -0.78d),
            new QuadraticForm(
                    new Matrix(new DoubleVector(254 * 2d, 506 / 2d),
                            new DoubleVector(506 / 2d, 254 * 2d)),
                    new DoubleVector(50d, 130d), -111d,
                    new DoubleVector(508, 508)),
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(254 * 2d, 254 * 2d)),
                    new DoubleVector(50d, 130d), -111d)
    ));
    QuadraticForm form;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        FullScreenButton.setOnMouseClicked(event -> ((Stage) (((ImageView) event.getSource()).getScene().getWindow())).setFullScreen(true));
        Controller.initScene(Exit, slider, Menu, MenuClose, lineChart);

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
        currentSeries.getNode().setStyle("-fx-stroke: orange");
        sizeIterationsText.setText(Integer.toString(currentIterations.size() - 1));
        updateCurrentSeries();
        nextIteration(1);
    }


    private void updateCurrentSeries() {
        final AbstractGradientMethod.State currentInfo = currentIterations.get(currentIteration);
        final double x = currentInfo.getPoint().get(0);
        final double y = currentInfo.getPoint().get(1);
        currentIterationText.setText(Integer.toString(currentIteration));
        addPoint(currentSeries, x, y);
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


    private void getFunctionLevels() {
        final double maxR = 2;
        final double minR = 0.1;
        final int count = 20;
        final double step = (maxR - minR) / count;
        for (double radius = minR; radius < maxR; radius += step) {
            drawFromPoint(radius, maxR);
        }
        for (final XYChart.Series<Number, Number> x : levels) {
            Platform.runLater(() -> x.getNode().getStyleClass().add("level-line"));
        }
    }

    @SuppressWarnings("SameParameterValue")
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
                String.format("%.4f", 1 - Math.sqrt(radius / maxR)).replace(',', '.'),
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
        //series.getNode().getStyleClass().add("level-line");
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
    private void showOrHideLevels() {
        levels.forEach(s -> s.getNode().pseudoClassStateChanged(hiddenLevelLine, !s.getNode().getPseudoClassStates().contains(hiddenLevelLine)));
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
