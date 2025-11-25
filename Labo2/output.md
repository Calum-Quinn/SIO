# TSP Heuristics Performance Analysis

## Overview

This document presents the performance analysis of two heuristics for the Traveling Salesman Problem (TSP):

1. **Nearest Neighbor (NN)**: Constructs a tour by repeatedly adding the nearest unvisited city
2. **Double Ended Nearest Neighbor (DENN)**: Constructs a tour by adding the nearest unvisited city to either end of a growing chain

Both heuristics were implemented with O(n²) time complexity and tested on six TSP instances from the TSPLIB library.

## Methodology

For each instance and each heuristic:
- Computed tours starting from each city (n starting points)
- Calculated minimum, mean, and maximum tour lengths
- Computed relative performance as percentage of optimal solution length
- Measured total computation time

## Results

### Instance: pcb442 (442 cities)
**Optimal tour length:** 50,778

| Heuristic | Min Length | Mean Length | Max Length | Min % | Mean % | Max % | Total Time (ms) |
|-----------|------------|-------------|------------|-------|--------|-------|-----------------|
| **NN**    | 58,950     | 62,248.90   | 66,158     | 116.09% | 122.59% | 130.29% | 57.28 |
| **DENN**  | 59,297     | 61,484.40   | 65,014     | 116.78% | 121.08% | 128.04% | 163.41 |

**Analysis:**
- DENN produces better quality tours on average (121.08% vs 122.59%)
- NN is approximately 2.9× faster than DENN
- Both heuristics achieve reasonable solutions within 16-30% of optimal

---

### Instance: att532 (532 cities)
**Optimal tour length:** 86,729

| Heuristic | Min Length | Mean Length | Max Length | Min % | Mean % | Max % | Total Time (ms) |
|-----------|------------|-------------|------------|-------|--------|-------|-----------------|
| **NN**    | 101,969    | 109,664.14  | 116,093    | 117.57% | 126.44% | 133.86% | 98.04 |
| **DENN**  | 101,337    | 108,131.37  | 113,191    | 116.84% | 124.68% | 130.51% | 207.59 |

**Analysis:**
- DENN performs better on average (124.68% vs 126.44%)
- NN is approximately 2.1× faster than DENN
- Similar quality improvement with DENN at the cost of longer computation time

---

### Instance: u574 (574 cities)
**Optimal tour length:** 36,923

| Heuristic | Min Length | Mean Length | Max Length | Min % | Mean % | Max % | Total Time (ms) |
|-----------|------------|-------------|------------|-------|--------|-------|-----------------|
| **NN**    | 45,308     | 47,801.81   | 51,477     | 122.71% | 129.46% | 139.42% | 84.36 |
| **DENN**  | 45,581     | 47,650.16   | 49,708     | 123.45% | 129.05% | 134.63% | 225.98 |

**Analysis:**
- Both heuristics perform similarly on average (129.05% vs 129.46%)
- DENN produces more consistent results (lower max: 134.63% vs 139.42%)
- NN is approximately 2.7× faster than DENN

---

### Instance: pcb1173 (1,173 cities)
**Optimal tour length:** 56,892

| Heuristic | Min Length | Mean Length | Max Length | Min % | Mean % | Max % | Total Time (ms) |
|-----------|------------|-------------|------------|-------|--------|-------|-----------------|
| **NN**    | 70,115     | 73,025.62   | 75,613     | 123.24% | 128.36% | 132.91% | 774.02 |
| **DENN**  | 69,647     | 72,678.73   | 75,765     | 122.42% | 127.75% | 133.17% | 2,043.44 |

**Analysis:**
- DENN slightly outperforms NN on average (127.75% vs 128.36%)
- NN is approximately 2.6× faster than DENN
- Performance gap between heuristics narrows for larger instances

---

### Instance: nrw1379 (1,379 cities)
**Optimal tour length:** 56,638

