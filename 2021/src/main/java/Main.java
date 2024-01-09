import days.MultipartDaySolution;
import days.day1.Day1;
import days.day10.Day10;
import days.day11.Day11;
import days.day12.Day12;
import days.day13.Day13;
import days.day14.Day14;
import days.day15.Day15;
import days.day16.Day16;
import days.day17.Day17;
import days.day18.Day18;
import days.day19.Day19;
import days.day2.Day2;
import days.day20.Day20;
import days.day21.Day21;
import days.day3.Day3;
import days.day4.Day4;
import days.day5.Day5;
import days.day6.Day6;
import days.day7.Day7;
import days.day8.Day8;
import days.day9.Day9;

import java.util.List;

public class Main {
    private static final List<MultipartDaySolution> solutions = List.of(
            new Day1("input/day1.txt"),
            new Day2("input/day2.txt"),
            new Day3("input/day3.txt"),
            new Day4("input/day4.txt"),
            new Day5("input/day5.txt"),
            new Day6("input/day6.txt"),
            new Day7("input/day7.txt"),
            new Day8("input/day8.txt"),
            new Day9("input/day9.txt"),
            new Day10("input/day10.txt"),
            new Day11("input/day11.txt"),
            new Day12("input/day12.txt"),
            new Day13("input/day13.txt"),
            new Day14("input/day14.txt"),
            new Day15("input/day15.txt"),
            new Day16("input/day16.txt"),
            new Day17("input/day17.txt"),
            new Day18("input/day18.txt"),
            new Day19("input/day19.txt"),
            new Day20("input/day20.txt"),
            new Day21("input/day21.txt")
    );

    public static void main(String[] args) {
        // solutions.forEach(System.out::println);
        System.out.println(solutions.get(solutions.size() - 1));
    }
}
