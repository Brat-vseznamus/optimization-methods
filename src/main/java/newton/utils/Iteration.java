package newton.utils;

import linear.DoubleVector;
import lombok.Data;

@Data
public class Iteration {
    private DoubleVector x0, x1, slope;
    private double f0, f1;


    public Iteration(DoubleVector x0, double f0, DoubleVector x1, double f1, DoubleVector slope) {
        this.x0 = x0;
        this.x1 = x1;
        this.slope = slope;
        this.f0 = f0;
        this.f1 = f1;
    }
}
