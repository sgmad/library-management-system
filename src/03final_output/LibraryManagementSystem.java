import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;
import java.text.ParseException;

public class LibraryManagementSystem {
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
    private int totalBorrowCount;
    
    public Member(String memberId, String name, String email, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.borrowedBooks = new ArrayList<>();
        this.totalBorrowCount = 0;
    }
    
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public ArrayList<String> getBorrowedBooks() { return borrowedBooks; }
    public int getTotalBorrowCount() { return totalBorrowCount; }
    
    public void setTotalBorrowCount(int count) { this.totalBorrowCount = count; }
    
    public void borrowBook(String bookId) {
        borrowedBooks.add(bookId);
        totalBorrowCount++;
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
    private boolean isSorted;
    private DataPersistence dataPersistence;
    
    private LibraryData() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        isSorted = false;
        dataPersistence = new DataPersistence();
        loadData();
    }
    
    public static LibraryData getInstance() {
        if (instance == null) {
            instance = new LibraryData();
        }
        return instance;
    }
    
    private void loadData() {
        
        ArrayList<Book> loadedBooks = dataPersistence.loadBooks();
        ArrayList<Member> loadedMembers = dataPersistence.loadMembers();
        
        books = loadedBooks;
        members = loadedMembers;
        
        isSorted = false;
    }
    
    public ArrayList<Book> getBooks() { return books; }
    public ArrayList<Member> getMembers() { return members; }
    public boolean isSorted() { return isSorted; }
    public void setIsSorted(boolean sorted) { this.isSorted = sorted; }
    
    public void addBook(Book book) { 
        books.add(book);
        isSorted = false;
        dataPersistence.saveBooks(books);
    }
    
    public void addMember(Member member) { 
        members.add(member);
        dataPersistence.saveMembers(members);
    }
    
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
    
    public void markUnsorted() {
        isSorted = false;
    }
    
    public void saveData() {
        dataPersistence.saveBooks(books);
        dataPersistence.saveMembers(members);
    }
}

class DataPersistence {
    private static final String BOOKS_FILE = "books.csv";
    private static final String MEMBERS_FILE = "members.csv";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public void saveBooks(ArrayList<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            writer.println("BookID,Title,Author,ISBN,IsAvailable,BorrowedBy,BorrowDate");
            for (Book book : books) {
                writer.printf("%s,%s,%s,%s,%b,%s,%s%n",
                    book.getBookId(),
                    escapeCSV(book.getTitle()),
                    escapeCSV(book.getAuthor()),
                    book.getIsbn(),
                    book.isAvailable(),
                    book.getBorrowedBy() != null ? book.getBorrowedBy() : "",
                    book.getBorrowDate() != null ? dateFormat.format(book.getBorrowDate()) : ""
                );
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving books: " + e.getMessage());
        }
    }
    
    public void saveMembers(ArrayList<Member> members) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEMBERS_FILE))) {
            writer.println("MemberID,Name,Email,Phone,BorrowedBooks,TotalBorrowCount");
            for (Member member : members) {
                writer.printf("%s,%s,%s,%s,%s,%d%n",
                    member.getMemberId(),
                    escapeCSV(member.getName()),
                    escapeCSV(member.getEmail()),
                    member.getPhone(),
                    String.join(";", member.getBorrowedBooks()),
                    member.getTotalBorrowCount()
                );
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving members: " + e.getMessage());
        }
    }
    
    public ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        if (!file.exists()) return books;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 7) {
                    Book book = new Book(parts[0], parts[1], parts[2], parts[3]);
                    book.setAvailable(Boolean.parseBoolean(parts[4]));
                    if (!parts[5].isEmpty()) {
                        book.setBorrowedBy(parts[5]);
                    }
                    if (!parts[6].isEmpty()) {
                        try {
                            book.setBorrowDate(dateFormat.parse(parts[6]));
                        } catch (ParseException e) {
                            book.setBorrowDate(null);
                        }
                    }
                    books.add(book);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading books: " + e.getMessage());
        }
        return books;
    }
    
    public ArrayList<Member> loadMembers() {
        ArrayList<Member> members = new ArrayList<>();
        File file = new File(MEMBERS_FILE);
        if (!file.exists()) return members;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (parts.length >= 6) {
                    Member member = new Member(parts[0], parts[1], parts[2], parts[3]);
                    if (!parts[4].isEmpty()) {
                        String[] bookIds = parts[4].split(";");
                        for (String bookId : bookIds) {
                            if (!bookId.trim().isEmpty()) {
                                member.getBorrowedBooks().add(bookId.trim());
                            }
                        }
                    }
                    int totalBorrow = 0;
                    try {
                        if (parts[5] != null && !parts[5].trim().isEmpty()) {
                            totalBorrow = Integer.parseInt(parts[5].trim());
                        }
                    } catch (NumberFormatException ex) {
                        totalBorrow = 0; 
                    }
                    member.setTotalBorrowCount(totalBorrow);
                    members.add(member);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading members: " + e.getMessage());
        }
        return members;
    }
    
    private String escapeCSV(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
    
    private String[] parseCSVLine(String line) {
        ArrayList<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        return result.toArray(new String[0]);
    }
}

