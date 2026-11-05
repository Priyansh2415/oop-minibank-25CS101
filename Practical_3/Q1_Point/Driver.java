public class Driver {
    public static void main(String[] args) {
        // (d) five points, two of them repeated
        Point[] pts = {
            new Point(1, 2), new Point(3, 4), new Point(1, 2),
            new Point(5, 6), new Point(3, 4)
        };
        System.out.print("Points: ");
        for (Point p : pts) System.out.print(p + " ");
        System.out.println();

        // (e) count distinct points using equals()
        int distinct = 0;
        for (int i = 0; i < pts.length; i++) {
            boolean seenBefore = false;
            for (int j = 0; j < i; j++) {
                if (pts[i].equals(pts[j])) {
                    seenBefore = true;
                    break;
                }
            }
            if (!seenBefore) distinct++;
        }

        // (f) result
        System.out.println("Distinct: " + distinct);
        System.out.println("hashCode(1,2) == hashCode(1,2) ? "
                + (pts[0].hashCode() == pts[2].hashCode()));
    }
}
