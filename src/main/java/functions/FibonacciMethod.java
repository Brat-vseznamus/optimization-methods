package functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class FibonacciMethod extends AbstractMethod {
    private List<Long> fs;


    public FibonacciMethod(UnaryOperator<Double> function) {
        super(function);
    }

    public FibonacciMethod(UnaryOperator<Double> function, double eps) {
        super(function, eps);
    }

    @Override
    public double findMin(double a, double b) {
        fs = new ArrayList<>();
        table = new ArrayList<>();
        calcs = 0;
        fs.add(0L);
        fs.add(1L);
        long lastFibonacci = (long) ((b - a) / eps);
        while (lastFibonacci > getF(0)) {
            long tmp = fs.get(fs.size() - 1);
            tmp += fs.get(fs.size() - 2);
            fs.add(tmp);
        }

        double x1 = a + getF(-2) * (b - a) / (double) getF(0);
        double x2 = b - getF(-2) * (b - a) / (double) getF(0);
        int k = 1;
        double fx1 = function.apply(x1);
        double fx2 = function.apply(x2);
        calcs += 2;
        double an = a, bn = b;
        while (k < fs.size() - 2) {
            // step 1
            if (fx1 > fx2) {
                // step 2
                an = x1;
                x1 = x2;
                x2 = bn - getF(-k - 2) * (bn - an) / (double) getF(-k);
                if (k < fs.size() - 3) {
                    fx1 = fx2;
                    fx2 = function.apply(x2);
                    calcs++;
                }
            } else {
                // step 3
                bn = x2;
                x2 = x1;
                x1 = an + getF(-k - 2) * (bn - an) / (double) getF(-k);
                if (k < fs.size() - 3) {
                    fx2 = fx1;
                    fx1 = function.apply(x1);
                    calcs++;
                }
            }
            // step 4
            k++;
            addInfo(an, bn, (an + bn) / 2d);
        }
        // step 5
        x2 = x1 + eps;
        if (function.apply(x1).equals(function.apply(x2))) {
            calcs++;
            an = x1;
        } else {
            bn = x2;
        }
        return (an + bn) / 2d;
    }


    public Long getF(int k) {
        return fs.get(fs.size() + k - 1);
    }


    @Override
    public String getName() {
        return "Фиббоначи";
    }
}
