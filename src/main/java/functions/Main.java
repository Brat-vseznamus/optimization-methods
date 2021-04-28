package functions;

import java.util.stream.Collectors;

public class Main {
    public static void main(final String[] args) {
        int dim = 4;
        int mu = 3;
        if (args.length != 0 && args.length != 2) {
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
      
        final OptimisationGradientMethod method = new GradientDescendMethod(form);
        final OptimisationGradientMethod method2 = new SteepestDescendMethod(form);
        final OptimisationGradientMethod method3 = new ConjugateGradientMethod(form);
      
        final long start = System.currentTimeMillis();
        final DoubleVector res1 = new DoubleVector(method.findMin());
        System.out.println(res1);
        final long end0 = System.currentTimeMillis();
        final DoubleVector res2 = new DoubleVector(method2.findMin());
        final long end1 = System.currentTimeMillis();
        final DoubleVector res3 = new DoubleVector(method3.findMin());
        final long end2 = System.currentTimeMillis();
        System.out.printf("differences: %f, %f%n", res1.subtract(res2).norm(), res1.subtract(res3).norm());
        final String timeFormat = "Time for %s: %f sec.%n";
        
        System.out.printf(timeFormat, "GradientDescendMethod", (end0 - start) / 1000.0);
        System.out.printf("iteration number: %d%n", method.getTable().size());

        System.out.printf(timeFormat, "SteepestDescendMethod", (end1 - end0) / 1000.0);
        System.out.printf("iteration number: %d%n", method2.getTable().size());

        System.out.printf(timeFormat, "ConjugateGradientMethod", (end2 - end1) / 1000.0);
        System.out.printf("iteration number: %d%n", method3.getTable().size());

        System.out.printf("Time duration: %f sec.%n", (System.currentTimeMillis() - start) / 1000.0);

        // System.out.printf();
        // System.out.printf();
    }

}

