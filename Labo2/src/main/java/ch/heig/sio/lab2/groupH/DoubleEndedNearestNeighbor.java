package ch.heig.sio.lab2.groupH;

import ch.heig.sio.lab2.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab2.display.TspHeuristicObserver;
import ch.heig.sio.lab2.tsp.Edge;
import ch.heig.sio.lab2.tsp.TspData;
import ch.heig.sio.lab2.tsp.TspTour;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Double Ended Nearest Neighbor (DENN) heuristic for the Traveling Salesman Problem.
 *
 * This heuristic constructs a tour by starting with the two nearest cities, then
 * repeatedly adding the nearest unvisited city to either end of the chain until
 * all cities have been visited. The last city is inserted between the two ends
 * to close the tour.
 *
 * When multiple cities are equidistant from an endpoint, the city with the smallest
 * index is chosen. When a city is equidistant from both ends (head and tail),
 * it is added to the head (as per assignment requirements).
 *
 * Time complexity: O(n²) where n is the number of cities
 * Space complexity: O(n)
 *
 * @author Group H
 */
public class DoubleEndedNearestNeighbor implements ObservableTspConstructiveHeuristic {

    /**
     * Iterator for traversing the edges of the partial tour during construction.
     * Used for visualization purposes through the observer pattern.
     */
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

        // Use a LinkedList to efficiently add cities at both ends - O(1) per insertion
        LinkedList<Integer> chain = new LinkedList<>();
        chain.add(startCityIndex);

        // Array to keep track of visited cities - O(n) space
        boolean[] visited = new boolean[n];
        visited[startCityIndex] = true;

        // Find the nearest city to the starting city to initialize the chain
        int nearestToStart = -1;
        int minDistanceToStart = Integer.MAX_VALUE;
        for (int city = 0; city < n; city++) {
            if (!visited[city]) {
                int distance = data.getDistance(startCityIndex, city);
                if (distance < minDistanceToStart ||
                    (distance == minDistanceToStart && city < nearestToStart)) {
                    minDistanceToStart = distance;
                    nearestToStart = city;
                }
            }
        }

        // Add the nearest city to form initial chain: [head, tail]
        chain.add(nearestToStart);
        visited[nearestToStart] = true;

        // Head is the first city in the chain, tail is the last city
        int head = startCityIndex;
        int tail = nearestToStart;

        // Incrementally computed tour length
        long length = minDistanceToStart;

        // Notify observer with initial chain (2 cities)
        int[] tourArray = new int[n];
        copyChainToArray(chain, tourArray);
        observer.update(new Traversal(tourArray, 2));

        // Build the tour by adding cities to either end until only one city remains
        int citiesAdded = 2;
        while (citiesAdded < n - 1) {
            // Find the nearest unvisited city to either head or tail - O(n) per iteration
            int nearestCity = -1;
            int shortestDistance = Integer.MAX_VALUE;
            boolean addToHead = true; // Track whether to add to head or tail

            for (int city = 0; city < n; city++) {
                if (!visited[city]) {
                    int distanceToHead = data.getDistance(head, city);
                    int distanceToTail = data.getDistance(tail, city);

                    // Find minimum distance to either end
                    int minDistance = Math.min(distanceToHead, distanceToTail);

                    // Update nearest city if this city is closer, or if it's equally close
                    // but has a smaller index (tie-breaking rule)
                    if (minDistance < shortestDistance ||
                        (minDistance == shortestDistance && city < nearestCity)) {
                        shortestDistance = minDistance;
                        nearestCity = city;

                        // Determine which end to add to
                        // If distances are equal, prefer head (as per assignment requirements)
                        if (distanceToHead <= distanceToTail) {
                            addToHead = true;
                        } else {
                            addToHead = false;
                        }
                    }
                }
            }

            // Add the nearest city to the appropriate end
            if (addToHead) {
                chain.addFirst(nearestCity);
                head = nearestCity;
            } else {
                chain.addLast(nearestCity);
                tail = nearestCity;
            }

            visited[nearestCity] = true;
            length += shortestDistance;
            citiesAdded++;

            // Notify observer for visualization
            copyChainToArray(chain, tourArray);
            observer.update(new Traversal(tourArray, citiesAdded));
        }

        // Add the last remaining city between head and tail to close the tour
        int lastCity = -1;
        for (int city = 0; city < n; city++) {
            if (!visited[city]) {
                lastCity = city;
                break;
            }
        }

        // Insert the last city at the end (which will connect back to head to close the cycle)
        chain.addLast(lastCity);
        length += data.getDistance(tail, lastCity);
        length += data.getDistance(lastCity, head); // Close the tour

        // Convert LinkedList to array for the final tour
        int[] tour = new int[n];
        int idx = 0;
        for (int city : chain) {
            tour[idx++] = city;
        }

        return new TspTour(data, tour, length);
    }

    /**
     * Helper method to copy the current chain to an array for observer notification.
     *
     * @param chain The current chain as a LinkedList
     * @param array The array to copy to
     */
    private void copyChainToArray(LinkedList<Integer> chain, int[] array) {
        int idx = 0;
        for (int city : chain) {
            array[idx++] = city;
        }
    }

    @Override
    public TspTour computeTour(TspData data, int startCity) {
        return ObservableTspConstructiveHeuristic.super.computeTour(data, startCity);
    }
}
