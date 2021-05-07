package methods;

import methods.dimensional.poly.*;

import java.util.List;

public class Main {
    public static void main(final String[] args) {
        int dim = 300;
        int mu = 50;
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

//        final QuadraticForm form = FormGenerator.generate(dim, mu);
        final QuadraticForm form = new QuadraticForm(
                new DiagonalMatrix(new DoubleVector(1d, 250d)),
                new DoubleVector(1d, 0d),
                1d);

        final GradientOptimizationMethod gradient = new GradientDescendMethod(form);
        final GradientOptimizationMethod steepest = new SteepestDescendMethod(form);
        final GradientOptimizationMethod conjugate = new ConjugateGradientMethod(form);

        // THIS SECTION IS FOR CHECKING THAT METHODS ARE ALIVE AND CALCULATION THE TIME OF WORK
        final int mode = 2;
        if (mode == 1) {
            final String timeFormat = "Time for %s: %f sec.%n";

            final long start = System.currentTimeMillis();

            final DoubleVector gradRes = new DoubleVector(gradient.findMin());
            final long end0 = System.currentTimeMillis();
            System.out.printf(timeFormat, "GradientDescendMethod", (end0 - start) / 1000.0);
            System.out.printf("iteration number: %d%n", gradient.getTable().size());
            System.out.println("gradRes = " + gradRes);
            System.out.println();

            final DoubleVector steepRes = new DoubleVector(steepest.findMin());
            final long end1 = System.currentTimeMillis();
            System.out.printf(timeFormat, "SteepestDescendMethod", (end1 - end0) / 1000.0);
            System.out.printf("iteration number: %d%n", steepest.getTable().size());
            System.out.println("steepRes = " + steepRes);
            System.out.println();

            final DoubleVector conjRes = new DoubleVector(conjugate.findMin());
            final long end2 = System.currentTimeMillis();
            System.out.printf(timeFormat, "ConjugateGradientMethod", (end2 - end1) / 1000.0);
            System.out.printf("iteration number: %d%n", conjugate.getTable().size());
            System.out.println("conjRes = " + conjRes);

            System.out.printf("differences: %f, %f%n", steepRes.subtract(gradRes).norm(), conjRes.subtract(steepRes).norm());

            System.out.printf("Time duration: %f sec.%n", (System.currentTimeMillis() - start) / 1000.0);
        } else if (mode == 2) {
            final String timeFormat = "Time for %s: %f sec.%n";
            final long start = System.currentTimeMillis();

            System.out.println("Gradient:");
            outputMethodInfo(gradient);
            final long end0 = System.currentTimeMillis();
            System.out.printf(timeFormat, "GradientDescendMethod", (end0 - start) / 1000.0);

            System.out.println("Steepest:");
            outputMethodInfo(steepest);
            final long end1 = System.currentTimeMillis();
            System.out.printf(timeFormat, "SteepestDescendMethod", (end1 - end0) / 1000.0);

            System.out.println("Conjugate:");
            outputMethodInfo(conjugate);
            final long end2 = System.currentTimeMillis();
            System.out.printf(timeFormat, "ConjugateGradientMethod", (end2 - end1) / 1000.0);

            System.out.printf("Time duration: %f sec.%n", (System.currentTimeMillis() - start) / 1000.0);
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
}
