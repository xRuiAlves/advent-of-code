package days;

import utils.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class DaySolution implements MultipartDaySolution {
    private static final Pattern DAY_NUMBER_PATTERN = Pattern.compile("(?<number>\\d+)$");

    protected final List<String> input;

    protected DaySolution(String filePath) {
        try {
            this.input = FileReader.readLines(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read input file", e);
        }
    }

    protected final List<Integer> inputAsIntList() {
        return input
            .stream()
            .map(Integer::parseInt)
            .collect(Collectors.toList());
    }

    protected final String firstInputLine() {
        return input.get(0);
    }

    protected final int[][] inputAsIntMatrix() {
        return input
            .stream()
            .map(line -> Arrays
                    .stream(line.split(""))
                    .mapToInt(Integer::parseInt)
                    .toArray())
            .toArray(int[][]::new);
    }

    @Override
    public String toString() {
        long t1 = System.nanoTime();
        Object resultPart1 = part1();
        long t2 = System.nanoTime();
        Object resultPart2 = part2();
        long t3 = System.nanoTime();

        double elapsedTimePart1 = elapsedTimeInMillis(t1, t2);
        double elapsedTimePart2 = elapsedTimeInMillis(t2, t3);

        return String.format(
                "Day %s:\n  > Part 1: %s (%.3fms)\n  > Part 2: %s (%.3fms)",
                getDayNumberFromClassName(),
                resultPart1,
                elapsedTimePart1,
                resultPart2,
                elapsedTimePart2
        );
    }

    private double elapsedTimeInMillis(long ti, long tf) {
        return (double) (tf - ti) / 1000000;
    }

    private int getDayNumberFromClassName() {
        Matcher matcher = DAY_NUMBER_PATTERN.matcher(this.getClass().getName());

        if (!matcher.find()) {
            throw new RuntimeException(String.format("Invalid class name %s: Does not have a day number!", this.getClass().getName()));
        }

        return Integer.parseInt(matcher.group("number"));
    }
}
