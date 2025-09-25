import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

class LoginFrame extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Login Page");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        add(panel);

        loginButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals("User") && password.equals("123")) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose();
            new MainSystemFrame();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class MainSystemFrame extends JFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu bookMenu, memberMenu, borrowMenu;
    private JMenuItem addBookItem, viewBooksItem;
    private JMenuItem addMemberItem, viewMembersItem;
    private JMenuItem borrowBookItem, returnBookItem;

    public MainSystemFrame() {
        setTitle("Library Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome to the Library Management System!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.CENTER);

        // Menu bar
        menuBar = new JMenuBar();

        // Book Menu
        bookMenu = new JMenu("Books");
        addBookItem = new JMenuItem("Add Book");
        viewBooksItem = new JMenuItem("View Books");
        bookMenu.add(addBookItem);
        bookMenu.add(viewBooksItem);

        // Member Menu
        memberMenu = new JMenu("Members");
        addMemberItem = new JMenuItem("Add Member");
        viewMembersItem = new JMenuItem("View Members");
        memberMenu.add(addMemberItem);
        memberMenu.add(viewMembersItem);

        // Borrow/Return Menu
        borrowMenu = new JMenu("Borrow/Return");
        borrowBookItem = new JMenuItem("Borrow Book");
        returnBookItem = new JMenuItem("Return Book");
        borrowMenu.add(borrowBookItem);
        borrowMenu.add(returnBookItem);

        // Add menus to menu bar
        menuBar.add(bookMenu);
        menuBar.add(memberMenu);
        menuBar.add(borrowMenu);

        setJMenuBar(menuBar);

        // Add action listeners
        addBookItem.addActionListener(this);
        viewBooksItem.addActionListener(this);
        addMemberItem.addActionListener(this);
        viewMembersItem.addActionListener(this);
        borrowBookItem.addActionListener(this);
        returnBookItem.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBookItem) {
            JOptionPane.showMessageDialog(this, "Add Book clicked.");
        } else if (e.getSource() == viewBooksItem) {
            JOptionPane.showMessageDialog(this, "View Books clicked.");
        } else if (e.getSource() == addMemberItem) {
            JOptionPane.showMessageDialog(this, "Add Member clicked.");
        } else if (e.getSource() == viewMembersItem) {
            JOptionPane.showMessageDialog(this, "View Members clicked.");
        } else if (e.getSource() == borrowBookItem) {
            JOptionPane.showMessageDialog(this, "Borrow Book clicked.");
        } else if (e.getSource() == returnBookItem) {
            JOptionPane.showMessageDialog(this, "Return Book clicked.");
        }
    }
}
