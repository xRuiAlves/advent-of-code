package days.day19;

import days.DaySolution;

import java.util.*;

public class Day19 extends DaySolution {
    public Day19(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        Map<Integer, Scanner> scannersIds = parseScanners();
        return updateScanners(scannersIds)
            .stream()
            .flatMap(scanner -> scanner
                .points()
                .stream()
                .map(Point::of))
            .count();
    }

    @Override
    public Object part2() {
        List<Scanner> scanners = updateScanners(parseScanners());

        int maxDistance = 0;
        for (int i = 0; i < scanners.size(); ++i) {
            for (int j = i + 1; j < scanners.size(); ++j) {
                maxDistance = Math.max(maxDistance,
                    Scanner.manhattanDistance(scanners.get(i), scanners.get(j)));
            }
        }

        return maxDistance;
    }

    private List<Scanner> updateScanners(Map<Integer, Scanner> scannersIds) {
        scannersIds.get(0).setPosition(new Point(0, 0, 0));
        List<Scanner> scanners = new ArrayList<>(scannersIds.values());
        Set<Integer> normalizedScanners = new HashSet<>();
        Queue<Integer> toVisit = new LinkedList<>();
        toVisit.offer(0);

        while (!toVisit.isEmpty()) {
            int currId = toVisit.poll();
            Scanner curr = scannersIds.get(currId);
            normalizedScanners.add(curr.id());

            for (Scanner other : scannersIds.values()) {
                if (normalizedScanners.contains(other.id())) {
                    continue;
                }

                AxisDiff xAxisDiff = getAxisDiff(curr, other, 0);
                if (xAxisDiff == null) {
                    continue;
                }

                AxisDiff yAxisDiff = getAxisDiff(curr, other, 1);
                AxisDiff zAxisDiff = getAxisDiff(curr, other, 2);
                assert yAxisDiff != null;
                assert zAxisDiff != null;

                other.update(xAxisDiff, yAxisDiff, zAxisDiff);
                other.setPosition(new Point(
                    xAxisDiff.shift(),
                    yAxisDiff.shift(),
                    zAxisDiff.shift()
                ));

                toVisit.add(other.id());
                normalizedScanners.add(other.id());
            }
        }

        return scanners;
    }

    private AxisDiff getAxisDiff(Scanner curr, Scanner other, int axisIndex) {
        for (int otherAxisIndex = 0; otherAxisIndex <= 2; ++otherAxisIndex) {
            for (int sign : List.of(-1, 1)) {
                Integer diff = diff(curr, other, axisIndex, otherAxisIndex, sign);
                if (diff != null) {
                    return new AxisDiff(otherAxisIndex, sign, diff);
                }
            }
        }

        return null;
    }

    private Integer diff(Scanner curr, Scanner other, int axisIndex, int otherAxisIndex, int sign) {
        Map<Integer, Integer> diffCounts = new HashMap<>();

        curr.points().forEach(currPoint -> {
            other.points().forEach(otherPoint -> {
                int diff = (otherPoint[otherAxisIndex] * sign) - currPoint[axisIndex];
                diffCounts.put(diff, diffCounts.getOrDefault(diff, 0) + 1);
            });
        });

        for (int diff : diffCounts.keySet()) {
            if (diffCounts.get(diff) >= 12) {
                return diff;
            }
        }

        return null;
    }

    private Map<Integer, Scanner> parseScanners() {
        Map<Integer, Scanner> scanners = new HashMap<>();
        int currScanner = -1;

        for (String line : input) {
            if (line.startsWith("---")) {
                currScanner += 1;
                scanners.put(currScanner, new Scanner(currScanner));
            } else if (!line.isBlank()) {
                int[] coords = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
                scanners.get(currScanner).points().add(coords);
            }
        }

        return scanners;
    }
}
