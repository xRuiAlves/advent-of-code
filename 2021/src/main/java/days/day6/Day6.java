package days.day6;

import days.DaySolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Day6 extends DaySolution {
    private static final int NUM_DAYS_PART_1 = 80;
    private static final int NUM_DAYS_PART_2 = 256;
    private static final int NUM_DAYS_AFTER_SPAWN = 7;
    private static final int FIRST_NUM_DAYS_AFTER_SPAWN = NUM_DAYS_AFTER_SPAWN + 2;

    public Day6(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        return fishCount(NUM_DAYS_PART_1);
    }

    @Override
    public Object part2() {
        return fishCount(NUM_DAYS_PART_2);
    }

    private long fishCount(int numDays) {
        ArrayList<Long> fishCounts = new ArrayList<>(Collections.nCopies(FIRST_NUM_DAYS_AFTER_SPAWN, 0L));
        Arrays
            .stream(firstInputLine().split(","))
            .mapToInt(Integer::parseInt)
            .forEach(day -> fishCounts.set(day, fishCounts.get(day) + 1));

        for (int i = 0; i < numDays; ++i) {
            long numSpawning = fishCounts.get(0);
            fishCounts.set(NUM_DAYS_AFTER_SPAWN, fishCounts.get(NUM_DAYS_AFTER_SPAWN) + numSpawning);
            Collections.rotate(fishCounts, -1);
        }

        return fishCounts.stream().mapToLong(i -> i).sum();
    }
}
