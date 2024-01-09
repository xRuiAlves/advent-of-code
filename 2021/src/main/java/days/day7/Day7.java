package days.day7;

import days.DaySolution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 extends DaySolution {
    public Day7(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        List<Integer> positions = positions();
        int median = positions.get(positions.size() / 2);
        return positions
            .stream()
            .mapToInt(distance -> Math.abs(distance - median))
            .sum();
    }

    @Override
    public Object part2() {
        List<Integer> positions = positions();
        Double average = positions.stream().mapToInt(i -> i).average().getAsDouble();
        int floorAverage = (int) Math.floor(average);
        int ceilingAverage = (int) Math.ceil(average);

        return Math.min(
            growingDistancesFuel(positions, floorAverage),
            growingDistancesFuel(positions, ceilingAverage)
        );
    }

    private List<Integer> positions() {
        return Arrays.stream(firstInputLine()
            .split(","))
            .map(Integer::valueOf)
            .sorted()
            .collect(Collectors.toList());
    }

    private int growingDistancesFuel(List<Integer> positions, int average) {
        return positions
            .stream()
            .mapToInt(distance -> Math.abs(distance - average))
            .map(distance -> (distance * (distance + 1)) / 2)
            .sum();
    }
}
