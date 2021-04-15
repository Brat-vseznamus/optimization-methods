package functions;

public class Main {
    public static void main(String[] args) {
        double[][] a = {
            {1, 0},
            {0, 3}
        };
        double[] b = {2, 3};
        double c = 4;
        double[] values = {1, 3};
        QuadraticForm form = new QuadraticForm(a, b, c, values);
        OptimizationMethod method = new GradientDescendMethod(form);
        double[] x = method.findMin();
        System.out.println(form.apply(x));
        System.out.println(x[0] + " " + x[1]);

        // double[][] a = {{1, 2}, {2, 1}};
        // double[] b = {0, 0};
        // double c = 0;
        // QuadraticForm form = new QuadraticForm(a, b, c);
        // System.out.println(Arrays.toString(form.gradient(new double[]{1.0, 0.0})));
        // System.out.println(form);
        // System.out.println(FormGenerator.generate(5, 3));
        // DoubleVector v1 = new DoubleVector(1d, 2d, 3d);
        // DoubleVector v2 = new DoubleVector(1d, 4d, 3d);
        // System.out.println(v1.add(v2));
        // // v1.get(2) = (Double)(3d);
        // System.out.println(v1.norm());
    }

}

