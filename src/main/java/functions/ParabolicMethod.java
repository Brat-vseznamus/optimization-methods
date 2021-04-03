package functions;

import java.util.function.UnaryOperator;

public class ParabolicMethod extends AbstractMethod {


    public ParabolicMethod(UnaryOperator<Double> function) {
        super(function);
    }

    public ParabolicMethod(UnaryOperator<Double> function, double eps) {
        super(function, eps);
    }

    @Override
    public double findMin(double a, double b) {
        // step 1:
        double[] values = initValues(a, (a + b) / 2, b);
        calcs = 0;
        double x1 = values[0],
                x2 = values[1],
                x3 = values[2],
                fx1 = values[3],
                fx2 = values[4],
                fx3 = values[5];
        // step 2:
        // Ax^2 + Bx + C = f(x)
        double xi = x2;
        double xi2 = xi;
        double fxi;
        int iteration = 1;
        addInfo(x1, x3, x2);
        // step 3:
        while (iteration == 1 || Math.abs(xi - xi2) > eps) {
            // step 4:
            xi = xi2;
            xi2 = getMin(x1, x2, x3, fx1, fx2, fx3);
            fxi = function.apply(xi2);
            calcs++;
            // step 5:
            if (x1 < xi2 && xi2 < x2) {
                if (fxi >= fx2) {
                    x1 = xi2;
                    fx1 = fxi;
                } else {
                    x3 = x2;
                    fx3 = fx2;
                    x2 = xi2;
                    fx2 = fxi;
                }
            } else {
                if (fx2 >= fxi) {
                    x1 = x2;
                    fx1 = fx2;
                    x2 = xi2;
                    fx2 = fxi;
                } else {
                    x3 = xi2;
                    fx3 = fxi;
                }
            }
            addInfo(x1, x3, x2);
            iteration++;
        }

        return xi2;
    }

    static double getMin(double x1, double x2, double x3, double f1, double f2, double f3) {
        double up = (x2 - x1) * (x2 - x1) * (f2 - f3) - (x2 - x3) * (x2 - x3) * (f2 - f1);
        double down = (x2 - x1) * (f2 - f3) - (x2 - x3) * (f2 - f1);
        return x2 - (up / (2 * down));
    }


    double[] initValues(double x1, double x2, double x3) {
        double fx1 = function.apply(x1);
        double fx2 = function.apply(x2);
        double fx3 = function.apply(x3);
        calcs += 3;
        // step 1:
        while (!(fx1 >= fx2 && fx2 <= fx3)) {
            if (fx1 < fx3) {
                x3 = x2;
                fx3 = fx2;
            } else {
                x1 = x2;
                fx1 = fx2;
            }
            x2 = (x1 + x3) / 2d;
            fx2 = function.apply(x2);
            calcs++;
        }
        return new double[]{x1, x2, x3, fx1, fx2, fx3};
    }


    @Override
    public String getName() {
        return "Параболы";
    }
}
