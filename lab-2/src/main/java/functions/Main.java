package functions;

import java.util.Arrays;
import java.util.Vector;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        NFunction fun = new NFunction(3, xs -> {
           double x1 = xs[0],
                x2 = xs[1],
                x3 = xs[2];
            return x1 + x2 + x3;
        });

        System.out.println(fun.apply(1.0, 1.4, 2.3));
        double[][] a = {{1, 2}, {2, 1}};
        double[] b = {0, 0};
        double c = 0;
        QuadraticForm form = new QuadraticForm(a, b, c);
        System.out.println(Arrays.toString(form.gradient(new double[]{1.0, 0.0})));
        System.out.println(form);
    }
}

