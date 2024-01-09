package days.day15;

import days.DaySolution;

import java.util.*;

public class Day15 extends DaySolution {
    public Day15(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        return minPath(inputAsIntMatrix());
    }

    @Override
    public Object part2() {
        return minPath(largeMatrix());
    }

    private int minPath(int[][] matrix) {
        PriorityQueue<Node> pQueue = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();

        pQueue.offer(new Node(0, 0, 0));

        while (!pQueue.isEmpty()) {
            Node curr = pQueue.poll();

            if (visited.contains(curr)) {
                continue;
            }

            visited.add(curr);

            if (curr.isEndNode(matrix[0].length - 1, matrix.length - 1)) {
                return curr.weight();
            }

            neighbors(curr, matrix).forEach(pQueue::offer);
        }

        throw new RuntimeException("No path found!");
    }

    private List<Node> neighbors(Node node, int[][] matrix) {
        List<Node> neighbors = new LinkedList<>();

        if (node.x() > 0) {
            neighbors.add(new Node(node.x() - 1, node.y(), node.weight() + matrix[node.y()][node.x() - 1]));
        }
        if (node.x() < matrix[0].length - 1) {
            neighbors.add(new Node(node.x() + 1, node.y(), node.weight() + matrix[node.y()][node.x() + 1]));
        }
        if (node.y() > 0) {
            neighbors.add(new Node(node.x(), node.y() - 1, node.weight() + matrix[node.y() - 1][node.x()]));
        }
        if (node.y() < matrix.length - 1) {
            neighbors.add(new Node(node.x(), node.y() + 1, node.weight() + matrix[node.y() + 1][node.x()]));
        }

        return neighbors;
    }

    private int[][] largeMatrix() {
        int[][] matrix = inputAsIntMatrix();
        int[][] largeMatrix = new int[matrix.length * 5][matrix[0].length * 5];

        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                for (int ki = 0; ki < 5; ++ki) {
                    for (int kj = 0; kj < 5; ++kj) {
                        largeMatrix[ki * matrix.length + i][kj * matrix[i].length + j] =
                            (matrix[i][j] + ki + kj - 1) % 9 + 1;
                    }
                }
            }
        }

        return largeMatrix;
    }
}
