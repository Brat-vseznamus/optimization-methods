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
        // TODO Auto-generated method stub
        return 0;
    }

    
}
