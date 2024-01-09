package days.day15;

import java.util.Objects;

public record Node(int x, int y, int weight) implements Comparable<Node> {
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.weight, other.weight);
    }

    boolean isEndNode(int x, int y) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
