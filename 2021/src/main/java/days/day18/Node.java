package days.day18;

public class Node {
    Node left;
    Node right;
    Integer value;
    int depth;
    Node parent;

    Node() {}

    Node(Integer value) {
        this.value = value;
    }

    Node(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    boolean isLeaf() {
        return value != null;
    }

    void updateDepth() {
        depth = (parent == null) ? 0 : parent.depth + 1;
        if (left != null) {
            left.updateDepth();
        }
        if (right != null) {
            right.updateDepth();
        }
    }

    int magnitude() {
        if (isLeaf()) {
            return value;
        } else {
            return 3 * left.magnitude() + 2 * right.magnitude();
        }
    }

    protected Node copy() {
        if (isLeaf()) {
            Node newNode = new Node(value);
            newNode.depth = depth;
            return newNode;
        }

        Node newNode = new Node();
        newNode.depth = depth;
        newNode.left = left.copy();
        newNode.left.parent = newNode;
        newNode.right = right.copy();
        newNode.right.parent = newNode;
        return newNode;
    }
}
