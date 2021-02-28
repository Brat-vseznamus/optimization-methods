package methods;

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
        double x1; 
        double x2; 
        double x3;
        x1 = a;
        x3 = b;
        x2 = (a + b) / 2d;
        double tmpx1 = x1;
        double tmpx3 = x3;
        double fx1 = function.apply(x1);
        double fx2 = function.apply(x2);
        double fx3 = function.apply(x3);
        // step 1:
        while (!(fx1 >= fx2 && fx2 <= fx3)) {
            if (fx1 < fx3) {
                tmpx3 = x2;
                fx3 = fx2;
            } else {
                tmpx1 = x2;
                fx1 = fx2;
            }
            x2 = (tmpx1 + tmpx3) / 2d;
            fx2 = function.apply(x2);
        }

        // step 2: 
        double a1; 
        double a2;
        a1 = (fx2 - fx1) / (x2 - x1);
        a2 = ((fx3 - fx1) / (x3 - x1) - (fx2 - fx1) / (x2 - x1)) / (x3 - x2);

        double xi = (x1 + x2 - a1 / a2) / 2d;
        double xi2 = xi;
        double fxi = function.apply(xi);
        int iteration = 1;
        // step 3:
        while (iteration == 1 || Math.abs(xi - xi2) < eps) {
            // step 4:
            xi = xi2;
            a1 = (fx2 - fx1) / (x2 - x1);
            a2 = ((fx3 - fx1) / (x3 - x1) - (fx2 - fx1) / (x2 - x1)) / (x3 - x2);
            xi2 = (x1 + x2 - a1 / a2) / 2d;
            fxi = function.apply(xi2);
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

            iteration++;

            System.out.println(String.format("x1 = %f, x2 = %f, x3: %f%nx' = %f%n%n",
                                                                    x1, x2, x3, xi2));
            System.out.println(String.format("f(x1): %f, f(x2): %f, f(x3): %f%nf(x') = %f%n%n",
                                                                    fx1, fx2, fx3, fxi));
            System.out.println(String.format("xi2 = %f, xi = %f, |xi2 - xi| = %f, dn < eps == %s%n",
            xi2, xi, Math.abs(xi - xi2), Double.compare(Math.abs(xi2 - xi), eps))); 
              
        }

        return xi2;
    }

    
}
