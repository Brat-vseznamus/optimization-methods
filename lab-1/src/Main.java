import methods.BrentsMethod;
import methods.DichotomyMethod;
import methods.FibonacciMethod;
import methods.GoldenRatioMethod;
import methods.OptimizationAlgorithm;
import methods.ParabolicMethod;

import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        UnaryOperator<Double> function;
        function = x -> x * x + Math.exp(-0.35d * x);
        double left = -2d;
        double right = 3d;
        
        OptimizationAlgorithm method;
        // method = new DichotomyMethod(function);
        // method = new GoldenRatioMethod(function);
        OptimizationAlgorithm method2 = new ParabolicMethod(function);
        method = new FibonacciMethod(function);
        
        System.out.println();
        System.out.println("Result of parabolic: " + method2.findMin(left, right));
        // System.out.println("Result of dichotomy: " + method.findMin(left, right));
        // System.out.println("Result of golden ratio: " + method.findMin(left, right));
        // System.out.println("Result of fibonacci: " + method.findMin(left, right));

        // System.out.println("Table: " + method.getTable());
    }
}
