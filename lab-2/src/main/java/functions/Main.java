package functions;

public class Main {
    public static void main(String[] args) {
        Matrix a = new Matrix(
            new DoubleVector(1d, 0d),
            new DoubleVector(0d, 3d)
        );
        DoubleVector b = new DoubleVector(2d, 3d);
        double c = 4d;
        DoubleVector values = new DoubleVector(1d, 3d);
        QuadraticForm form = new QuadraticForm(a, b, c, values);
        DoubleVector x;

        OptimizationMethod gradient = new GradientDescendMethod(form);
        x = gradient.findMin();
        System.out.println(form.apply(x));
        System.out.printf("Gradient result: %s", x);

        OptimizationMethod steepest = new SteepestDescendMethod(form);
        x = steepest.findMin();
        System.out.println(form.apply(x));
        System.out.printf("Steepest result: %s", x);

    }

}