| Heuristic | Min Length | Mean Length | Max Length | Min % | Mean % | Max % | Total Time (ms) |
|-----------|------------|-------------|------------|-------|--------|-------|-----------------|
| **NN**    | 68,531     | 71,236.98   | 73,781     | 121.00% | 125.78% | 130.27% | 1,583.95 |
| **DENN**  | 68,599     | 70,702.35   | 72,642     | 121.12% | 124.83% | 128.26% | 3,671.67 |

**Analysis:**
- DENN produces better average tours (124.83% vs 125.78%)
- NN is approximately 2.3× faster than DENN
- Best minimum result achieved by NN (121.00%)

---

### Instance: u1817 (1,817 cities)
**Optimal tour length:** 57,201

| Heuristic | Min Length | Mean Length | Max Length | Min % | Mean % | Max % | Total Time (ms) |
|-----------|------------|-------------|------------|-------|--------|-------|-----------------|
| **NN**    | 68,227     | 71,045.62   | 73,954     | 119.28% | 124.20% | 129.29% | 3,226.44 |
| **DENN**  | 68,386     | 70,316.53   | 72,735     | 119.55% | 122.93% | 127.16% | 8,413.61 |

**Analysis:**
- DENN shows clear advantage in solution quality (122.93% vs 124.20%)
- NN is approximately 2.6× faster than DENN
- Both heuristics achieve good results (under 130% of optimal on average)

---

## Summary Statistics

### Average Performance Across All Instances

| Metric | NN | DENN | Winner |
|--------|-----|------|--------|
| **Mean tour quality (% of optimal)** | 126.14% | 125.05% | DENN |
| **Speed advantage** | 1× (baseline) | ~2.6× slower | NN |
| **Best minimum found** | 4 instances | 2 instances | NN |
| **Best mean found** | 1 instance | 5 instances | DENN |

### Key Observations

1. **Solution Quality:**
   - DENN consistently produces better average tour lengths across 5 out of 6 instances
   - DENN average performance: 125.05% of optimal
   - NN average performance: 126.14% of optimal
   - Improvement of ~1.09% by using DENN

2. **Computational Efficiency:**
   - NN is significantly faster (approximately 2.6× on average)
   - Time complexity difference likely due to DENN checking distances to two endpoints
   - Both algorithms scale well with O(n²) complexity

3. **Trade-offs:**
   - Use **NN** when: Speed is critical and 1-2% quality difference is acceptable
   - Use **DENN** when: Solution quality is prioritized over computation time
   - For very large instances (>1000 cities), the time difference becomes more significant

4. **Tie-Breaking Rules:**
   - Both heuristics implement the required tie-breaking: selecting the city with the smallest index
   - DENN correctly adds to the head when distances to both endpoints are equal

5. **Starting Point Sensitivity:**
   - Tour quality varies significantly based on starting city (up to 14% difference in some instances)
   - DENN shows slightly more consistency (lower variance in max-min spread)

## Implementation Details

### Time Complexity
Both heuristics achieve the required **O(n²)** time complexity:
- **NN:** n iterations × O(n) to find nearest city = O(n²)
- **DENN:** n iterations × O(n) to find nearest to either endpoint = O(n²)

### Space Complexity
Both heuristics use **O(n)** space:
- Boolean array for visited cities: O(n)
- Tour array: O(n)
- DENN additionally uses a LinkedList for efficient dual-ended insertion

### Correctness
All implementations:
- Visit each city exactly once
- Return to the starting city to form a valid tour
- Correctly compute tour lengths (validated against data structures)
- Handle tie-breaking according to specifications

## Conclusion

The Double Ended Nearest Neighbor (DENN) heuristic demonstrates superior solution quality compared to the basic Nearest Neighbor (NN) heuristic, achieving approximately 1% better performance on average across all test instances. This improvement comes at the cost of increased computation time (approximately 2.6× slower).

For practical applications:
- **Small to medium instances (<1000 cities):** DENN is recommended for its better quality with acceptable runtime
- **Large instances or time-critical applications:** NN provides a good balance of speed and quality
- **Best practice:** Run both heuristics with multiple starting points and select the best solution

Both heuristics provide reasonable solutions (generally within 20-35% of optimal) and can serve as good initial solutions for more sophisticated optimization methods such as local search or metaheuristics.
