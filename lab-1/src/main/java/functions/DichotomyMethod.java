package functions;

import java.util.function.UnaryOperator;

public class DichotomyMethod extends AbstractMethod {
    private static final double DELTA = 15e-11d;

    public DichotomyMethod(UnaryOperator<Double> function) {
        super(function);
    }

    public DichotomyMethod(UnaryOperator<Double> function, double eps) {
        super(function, eps);
    }

    @Override
    public double findMin(double a, double b) {
        table.clear();
        calcs = 0;
        double epsN = (b - a) / 2d;

        while (epsN > eps) {
            // step 1
            double x1 = (a + b - DELTA) / 2d;
            double x2 = (a + b + DELTA) / 2d;
            double fx1 = function.apply(x1);
            double fx2 = function.apply(x2);
            calcs += 2;
            // step 2
            if (fx1 <= fx2) {
                b = x2;
            } else {
                a = x1;
            }
            addInfo(a, b, (a + b) / 2d);
            // step 3
            epsN = (b - a) / 2d;
        }

        // step 4
        return (a + b) / 2d;
    }

    @Override
    public String getName() {
        return "Дихотомия";
    }
}
