package days.day8;

import days.DaySolution;

import java.util.Arrays;
import java.util.Set;

public class Day8 extends DaySolution {
    private static final Set<Integer> PART_1_DIGITS_SIZES = Set.of(2, 3, 4, 7);

    public Day8(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        return input
            .stream()
            .map(Entry::new)
            .mapToLong(entry -> entry
                .fourDigitOutputValue()
                .stream()
                .filter(digit -> PART_1_DIGITS_SIZES.contains(digit.size()))
                .count())
            .sum();
    }

    @Override
    public Object part2() {
        return input
            .stream()
            .map(Entry::new)
            .mapToInt(Entry::getMessage)
            .sum();
    }
}
