package methods;

import java.util.function.Function;

public class GoldenRationMethod extends AbstractMethod {
    
    public GoldenRationMethod(Function<Double, Double> function) {
        super(function);
    }
    
    public GoldenRationMethod(Function<Double, Double> function, double eps) {
        super(function, eps);
    }

    @Override
    public double findMin(double a, double b) {
        // TODO Auto-generated method stub
        return 0;
    }

}
