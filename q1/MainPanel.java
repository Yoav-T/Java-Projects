package q1;
/*
 * Yoav Tulpan - yoavtp@gmail.com
 * 19.12.2019
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * main panel class that brings all the panels together in the right layout it
 * creates instances of the other panels, and adds them to itself in the correct
 * layout
 */
public class MainPanel extends JPanel {

    private final JButton clear, setXAxis, setYAxis;
    private final JPanel buttons;
    private final LineGraph lineGraph;
    private final XAxis xAxis;
    private final YAxis yAxis;

    // constructor that creates buttons, and the other panels, and adds them all together

    public MainPanel() {
        // creating buttons
        clear = new JButton("clear");
        setXAxis = new JButton("setXAxis");
        setYAxis = new JButton("setYAxis");
        buttons = new JPanel();
        buttons.add(clear);
        buttons.add(setXAxis);
        buttons.add(setYAxis);
        // adding the internal listener class to the created buttons
        Listener lis = new Listener();
        clear.addActionListener(lis);
        setXAxis.addActionListener(lis);
        setYAxis.addActionListener(lis);
        // creating the other interactive JPanels
        lineGraph = new LineGraph();
        xAxis = new XAxis();
        yAxis = new YAxis();
        GraphAndAxes graphAndAxes = new GraphAndAxes(lineGraph, xAxis, yAxis);
        // affixing all the components to this main panel
        this.setLayout(new BorderLayout());
        this.add(buttons, BorderLayout.SOUTH);
        this.add(graphAndAxes, BorderLayout.CENTER);
    }

    // internal listener class for the three buttons created in the constructor
    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == clear) {
                lineGraph.clearPoints();
            } else if (event.getSource() == setXAxis) {
                axisRepopulate(1); // repopulate 1 --> repopulate x axis
            } else if (event.getSource() == setYAxis) {
                axisRepopulate(2); // repopulate 2 --> repopulate y axis
            }
            revalidate();
            repaint();
        }
    }

    /**
     * input dialog for the interactive axes: requests the start, end and the
     * interval outputs an array of these three integers. the call to axisPopup
     * method is in a try block so any illegal entries (not integers, empty
     * fields) will cause a parsing error that will be caught this way we
     * validate the input
     */
    private int[] axisPopup() {
        int[] results = new int[3]; // output variable
        // input text fields
        JTextField start = new JTextField(5);
        JTextField end = new JTextField(5);
        JTextField interval = new JTextField(5);
        // panel to hold all my stuff
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Start (int)"));
        myPanel.add(start);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("End (int)"));
        myPanel.add(end);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Interval (int)"));
        myPanel.add(interval);
        // popup itself
        JOptionPane.showConfirmDialog(null, myPanel, "Input Start, "
                + "End and Interval size for the new axis", JOptionPane.DEFAULT_OPTION);
        // populating the output variable
        results[0] = Integer.parseInt(start.getText());
        results[1] = Integer.parseInt(end.getText());
        results[2] = Integer.parseInt(interval.getText());

        return results;
    }

    // using the axisPopup method to ask the axes to redraw themselves according to demands
    // invalid inputs are caught here
    private void axisRepopulate(int axis) { // axis: 1 for x axis, 2 for y axis
        try { // catching invalid inputs
            int[] data = axisPopup();
            if (axis == 1) {
                xAxis.repopulate(data[0], data[1], data[2]);
            } else if (axis == 2) {
                yAxis.repopulate(data[0], data[1], data[2]);
            }
        } catch (Exception e) { // if it's an invalid input
            JOptionPane.showMessageDialog(null, "Input three logical integers please",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
