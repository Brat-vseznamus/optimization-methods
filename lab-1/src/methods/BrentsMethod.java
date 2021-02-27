package methods;

import java.util.function.UnaryOperator;

public class BrentsMethod extends AbstractMethod {
    
    public BrentsMethod(UnaryOperator<Double> function) {
        super(function);
    }
    
    public BrentsMethod(UnaryOperator<Double> function, double eps) {
        super(function, eps);
    }
    

    @Override
    public double findMin(double a, double b) {
        // TODO Auto-generated method stub
        return 0;
    }

    
}
