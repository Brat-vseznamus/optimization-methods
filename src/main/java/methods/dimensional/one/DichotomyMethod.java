package methods.dimensional.one;

import java.util.function.UnaryOperator;

public class DichotomyMethod extends AbstractOneDimensionalMethod {
    private static final double DELTA = 15e-11d;

    public DichotomyMethod(final UnaryOperator<Double> function) {
        super(function);
    }

    public DichotomyMethod(final UnaryOperator<Double> function, final double eps) {
        super(function, eps);
    }

    public DichotomyMethod() {
        super(null);
    }

    @Override
    public double findMin(double a, double b) {
        table.clear();
        calculations = 0;
        double epsN = (b - a) / 2d;

        while (epsN > eps) {
            // step 1
            final double x1 = (a + b - DELTA) / 2d;
            final double x2 = (a + b + DELTA) / 2d;
            final double fx1 = function.apply(x1);
            final double fx2 = function.apply(x2);
            calculations += 2;
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
