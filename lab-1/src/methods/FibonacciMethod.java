package methods;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class FibonacciMethod extends AbstractMethod {
    List<Long> fs;

    public FibonacciMethod(UnaryOperator<Double> function) {
        super(function);
    }

    public FibonacciMethod(UnaryOperator<Double> function, double eps) {
        super(function, eps);
    }

    @Override
    public double findMin(double a, double b) {
        fs = new ArrayList<>();
        fs.add(0l);
        fs.add(1l);
        long lastFibonacci = (long)((b - a) / eps);
        // System.out.println(lastFibonacci);
        while (lastFibonacci > getF(0)) {
            long tmp = fs.get(fs.size() - 1);
            tmp += fs.get(fs.size() - 2);
            fs.add(tmp);
        }
        double lamda = a + getF(-2) * (b - a) / (double)getF(0);
        double mu = a + getF(-1) * (b - a) / (double)getF(0);
        int k = 1;
        double fx1 = function.apply(lamda);
        double fx2 = function.apply(mu);
        double an = a, bn = b;
        while (k < fs.size()) {
            // step 1
            if (fx1 > fx2) {
                // step 2
                an = lamda;
                lamda = mu;
                mu = an + getF(-k-1) * (bn - an) / (double)getF(-k);
                if (k == fs.size() - 2) {
                    // go to step 5
                    break;
                } else {
                    fx2 = function.apply(mu);
                }
            } else {
                // step 3
                bn = mu;
                mu = lamda;
                lamda = an + getF(-k-2) * (bn - an) / (double)getF(-k);
                if (k == fs.size() - 2) {
                    // go to step 5
                    break;
                } else {
                    fx1 = function.apply(lamda);
                }
            }
            // step 4
            k++;
            System.out.println(String.format("[%s, %s]", an, bn));
        }
        // step 5
        mu = lamda + eps;
        if (function.apply(lamda).equals(function.apply(mu))) {
            an = lamda;
        } else {
            bn = mu;
        }
        return (an + bn) / 2d;
    }


    public Long getF(int k) {
        return fs.get(fs.size() + k - 1);
    }

    
}
