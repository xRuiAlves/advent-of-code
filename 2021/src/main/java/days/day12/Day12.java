package days.day12;

import days.DaySolution;

import java.util.*;
import java.util.stream.Collectors;

public class Day12 extends DaySolution {
    private static final String START = "start";
    private static final String END = "end";

    public Day12(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        return countPaths(START, graph(), new HashSet<>());
    }

    @Override
    public Object part2() {
        return countPaths(START, graph(), new HashMap<>(), null);
    }

    private int countPaths(String cave, Map<String, Set<String>> graph, HashSet<String> visited) {
        if (cave.equals(END)) {
            return 1;
        }

        if (Character.isLowerCase(cave.charAt(0)) && visited.contains(cave)) {
            return 0;
        }

        visited.add(cave);
        int numPaths = graph
            .get(cave)
            .stream()
            .mapToInt(neighborCave -> countPaths(neighborCave, graph, visited))
            .sum();
        visited.remove(cave);

        return numPaths;
    }

    private int countPaths(String cave, Map<String, Set<String>> graph, HashMap<String, Integer> visitedCounts) {
        return countPaths(cave, graph, visitedCounts, null);
    }

    private int countPaths(String cave, Map<String, Set<String>> graph, HashMap<String, Integer> visitedCounts, String special) {
        if (cave.equals(END)) {
            return 1;
        }

        if (cave.equals(START) && visitedCounts.containsKey(START)) {
            return 0;
        }

        if (special != null) {
            if (cave.equals(special)) {
                if (visitedCounts.get(cave) >= 2) {
                    return 0;
                }
            } else if (Character.isLowerCase(cave.charAt(0)) && visitedCounts.getOrDefault(cave, 0) >= 1) {
                return 0;
            }
        }

        int numVisited = visitedCounts.getOrDefault(cave, 0) + 1;
        visitedCounts.put(cave, numVisited);

        String updatedSpecialCave =  Character.isLowerCase(cave.charAt(0)) && special == null && numVisited == 2
            ? cave
            : special;

        int numPaths = graph
            .get(cave)
            .stream()
            .mapToInt(neighborCave -> countPaths(
                neighborCave,
                graph,
                visitedCounts,
                updatedSpecialCave
            ))
            .sum();
        visitedCounts.put(cave, visitedCounts.get(cave) - 1);

        return numPaths;
    }

    private Map<String, Set<String>> graph() {
        List<CaveConnection> caveConnections = caveConnections();
        Map<String, Set<String>> graph = new HashMap<>();

        caveConnections.forEach(caveConnection -> {
            if (!graph.containsKey(caveConnection.cave1())) {
                graph.put(caveConnection.cave1(), new HashSet<>());
            }
            if (!graph.containsKey(caveConnection.cave2())) {
                graph.put(caveConnection.cave2(), new HashSet<>());
            }
            graph.get(caveConnection.cave1()).add(caveConnection.cave2());
            graph.get(caveConnection.cave2()).add(caveConnection.cave1());
        });

        return graph;
    }

    private List<CaveConnection> caveConnections() {
        return input
            .stream()
            .map(CaveConnection::of)
            .collect(Collectors.toList());
    }
}
