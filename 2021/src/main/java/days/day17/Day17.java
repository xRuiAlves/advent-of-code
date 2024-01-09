package days.day17;

import days.DaySolution;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 extends DaySolution {
    public Day17(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        Square target = target();
        int maxY = 0;

        for (int xInitSpeed = 1; xInitSpeed <= target.x1(); ++xInitSpeed) {
            for (int yInitSpeed = 1; yInitSpeed <= -target.y2(); ++yInitSpeed) {
                Probe probe = new Probe(xInitSpeed, yInitSpeed);
                if (probe.simulate(target)) {
                    maxY = Math.max(maxY, probe.highestY());
                }
            }
        }

        return maxY;
    }

    @Override
    public Object part2() {
        Square target = target();
        int count = 0;

        for (int xInitSpeed = 1; xInitSpeed <= target.x2(); ++xInitSpeed) {
            for (int yInitSpeed = target.y2(); yInitSpeed <= -target.y2(); ++yInitSpeed) {
                Probe probe = new Probe(xInitSpeed, yInitSpeed);
                if (probe.simulate(target)) {
                    ++count;
                }
            }
        }

        return count;
    }

    private Square target() {
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(firstInputLine());
        List<Integer> results = new ArrayList<>();

        while(m.find()) {
            results.add(Integer.parseInt(m.group()));
        }

        return new Square(results.get(0), results.get(1), results.get(3), results.get(2));
    }
}
