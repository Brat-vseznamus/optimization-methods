package functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        // double[][] a = {
        //     {1, 0},
        //     {0, 3}
        // };
        // double[] b = {2, 3};
        // double c = 4;
        // double[] values = {1, 3};
        Matrix a = new Matrix(
            new DoubleVector(1d, 0d),
            new DoubleVector(0d, 3d)
        );
        DoubleVector b = new DoubleVector(2d, 3d);
        Double c = 4d;
        DoubleVector values = new DoubleVector(1d, 3d);
        QuadraticForm form = new QuadraticForm(a, b, c, values);
        OptimisationMethod method = new GradientDescendMethod(form);
        DoubleVector x = new DoubleVector(1d, 1d);
        System.out.println(form.apply(x));
        System.out.println(form.gradient(x));
        DoubleVector v = new DoubleVector(1d, 2.4, 3d);
        Matrix mat = new Matrix(
            new DoubleVector(2d, 1d, 1d), 
            new DoubleVector(2d, 1d, 1d),
            new DoubleVector(1d, 1d, 2d));
        System.out.println(v.multiply(mat));
        System.out.println(mat.multiply(v));
    }

}

