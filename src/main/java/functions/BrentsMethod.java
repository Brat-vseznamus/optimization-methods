package functions;

import java.nio.channels.GatheringByteChannel;
import java.util.function.UnaryOperator;

public class BrentsMethod extends AbstractMethod {

    public BrentsMethod(UnaryOperator<Double> function) {
        super(function);
    }

    public BrentsMethod(UnaryOperator<Double> function, double eps) {
        super(function, eps);
    }


    @Override
    public double findMin(double a, double b) {

        double x, fX;  // temp min
        double w, fW;  // second min
        double v, fV;  // last second min
        double d, e;   // temp and last lengths
        x = w = v = (a + b) / 2d;
        fX = fW = fV = function.apply(x);
        d = e = b - a;

        int iteration = 0;
        while (true) {
            iteration++;
            System.out.println("next iteration " + iteration + ":\n");

            double g = e;
            e = d;
            System.out.println("g = " + g);
            System.out.println("e = " + e);

            if (Math.abs(x - (a + b) / 2d) + (b - a) / 2d <= 2 * eps) {
                break;
            }

            double u = 0;
            boolean parabolaAccepted = false;

            if (x != w && w != v && x != v
                    && fX != fW && fW != fV && fX != fV) {
                u = ParabolicMethod.getMin(x, w, v, fX, fW, fV);  // parabola min

                if ((a + eps <= u && u <= b - eps) && Math.abs(u - x) < g / 2d) {
                    parabolaAccepted = true; // accept u
                    System.out.println("parabola was accepted !");
                    d = b - x;
                }
            }

            if (!parabolaAccepted) {
                System.out.println("parabola was NOT accepted !");
                if (x < (b - a) / 2d) {
                    u = b - GoldenRatioMethod.GOLD * (b - x);
                    d = b - x;
                } else {
                    u = a + GoldenRatioMethod.GOLD * (x - a);
                    d = x - a;
                }
            }

            if (Math.abs(u - x) < eps) {
                u = x + Math.signum(u - x) * eps;
            }

            double fU = function.apply(u);

            if (fU <= fX) {
                if (u >= x) {
                    a = x;
                } else {
                    b = x;
                }
                v = w;
                w = x;
                x = u;
                fV = fW;
                fW = fX;
                fX = fU;
            } else {
                if (u >= x) {
                    b = u;
                } else {
                    a = u;
                }

                if (fU <= fW || w == x) {
                    v = w;
                    w = u;
                    fV = fW;
                    fW = fU;
                } else if (fU <= fV || v == x || v == w) {
                    v = u;
                    fV = fU;
                }
            }

            System.out.println("u = " + u);


        }

        return (a + b) / 2d;
    }

    public static class BrentInfo extends Info {
        public boolean isParabola = false;
    }



    @Override
    public String getName() {
        return "Брент";
    }
}