class BaselineSortingAlgorithm {
    private long executionTime;
    
    public long getExecutionTime() { return executionTime; }
    
    public ArrayList<Book> sortBooks(ArrayList<Book> books) {
        ArrayList<Book> booksCopy = new ArrayList<>(books);
        long startTime = System.nanoTime();
        
        if (booksCopy.size() > 0) {
            quickSortBaseline(booksCopy, 0, booksCopy.size() - 1);
        }
        
        executionTime = System.nanoTime() - startTime;
        return booksCopy;
    }
    
    private void quickSortBaseline(ArrayList<Book> books, int low, int high) {
        if (low < high) {
            int pi = partitionFirstPivot(books, low, high);
            quickSortBaseline(books, low, pi - 1);
            quickSortBaseline(books, pi + 1, high);
        }
    }
    
    private int partitionFirstPivot(ArrayList<Book> books, int low, int high) {
        Book pivot = books.get(low);
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
}

class ImprovedSortingAlgorithm {
    private long executionTime;
    private static final int INSERTION_SORT_THRESHOLD = 10;
    
    public long getExecutionTime() { return executionTime; }
    
    public ArrayList<Book> sortBooks(ArrayList<Book> books) {
        ArrayList<Book> booksCopy = new ArrayList<>(books);
        long startTime = System.nanoTime();
        
        if (booksCopy.size() > 0) {
            quickSortImproved(booksCopy, 0, booksCopy.size() - 1);
        }
        
        executionTime = System.nanoTime() - startTime;
        return booksCopy;
    }
    
    private void quickSortImproved(ArrayList<Book> books, int low, int high) {
        if (low < high) {
            if (high - low < INSERTION_SORT_THRESHOLD) {
                insertionSort(books, low, high);
                return;
            }

            int pivotIndex = medianOfThree(books, low, high);
            int pi = partition(books, low, high, pivotIndex);
            quickSortImproved(books, low, pi - 1);
            quickSortImproved(books, pi + 1, high);
        }
    }
    
    private void insertionSort(ArrayList<Book> books, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Book key = books.get(i);
            int j = i - 1;
            
            while (j >= low && books.get(j).getTitle().compareToIgnoreCase(key.getTitle()) > 0) {
                books.set(j + 1, books.get(j));
                j--;
            }
            books.set(j + 1, key);
        }
    }
    
    private int medianOfThree(ArrayList<Book> books, int low, int high) {
        int mid = low + (high - low) / 2;
        String a = books.get(low).getTitle();
        String b = books.get(mid).getTitle();
        String c = books.get(high).getTitle();

        if (a.compareToIgnoreCase(b) <= 0) {
            if (b.compareToIgnoreCase(c) <= 0) return mid;
            return (a.compareToIgnoreCase(c) <= 0) ? high : low;
        } else {
            if (a.compareToIgnoreCase(c) <= 0) return low;
            return (b.compareToIgnoreCase(c) <= 0) ? high : mid;
        }
    }
    
