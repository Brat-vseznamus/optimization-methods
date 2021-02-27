import methods.DichotomyMethod;
import methods.OptimizationAlgorithm;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Double, Double> function;
        function = x -> x * x + Math.exp(-0.35d * x);

        OptimizationAlgorithm method = new DichotomyMethod();
        double min = method.findMin(function, -2d, 3d);

        System.out.println(min);
    }
}
