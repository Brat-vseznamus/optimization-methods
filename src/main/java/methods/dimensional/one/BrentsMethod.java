package methods.dimensional.one;
import java.util.function.UnaryOperator;

public class BrentsMethod extends AbstractOneDimensionalMethod {

    public BrentsMethod(final UnaryOperator<Double> function) {
        super(function);
    }

    public BrentsMethod(final UnaryOperator<Double> function, final double eps) {
        super(function, eps);
    }

    public void addInfo(final double l, final double r, final double value, final boolean isParabolic) {
        table.add(new BrentInfo(l, r, value, isParabolic));
    }

    @Override
    public double findMin(double a, double b) {
        table.clear();
        calcs = 0;

        double x, fX;  // temp min
        double w, fW;  // second min
        double v, fV;  // last second min
        double d, e;   // temp and last lengths
        x = w = v = b - GoldenRatioMethod.GOLD * (b - a);
        fX = fW = fV = function.apply(x);
        calcs++;
        d = e = b - a;

        int iteration = 0;
        while (iteration < 100) {
            iteration++;

            final double g = e;
            e = d;
            final double tol = eps * Math.abs(x) + eps / 10d;

            if (Math.abs(x - (a + b) / 2d) + (b - a) / 2d <= 2 * tol) {
                break;
            }

            double u = 0;
            boolean parabolaAccepted = false;

            if (x != w && w != v && x != v
                    && fX != fW && fW != fV && fX != fV) {
                u = ParabolicMethod.getMin(x, w, v, fX, fW, fV);  // parabola min

                if ((a <= u && u <= b) && Math.abs(u - x) < g / 2d) {
                    parabolaAccepted = true; // accept u
                    if (u - a < 2 * tol || b - u < 2 * tol) {
                        u = x - Math.signum(x - (a + b) / 2d) * tol;
                    }
                    addInfo(x, v, w, true);
                }
            }

            if (!parabolaAccepted) {
                if (x < (b - a) / 2d) {
                    u = b - GoldenRatioMethod.GOLD * (b - x);
                    e = b - x;
                } else {
                    u = a + GoldenRatioMethod.GOLD * (x - a);
                    e = x - a;
                }

            }

            if (Math.abs(u - x) < tol) {
                u = x + Math.signum(u - x) * tol;
            }
            d = Math.abs(u - x);

            final double fU = function.apply(u);
            calcs++;

            if (fU <= fX) {
                if (u >= x) {
                    if (a == x) {
                        break;
                    }
                    a = x;
                } else {
                    if (b == x) {
                        break;
                    }
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
                    if (b == u) {
                        break;
                    }
                    b = u;
                } else {
                    if (a == u) {
                        break;
                    }
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

            if (!parabolaAccepted) {
                addInfo(a, b, (a + b) / 2d, false);
            }

        }

        return (a + b) / 2d;
    }

    public static class BrentInfo extends Info {
        public boolean isParabolic;

        public BrentInfo(final double l, final double r, final double value, final boolean isParabolic) {
            super(l, r, value);
            this.isParabolic = isParabolic;
        }
    }

    @Override
    public String getName() {
        return "Брент";
    }
}
