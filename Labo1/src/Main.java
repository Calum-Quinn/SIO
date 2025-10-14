import montecarlo.*;
import statistics.*;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
        double maxHalfWidth = 0.0001;

        for (int i = 0; i < 3; i++) {
            StatCollector stat = new StatCollector();

            Random rnd = new Random();
            rnd.setSeed(0x1350185);

            Experiment exp = new GoBigOrGoHome(18.0/37.0, 5);

            long start = System.currentTimeMillis();
            MonteCarloSimulation.simulateTillGivenCIHalfWidth(exp, 0.95, maxHalfWidth, 1000000, 100000, rnd, stat);
            long end = System.currentTimeMillis();

            System.out.printf("*************************************%n  Simulation results - Experiment %d%n*************************************%n", i + 1);
            System.out.printf("Number of rounds generated: %d%n", stat.getNumberOfObs());
            System.out.printf("Estimated prob. of doubling amount:    %.6f%n", stat.getAverage());
            System.out.printf("Confidence interval (95%%):  %.5f +/- %.6f%n", stat.getAverage(), stat.getConfidenceIntervalHalfWidth(0.95));
            System.out.printf("Time taken (ms): %d%n%n", end - start);

            maxHalfWidth /= 2;
        }
	}
}
