import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class LibraryManagementSystem_Baseline {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

class Book {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    private String borrowedBy;
    private Date borrowDate;
    
    public Book(String bookId, String title, String author, String isbn) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
        this.borrowedBy = null;
        this.borrowDate = null;
    }
    
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return isAvailable; }
    public String getBorrowedBy() { return borrowedBy; }
    public Date getBorrowDate() { return borrowDate; }
    
    public void setAvailable(boolean available) { this.isAvailable = available; }
    public void setBorrowedBy(String memberId) { this.borrowedBy = memberId; }
    public void setBorrowDate(Date date) { this.borrowDate = date; }
    
    @Override
    public String toString() {
        return bookId + " - " + title + " by " + author;
    }
}

class Member {
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private ArrayList<String> borrowedBooks;
    
    public Member(String memberId, String name, String email, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.borrowedBooks = new ArrayList<>();
    }
    
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public ArrayList<String> getBorrowedBooks() { return borrowedBooks; }
    
    public void borrowBook(String bookId) {
        borrowedBooks.add(bookId);
    }
    
    public void returnBook(String bookId) {
        borrowedBooks.remove(bookId);
    }
    
    @Override
    public String toString() {
        return memberId + " - " + name;
    }
}

class LibraryData {
    private static LibraryData instance;
    private ArrayList<Book> books;
    private ArrayList<Member> members;
    
