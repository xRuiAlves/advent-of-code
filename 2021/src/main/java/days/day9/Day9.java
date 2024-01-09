package days.day9;

import days.DaySolution;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 extends DaySolution {
    private static final int OUT_OF_BOUNDS = Integer.MAX_VALUE;
    private static final int HIGH_POINT = 9;

    public Day9(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        List<List<Integer>> map = buildMap();
        int count = 0;
        for (int i = 0; i < map.size(); ++i) {
            for (int j = 0; j < map.get(i).size(); ++j) {
                count += lowPointValue(map, j, i);
            }
        }
        return count;
    }

    @Override
    public Object part2() {
        List<List<Integer>> map = buildMap();
        TreeSet<Integer> basins = new TreeSet<>();
        for (int i = 0; i < map.size(); ++i) {
            for (int j = 0; j < map.get(i).size(); ++j) {
                basins.add(expandBasin(map, j, i));
            }
        }
        return Stream.of(basins.pollLast(), basins.pollLast(), basins.pollLast())
            .reduce(1, Math::multiplyExact);
    }

    private List<List<Integer>> buildMap() {
        return input
            .stream()
            .map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).collect(Collectors.toList())).collect(Collectors.toList());
    }

    private int visit(List<List<Integer>> map, int x, int y) {
        return (y < 0 || x < 0 || y >= map.size() || x >= map.get(y).size())
            ? OUT_OF_BOUNDS
            : map.get(y).get(x);
    }

    private int lowPointValue(List<List<Integer>> map, int x, int y) {
        return isLowPoint(map, x, y)
            ? visit(map, x, y) + 1
            : 0;
    }

    private boolean isLowPoint(List<List<Integer>> map, int x, int y) {
        int pointValue = visit(map, x, y);
        return pointValue < visit(map, x + 1, y)
            && pointValue < visit(map, x - 1, y)
            && pointValue < visit(map, x, y + 1)
            && pointValue < visit(map, x, y - 1);
    }

    private int expandBasin(List<List<Integer>> map, int x, int y) {
        int pointValue = visit(map, x, y);
        if (pointValue == OUT_OF_BOUNDS || pointValue == HIGH_POINT) {
            return 0;
        }
        map.get(y).set(x, HIGH_POINT);
        return 1
            + expandBasin(map, x + 1, y)
            + expandBasin(map, x - 1, y)
            + expandBasin(map, x, y + 1)
            + expandBasin(map, x, y - 1);
    }
}
