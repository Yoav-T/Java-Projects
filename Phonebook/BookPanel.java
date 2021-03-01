package q2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 * 5.1.2020
 * graphics panel class for the phonebook management.
 * uses basic database and logic from PhoneBook class
 * @author Yoav Tulpan yoavtp@gmail.com
 */
public class BookPanel extends JPanel {
    private PhoneBook phoneBook; // database
    JTable table;
    DefaultTableModel model;
    JButton add, remove, search, save, load, update;
    JTextField name, number;
    
    /**
     * constructor for setting up the panel of the GUI, comprised of three
     * main parts - bottom buttons (SOUTH position), the table of names and numbers
     * (CENTER position) and the top buttons and text fields (NORTH position)
     */
    public BookPanel() {
        phoneBook = new PhoneBook();
        this.setLayout(new BorderLayout());
        table = new JTable();
        Object[] columns = {"Names", "Phone Numbers"};
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("", 1, 20));
        table.setRowHeight(30);
        
        // make the table un-editable from user double click
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // setting up the table for CENTER position
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        JScrollPane pane = new JScrollPane(table);
        
        // setting up the topButtons for NORTH position
        name = new JTextField(15);
        number = new JTextField(15);
        add = new JButton("Add");
        update = new JButton("Update");
        search = new JButton("Search");
        JPanel topButtons = new JPanel();
        topButtons.add(new JLabel("Name:"));
        topButtons.add(name);
        topButtons.add(new JLabel("Number:"));
        topButtons.add(number);
        topButtons.add(add);
        topButtons.add(update);
        topButtons.add(search);
        // mouse listener to update the NORTH text fields when a name is clicked
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = table.getSelectedRow();
                name.setText((String)table.getModel().getValueAt(i, 0));
                number.setText((String)table.getModel().getValueAt(i, 1));
            }
        });
        // setting up bottom buttons for SOUTH position
        remove = new JButton("Remove Selected");
        save = new JButton("Save");
        load = new JButton("Load");
        JPanel bottomButtons = new JPanel();
        bottomButtons.add(remove);
        bottomButtons.add(save);
        bottomButtons.add(load);
        // registring the listener (lots of buttons on one listener is a
        // conscious decision) I personally don't like anonymous listener classes
        // I think they clutter up the code when there are a bunch of them together
        Listener lis = new Listener();
        add.addActionListener(lis);
        update.addActionListener(lis);
        remove.addActionListener(lis);
        search.addActionListener(lis);
        save.addActionListener(lis);
        load.addActionListener(lis);
        // sticking it all together with a little bit of magic glue
        add(pane, BorderLayout.CENTER);
        add(bottomButtons, BorderLayout.SOUTH);
        add(topButtons, BorderLayout.NORTH);
    }
    /**
     * this method updates the table by deleting all the rows and redrawing them
     * it comes out sorted alphabetically because of the fact it's a TreeMap 
     * database
     */
    private void updateTable() {
        model.setRowCount(0); // deletes all rows 
        String[] row = new String[2];
        for (String currentName : phoneBook.getNames()) { // iterate over the keys
            row[0] = currentName;
            row[1] = phoneBook.getNumber(currentName);
            model.addRow(row);
        }
    }
    /**
     * listener for all the buttons
     */
    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            // add button (if there something inside the name field)
            if (event.getSource() == add && !name.getText().equals("")) { 
                try { // add the entry and update the table so it's displayed sorted
                    phoneBook.addEntry(name.getText(), number.getText());
                    updateTable();
                } catch (IllegalArgumentException e) { // thrown from the PhoneBook class
                    // if the name already exists
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            else if (event.getSource() == update) { // update button
                try { // updates if the name already exists
                    phoneBook.updateNumber(name.getText(), number.getText());
                    updateTable();
                } catch (IllegalArgumentException e) { //if the name doesn't exist
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (event.getSource() == remove) { // remove button
                try { // show a confirm dialog for removal, make sure an item is actually selected
                    String selectedName = (String)table.getModel().getValueAt(table.getSelectedRow(), 0);
                    if (JOptionPane.showConfirmDialog(null, "Remove " + selectedName + " from the phonebook?", 
                            "Remove Entry", JOptionPane.YES_NO_OPTION) == 0) {
                        phoneBook.removeEntry(selectedName);
                        updateTable();
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(null, "Select (click)"
                            + " an entry to remove", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            else if (event.getSource() == search) { // search button
                String searchKey = name.getText();
                if (!phoneBook.isInPhoneBook(searchKey)) {
                    JOptionPane.showMessageDialog(null, searchKey 
                            + " is not in the phonebook", "Error", JOptionPane.ERROR_MESSAGE);
                } else { // if they're in the phonebook, display the number
                number.setText(phoneBook.getNumber(searchKey));
                }
            } 
            else if (event.getSource() == save) { // save button
                try {
                    phoneBook.savePhoneBook();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Save Error. You must "
                            + "have done something wrong.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } 
            else if (event.getSource() == load) { // load button
                try {
                    phoneBook.loadPhoneBook();
                    updateTable();
                } catch (IOException | ClassNotFoundException | ClassCastException e) {
                    JOptionPane.showMessageDialog(null, "Load Error. You must "
                            + "have done something wrong.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
