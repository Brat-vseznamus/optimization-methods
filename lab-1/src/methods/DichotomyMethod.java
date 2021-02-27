package methods;

import java.util.function.Function;

public class DichotomyMethod extends AbstractMethod {

    public DichotomyMethod(Function<Double, Double> function) {
        super(function);
    }

    @Override
    public double findMin(double a, double b) {
        return 0;
    }
}
