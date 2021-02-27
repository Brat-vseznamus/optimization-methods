package methods;

import java.util.function.Function;

public class BrentsMethod extends AbstractMethod {
    
    public BrentsMethod(Function<Double, Double> function) {
        super(function);
    }
    
    public BrentsMethod(Function<Double, Double> function, double eps) {
        super(function, eps);
    }
    

    @Override
    public double findMin(double a, double b) {
        // TODO Auto-generated method stub
        return 0;
    }

    
}
