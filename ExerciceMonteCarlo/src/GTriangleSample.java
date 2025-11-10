import montecarlo.Experiment;

import java.util.Random;

public class GTriangleSample implements Experiment {
    double xlimit;

    double fx(double x) {
        return 1/18 * x + 1/3;
    }

    public GTriangleSample(double xlimit) {
        this.xlimit = xlimit;
    }

    @Override
    public double execute(Random rnd) {
        double x = rnd.nextDouble() * xlimit;

        double gx = Math.pow(Math.E, -(x/8)) * Math.abs(Math.sin((Math.PI * x) / 2)) * Math.sqrt(6 - x);

        return gx * 6;
    }
}
