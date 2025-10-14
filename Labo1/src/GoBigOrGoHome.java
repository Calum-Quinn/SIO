import montecarlo.Experiment;

import java.util.Random;

public class GoBigOrGoHome implements Experiment {
    double p; // probability of success
    int f; // starting amount

    public GoBigOrGoHome(double p, int f) {
        this.p = p;
        this.f = f;
    }

    @Override
    public double execute(Random rnd) {
        int amount = f;
        int target = 2 * f;

        while (amount > 0 && amount < target) {
            if (rnd.nextDouble() < p) {
                amount++;
            } else {
                amount--;
            }
        }

        return amount == target ? 1.0 : 0.0;
    }
}
