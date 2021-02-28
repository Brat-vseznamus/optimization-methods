package methods;

import java.util.function.UnaryOperator;

public class DichotomyMethod extends AbstractMethod {
    
    public DichotomyMethod(UnaryOperator<Double> function) {
        super(function);
    }

    public DichotomyMethod(UnaryOperator<Double> function, double eps) {
        super(function, eps);
    }
    
    @Override
    public double findMin(double a, double b) {
        table.clear();
        
        double delta = 15e-11d;
        double epsN = (b - a) / 2d;
        while (epsN > eps) {
            // step 1
            double x1 = (a + b - delta) / 2d;
            double x2 = (a + b + delta) / 2d;
            double fx1 = function.apply(x1);
            double fx2 = function.apply(x2);
            
            // step 2
            if (fx1 <= fx2) {
                b = x2;
            } else {
                a = x1;
            }

            addInfo(a, b, (a + b) / 2);

            // step 3
            epsN = (b - a) / 2d;
        }

        // step 4
        return (a + b) / 2d;
    }

}
