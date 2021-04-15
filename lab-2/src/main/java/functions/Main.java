package functions;

public class Main {
    public static void main(final String[] args) {
        final Matrix a = new Matrix(
            new DoubleVector(1d, 0d),
            new DoubleVector(0d, 3d)
        );
        final DoubleVector b = new DoubleVector(2d, 3d);
        final double c = 4d;
        final DoubleVector values = new DoubleVector(1d, 3d);
        final QuadraticForm form = new QuadraticForm(a, b, c, values);
        DoubleVector x;

        final OptimizationMethod gradient = new GradientDescendMethod(form);
        x = gradient.findMin();
        System.out.printf("Gradient result: %s%n", x);
        System.out.println(form.apply(x));

        final OptimizationMethod steepest = new SteepestDescendMethod(form);
        x = steepest.findMin();
        System.out.printf("Steepest result: %s%n", x);
        System.out.println(form.apply(x));

    }

}