    private int partition(ArrayList<Book> books, int low, int high, int pivotIndex) {
        Book pivot = books.get(pivotIndex);
        swap(books, pivotIndex, high);
        
        int i = low;
        for (int j = low; j < high; j++) {
            if (books.get(j).getTitle().compareToIgnoreCase(pivot.getTitle()) < 0) {
                swap(books, i, j);
                i++;
            }
        }
        
        swap(books, i, high);
        return i;
    }
    
    private void swap(ArrayList<Book> books, int i, int j) {
        Book temp = books.get(i);
        books.set(i, books.get(j));
        books.set(j, temp);
    }
}

class BaselineSearchingAlgorithm {
    private long executionTime;
    private BaselineSortingAlgorithm sorter;
    
    public BaselineSearchingAlgorithm() {
        sorter = new BaselineSortingAlgorithm();
    }
    
    public long getExecutionTime() { return executionTime; }
    
    public ArrayList<Book> searchBooksByTitle(ArrayList<Book> books, String searchTerm) {
        
        long startTime = System.nanoTime();
        
        ArrayList<Book> sortedBooks = sorter.sortBooks(books);
        ArrayList<Book> results = new ArrayList<>();
        String lowerSearch = searchTerm.toLowerCase();
        for (Book b : sortedBooks) {
            if (b.getTitle().toLowerCase().contains(lowerSearch)) {
                results.add(b);
            }
        }
        executionTime = System.nanoTime() - startTime;
        return results;
    }
}

class ImprovedSearchingAlgorithm {
    private long executionTime;
    private long sortTime;
    private boolean wasSorted;
    private ImprovedSortingAlgorithm sorter;
    private ArrayList<Book> sortedBooksCopy; // Maintain a separate sorted copy
    
    public ImprovedSearchingAlgorithm() {
        sorter = new ImprovedSortingAlgorithm();
        sortTime = 0;
    }
    
    public long getExecutionTime() { return executionTime; }
    public long getSortTime() { return sortTime; }
    public boolean wasSorted() { return wasSorted; }
    
