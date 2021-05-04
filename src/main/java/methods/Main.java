package methods;

import methods.dimensional.poly.*;

import java.util.List;

public class Main {
    public static void main(final String[] args) {
        int dim = 10000;
        int mu = 2;
        // if (args.length == 2) {
        //     try {
        //         dim = Integer.parseInt(args[0]);
        //         mu = Integer.parseInt(args[1]);
        //     } catch (final NumberFormatException e) {
        //         System.out.println("Usage:");
        //         System.out.println("\t\tjava function.Main <dimensions> <mu>");
        //         System.out.println("\t\tjava function.Main");
        //         return;
        //     }
        // }

        final QuadraticForm form = FormGenerator.generate(dim, mu);
        // final QuadraticForm form = new QuadraticForm(
        //      new DiagonalMatrix(new DoubleVector(2d,128d)),
        //          new DoubleVector(-10d, 30d), 2d);

        final GradientOptimizationMethod gradient = new GradientDescendMethod(form);
        final GradientOptimizationMethod steepest = new SteepestDescendMethod(form);
        final GradientOptimizationMethod conjugate = new ConjugateGradientMethod(form);

        // THIS SECTION IS FOR CHECKING THAT METHODS ARE ALIVE AND CALCULATION THE TIME OF WORK
        final int mode = 2;
        if (mode == 1) {
            // System.out.println(form.getA());
            // System.out.println(form.getB()); 
            final DoubleVector v = new DoubleVector(1d, 1d, 1d);
            // System.out.println(form.getA().multiply(v));
            // System.out.println(v.multiply(form.getA()));
            // System.out.println(form.gradient(v));
            // System.out.println(form.apply(v));
            final long start = System.currentTimeMillis();
            final DoubleVector res1 = new DoubleVector(gradient.findMin());
            // // System.out.println(res1);
            final String timeFormat = "Time for %s: %f sec.%n";
            final long end0 = System.currentTimeMillis();
            System.out.printf(timeFormat, "GradientDescendMethod", (end0 - start) / 1000.0);
            System.out.printf("iteration number: %d%n", gradient.getTable().size());

            // final List<AbstractGradientMethod.State> table = gradient.getTable();

            // final DoubleVector res3 = new DoubleVector(conjugate.findMin());
            // final long end1 = System.currentTimeMillis();
            // System.out.printf(timeFormat, "ConjugateGradientMethod", (end1 - end0) / 1000.0);
            // System.out.printf("iteration number: %d%n", conjugate.getTable().size());

            // final DoubleVector res2 = new DoubleVector(steepest.findMin());
            // final long end2 = System.currentTimeMillis();
            // System.out.printf(timeFormat, "SteepestDescendMethod", (end2 - end1) / 1000.0);
            // System.out.printf("iteration number: %d%n", steepest.getTable().size());

            // System.out.printf("differences: %f, %f%n", res1.subtract(res2).norm(), res1.subtract(res3).norm());
            
            // System.out.printf("Time duration: %f sec.%n", (System.currentTimeMillis() - start) / 1000.0);
        } else if (mode == 2) {
            // System.out.println("Gradient:");
            // outputMethodInfo(gradient);
            // System.out.println("Conjugate:");
            // outputMethodInfo(conjugate);
            System.out.println("Steepest:");
            outputMethodInfo(steepest);
        }
    }

    private static void outputMethodInfo(final GradientOptimizationMethod method) {
        final List<Pair<Integer, List<Pair<Integer, Integer>>>> methodInfo = method.valueAndDimToIterations();
        // for (final Pair<Integer, List<Pair<Integer, Integer>>> row : methodInfo) {
        //     System.out.printf("%d ", row.first);
        //     for (final Pair<Integer, Integer> cell : row.second) {
        //         System.out.printf("%d ", cell.second);
        //     }
        //     System.out.println();
        // }
    }
}
