package days.day5;

import days.DaySolution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day5 extends DaySolution {
    public Day5(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        return countOverlappingPoints(false);
    }

    @Override
    public Object part2() {
        return countOverlappingPoints(true);
    }

    private long countOverlappingPoints(boolean includeDiagonalLines) {
        List<Line> lines = lines();
        Map<Point, Integer> pointCounts = new HashMap<>();

        lines.stream()
            .filter(line -> line.isStraightLine() || includeDiagonalLines)
            .forEach(line -> {
                line.points().forEach(point -> {
                    pointCounts.put(point, pointCounts.getOrDefault(point, 0) + 1);
                });
            });

        return pointCounts
            .values()
            .stream()
            .filter(count -> count > 1)
            .count();
    }

    private List<Line> lines() {
        return input
            .stream()
            .map(Line::of)
            .collect(Collectors.toList());
    }
}
