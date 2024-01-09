package days.day14;

import days.DaySolution;

import java.util.HashMap;
import java.util.Map;

public class Day14 extends DaySolution {
    private static final int PART_1_NUM_ITERS = 10;
    private static final int PART_2_NUM_ITERS = 40;

    public Day14(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        Input input = input();
        Polymer polymer = input.template();

        for (int i = 0; i < PART_1_NUM_ITERS; ++i) {
            polymer = polymer.getNextPolymer(input.insertionRules());
        }

        return polymer.countDiff();
    }

    @Override
    public Object part2() {
        Input input = input();
        Polymer polymer = input.template();

        for (int i = 0; i < PART_2_NUM_ITERS; ++i) {
            polymer = polymer.getNextPolymer(input.insertionRules());
        }

        return polymer.countDiff();
    }

    private Input input() {
        Polymer template = Polymer.of(input.get(0));

        Map<String, String> insertionRules = new HashMap<>();
        for (int i = 2; i < input.size(); ++i) {
            String[] tokens = input.get(i).split(" -> ");
            insertionRules.put(tokens[0], tokens[1]);
        }

        return new Input(template, insertionRules);
    }
}
