package days.day1;

import days.DaySolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 extends DaySolution {
    public Day1(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        return countIncreases(inputAsIntList());
    }

    @Override
    public Object part2() {
        List<Integer> measurementsTrios = getMeasurementsTrios(inputAsIntList());
        return countIncreases(measurementsTrios);
    }

    private List<Integer> getMeasurementsTrios(List<Integer> depths) {
        List<Integer> trios = new ArrayList<>();
        for (int i = 2; i < depths.size(); ++i) {
            trios.add(depths.get(i - 2) + depths.get(i - 1) + depths.get(i));
        }
        return trios;
    }

    private int countIncreases(List<Integer> depths) {
        int count = 0;
        for (int i = 1; i < depths.size(); ++i) {
            count += depths.get(i) > depths.get(i - 1) ? 1 : 0;
        }
        return count;
    }
}
