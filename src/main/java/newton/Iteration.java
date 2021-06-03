package newton;

import lombok.Data;

@Data
public class Iteration {
    private double x0, f0, x1, f1, slope;

    public Iteration(final double x0, final double f0, final double x1, final double f1, final double slope) {
        this.x0 = x0;
        this.f0 = f0;
        this.x1 = x1;
        this.f1 = f1;
        this.slope = slope;
    }

}
