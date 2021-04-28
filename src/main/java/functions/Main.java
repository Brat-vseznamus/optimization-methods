package functions;

public class Main {
    public static void main(final String[] args) {
        // double[][] a = {
        //     {1, 0},
        //     {0, 3}
        // };
        // double[] b = {2, 3};
        // double c = 4;
        // double[] values = {1, 3};
        // Matrix a = new Matrix(
        //     new DoubleVector(1d, 0d),
        //     new DoubleVector(0d, 3d)
        // );
        // DoubleVector b = new DoubleVector(2d, 3d);
        // Double c = 4d;
        // DoubleVector values = new DoubleVector(1d, 3d);
        // QuadraticForm form = new QuadraticForm(a, b, c, values);
        int dim = 4;
        int mu = 3;
        if (args.length == 2) {
            try {
                dim = Integer.parseInt(args[0]);
                mu = Integer.parseInt(args[1]);
            } catch (final NumberFormatException e) {
                System.err.println("Two integers wsa expected: " + e.getMessage());
                return;
            }
        }
        final QuadraticForm form = FormGenerator.generate(dim, mu);
        System.out.println(form.getA());
        // System.out.println(form.getA());
        // System.out.println(form.getB());       
        final OptimisationGradientMethod method = new GradientDescendMethod(form);
        final OptimisationGradientMethod method2 = new SteepestDescendMethod(form);
        final OptimisationGradientMethod method3 = new ConjugateGradientMethod(form);
        // DoubleVector x = new DoubleVector(1d, 1d, 1d);
        // System.out.println(form.apply(x));
        // System.out.println(form.gradient(x));
        // DoubleVector v = new DoubleVector(1d, 2.4, 3d);
        // Matrix mat = new Matrix(
        //     new DoubleVector(2d, 1d, 1d), 
        //     new DoubleVector(2d, 1d, 1d),
        //     new DoubleVector(1d, 1d, 2d));
        // System.out.println(v.multiply(mat));
        // System.out.println(mat.multiply(v));
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
        System.out.printf(timeFormat, "SteepestDescendMethod", (end1 - end0) / 1000.0);
        System.out.printf(timeFormat, "ConjugateGradientMethod", (end2 - end1) / 1000.0);
        System.out.printf("Time duration: %f sec.%n", (System.currentTimeMillis() - start) / 1000.0);
    }

}

