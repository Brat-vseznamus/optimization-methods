package functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

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
        double[] x;

        OptimizationMethod gradient = new GradientDescendMethod(form);
        x = gradient.findMin();
        System.out.println(form.apply(x));
        System.out.println("Gradient result: " + x[0] + " " + x[1]);

        OptimizationMethod steepest = new SteepestDescendMethod(form);
        x = steepest.findMin();
        System.out.println(form.apply(x));
        System.out.println("Steepest result: " + x[0] + " " + x[1]);

    }

}

