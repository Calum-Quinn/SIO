package ch.heig.sio.lab2.groupH;

import ch.heig.sio.lab2.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab2.display.TspHeuristicObserver;
import ch.heig.sio.lab2.tsp.Edge;
import ch.heig.sio.lab2.tsp.TspData;
import ch.heig.sio.lab2.tsp.TspTour;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Nearest Neighbor (NN) heuristic for the Traveling Salesman Problem.
 *
 * This heuristic constructs a tour by starting at a given city and repeatedly
 * adding the nearest unvisited city to the end of the tour until all cities
 * have been visited.
 *
 * When multiple cities are equidistant (ties), the city with the smallest
 * index is chosen.
 *
 * Time complexity: O(n²)
 * Space complexity: O(n)
 */
public class NearestNeighbor implements ObservableTspConstructiveHeuristic {
    private static final class Traversal implements Iterator<Edge> {
        /** Number of cities currently in the partial tour */
        private final int n;

        /** Current position in the traversal */
        private int i = 0;

        /** Array containing the partial tour */
        private final int[] partialTour;

        /**
         * Constructs a new traversal iterator.
         *
         * @param partialTour Array containing the cities in the partial tour
         * @param n Number of cities to traverse
         * @throws IllegalArgumentException if n is larger than the tour array
         */
        Traversal(int[] partialTour, int n) {
            this.partialTour = partialTour;
            this.n = n;

            if (n > partialTour.length)
                throw new IllegalArgumentException("Number of edges to traverse (" + (n - 1) +
                    ") should be smaller than tour length (" + partialTour.length + ").");

            // Avoid self-loop if n = 1.
            if (n < 2)
                i = 1;
        }

        @Override
        public boolean hasNext() {
            // Traverse only the edges of actual path without closing it (i < n would close it).
            return i < (n - 1);
        }

        @Override
        public Edge next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return new Edge(partialTour[i], partialTour[++i]);
        }
    }

    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        // Number of cities in data set.
        int n = data.getNumberOfCities();

        // Array storing the (partial) tour.
        int[] tour = new int[n];
        tour[0] = startCityIndex;

        // Array to keep track of visited cities - O(n) space
        boolean[] visited = new boolean[n];
        visited[startCityIndex] = true;

        // Current city (last city added to the tour)
        int currentCity = startCityIndex;

        // Incrementally computed tour length.
        long length = 0;

        // Build the tour by adding n-1 cities (the starting city is already in the tour)
        for (int step = 1; step < n; step++) {
            int nearestCity = -1;
            int shortestDistance = Integer.MAX_VALUE;

            // Find the nearest unvisited city to currentCity - O(n) per iteration
            for (int city = 0; city < n; city++) {
                if (!visited[city]) {
                    int distance = data.getDistance(currentCity, city);

                    // Choose this city if it's closer, or if it's equally close but has a smaller index
                    // (tie-breaking rule: choose smallest city number)
                    if (distance < shortestDistance ||
                        (distance == shortestDistance && city < nearestCity)) {
                        shortestDistance = distance;
                        nearestCity = city;
                    }
                }
            }

            // Add the nearest city to the tour
            tour[step] = nearestCity;
            visited[nearestCity] = true;

            // Update tour length
            length += shortestDistance;

            // Move to the newly added city
            currentCity = nearestCity;

            // Notify observer for visualization (show partial tour)
            observer.update(new Traversal(tour, step + 1));
        }

        // Close the tour by adding the distance from the last city back to the start
        length += data.getDistance(tour[n - 1], tour[0]);

        return new TspTour(data, tour, length);
    }

    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }
}