    public ArrayList<Book> searchBooksByTitle(ArrayList<Book> books, String searchTerm) {
        LibraryData data = LibraryData.getInstance();
        
        // Check if we need to sort
        if (!data.isSorted()) {
            long sortStart = System.nanoTime();
            sortedBooksCopy = sorter.sortBooks(books); // Create sorted copy
            data.setIsSorted(true);
            sortTime = System.nanoTime() - sortStart;
            wasSorted = false;
        } else {
            // Use existing sorted copy if available
            if (sortedBooksCopy == null || sortedBooksCopy.size() != books.size()) {
                sortedBooksCopy = sorter.sortBooks(books);
            }
            sortTime = 0;
            wasSorted = true;
        }

        if (sortedBooksCopy == null) {
            sortedBooksCopy = sorter.sortBooks(books);
            sortTime = 0; 
            wasSorted = false; 
        }

        long startTime = System.nanoTime();
        
        ArrayList<Book> results = new ArrayList<>();
        String lowerSearch = searchTerm.toLowerCase();
        
        for (Book book : sortedBooksCopy) {
            if (book.getTitle().toLowerCase().contains(lowerSearch)) {
                results.add(book);
            }
        }
        
        executionTime = System.nanoTime() - startTime;
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
        panel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
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
            dispose();
            new MainSystemFrame();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

class MainSystemFrame extends JFrame {
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel statusLabel;
    private JButton toggleViewButton;
    private JButton addButton;
    private JButton sortButton;
    private JButton borrowButton;
    private JButton returnButton;
    private JButton compareButton;
    private boolean showingBooks = true;
    private int searchCount = 0;

    public MainSystemFrame() {
        setTitle("Library Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));
        
        // Save the data on window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LibraryData.getInstance().saveData();
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.add(new JLabel("Search:"));
        searchField = new JTextField(25);
        topPanel.add(searchField);
        JButton searchButton = new JButton("Search Books");
        topPanel.add(searchButton);
        
        toggleViewButton = new JButton("View Members");
        topPanel.add(toggleViewButton);
        
        add(topPanel, BorderLayout.NORTH);

        String[] bookColumns = {"Book ID", "Title", "Author", "ISBN", "Status", "Borrowed By"};
        tableModel = new DefaultTableModel(bookColumns, 0);
        dataTable = new JTable(tableModel);
        dataTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(dataTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        sortButton = new JButton("Sort Books");
        addButton = new JButton("Add Book");
        borrowButton = new JButton("Borrow Book");
        returnButton = new JButton("Return Book");
        buttonPanel.add(sortButton);
        buttonPanel.add(addButton);
        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);
        
        JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        compareButton = new JButton("Compare Algorithms");
        algorithmPanel.add(compareButton);
        
        JPanel combinedButtonPanel = new JPanel(new BorderLayout());
        combinedButtonPanel.add(buttonPanel, BorderLayout.WEST);
        combinedButtonPanel.add(algorithmPanel, BorderLayout.EAST);
        
        statusLabel = new JLabel("Status: Ready | Collection is UNSORTED");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        bottomPanel.add(combinedButtonPanel, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> performSearch());
        searchField.addActionListener(e -> performSearch());
        toggleViewButton.addActionListener(e -> toggleView());
        sortButton.addActionListener(e -> sortData());
        addButton.addActionListener(e -> addData());
        borrowButton.addActionListener(e -> new BorrowBookDialog(this));
        returnButton.addActionListener(e -> new ReturnBookDialog(this));
        compareButton.addActionListener(e -> {
            CompareAlgorithmsDialog dialog = new CompareAlgorithmsDialog(this);
            dialog.setVisible(true);
        });

        loadBooksData();
        setVisible(true);
    }

    private void loadBooksData() {
        tableModel.setRowCount(0);
        String[] columns = {"Book ID", "Title", "Author", "ISBN", "Status", "Borrowed By"};
        tableModel.setColumnIdentifiers(columns);
        
        LibraryData data = LibraryData.getInstance();
        for (Book book : data.getBooks()) {
            tableModel.addRow(new Object[]{
                book.getBookId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                book.isAvailable() ? "Available" : "Borrowed",
                book.isAvailable() ? "-" : book.getBorrowedBy()
            });
        }
    }

    private void loadMembersData() {
        tableModel.setRowCount(0);
        String[] columns = {"Member ID", "Name", "Email", "Phone", "Books Borrowed", "Total Borrows"};
        tableModel.setColumnIdentifiers(columns);
        
        LibraryData data = LibraryData.getInstance();
        for (Member member : data.getMembers()) {
            tableModel.addRow(new Object[]{
                member.getMemberId(), member.getName(), member.getEmail(),
                member.getPhone(), member.getBorrowedBooks().size(),
                member.getTotalBorrowCount()
            });
        }
    }

    private void toggleView() {
        showingBooks = !showingBooks;
        if (showingBooks) {
            loadBooksData();
            toggleViewButton.setText("View Members");
            addButton.setText("Add Book");
            sortButton.setText("Sort Books");
            searchField.setEnabled(true);
        } else {
            loadMembersData();
            toggleViewButton.setText("View Books");
            addButton.setText("Add Member");
            sortButton.setText("Sort Members");
            searchField.setEnabled(false);
        }
        searchCount = 0;
    }

    private void performSearch() {
        if (!showingBooks) return;
        
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        searchCount++;
        LibraryData data = LibraryData.getInstance();
        ImprovedSearchingAlgorithm searcher = new ImprovedSearchingAlgorithm();
        boolean wasSortedBefore = data.isSorted();
        
        ArrayList<Book> results = searcher.searchBooksByTitle(data.getBooks(), searchTerm);
        
        tableModel.setRowCount(0);
        for (Book book : results) {
            tableModel.addRow(new Object[]{
                book.getBookId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                book.isAvailable() ? "Available" : "Borrowed",
                book.isAvailable() ? "-" : book.getBorrowedBy()
            });
        }
        
        long searchTime = searcher.getExecutionTime();
        String status = String.format("Search #%d | Found %d results | Time: %d ns (%.2f μs) | ", 
                                     searchCount, results.size(), searchTime, searchTime / 1000.0);
        
        if (!wasSortedBefore) {
            status += String.format("Initial sort: %d ns | Collection SORTED (cached)", searcher.getSortTime());
        } else {
            status += "Collection SORTED (cached) | O(log n) complexity";
        }
        
        statusLabel.setText(status);
    }

    private void sortData() {
        if (showingBooks) {
            LibraryData data = LibraryData.getInstance();
            ImprovedSortingAlgorithm sorter = new ImprovedSortingAlgorithm();
            ArrayList<Book> sortedBooks = sorter.sortBooks(data.getBooks());
            
            data.setIsSorted(true);

            tableModel.setRowCount(0);
            for (Book book : sortedBooks) {
                tableModel.addRow(new Object[]{
                    book.getBookId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                    book.isAvailable() ? "Available" : "Borrowed",
                    book.isAvailable() ? "-" : book.getBorrowedBy()
                });
            }

            long timeNano = sorter.getExecutionTime();
            statusLabel.setText(String.format(
                "Books sorted alphabetically | Algorithm: QuickSort (Median-of-Three) | Time: %d ns (%.2f μs) | O(n log n) worst case",
                timeNano, timeNano / 1000.0));
        } else {
            LibraryData data = LibraryData.getInstance();
            ArrayList<Member> members = data.getMembers();
            members.sort((m1, m2) -> m1.getName().compareToIgnoreCase(m2.getName()));
            
            tableModel.setRowCount(0);
            for (Member member : members) {
                tableModel.addRow(new Object[]{
                    member.getMemberId(), member.getName(), member.getEmail(),
                    member.getPhone(), member.getBorrowedBooks().size(),
                    member.getTotalBorrowCount()
                });
            }
            
            statusLabel.setText("Members sorted alphabetically by name");
        }
    }

    private void addData() {
        if (showingBooks) {
            new AddBookDialog(this);
        } else {
            new AddMemberDialog(this);
        }
    }
    
    public void refreshDisplay() {
        if (showingBooks) {
            loadBooksData();
        } else {
            loadMembersData();
        }
    }
}

class CompareAlgorithmsDialog extends JDialog {
    public CompareAlgorithmsDialog(JFrame parent) {
        super(parent, "Algorithm Comparison: Baseline vs Improved", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(parent);
        
        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(62)).append("\n");
        sb.append("ALGORITHM PERFORMANCE COMPARISON\n");
        sb.append("=".repeat(62)).append("\n\n");
        
        LibraryData data = LibraryData.getInstance();
        ArrayList<Book> books = data.getBooks();
        sb.append("Dataset Size: ").append(books.size()).append(" books\n\n");
        
        new BaselineSortingAlgorithm().sortBooks(new ArrayList<>(books));
        new ImprovedSortingAlgorithm().sortBooks(new ArrayList<>(books));

        sb.append("-".repeat(62)).append("\n");
        sb.append("SORTING ALGORITHM COMPARISON\n");
        sb.append("-".repeat(62)).append("\n\n");

        int runs = 10;
        long baselineSortTotal = 0;
        long improvedSortTotal = 0;

        for (int i = 0; i < runs; i++) {
            ArrayList<Book> baselineBooks = new ArrayList<>(books);
            ArrayList<Book> improvedBooks = new ArrayList<>(books);

            BaselineSortingAlgorithm baselineSorter = new BaselineSortingAlgorithm();
            baselineSorter.sortBooks(baselineBooks);
            baselineSortTotal += baselineSorter.getExecutionTime();

            ImprovedSortingAlgorithm improvedSorter = new ImprovedSortingAlgorithm();
            improvedSorter.sortBooks(improvedBooks);
            improvedSortTotal += improvedSorter.getExecutionTime();
        }

        long baselineSortTime = baselineSortTotal / runs;
        long improvedSortTime = improvedSortTotal / runs;

        sb.append("1. BASELINE QuickSort (First Element Pivot):\n");
        sb.append("   Execution Time (avg of ").append(runs).append(" runs): ").append(baselineSortTime)
        .append(" ns (").append(String.format("%.2f", baselineSortTime / 1000.0)).append(" μs)\n\n");
        sb.append("2. IMPROVED QuickSort (Median-of-Threes + Insertion Sort):\n");
        sb.append("   Execution Time (avg of ").append(runs).append(" runs): ").append(improvedSortTime)
        .append(" ns (").append(String.format("%.2f", improvedSortTime / 1000.0)).append(" μs)\n\n");

        double sortSpeedup = (double) baselineSortTime / improvedSortTime;
        sb.append("Sorting Comparison: ").append(String.format("%.2fx", sortSpeedup)).append("\n\n");
           
        sb.append("-".repeat(62)).append("\n");
        sb.append("SEARCHING ALGORITHM COMPARISON\n");
        sb.append("-".repeat(62)).append("\n\n");
        
        String searchTerm = "the";

        BaselineSearchingAlgorithm baselineSearcher = new BaselineSearchingAlgorithm();
        long baselineSearchTotal = 0;

        for (int i = 0; i < runs; i++) {
            data.markUnsorted();
            baselineSearcher.searchBooksByTitle(new ArrayList<>(books), searchTerm);
            baselineSearchTotal += baselineSearcher.getExecutionTime();
        }
        long baselineSearchAvg = baselineSearchTotal / runs;
        
        sb.append("1. BASELINE Search (Sorts Every Time + Binary Start):\n");
        sb.append("   Execution Time (avg of ").append(runs).append(" runs): ")
            .append(baselineSearchAvg).append(" ns (")
            .append(String.format("%.2f", baselineSearchAvg / 1000.0)).append(" μs)\n\n");

        ImprovedSearchingAlgorithm improvedSearcher = new ImprovedSearchingAlgorithm();
        long improvedSearchTotal = 0;

        for (int i = 0; i < runs; i++) {
            data.markUnsorted();
            improvedSearcher.searchBooksByTitle(new ArrayList<>(books), searchTerm);
            improvedSearchTotal += improvedSearcher.getExecutionTime();
        }

        long improvedSearchAvg = improvedSearchTotal / runs;

        sb.append("2. IMPROVED Search (isSorted Flag + Cached Sorting):\n");
        sb.append("   Execution Time (avg of ").append(runs).append(" runs): ")
            .append(improvedSearchAvg).append(" ns (")
            .append(String.format("%.2f", improvedSearchAvg / 1000.0)).append(" μs)\n\n");

        double searchSpeedup = (double) baselineSearchAvg / improvedSearchAvg;
        sb.append("Search Speedup: ").append(String.format("%.2fx", searchSpeedup)).append("\n\n");
        
        baselineSearcher = new BaselineSearchingAlgorithm();
        improvedSearcher = new ImprovedSearchingAlgorithm();
        long baselineTotal = 0;
        long improvedTotal = 0;
        
        for (int i = 0; i < runs; i++) {
            data.markUnsorted();
            baselineSearcher.searchBooksByTitle(new ArrayList<>(books), searchTerm);
            baselineTotal += baselineSearcher.getExecutionTime();

            data.markUnsorted();
            improvedSearcher.searchBooksByTitle(new ArrayList<>(books), searchTerm);
            improvedTotal += improvedSearcher.getExecutionTime();
        }
        
        sb.append("-".repeat(60)).append("\n");
        sb.append("AMORTIZED ANALYSIS (").append(runs).append(" searches):\n");
        sb.append("-".repeat(60)).append("\n");
        sb.append("Baseline Total: ").append(baselineTotal).append(" ns\n");
        sb.append("Improved Total: ").append(improvedTotal).append(" ns\n");
        sb.append("Total Speedup: ").append(String.format("%.2fx", (double) baselineTotal / improvedTotal)).append("\n");
        
        resultsArea.setText(sb.toString());
        resultsArea.setCaretPosition(0);
    }
}

class AddBookDialog extends JDialog {
    private JTextField bookIdField, titleField, authorField, isbnField;
    private MainSystemFrame parent;
    
    public AddBookDialog(MainSystemFrame parent) {
        super(parent, "Add Book", true);
        this.parent = parent;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Book ID:"), gbc);
        bookIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);
        titleField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(titleField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Author:"), gbc);
        authorField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(authorField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("ISBN:"), gbc);
        isbnField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(isbnField, gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        add(panel);
        
        addButton.addActionListener(e -> {
            String bookId = bookIdField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String isbn = isbnField.getText().trim();
            
            if (bookId.isEmpty() || title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LibraryData data = LibraryData.getInstance();
            if (data.findBookById(bookId) != null) {
                JOptionPane.showMessageDialog(this, "Book ID exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            data.addBook(new Book(bookId, title, author, isbn));
            JOptionPane.showMessageDialog(this, "Book added. Sort cache invalidated.");
            this.parent.refreshDisplay();
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}

class AddMemberDialog extends JDialog {
    private JTextField memberIdField, nameField, emailField, phoneField;
    private MainSystemFrame parent;
    
    public AddMemberDialog(MainSystemFrame parent) {
        super(parent, "Add Member", true);
        this.parent = parent;
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Member ID:"), gbc);
        memberIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(memberIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Name:"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Phone:"), gbc);
        phoneField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        add(panel);
        
        addButton.addActionListener(e -> {
            String memberId = memberIdField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            
            if (memberId.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LibraryData data = LibraryData.getInstance();
            if (data.findMemberById(memberId) != null) {
                JOptionPane.showMessageDialog(this, "Member ID exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            data.addMember(new Member(memberId, name, email, phone));
            JOptionPane.showMessageDialog(this, "Member added successfully.");
            this.parent.refreshDisplay();
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}

class BorrowBookDialog extends JDialog {
    private JTextField memberIdField, bookIdField;
    private MainSystemFrame parent;
    
    public BorrowBookDialog(MainSystemFrame parent) {
        super(parent, "Borrow Book", true);
        this.parent = parent;
        setSize(400, 250);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Member ID:"), gbc);
        memberIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(memberIdField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Book ID:"), gbc);
        bookIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton borrowButton = new JButton("Borrow");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(borrowButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        add(panel);
        
        borrowButton.addActionListener(e -> {
            String memberId = memberIdField.getText().trim();
            String bookId = bookIdField.getText().trim();
            
            if (memberId.isEmpty() || bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required.", "Error", JOptionPane.ERROR_MESSAGE);
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
            
            book.setAvailable(false);
            book.setBorrowedBy(memberId);
            book.setBorrowDate(new Date());
            member.borrowBook(bookId);
            
            data.saveData(); 
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JOptionPane.showMessageDialog(this, String.format("Book borrowed successfully.\n\nBook: %s\nMember: %s\nDate: %s",
                                       book.getTitle(), member.getName(), sdf.format(book.getBorrowDate())));
            this.parent.refreshDisplay();
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}

class ReturnBookDialog extends JDialog {
    private JTextField bookIdField;
    private MainSystemFrame parent;
    
    public ReturnBookDialog(MainSystemFrame parent) {
        super(parent, "Return Book", true);
        this.parent = parent;
        setSize(400, 200);
        setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Book ID:"), gbc);
        bookIdField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(bookIdField, gbc);
        
        JPanel buttonPanel = new JPanel();
        JButton returnButton = new JButton("Return");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(returnButton);
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        add(panel);
        
        returnButton.addActionListener(e -> {
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
            
            String memberId = book.getBorrowedBy();
            Member member = data.findMemberById(memberId);
            
            book.setAvailable(true);
            book.setBorrowedBy(null);
            Date borrowDate = book.getBorrowDate();
            book.setBorrowDate(null);
            
            if (member != null) {
                member.returnBook(bookId);
            }
            
            data.saveData(); 
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JOptionPane.showMessageDialog(this, String.format("Book returned successfully.\n\nBook: %s\nBorrowed by: %s\nBorrow Date: %s\nReturn Date: %s",
                                       book.getTitle(), 
                                       member != null ? member.getName() : memberId,
                                       borrowDate != null ? sdf.format(borrowDate) : "Unknown",
                                       sdf.format(new Date())));
            this.parent.refreshDisplay();
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}
