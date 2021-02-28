package methods;

import java.util.function.UnaryOperator;

public class GoldenRatioMethod extends AbstractMethod {

    public GoldenRatioMethod(UnaryOperator<Double> function) {
        super(function);
    }

    public GoldenRatioMethod(UnaryOperator<Double> function, double eps) {
        super(function, eps);
    }

    @Override
    public double findMin(double a, double b) {
        table.clear();

        double epsN = (b - a) / 2d;
        double tau = ((Math.sqrt(5d) - 1d) / 2d);

        // step 1
        double x1 = b - tau * (b - a);
        double x2 = a + tau * (b - a);
        double fx1 = function.apply(x1);
        double fx2 = function.apply(x2);

        // step 2
        while (epsN > eps) {
            // step 3
            if (fx1 <= fx2) {
                b = x2;
                x2 = x1;
                fx2 = fx1;

                x1 = b - tau * (b - a);
                fx1 = function.apply(x1);
            } else {
                a = x1;
                x1 = x2;
                fx1 = fx2;

                x2 = a + tau * (b - a);
                fx2 = function.apply(x2);
            }
            addInfo(a, b, (a + b) / 2);

            epsN = (b - a) / 2d;
        }

        // step 4
        return (a + b) / 2d;
    }

}
