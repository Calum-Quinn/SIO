package ch.heig.sio.lab2.groupH;

import ch.heig.sio.lab2.tsp.TspConstructiveHeuristic;
import ch.heig.sio.lab2.tsp.TspData;
import ch.heig.sio.lab2.tsp.TspTour;

import java.util.HashMap;
import java.util.Map;

/**
 * Analysis program for evaluating the performance of TSP heuristics.
 *
 * This program runs the Nearest Neighbor (NN) and Double Ended Nearest Neighbor (DENN)
 * heuristics on multiple TSP instances, testing each heuristic with every possible
 * starting city. It computes and displays statistics including minimum, mean, and
 * maximum tour lengths (both absolute and relative to optimal solutions).
 */
public final class Analyze {

    /**
     * Map of instance names to their optimal tour lengths.
     */
    private static final Map<String, Long> OPTIMAL_SOLUTIONS = new HashMap<>();

    static {
        OPTIMAL_SOLUTIONS.put("pcb442.dat", 50778L);
        OPTIMAL_SOLUTIONS.put("att532.dat", 86729L);
        OPTIMAL_SOLUTIONS.put("u574.dat", 36923L);
        OPTIMAL_SOLUTIONS.put("pcb1173.dat", 56892L);
        OPTIMAL_SOLUTIONS.put("nrw1379.dat", 56638L);
        OPTIMAL_SOLUTIONS.put("u1817.dat", 57201L);
    }

    public static void main(String[] args) {
        // Instances to test
        String[] instances = {
            "pcb442.dat",
            "att532.dat",
            "u574.dat",
            "pcb1173.dat",
            "nrw1379.dat",
            "u1817.dat"
        };

        // Heuristics to test
        Map<String, TspConstructiveHeuristic> heuristics = new HashMap<>();
        heuristics.put("Nearest Neighbor (NN)", new NearestNeighbor());
        heuristics.put("Double Ended Nearest Neighbor (DENN)", new DoubleEndedNearestNeighbor());

        System.out.println("=".repeat(80));
        System.out.println("TSP HEURISTICS PERFORMANCE ANALYSIS");
        System.out.println("=".repeat(80));
        System.out.println();

        // Test each instance
        for (String instance : instances) {
            try {
                System.out.println("Instance: " + instance);
                System.out.println("-".repeat(80));

                // Load the data
                TspData data = TspData.fromFile("data/" + instance);
                int n = data.getNumberOfCities();
                long optimalLength = OPTIMAL_SOLUTIONS.get(instance);

                System.out.println("Number of cities: " + n);
                System.out.println("Optimal tour length: " + optimalLength);
                System.out.println();

                // Test each heuristic
                for (Map.Entry<String, TspConstructiveHeuristic> entry : heuristics.entrySet()) {
                    String heuristicName = entry.getKey();
                    TspConstructiveHeuristic heuristic = entry.getValue();

                    System.out.println("Heuristic: " + heuristicName);

                    // Statistics initialization
                    long minLength = Long.MAX_VALUE;
                    long maxLength = Long.MIN_VALUE;
                    long totalLength = 0;
                    double minRelative = Double.MAX_VALUE;
                    double maxRelative = Double.MIN_VALUE;
                    double totalRelative = 0.0;

                    // Measure computation time
                    long startTime = System.currentTimeMillis();

                    // Run heuristic with each different city as the starting point
                    for (int startCity = 0; startCity < n; startCity++) {
                        TspTour tour = heuristic.computeTour(data, startCity);
                        long tourLength = tour.length();

                        // Update absolute statistics
                        minLength = Math.min(minLength, tourLength);
                        maxLength = Math.max(maxLength, tourLength);
                        totalLength += tourLength;

                        // Update relative statistics (as percentage above optimal)
                        double relativePerformance = ((double) tourLength / optimalLength) * 100.0;
                        minRelative = Math.min(minRelative, relativePerformance);
                        maxRelative = Math.max(maxRelative, relativePerformance);
                        totalRelative += relativePerformance;
                    }

                    long endTime = System.currentTimeMillis();
                    double elapsedTimeMs = endTime - startTime;

                    // Calculate means
                    double meanLength = (double) totalLength / n;
                    double meanRelative = totalRelative / n;

                    // Display statistics
                    System.out.println("  Absolute tour lengths:");
                    System.out.printf("    Minimum: %d%n", minLength);
                    System.out.printf("    Mean:    %.2f%n", meanLength);
                    System.out.printf("    Maximum: %d%n", maxLength);
                    System.out.println();

                    System.out.println("  Relative performance (% of optimal):");
                    System.out.printf("    Minimum: %.2f%%%n", minRelative);
                    System.out.printf("    Mean:    %.2f%%%n", meanRelative);
                    System.out.printf("    Maximum: %.2f%%%n", maxRelative);
                    System.out.println();

                    System.out.printf("  Total computation time: %.2f ms%n", elapsedTimeMs);
                    System.out.printf("  Average time per tour: %.4f ms%n", elapsedTimeMs / n);
                    System.out.println();
                }
                System.out.println("=".repeat(80));
                System.out.println();
            } catch (java.io.FileNotFoundException e) {
                System.err.println("Error: File not found - " + instance);
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Error processing instance " + instance);
                e.printStackTrace();
            }
        }
        System.out.println("Analysis complete.");
    }
}
