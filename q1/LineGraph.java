package q1;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 * panel class with graphics used to draw the points and lines on the graph //
 * also to draw the delimiting lines on the sides of the drawing surface it uses
 * an connects the points out of an arraylist, starting with the second points
 * also draws the points themselves
 */
public class LineGraph extends JPanel {

    private final ArrayList<Point> points;

    public LineGraph() {
        points = new ArrayList<>();
        Listener lis = new Listener();

        this.addMouseListener(lis);
    }

    // the paintComponent method for drawing the graphs interactively
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Point p : points) // drawing the points themselves
        { // multiplying by the current width and height to re-normalize the points
            g.fillOval((int)(getWidth()*p.getX()) - 2, (int)(getHeight()*p.getY()) - 2, 4, 4);
        }
        if (points.size() > 1) // if there are points to connect (2 or more)
        // connect each point to the next one
        {
            for (int i = 0; i < points.size() - 1; i++) {
                g.drawLine((int)(getWidth()*points.get(i).getX()), (int)(getHeight()*points.get(i).getY()),
                        (int)(getWidth()*points.get(i+1).getX()), (int)(getHeight()*points.get(i+1).getY()));
            }
        }
        // lines on the edge of the graph
        g.drawLine(0, getHeight() - 5, getWidth(), getHeight() - 5);
        g.drawLine(10, 0, 10, getHeight());
    }

    // adding a point and repainting (called by the listener class)
    private void addPoint(double x, double y) {
        // diving by the current width and height to normalize the points
        // to between 0 and 1, so they'll be at constant distances
        Point p = new Point(x/this.getWidth(),y/this.getHeight());
        points.add(p);
        repaint();
    }

    // clear the points arraylist in case the user uses the 'clear' button
    public void clearPoints() {
        points.clear();
    }

    // internal listener class for mouse actions
    private class Listener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent event) {
            addPoint(event.getX(), event.getY());
        }
    }
}
