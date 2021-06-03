package newton;

public class Iteration {
    public double x0, f0, x1, f1, slope;

    public Iteration(double x0, double f0, double x1, double f1, double slope) {
        this.x0 = x0;
        this.f0 = f0;
        this.x1 = x1;
        this.f1 = f1;
        this.slope = slope;
    }

}