    private LibraryData() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        loadSampleData();
    }
    
    public static LibraryData getInstance() {
        if (instance == null) {
            instance = new LibraryData();
        }
        return instance;
    }
    
    private void loadSampleData() {
        // Add sample books
        books.add(new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5"));
        books.add(new Book("B002", "To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4"));
        books.add(new Book("B003", "1984", "George Orwell", "978-0-452-28423-4"));
        books.add(new Book("B004", "Pride and Prejudice", "Jane Austen", "978-0-14-143951-8"));
        books.add(new Book("B005", "The Catcher in the Rye", "J.D. Salinger", "978-0-316-76948-0"));
        books.add(new Book("B006", "Animal Farm", "George Orwell", "978-0-452-28424-1"));
        books.add(new Book("B007", "Brave New World", "Aldous Huxley", "978-0-06-085052-4"));
        books.add(new Book("B008", "Lord of the Flies", "William Golding", "978-0-399-50148-7"));
        books.add(new Book("B009", "The Hobbit", "J.R.R. Tolkien", "978-0-547-92822-7"));
        books.add(new Book("B010", "Fahrenheit 451", "Ray Bradbury", "978-1-451-67331-9"));
        
        // Add sample members
        members.add(new Member("M001", "Mark Garata", "markgarata@umin.com", "639-0101"));
        members.add(new Member("M002", "Franciene Candare", "francienecandare@umin.com", "639-0102"));
        members.add(new Member("M003", "Kiera Aguiadan", "kieraaguiadan@umin.com", "639-0103"));
        members.add(new Member("M004", "Emmanuel Tuling", "emmanueltuling@umin.com", "639-0104"));
    }
    public ArrayList<Book> getBooks() { return books; }
    public ArrayList<Member> getMembers() { return members; }
    
    public void addBook(Book book) { books.add(book); }
    public void addMember(Member member) { members.add(member); }
    
    public Book findBookById(String bookId) {
        for (Book book : books) {
            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }
    
    public Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}

// BASELINE SORTING ALGORITHM: QuickSort with first element as pivot (O(n²) worst case)
class SortingAlgorithm {
    private long executionTime;
    
    public long getExecutionTime() {
        return executionTime;
    }
    
    // Baseline QuickSort - always uses first element as pivot
    public void quickSortBaseline(ArrayList<Book> books, int low, int high) {
        if (low < high) {
            int pi = partitionFirstPivot(books, low, high);
            quickSortBaseline(books, low, pi - 1);
            quickSortBaseline(books, pi + 1, high);
        }
    }
    
    // Partition using first element as pivot (poor performance on sorted data)
    private int partitionFirstPivot(ArrayList<Book> books, int low, int high) {
        Book pivot = books.get(low); // Always choose first element as pivot
        int i = low + 1;
        
        for (int j = low + 1; j <= high; j++) {
            if (books.get(j).getTitle().compareToIgnoreCase(pivot.getTitle()) < 0) {
                swap(books, i, j);
                i++;
            }
        }
        swap(books, low, i - 1);
        return i - 1;
    }
    
    private void swap(ArrayList<Book> books, int i, int j) {
        Book temp = books.get(i);
        books.set(i, books.get(j));
        books.set(j, temp);
    }
    
    public ArrayList<Book> sortBooks(ArrayList<Book> books) {
        ArrayList<Book> booksCopy = new ArrayList<>(books);
        long startTime = System.nanoTime();
        
        if (booksCopy.size() > 0) {
            quickSortBaseline(booksCopy, 0, booksCopy.size() - 1);
        }
        
        long endTime = System.nanoTime();
        executionTime = endTime - startTime;
        
        return booksCopy;
    }
}

// BASELINE SEARCHING ALGORITHM: Binary Search with sorting on every search (O(n log n) per search)
class SearchingAlgorithm {
    private long executionTime;
    private SortingAlgorithm sorter;
    
    public SearchingAlgorithm() {
        sorter = new SortingAlgorithm();
    }
    
    public long getExecutionTime() {
        return executionTime;
    }
    
    // Baseline: Sort EVERY time before searching (inefficient)
    public ArrayList<Book> searchBooksByTitle(ArrayList<Book> books, String searchTerm) {
        long startTime = System.nanoTime();
        
        // BASELINE INEFFICIENCY: Sort the list every single time
        ArrayList<Book> sortedBooks = sorter.sortBooks(books);
        
        ArrayList<Book> results = new ArrayList<>();
        
        // Perform binary search for partial matches
        for (Book book : sortedBooks) {
            if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(book);
            }
        }
        
        long endTime = System.nanoTime();
        executionTime = endTime - startTime;
        
        return results;
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
    private JMenuItem addBookItem, viewBooksItem, searchBooksItem, sortBooksItem;
    private JMenuItem addMemberItem, viewMembersItem;
    private JMenuItem borrowBookItem, returnBookItem;

    public MainSystemFrame() {
        setTitle("Library Management System");
        setSize(900, 600);
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
        searchBooksItem = new JMenuItem("Search Books");
        sortBooksItem = new JMenuItem("Sort Books");
        bookMenu.add(addBookItem);
        bookMenu.add(viewBooksItem);
        bookMenu.add(searchBooksItem);
        bookMenu.add(sortBooksItem);

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
        searchBooksItem.addActionListener(this);
        sortBooksItem.addActionListener(this);
        addMemberItem.addActionListener(this);
        viewMembersItem.addActionListener(this);
        borrowBookItem.addActionListener(this);
        returnBookItem.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBookItem) {
            new AddBookDialog(this);
        } else if (e.getSource() == viewBooksItem) {
            new ViewBooksDialog(this);
        } else if (e.getSource() == searchBooksItem) {
            new SearchBooksDialog(this);
        } else if (e.getSource() == sortBooksItem) {
            new SortBooksDialog(this);
        } else if (e.getSource() == addMemberItem) {
            new AddMemberDialog(this);
        } else if (e.getSource() == viewMembersItem) {
            new ViewMembersDialog(this);
        } else if (e.getSource() == borrowBookItem) {
            new BorrowBookDialog(this);
        } else if (e.getSource() == returnBookItem) {
            new ReturnBookDialog(this);
        }
    }
}

// Dialog to add a new book
class AddBookDialog extends JDialog {
    private JTextField bookIdField, titleField, authorField, isbnField;
    private JButton addButton, cancelButton;
    
    public AddBookDialog(JFrame parent) {
        super(parent, "Add Book", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Book ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Book ID:"), gbc);
        bookIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);
        
        // Title
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);
        titleField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(titleField, gbc);
        
        // Author
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Author:"), gbc);
        authorField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(authorField, gbc);
        
