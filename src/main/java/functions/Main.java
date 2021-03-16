package functions;


import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        UnaryOperator<Double> function;
        function = x -> x * x + Math.exp(-0.35d * x);
        double left = -2d;
        double right = 3d;


        OptimizationAlgorithm methodBrent = new BrentsMethod(function);

        OptimizationAlgorithm methodPar = new ParabolicMethod(function);

        OptimizationAlgorithm methodDick = new DichotomyMethod(function);
        OptimizationAlgorithm methodGold = new GoldenRatioMethod(function);
        OptimizationAlgorithm methodFib = new FibonacciMethod(function);

        System.out.println("Result of Brents: " + methodBrent.findMin(left, right));

        System.out.println("Result of parabolic: " + methodPar.findMin(left, right));

        System.out.println("Result of dichotomy: " + methodDick.findMin(left, right));
        System.out.println("Result of golden ratio: " + methodGold.findMin(left, right));
        System.out.println("Result of fibonacci: " + methodFib.findMin(left, right));

        // System.out.println("Table: " + method.getTable());
    }
}
