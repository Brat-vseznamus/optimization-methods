package newton.utils;

import linear.DoubleVector;
import lombok.Data;

@Data
public class Iteration {
    private DoubleVector x0, x1, slope;
    private double f0, f1;


    public Iteration(final DoubleVector x0, final double f0, final DoubleVector x1, final double f1, final DoubleVector slope) {
        this.x0 = x0;
        this.x1 = x1;
        this.slope = slope;
        this.f0 = f0;
        this.f1 = f1;
    }
}
