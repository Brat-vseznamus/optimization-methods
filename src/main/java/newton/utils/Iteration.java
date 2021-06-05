package newton.utils;

import linear.DoubleVector;

public class Iteration {
    private final DoubleVector x0;
    private final DoubleVector x1;
    private final DoubleVector slope;
    private final double f0;
    private final double f1;


    public Iteration(final DoubleVector x0, final double f0, final DoubleVector x1, final double f1, final DoubleVector slope) {
        this.x0 = x0;
        this.x1 = x1;
        this.slope = slope;
        this.f0 = f0;
        this.f1 = f1;
    }

    public DoubleVector getX0() {
        return x0;
    }

    public DoubleVector getX1() {
        return x1;
    }

    public DoubleVector getSlope() {
        return slope;
    }

    public double getF0() {
        return f0;
    }

    public double getF1() {
        return f1;
    }
}
