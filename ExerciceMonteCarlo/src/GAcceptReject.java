import montecarlo.Experiment;

import java.util.Random;

public class GAcceptReject implements Experiment {
    double lowerlimit;
    double xlimit;
    double ylimit;

    public GAcceptReject(double lowerlimit, double xlimit, int ylimit) {
        this.lowerlimit = lowerlimit;
        this.xlimit = xlimit;
        this.ylimit = ylimit;
    }

    @Override
    public double execute(Random rnd) {
        double x = rnd.nextDouble() * xlimit;
        double y = rnd.nextDouble() * ylimit;

        double gx = Math.pow(Math.E, -(x/8)) * Math.abs(Math.sin((Math.PI * x) / 2)) * Math.sqrt(6 - x);

        if (y <= gx) {
            return ylimit * (xlimit - lowerlimit);
        } else {
            return 0.0;
        }
    }
}
