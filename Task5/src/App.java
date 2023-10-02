import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
    }
}

public class App {
    private JFrame frame;
    private Map<String, Contact> contacts;
    private JList<String> contactList;
    private DefaultListModel<String> listModel;

    public App() {
        contacts = new HashMap<>();
        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);

        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Address Book");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(e -> addContact());
        JButton removeButton = new JButton("Remove Contact");
        removeButton.addActionListener(e -> removeContact());
        JButton searchButton = new JButton("Search Contact");
        searchButton.addActionListener(e -> searchContact());

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(searchButton);

        panel.add(buttonsPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(contactList), BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addContact() {
        String name = JOptionPane.showInputDialog(frame, "Enter Name:");
        if (name != null && !name.trim().isEmpty()) {
            String phoneNumber = JOptionPane.showInputDialog(frame, "Enter Phone Number:");
            String email = JOptionPane.showInputDialog(frame, "Enter Email:");

            if (isValidPhoneNumber(phoneNumber) && isValidEmail(email)) {
                Contact contact = new Contact(name, phoneNumber, email);
                contacts.put(name, contact);
                listModel.addElement(contact.toString());
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid phone number or email format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    

    private void removeContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedContact = listModel.getElementAt(selectedIndex);
            String[] contactInfo = selectedContact.split(", ");
            String name = contactInfo[0].substring(6); // Remove "Name: "
            contacts.remove(name);
            listModel.remove(selectedIndex);
        }
    }

    private void searchContact() {
        String name = JOptionPane.showInputDialog(frame, "Enter Name to Search:");
        if (name != null) {
            Contact contact = contacts.get(name);
            if (contact != null) {
                JOptionPane.showMessageDialog(frame, contact.toString(), "Contact Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Contact not found.", "Contact Not Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
