package functions;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        NFunction fun = new NFunction(3, xs -> {
           double x1 = xs[0],
                x2 = xs[1],
                x3 = xs[2];
            return x1 + x2 + x3;
        });

        System.out.println(fun.apply(1.0, 1.4, 2.3));
    }
}

