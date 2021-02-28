import methods.BrentsMethod;
import methods.DichotomyMethod;
import methods.FibonacciMethod;
import methods.GoldenRatioMethod;
import methods.OptimizationAlgorithm;

import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        UnaryOperator<Double> function;
        function = x -> x * x + Math.exp(-0.35d * x);
        // function = x -> x * Math.sin(x) + 2 * Math.cos(x);
        double left = -2d;
        double right = 3d;
        
        OptimizationAlgorithm method = new DichotomyMethod(function);
        OptimizationAlgorithm method2 = new GoldenRatioMethod(function);
        OptimizationAlgorithm method3 = new FibonacciMethod(function);
        
        System.out.println();
        System.out.println("Result of dichotomy: " + method.findMin(left, right));
        System.out.println("Result of golden ratio: " + method2.findMin(left, right));
        System.out.println("Result of fibonacci: " + method3.findMin(left, right));
    }
}
