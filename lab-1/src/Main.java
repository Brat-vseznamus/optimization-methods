import methods.BrentsMethod;
import methods.DichotomyMethod;
import methods.GoldenRatioMethod;
import methods.OptimizationAlgorithm;

import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        UnaryOperator<Double> function;
        function = x -> x * x + Math.exp(-0.35d * x);

        OptimizationAlgorithm method = new DichotomyMethod(function);
        OptimizationAlgorithm method2 = new GoldenRatioMethod(function);
        System.out.println();
        System.out.println("Result of dichotomy: " + method.findMin(-2d, 3d));
        System.out.println("Result of golden ratio: " + method2.findMin(-2d, 3d));
        
    }
}
