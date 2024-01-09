package days.day18;

import days.DaySolution;

import java.util.*;
import java.util.stream.Collectors;

public class Day18 extends DaySolution {
    public Day18(String filePath) {
        super(filePath);
    }

    @Override
    public Object part1() {
        Queue<SnailNumber> snailNumbers = new LinkedList<>(parseSnailNumbers());

        SnailNumber acc = snailNumbers.poll();

        while (!snailNumbers.isEmpty()) {
            SnailNumber curr = snailNumbers.poll();
            acc = SnailNumber.addNumbers(acc, curr);
            acc.reduce();
        }

        return acc.root.magnitude();
    }

    @Override
    public Object part2() {
        ArrayList<SnailNumber> snailNumbers = new ArrayList<>(parseSnailNumbers());
        int max = 0;

        for (int i = 0; i < snailNumbers.size(); ++i) {
            for (int j = 0; j < snailNumbers.size(); ++j) {
                if (i == j) {
                    continue;
                }

                SnailNumber sn1 = new SnailNumber(snailNumbers.get(i).root.copy());
                SnailNumber sn2 = new SnailNumber(snailNumbers.get(j).root.copy());

                SnailNumber res = SnailNumber.addNumbers(sn1, sn2);
                res.reduce();
                max = Math.max(max, res.root.magnitude());
            }
        }

        return max;
    }

    private List<SnailNumber> parseSnailNumbers() {
        return input
            .stream()
            .map(this::parseSnailNumber)
            .collect(Collectors.toList());
    }

    private SnailNumber parseSnailNumber(String str) {
        Node node = parseNode(str, 0, 0).node();
        return new SnailNumber(node);
    }

    private NodeParseResult parseNode(String str, int pointer, int depth) {
        char firstChar = str.charAt(pointer);
        if (Character.isDigit(firstChar)) {
            Node node = new Node(firstChar - '0');
            node.depth = depth;
            return new NodeParseResult(node, 1);
        }

        int shift = 1;
        Node node = new Node();
        node.depth = depth;
        NodeParseResult leftParseResult = parseNode(str, pointer + shift, depth + 1);
        node.left = leftParseResult.node();
        node.left.parent = node;
        shift += leftParseResult.shift() + 1;
        NodeParseResult rightParseResult = parseNode(str, pointer + shift, depth + 1);
        node.right = rightParseResult.node();
        node.right.parent = node;
        shift += rightParseResult.shift() + 1;
        return new NodeParseResult(node, shift);
    }
}
