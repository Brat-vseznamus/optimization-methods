package methods;

import methods.dimensional.one.*;
import methods.dimensional.poly.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static DoubleVector answer;

    private static final List<QuadraticForm> forms = new ArrayList<>(List.of(
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(60d, 2d)),
                    new DoubleVector(-10d, 10d), 2d),
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(0.5d, 32d)),
                    new DoubleVector(-5d, 15d), 2d),
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(51.3d * 2, 27.9d * 2)),
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

    public static void main(final String[] args) {
        int dim = 500;
        int mu = 80;
        if (args.length == 2) {
            try {
                dim = Integer.parseInt(args[0]);
                mu = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                System.out.println("Usage:");
                System.out.println("\t\tjava function.Main <dimensions> <mu>");
                System.out.println("\t\tjava function.Main");
                return;
            }
        }

        final QuadraticForm form = FormGenerator.generate(dim, mu);

        final GradientOptimizationMethod gradient = new GradientDescendMethod(form);
        final GradientOptimizationMethod steepest = new SteepestDescendMethod(form);
        final GradientOptimizationMethod conjugate = new ConjugateGradientMethod(form);

        // THIS SECTION IS FOR CHECKING THAT METHODS ARE ALIVE AND CALCULATION THE TIME OF WORK
        final int mode = 0;
        if (mode == 0) {
            steepest.setForm(forms.get(5));

            System.out.println(Arrays.toString(steepest.findMin()));
            System.out.println(steepest.getTable().size());
        }
        if (mode == 1) {
            answer = measureMethod(conjugate);
            measureMethod(steepest);
        } else if (mode == 2) {
             System.out.println("Gradient:");
             outputMethodInfo(gradient);
            System.out.println("Conjugate:");
            outputMethodInfo(conjugate);
            System.out.println("Steepest:");
            outputMethodInfo(steepest);
        } else if (mode == 3) {
            final DrawableMethod[] methods = new DrawableMethod[]{
                    new DichotomyMethod(),
                    new GoldenRatioMethod(),
                    new FibonacciMethod(),
                    new ParabolicMethod(),
                    new BrentsMethod()
            };
            GradientOptimizationMethod step;
            final QuadraticForm testing = forms.get(1);//FormGenerator.generate(dim, mu);
            for (final DrawableMethod method : methods) {
                step = new SteepestDescendMethod(method);
                step.setIterationsWithoutTable(true);
                step.setForm(testing);
                step.findMin();
                System.out.printf("%s - %d%n", method.getName(), step.getIterations());
            }
        }
    }

    private static void outputMethodInfo(final GradientOptimizationMethod method) {
        final List<Pair<Integer, List<Pair<Integer, Integer>>>> methodInfo = method.valueAndDimToIterations();
    }

    public static DoubleVector measureMethod(final GradientOptimizationMethod method) {
        final long start = System.currentTimeMillis();
        final DoubleVector res = new DoubleVector(method.findMin());
        final long end = System.currentTimeMillis();
        System.out.printf("Method %s found minimum for time: %fs%n", method.getClass().getSimpleName(), (end - start) / 1000d);
        return res;
    }
}
