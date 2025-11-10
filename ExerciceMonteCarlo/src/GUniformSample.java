import montecarlo.Experiment;

import java.util.Random;

public class GUniformSample implements Experiment {
    double xlimit;

    public GUniformSample(double lowerlimit, double xlimit, int ylimit) {
        this.xlimit = xlimit;
    }

    @Override
    public double execute(Random rnd) {
        double x = rnd.nextDouble() * xlimit;

        double gx = Math.pow(Math.E, -(x/8)) * Math.abs(Math.sin((Math.PI * x) / 2)) * Math.sqrt(6 - x);

        return gx * 6;
    }
}
