import montecarlo.Experiment;
import montecarlo.MonteCarloSimulation;
import statistics.StatCollector;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
        double maxHalfWidth = 0.00001;
        double level = 0.95;
        long initialNumberOfRuns = 500_000_000;
        StatCollector stat = new StatCollector();

        Random rnd = new Random();
        rnd.setSeed(0x1350185);

        Experiment exp = new GAcceptReject(0, 6, 2);

        long start = System.currentTimeMillis();
        MonteCarloSimulation.simulateNRuns(exp,initialNumberOfRuns, rnd, stat);
        long end = System.currentTimeMillis();

        // Estimate the number of runs needed for the desired confidence interval half width and the time it would take
        double currentHalfWidth = stat.getConfidenceIntervalHalfWidth(level);
        // If the current half-width is already within the limit, no further action is needed

        // Calculate the total number of runs needed using the scaling relationship:
        // n_needed = n_current * (currentHalfWidth / maxHalfWidth)^2
        double totalRunsNeeded = initialNumberOfRuns * Math.pow(currentHalfWidth / maxHalfWidth, 2);
        long additionalRunsNeeded = (long) totalRunsNeeded - initialNumberOfRuns;

        // Estimate time for additional runs based on initial run time
        long estimatedAdditionalTime = (end - start) * additionalRunsNeeded / initialNumberOfRuns;

        System.out.println("Total runs needed: " + totalRunsNeeded);
        System.out.println("Estimated additional time needed (ms): " + estimatedAdditionalTime);
	}
}
