import java.util.Objects;

public class Point {

    // (a) fields and constructor
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // (b) toString
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // (c) equals + hashCode agree with each other
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point p = (Point) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