        // ISBN
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("ISBN:"), gbc);
        isbnField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(isbnField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        add(panel);
        
        addButton.addActionListener(e -> addBook());
        cancelButton.addActionListener(e -> dispose());
        
        setVisible(true);
    }
    
    private void addBook() {
        String bookId = bookIdField.getText().trim();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String isbn = isbnField.getText().trim();
        
        if (bookId.isEmpty() || title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LibraryData data = LibraryData.getInstance();
        if (data.findBookById(bookId) != null) {
            JOptionPane.showMessageDialog(this, "Book ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Book book = new Book(bookId, title, author, isbn);
        data.addBook(book);
        
        JOptionPane.showMessageDialog(this, "Book added successfully.");
        dispose();
    }
}

// Dialog to view all books
class ViewBooksDialog extends JDialog {
    public ViewBooksDialog(JFrame parent) {
        super(parent, "View Books", true);
        setSize(800, 500);
        setLocationRelativeTo(parent);
        
        LibraryData data = LibraryData.getInstance();
        ArrayList<Book> books = data.getBooks();
        
        String[] columns = {"Book ID", "Title", "Author", "ISBN", "Status", "Borrowed By"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Book book : books) {
            Object[] row = {
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.isAvailable() ? "Available" : "Borrowed",
                book.isAvailable() ? "-" : book.getBorrowedBy()
            };
            model.addRow(row);
        }
        
        JTable table = new JTable(model);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
}

// Dialog to search books
class SearchBooksDialog extends JDialog {
    private JTextField searchField;
    private JButton searchButton;
    private JTextArea resultsArea;
    private JLabel timeLabel;
    
    public SearchBooksDialog(JFrame parent) {
        super(parent, "Search Books", true);
        setSize(700, 500);
        setLocationRelativeTo(parent);
        
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Search by Title:"));
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        topPanel.add(searchField);
        topPanel.add(searchButton);
        
        add(topPanel, BorderLayout.NORTH);
        
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        timeLabel = new JLabel("Time Complexity: O(n log n) per search (Baseline - sorts every time)");
        timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bottomPanel.add(timeLabel, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        searchButton.addActionListener(e -> performSearch());
        
        setVisible(true);
    }
    
    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LibraryData data = LibraryData.getInstance();
        SearchingAlgorithm searcher = new SearchingAlgorithm();
        
        ArrayList<Book> results = searcher.searchBooksByTitle(data.getBooks(), searchTerm);
        
        StringBuilder sb = new StringBuilder();
        sb.append("Search Results for: \"").append(searchTerm).append("\"\n");
        sb.append("=".repeat(60)).append("\n\n");
        
        if (results.isEmpty()) {
            sb.append("No books found.\n");
        } else {
            sb.append("Found ").append(results.size()).append(" book(s):\n\n");
            for (Book book : results) {
                sb.append("Book ID: ").append(book.getBookId()).append("\n");
                sb.append("Title: ").append(book.getTitle()).append("\n");
                sb.append("Author: ").append(book.getAuthor()).append("\n");
                sb.append("ISBN: ").append(book.getIsbn()).append("\n");
                sb.append("Status: ").append(book.isAvailable() ? "Available" : "Borrowed").append("\n");
                sb.append("-".repeat(60)).append("\n");
            }
        }
        
        long timeNano = searcher.getExecutionTime();
        double timeMicro = timeNano / 1000.0;
        sb.append("\nExecution Time: ").append(String.format("%.2f", timeMicro)).append(" microseconds\n");
        sb.append("(").append(timeNano).append(" nanoseconds)\n");
        
        resultsArea.setText(sb.toString());
    }
}

// Dialog to sort books
class SortBooksDialog extends JDialog {
    public SortBooksDialog(JFrame parent) {
        super(parent, "Sort Books", true);
        setSize(800, 500);
        setLocationRelativeTo(parent);
        
        LibraryData data = LibraryData.getInstance();
        SortingAlgorithm sorter = new SortingAlgorithm();
        
        ArrayList<Book> sortedBooks = sorter.sortBooks(data.getBooks());
        
        String[] columns = {"Book ID", "Title", "Author", "ISBN", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Book book : sortedBooks) {
            Object[] row = {
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.isAvailable() ? "Available" : "Borrowed"
            };
            model.addRow(row);
        }
        
        JTable table = new JTable(model);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        long timeNano = sorter.getExecutionTime();
        double timeMicro = timeNano / 1000.0;
        
        JLabel infoLabel = new JLabel(
            String.format("<html>Books sorted by title (Alphabetically)<br>" +
                         "Algorithm: QuickSort (Baseline - First Element Pivot)<br>" +
                         "Time Complexity: O(n²) worst case<br>" +
                         "Execution Time: %.2f microseconds (%d nanoseconds)</html>", 
                         timeMicro, timeNano)
        );
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(infoLabel, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
}

// Dialog to add a new member
class AddMemberDialog extends JDialog {
    private JTextField memberIdField, nameField, emailField, phoneField;
    private JButton addButton, cancelButton;
    
    public AddMemberDialog(JFrame parent) {
        super(parent, "Add Member", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Member ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Member ID:"), gbc);
        memberIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(memberIdField, gbc);
        
        // Name
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Name:"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Phone:"), gbc);
        phoneField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        add(panel);
        
        addButton.addActionListener(e -> addMember());
        cancelButton.addActionListener(e -> dispose());
        
        setVisible(true);
    }
    
    private void addMember() {
        String memberId = memberIdField.getText().trim();
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        
        if (memberId.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LibraryData data = LibraryData.getInstance();
        if (data.findMemberById(memberId) != null) {
            JOptionPane.showMessageDialog(this, "Member ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Member member = new Member(memberId, name, email, phone);
        data.addMember(member);
        
        JOptionPane.showMessageDialog(this, "Member added successfully.");
        dispose();
    }
}

// Dialog to view all members
class ViewMembersDialog extends JDialog {
    public ViewMembersDialog(JFrame parent) {
        super(parent, "View Members", true);
        setSize(700, 400);
        setLocationRelativeTo(parent);
        
        LibraryData data = LibraryData.getInstance();
        ArrayList<Member> members = data.getMembers();
        
        String[] columns = {"Member ID", "Name", "Email", "Phone", "Books Borrowed"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Member member : members) {
            Object[] row = {
                member.getMemberId(),
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getBorrowedBooks().size()
            };
            model.addRow(row);
        }
        
        JTable table = new JTable(model);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        
        add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
}

// Dialog to borrow a book
class BorrowBookDialog extends JDialog {
    private JTextField memberIdField, bookIdField;
    private JButton borrowButton, cancelButton;
    
    public BorrowBookDialog(JFrame parent) {
        super(parent, "Borrow Book", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Member ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Member ID:"), gbc);
        memberIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(memberIdField, gbc);
        
        // Book ID
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Book ID:"), gbc);
        bookIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        borrowButton = new JButton("Borrow");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(borrowButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        add(panel);
        
        borrowButton.addActionListener(e -> borrowBook());
        cancelButton.addActionListener(e -> dispose());
        
        setVisible(true);
    }
    
    private void borrowBook() {
        String memberId = memberIdField.getText().trim();
        String bookId = bookIdField.getText().trim();
        
        if (memberId.isEmpty() || bookId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LibraryData data = LibraryData.getInstance();
        Member member = data.findMemberById(memberId);
        Book book = data.findBookById(bookId);
        
        if (member == null) {
            JOptionPane.showMessageDialog(this, "Member not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!book.isAvailable()) {
            JOptionPane.showMessageDialog(this, "Book is already borrowed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Borrow the book
        book.setAvailable(false);
        book.setBorrowedBy(memberId);
        book.setBorrowDate(new Date());
        member.borrowBook(bookId);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = String.format("Book borrowed successfully.\n\nBook: %s\nMember: %s\nDate: %s",
                                       book.getTitle(), member.getName(), sdf.format(book.getBorrowDate()));
        
        JOptionPane.showMessageDialog(this, message);
        dispose();
    }
}

// Dialog to return a book
class ReturnBookDialog extends JDialog {
    private JTextField bookIdField;
    private JButton returnButton, cancelButton;
    
    public ReturnBookDialog(JFrame parent) {
        super(parent, "Return Book", true);
        setSize(400, 200);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Book ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Book ID:"), gbc);
        bookIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        returnButton = new JButton("Return");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(returnButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        add(panel);
        
        returnButton.addActionListener(e -> returnBook());
        cancelButton.addActionListener(e -> dispose());
        
        setVisible(true);
    }
    
    private void returnBook() {
        String bookId = bookIdField.getText().trim();
        
        if (bookId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Book ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LibraryData data = LibraryData.getInstance();
        Book book = data.findBookById(bookId);
        
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (book.isAvailable()) {
            JOptionPane.showMessageDialog(this, "Book is not currently borrowed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Find the member who borrowed the book
        String memberId = book.getBorrowedBy();
        Member member = data.findMemberById(memberId);
        
        // Return the book
        book.setAvailable(true);
        book.setBorrowedBy(null);
        Date borrowDate = book.getBorrowDate();
        book.setBorrowDate(null);
        
        if (member != null) {
            member.returnBook(bookId);
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String message = String.format("Book returned successfully.\n\nBook: %s\nBorrowed by: %s\nBorrow Date: %s\nReturn Date: %s",
                                       book.getTitle(), 
                                       member != null ? member.getName() : memberId,
                                       borrowDate != null ? sdf.format(borrowDate) : "Unknown",
                                       sdf.format(new Date()));
        
        JOptionPane.showMessageDialog(this, message);
        dispose();
    }
}
