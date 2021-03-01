package q1;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

/**
 * simple point class to save x and y coordinates intializes with x and y
 * (integers).
 */
public class Point {

    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
