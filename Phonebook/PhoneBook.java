package q2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 * 5.1.2020
 * PhoneBook class for all the logic required of the phonebook program.
 * uses a TreeMap as database because of its automatic sorting. very convenient.
 * @author Yoav Tulpan yoavtp@gmail.com
 */
public class PhoneBook implements Serializable {
    // I use String for the phonebook because many phone numbers begin with a '0'
    // this would be an issue for int objects. I don't bother validating the number
    // since it isn't required
    private TreeMap<String, String> phoneBook;
    
    /**
     * basic empty constructor
     */
    public PhoneBook() {
        phoneBook = new TreeMap<>();
    }

    /**
     * adds an entry to the map database, throws an exception if the user tries
     * to add a duplicate name (my decision to throw an exception, I know 
     * that maps don't allow duplicate keys anyways. it's a feature not a bug).
     * @param name- String of a name
     * @param number- String of a phone number
     */
    public void addEntry(String name, String number) {
        if (!phoneBook.containsKey(name)) {
            phoneBook.put(name, number);
        } else {
            throw new IllegalArgumentException(name + " already exists "
                    + "in the phonebook");
        }
    }
    // simple check
    public boolean isInPhoneBook(String name) {
        return phoneBook.containsKey(name);
    }
    // getting an unmodifiable set of the keys (no way to tinker with the database)
    public Set<String> getNames() {
        return Collections.unmodifiableSet(phoneBook.keySet());
    }
    // basic getter
    public String getNumber(String name) {
        return phoneBook.get(name);
    }
    // remove an entry, throw an exception if it doesn't exist (my choice)
    public void removeEntry(String name) {
        if (phoneBook.containsKey(name)) {
            phoneBook.remove(name);
        } else {
            throw new IllegalArgumentException(name + "does not currently exist"
                    + "in the phonebook");
        }
    }
    // change the number, throw an exception if the key doesn't exist
    public void updateNumber(String name, String number) {
        if (phoneBook.containsKey(name)) {
            phoneBook.put(name, number);
        } else {
            throw new IllegalArgumentException(name + " is not in the phonebook");
        }
    }
    
    /**
     * uses ObjectFileStream to save the PhoneBook object to the current directory
     * throws a popup for the user to decide the file name. adds .phonebook as a file
     * extension to that name
     * @throws IOException which is dealt with in the GUI class
     */
    public void savePhoneBook() throws IOException {
        String fileName = JOptionPane.showInputDialog(null, "Enter phonebook name to"
                + " be saved to current directory");
        if (fileName != null && !fileName.isEmpty()) { // saving with a proper file name
            FileOutputStream output = new FileOutputStream(fileName + ".phonebook");
            ObjectOutputStream objectOutput = new ObjectOutputStream(output);
            objectOutput.writeObject(phoneBook);
            output.close();
            objectOutput.close();
            JOptionPane.showMessageDialog(null, "Saved phonebook.","Success",JOptionPane.INFORMATION_MESSAGE);
        } else { // trying to save with no file name or hitting cancel/x
            JOptionPane.showMessageDialog(null, "Did not save phonebook.","Cancelled",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * uses JFileChooser in the current directory to find the file to load
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadPhoneBook() throws IOException, ClassNotFoundException {
        File workingDirectory = new File(System.getProperty("user.dir"));
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(workingDirectory);
        chooser.setDialogTitle("Load Phonebook");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            InputStream input = new FileInputStream(chooser.getSelectedFile());
            ObjectInputStream objectInput = new ObjectInputStream(input);
            this.phoneBook = (TreeMap<String, String>)objectInput.readObject();
            input.close();
            objectInput.close();
        }
    }
}
