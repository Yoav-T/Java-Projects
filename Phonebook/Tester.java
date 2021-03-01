package q2;

import javax.swing.JFrame;

/**
 *
 * @author Yoav
 */
public class Tester {
    
    public static void main(String args[]) {
        JFrame frame = new JFrame("Phonebook");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        BookPanel p = new BookPanel();
        frame.setLocationRelativeTo(null);
        frame.add(p);
        frame.setVisible(true);
    }
    
}
