package methods;

import methods.dimensional.one.*;
import methods.dimensional.poly.*;

import java.util.List;

public class Main {
    public static DoubleVector answer;

    private static final List<QuadraticForm> forms = List.of(
            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(60d, 2d)),
                    new DoubleVector(-10d, 10d), 2d),

            new QuadraticForm(
                    new DiagonalMatrix(new DoubleVector(1d, 64d)),
                    new DoubleVector(-10, 30),
                    2d)
    );

    public static void main(final String[] args) {
        int dim = 2_000;
        int mu = 200;
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
        final int mode = 2;
        if (mode == 0) {
            steepest.setForm(forms.get(0));
            steepest.findMin();
            System.out.println(steepest.getTable().size());

            // conjugate.findMin();
            // System.out.println(conjugate.getTable().size());
        }
        if (mode == 1) {

            answer = measureMethod(conjugate);
            measureMethod(steepest);
            // System.out.println(form.getA());
            // System.out.println(form.getB()); 
            // final DoubleVector v = new DoubleVector(1d, 1d, 1d);
            // // System.out.println(form.getA().multiply(v));
            // // System.out.println(v.multiply(form.getA()));
            // // System.out.println(form.gradient(v));
            // // System.out.println(form.apply(v));
            // final long start = System.currentTimeMillis();
            // // final DoubleVector res1 = new DoubleVector(gradient.findMin());
            // // // System.out.println(res1);
            // final String timeFormat = "Time for %s: %f sec.%n";
            // answer = new DoubleVector(gradient.findMin());
            // // final DoubleVector gradRes = new DoubleVector(answer);
            // final long end0 = System.currentTimeMillis();
            // System.out.printf(timeFormat, "GradientDescendMethod", (end0 - start) / 1000.0);
            // // System.out.printf("iteration number: %d%n", gradient.getTable().size());

            // final DoubleVector steepRes = new DoubleVector(steepest.findMin());
            // final long end1 = System.currentTimeMillis();
            // System.out.printf(timeFormat, "SteepestDescendMethod", (end1 - end0) / 1000.0);
            // System.out.printf("iteration number: %d%n", steepest.getTable().size());
            // System.out.println("steepRes = " + steepRes);
            // System.out.println();

            // final DoubleVector res3 = new DoubleVector(conjugate.findMin());
            // answer = res3;
            // // System.out.printf(timeFormat, "ConjugateGradientMethod", (end1 - end0) / 1000.0);
            // // System.out.printf("iteration number: %d%n", conjugate.getTable().size());

            // final DoubleVector res2 = new DoubleVector(steepest.findMin());
            // final long end2 = System.currentTimeMillis();
            // System.out.printf(timeFormat, "SteepestDescendMethod", (end2 - end1) / 1000.0);
            // // System.out.printf("iteration number: %d%n", steepest.getTable().size());

            // System.out.printf("Time duration: %f sec.%n", (System.currentTimeMillis() - start) / 1000.0);
        } else if (mode == 2) {
            // System.out.println("Gradient:");
            // outputMethodInfo(gradient);
//            System.out.println("Conjugate:");
//            outputMethodInfo(conjugate);
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
//        for (final Pair<Integer, List<Pair<Integer, Integer>>> row : methodInfo) {
//            System.out.printf("%d ", row.first);
//            for (final Pair<Integer, Integer> cell : row.second) {
//                System.out.printf("%d ", cell.second);
//            }
//            System.out.println();
//        }
    }

    public static DoubleVector measureMethod(final GradientOptimizationMethod method) {
        long start = System.currentTimeMillis();
        DoubleVector res = new DoubleVector(method.findMin());
        long end = System.currentTimeMillis();
        System.out.printf("Method %s found minimum for time: %fs%n", method.getClass().getSimpleName(), (end - start) / 1000d);
        return res;
    }
}
