package q1;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

import javax.swing.*;
import java.awt.*;

/**
 * a panel used to set up the relative locations of the axes and the line graph
 */
public class GraphAndAxes extends JPanel {

    public GraphAndAxes(LineGraph myGraph, XAxis myX, YAxis myY) {
        this.setLayout(new BorderLayout());
        this.add(myX, BorderLayout.SOUTH);
        this.add(myY, BorderLayout.WEST);
        this.add(myGraph, BorderLayout.CENTER);
    }
}
