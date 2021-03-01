package q1;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

import javax.swing.*;
import java.awt.*;

/**
 * axis class: uses the grid layout to fill the axis with numbers according to
 * user demands uses the repopulate method to calculate the ticks in the graph,
 * also to catch illogical entries for example "from 1 to 10 in increments of
 * -1" which is impossible.
 */
public class YAxis extends JPanel {

    public YAxis() {
        // default axis layout: 1 to 10 (entered in reverse because it's from top to bottom)
        setLayout(new GridLayout(0, 1));
        for (int i = 10; i >= 0; i -= 1) {
            this.add(new JLabel("   " + Integer.toString(i))); // spacer for aesthetic reasons
        }
    }

    /**
     * method to calculate the ticks in the axis. only one of the 'for' loops
     * will work per input, depending if the increment is positive or negative.
     * illogical inputs throw an exception here.
     *
     * @param start = starting point for ticks
     * @param end = ending point for ticks
     * @param interval = interval between ticks
     */
    public void repopulate(int start, int end, int interval) {
        // if adding an increment takes us further away from the end point, then 
        // the input is obviously illogical.
        if (Math.abs(end - start) <= Math.abs(end - (start + interval))) {
            throw new IllegalArgumentException();
        }
        this.removeAll();
        for (int i = end; i >= start; i -= interval) // for positive increments
        {
            this.add(new JLabel("   " + Integer.toString(i)));
        }
        for (int i = end; i <= start; i -= interval) // for negative increments
        {
            this.add(new JLabel("   " + Integer.toString(i)));
        }
        this.revalidate();
        this.repaint();
    }
}
