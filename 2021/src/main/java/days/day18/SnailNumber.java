package days.day18;

import java.util.ArrayList;

public class SnailNumber {
    Node root;
    ArrayList<Node> leaves;

    SnailNumber(Node root) {
        this.root = root;
        this.leaves = new ArrayList<>();
        populateLeaves(root);
    }

    private void populateLeaves(Node node) {
        if (node.isLeaf()) {
            leaves.add(node);
        } else {
            populateLeaves(node.left);
            populateLeaves(node.right);
        }
    }

    static SnailNumber addNumbers(SnailNumber sn1, SnailNumber sn2) {
        Node newNode = new Node(sn1.root, sn2.root);
        sn1.root.parent = newNode;
        sn2.root.parent = newNode;
        newNode.updateDepth();
        return new SnailNumber(newNode);
    }

    void reduce() {
        while (true) {
            if (explode()) {
                continue;
            }

            if (!split()) {
                break;
            }
        }
    }

    private boolean explode() {
        for (Node leave: leaves) {
            if (leave.depth > 4) {
                explodeNode(leave.parent);
                return true;
            }
        }
        return false;
    }

    private void explodeNode(Node node) {
        int leftIndex = findIndexInLeaves(node.left);
        int rightIndex = findIndexInLeaves(node.right);

        if (leftIndex > 0) {
            leaves.get(leftIndex - 1).value += node.left.value;
        }
        if (rightIndex < leaves.size() - 1) {
            leaves.get(rightIndex + 1).value += node.right.value;
        }

        leaves.add(leftIndex, node);
        leaves.remove(node.left);
        leaves.remove(node.right);

        node.left = null;
        node.right = null;
        node.value = 0;
        node.depth = node.parent.depth + 1;
    }

    private boolean split() {
        for (Node leave: leaves) {
            if (leave.value >= 10) {
                splitNode(leave);
                return true;
            }
        }
        return false;
    }

    private void splitNode(Node node) {
        node.left = new Node(node.value / 2);
        node.right = new Node((node.value / 2) + (node.value % 2));
        node.left.depth = node.depth + 1;
        node.right.depth = node.depth + 1;
        node.left.parent = node;
        node.right.parent = node;
        node.value = null;
        int index = findIndexInLeaves(node);
        leaves.add(index, node.right);
        leaves.add(index, node.left);
        leaves.remove(node);
    }

    private int findIndexInLeaves(Node leave) {
        for (int i = 0; i < leaves.size(); ++i) {
            if (leave == leaves.get(i)) {
                return i;
            }
        }
        throw new RuntimeException("Leave not found: " + leave);
    }
}
