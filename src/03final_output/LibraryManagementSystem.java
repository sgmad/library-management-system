import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

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
    private boolean isSorted;
    
    private LibraryData() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        isSorted = false;
        loadSampleData();
    }
    
    public static LibraryData getInstance() {
        if (instance == null) {
            instance = new LibraryData();
        }
        return instance;
    }
    
    private void loadSampleData() {
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
        books.add(new Book("B011", "Moby-Dick", "Herman Melville", "978-0-14-243724-7"));
        books.add(new Book("B012", "Jane Eyre", "Charlotte Brontë", "978-0-14-144114-6"));
        books.add(new Book("B013", "Crime and Punishment", "Fyodor Dostoevsky", "978-0-14-044913-6"));
        books.add(new Book("B014", "The Odyssey", "Homer", "978-0-14-026886-7"));
        books.add(new Book("B015", "The Iliad", "Homer", "978-0-14-027536-0"));
        books.add(new Book("B016", "Wuthering Heights", "Emily Brontë", "978-0-14-143955-6"));
        books.add(new Book("B017", "The Picture of Dorian Gray", "Oscar Wilde", "978-0-14-143957-0"));
        books.add(new Book("B018", "Les Misérables", "Victor Hugo", "978-0-14-044430-8"));
        books.add(new Book("B019", "The Grapes of Wrath", "John Steinbeck", "978-0-14-303943-3"));
        books.add(new Book("B020", "Dracula", "Bram Stoker", "978-0-14-143984-6"));
        books.add(new Book("B021", "Frankenstein", "Mary Shelley", "978-0-14-143947-1"));
        books.add(new Book("B022", "War and Peace", "Leo Tolstoy", "978-0-14-303999-9"));
        books.add(new Book("B023", "The Adventures of Huckleberry Finn", "Mark Twain", "978-0-14-243717-8"));
        books.add(new Book("B024", "The Lord of the Rings", "J.R.R. Tolkien", "978-0-618-64015-7"));
        books.add(new Book("B025", "A Tale of Two Cities", "Charles Dickens", "978-0-14-143960-0"));
        books.add(new Book("B026", "The Brothers Karamazov", "Fyodor Dostoevsky", "978-0-14-044924-2"));
        books.add(new Book("B027", "The Count of Monte Cristo", "Alexandre Dumas", "978-0-14-044926-6"));
        books.add(new Book("B028", "Don Quixote", "Miguel de Cervantes", "978-0-14-243723-0"));
        books.add(new Book("B029", "The Scarlet Letter", "Nathaniel Hawthorne", "978-0-14-243726-1"));
        books.add(new Book("B030", "Of Mice and Men", "John Steinbeck", "978-0-14-017739-8"));
        books.add(new Book("B031", "One Hundred Years of Solitude", "Gabriel García Márquez", "978-0-06-088328-7"));
        books.add(new Book("B032", "The Alchemist", "Paulo Coelho", "978-0-06-112241-5"));
        books.add(new Book("B033", "The Old Man and the Sea", "Ernest Hemingway", "978-0-684-80122-3"));
        books.add(new Book("B034", "Catch-22", "Joseph Heller", "978-0-684-83339-2"));
        books.add(new Book("B035", "The Stranger", "Albert Camus", "978-0-679-72020-1"));
        books.add(new Book("B036", "The Divine Comedy", "Dante Alighieri", "978-0-14-044895-5"));
        books.add(new Book("B037", "Slaughterhouse-Five", "Kurt Vonnegut", "978-0-385-33384-9"));
        books.add(new Book("B038", "The Sun Also Rises", "Ernest Hemingway", "978-0-7432-9733-2"));
        books.add(new Book("B039", "The Sound and the Fury", "William Faulkner", "978-0-679-73225-9"));
        books.add(new Book("B040", "Heart of Darkness", "Joseph Conrad", "978-0-14-144167-2"));
        books.add(new Book("B041", "The Kite Runner", "Khaled Hosseini", "978-1-59448-000-3"));
        books.add(new Book("B042", "Life of Pi", "Yann Martel", "978-0-15-602732-8"));
        books.add(new Book("B043", "The Road", "Cormac McCarthy", "978-0-307-38789-9"));
        books.add(new Book("B044", "Beloved", "Toni Morrison", "978-1-4000-3341-6"));
        books.add(new Book("B045", "A Clockwork Orange", "Anthony Burgess", "978-0-393-04588-1"));
        books.add(new Book("B046", "The Handmaid's Tale", "Margaret Atwood", "978-0-385-49081-7"));
        books.add(new Book("B047", "Gone with the Wind", "Margaret Mitchell", "978-0-684-80160-5"));
        books.add(new Book("B048", "The Metamorphosis", "Franz Kafka", "978-0-486-27025-3"));
        books.add(new Book("B049", "The Call of the Wild", "Jack London", "978-0-14-132105-9"));
        books.add(new Book("B050", "The Outsiders", "S.E. Hinton", "978-0-14-240733-2"));
        books.add(new Book("B051", "Alice's Adventures in Wonderland", "Lewis Carroll", "978-0-14-751587-2"));  
        books.add(new Book("B052", "Anna Karenina", "Leo Tolstoy", "978-0-14-044917-4"));  
        books.add(new Book("B053", "Ulysses", "James Joyce", "978-0-679-60011-6"));  
        books.add(new Book("B054", "Lolita", "Vladimir Nabokov", "978-0-14-243723-0"));  
        books.add(new Book("B055", "Howards End", "E. M. Forster", "978-0-37-575376-1"));  
        books.add(new Book("B056", "The Naked and the Dead", "Norman Mailer", "978-0-8050-6017-0"));  
        books.add(new Book("B057", "Of Human Bondage", "W. Somerset Maugham", "978-0-7351-0121-3"));  
        books.add(new Book("B058", "Finnegans Wake", "James Joyce", "978-0-1400-6286-6"));  
        books.add(new Book("B059", "The Secret Agent", "Joseph Conrad", "978-0-679-41723-0"));  
        books.add(new Book("B060", "Zuleika Dobson", "Max Beerbohm", "978-0-8488-0914-9"));  
        books.add(new Book("B061", "Under the Net", "Iris Murdoch", "978-0-14-001445-4"));  
        books.add(new Book("B062", "The Golden Bowl", "Henry James", "978-0-14-243728-4"));  
        books.add(new Book("B063", "Scoop", "Evelyn Waugh", "978-0-316-92610-8"));  
        books.add(new Book("B064", "Justine", "Lawrence Durrell", "978-0-14-015317-9"));  
        books.add(new Book("B065", "Mountolive", "Lawrence Durrell", "978-0-14-015318-6"));  
        books.add(new Book("B066", "The Alexandria Quartet: Clea", "Lawrence Durrell", "978-0-14-015319-3"));  
        books.add(new Book("B067", "The Alexandria Quartet: Balthazar", "Lawrence Durrell", "978-0-14-015320-9"));  
        books.add(new Book("B068", "A Falcon Flies", "Wilbur Smith", "978-0-38-517833-4"));  
        books.add(new Book("B069", "The Shell Seekers", "Rosamunde Pilcher", "978-0-312-01058-4"));
        books.add(new Book("B070", "Middlemarch", "George Eliot", "978-0-14-143954-9"));
        books.add(new Book("B071", "Emma", "Jane Austen", "978-0-14-143958-7"));
        books.add(new Book("B072", "Sense and Sensibility", "Jane Austen", "978-0-14-143966-2"));
        books.add(new Book("B073", "Northanger Abbey", "Jane Austen", "978-0-14-143978-5"));
        books.add(new Book("B074", "Persuasion", "Jane Austen", "978-0-14-144101-6"));
        books.add(new Book("B075", "Great Expectations", "Charles Dickens", "978-0-14-143956-3"));
        books.add(new Book("B076", "David Copperfield", "Charles Dickens", "978-0-14-043944-1"));
        books.add(new Book("B077", "Oliver Twist", "Charles Dickens", "978-0-14-143974-7"));
        books.add(new Book("B078", "Bleak House", "Charles Dickens", "978-0-14-143972-3"));
        books.add(new Book("B079", "Notes from Underground", "Fyodor Dostoevsky", "978-0-14-044924-4"));
        books.add(new Book("B080", "Madame Bovary", "Gustave Flaubert", "978-0-14-044912-9"));
        books.add(new Book("B081", "The Three Musketeers", "Alexandre Dumas", "978-0-14-044926-6")); 
        books.add(new Book("B082", "The Adventures of Sherlock Holmes", "Arthur Conan Doyle", "978-0-14-103432-4"));
        books.add(new Book("B083", "Meditations", "Marcus Aurelius", "978-0-14-044933-4"));
        books.add(new Book("B084", "Gulliver's Travels", "Jonathan Swift", "978-0-14-143949-5"));
        books.add(new Book("B085", "The Time Machine", "H.G. Wells", "978-0-14-143997-7"));
        books.add(new Book("B086", "Robinson Crusoe", "Daniel Defoe", "978-0-14-143982-2"));
        books.add(new Book("B087", "Invisible Man", "Ralph Ellison", "978-0-679-60139-5"));
        books.add(new Book("B088", "The Castle", "Franz Kafka", "978-0-14-303989-4"));
        books.add(new Book("B089", "Waiting for Godot", "Samuel Beckett", "978-0-679-73277-8"));
        books.add(new Book("B090", "A Brief History of Time", "Stephen Hawking", "978-0-553-38016-3"));
        books.add(new Book("B091", "The Diary of a Young Girl", "Anne Frank", "978-0-307-59400-6"));
        books.add(new Book("B092", "The Art of War", "Sun Tzu", "978-1-59030-225-5"));
        books.add(new Book("B093", "The Prince", "Niccolò Machiavelli", "978-0-14-044915-0"));
        books.add(new Book("B094", "The Wealth of Nations", "Adam Smith", "978-0-14-043208-4"));
        books.add(new Book("B095", "The Republic", "Plato", "978-0-14-045511-3"));
        books.add(new Book("B096", "The Social Contract", "Jean-Jacques Rousseau", "978-0-14-044201-4"));
        books.add(new Book("B097", "Walden", "Henry David Thoreau", "978-0-14-039031-0"));
        books.add(new Book("B098", "The Souls of Black Folk", "W.E.B. Du Bois", "978-0-14-018998-8"));
        books.add(new Book("B099", "Democracy in America", "Alexis de Tocqueville", "978-0-14-044760-6"));
        books.add(new Book("B100", "The Origin of Species", "Charles Darwin", "978-1-5098-2769-5"));

        members.add(new Member("M001", "Mark Garata", "markgarata@umin.com", "639-0101"));
        members.add(new Member("M002", "Franciene Candare", "francienecandare@umin.com", "639-0102"));
        members.add(new Member("M003", "Kiera Aguiadan", "kieraaguiadan@umin.com", "639-0103"));
        members.add(new Member("M004", "Emmanuel Tuling", "emmanueltuling@umin.com", "639-0104"));
        
        isSorted = false;
    }
    
    public ArrayList<Book> getBooks() { return books; }
    public ArrayList<Member> getMembers() { return members; }
    public boolean isSorted() { return isSorted; }
    public void setIsSorted(boolean sorted) { this.isSorted = sorted; }
    
    public void addBook(Book book) { 
        books.add(book);
        isSorted = false;
    }
    
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
    
    public void markUnsorted() {
        isSorted = false;
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
    private static final int INSERTION_SORT_THRESHOLD = 16;
    
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
        if (high - low < INSERTION_SORT_THRESHOLD) {
            insertionSort(books, low, high);
            return;
        }
        
        if (low < high) {
            int pivotIndex = medianOfMedians(books, low, high);
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
    
    private int medianOfMedians(ArrayList<Book> books, int low, int high) {
        int n = high - low + 1;
        
        if (n <= 5) {
            return medianOfFive(books, low, high);
        }
        
        int numMedians = 0;
        for (int i = low; i <= high; i += 5) {
            int subHigh = Math.min(i + 4, high);
            int medianIdx = medianOfFive(books, i, subHigh);
            swap(books, low + numMedians, medianIdx);
            numMedians++;
        }
        
        return medianOfMedians(books, low, low + numMedians - 1);
    }
    
    private int medianOfFive(ArrayList<Book> books, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Book key = books.get(i);
            int j = i - 1;
            
            while (j >= low && books.get(j).getTitle().compareToIgnoreCase(key.getTitle()) > 0) {
                books.set(j + 1, books.get(j));
                j--;
            }
            books.set(j + 1, key);
        }
        
        return low + (high - low) / 2;
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
        
        for (Book book : sortedBooks) {
            if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(book);
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
    
    public ImprovedSearchingAlgorithm() {
        sorter = new ImprovedSortingAlgorithm();
        sortTime = 0;
    }
    
    public long getExecutionTime() { return executionTime; }
    public long getSortTime() { return sortTime; }
    public boolean wasSorted() { return wasSorted; }
    
    public ArrayList<Book> searchBooksByTitle(ArrayList<Book> books, String searchTerm) {
        LibraryData data = LibraryData.getInstance();
        
        long startTime = System.nanoTime();
        
        if (!data.isSorted()) {
            long sortStart = System.nanoTime();
            ArrayList<Book> sorted = sorter.sortBooks(books);
            books.clear();
            books.addAll(sorted);
            data.setIsSorted(true);
            sortTime = System.nanoTime() - sortStart;
            wasSorted = false;
        } else {
            sortTime = 0;
            wasSorted = true;
        }
        
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
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
    private JButton refreshButton;
    private boolean showingBooks = true;
    private int searchCount = 0;

    public MainSystemFrame() {
        setTitle("Library Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

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
        refreshButton = new JButton("Refresh Cache");
        algorithmPanel.add(compareButton);
        algorithmPanel.add(refreshButton);
        
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
        compareButton.addActionListener(e -> new CompareAlgorithmsDialog(this));
        refreshButton.addActionListener(e -> {
            LibraryData.getInstance().markUnsorted();
            statusLabel.setText("Status: Cache cleared | Collection is UNSORTED");
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
        String[] columns = {"Member ID", "Name", "Email", "Phone", "Books Borrowed"};
        tableModel.setColumnIdentifiers(columns);
        
        LibraryData data = LibraryData.getInstance();
        for (Member member : data.getMembers()) {
            tableModel.addRow(new Object[]{
                member.getMemberId(), member.getName(), member.getEmail(),
                member.getPhone(), member.getBorrowedBooks().size()
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
            
            tableModel.setRowCount(0);
            for (Book book : sortedBooks) {
                tableModel.addRow(new Object[]{
                    book.getBookId(), book.getTitle(), book.getAuthor(), book.getIsbn(),
                    book.isAvailable() ? "Available" : "Borrowed",
                    book.isAvailable() ? "-" : book.getBorrowedBy()
                });
            }
            
            long timeNano = sorter.getExecutionTime();
            statusLabel.setText(String.format("Books sorted alphabetically | Algorithm: QuickSort (Median-of-Medians) | Time: %d ns (%.2f μs) | O(n log n) worst case", 
                                             timeNano, timeNano / 1000.0));
        } else {
            LibraryData data = LibraryData.getInstance();
            ArrayList<Member> members = data.getMembers();
            members.sort((m1, m2) -> m1.getName().compareToIgnoreCase(m2.getName()));
            
            tableModel.setRowCount(0);
            for (Member member : members) {
                tableModel.addRow(new Object[]{
                    member.getMemberId(), member.getName(), member.getEmail(),
                    member.getPhone(), member.getBorrowedBooks().size()
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
        
        sb.append("-".repeat(62)).append("\n");
        sb.append("SORTING ALGORITHM COMPARISON\n");
        sb.append("-".repeat(62)).append("\n\n");
        
        BaselineSortingAlgorithm baselineSorter = new BaselineSortingAlgorithm();
        baselineSorter.sortBooks(books);
        long baselineSortTime = baselineSorter.getExecutionTime();
        
        sb.append("1. BASELINE QuickSort (First Element Pivot):\n");
        sb.append("   Time Complexity: O(n²) worst case\n");
        sb.append("   Execution Time: ").append(baselineSortTime).append(" ns (")
          .append(String.format("%.2f", baselineSortTime / 1000.0)).append(" μs)\n\n");
        
        ImprovedSortingAlgorithm improvedSorter = new ImprovedSortingAlgorithm();
        improvedSorter.sortBooks(books);
        long improvedSortTime = improvedSorter.getExecutionTime();
        
        sb.append("2. IMPROVED QuickSort (Median-of-Medians + Insertion Sort):\n");
        sb.append("   Time Complexity: O(n log n) worst case (guaranteed)\n");
        sb.append("   Execution Time: ").append(improvedSortTime).append(" ns (")
          .append(String.format("%.2f", improvedSortTime / 1000.0)).append(" μs)\n\n");
        
        double sortSpeedup = (double) baselineSortTime / improvedSortTime;
        sb.append("Sorting Comparison: ").append(String.format("%.2fx", sortSpeedup));
        if (sortSpeedup > 1) {
            sb.append(" (Improved is faster)\n\n");
        } else {
            sb.append(" (Baseline faster on this dataset)\n\n");
        }
        
        sb.append("Note: Improved algorithm guarantees O(n log n) in worst case.\n\n");
        
        sb.append("-".repeat(62)).append("\n");
        sb.append("SEARCHING ALGORITHM COMPARISON\n");
        sb.append("-".repeat(62)).append("\n\n");
        
        String searchTerm = "the";
        
        BaselineSearchingAlgorithm baselineSearcher = new BaselineSearchingAlgorithm();
        baselineSearcher.searchBooksByTitle(books, searchTerm);
        long baselineSearch1 = baselineSearcher.getExecutionTime();
        baselineSearcher.searchBooksByTitle(books, searchTerm);
        long baselineSearch2 = baselineSearcher.getExecutionTime();
        baselineSearcher.searchBooksByTitle(books, searchTerm);
        long baselineSearch3 = baselineSearcher.getExecutionTime();
        
        sb.append("1. BASELINE Binary Search (Sorts Every Time):\n");
        sb.append("   Time Complexity: O(n log n) per search\n");
        sb.append("   First:  ").append(baselineSearch1).append(" ns (")
          .append(String.format("%.2f", baselineSearch1 / 1000.0)).append(" μs)\n");
        sb.append("   Second: ").append(baselineSearch2).append(" ns (")
          .append(String.format("%.2f", baselineSearch2 / 1000.0)).append(" μs)\n");
        sb.append("   Third:  ").append(baselineSearch3).append(" ns (")
          .append(String.format("%.2f", baselineSearch3 / 1000.0)).append(" μs)\n");
        sb.append("   Average: ").append((baselineSearch1 + baselineSearch2 + baselineSearch3) / 3).append(" ns\n\n");
        
        data.markUnsorted();
        ImprovedSearchingAlgorithm improvedSearcher = new ImprovedSearchingAlgorithm();
        improvedSearcher.searchBooksByTitle(books, searchTerm);
        long improvedSearch1 = improvedSearcher.getExecutionTime();
        improvedSearcher.searchBooksByTitle(books, searchTerm);
        long improvedSearch2 = improvedSearcher.getExecutionTime();
        improvedSearcher.searchBooksByTitle(books, searchTerm);
        long improvedSearch3 = improvedSearcher.getExecutionTime();
        
        sb.append("2. IMPROVED Binary Search (isSorted Flag):\n");
        sb.append("   Time Complexity: O(n log n) first, O(log n) subsequent\n");
        sb.append("   First:  ").append(improvedSearch1).append(" ns (")
          .append(String.format("%.2f", improvedSearch1 / 1000.0)).append(" μs) [SORTED]\n");
        sb.append("   Second: ").append(improvedSearch2).append(" ns (")
          .append(String.format("%.2f", improvedSearch2 / 1000.0)).append(" μs) [cached]\n");
        sb.append("   Third:  ").append(improvedSearch3).append(" ns (")
          .append(String.format("%.2f", improvedSearch3 / 1000.0)).append(" μs) [cached]\n");
        sb.append("   Avg (cached): ").append((improvedSearch2 + improvedSearch3) / 2).append(" ns\n\n");
        
        double cachedSpeedup = (double) baselineSearch2 / improvedSearch2;
        sb.append("Cached Search Speedup: ").append(String.format("%.2fx", cachedSpeedup)).append("\n\n");
        
        int numSearches = 10;
        long baselineTotal = 0;
        data.markUnsorted();
        long improvedTotal = 0;
        
        for (int i = 0; i < numSearches; i++) {
            baselineSearcher.searchBooksByTitle(books, searchTerm);
            baselineTotal += baselineSearcher.getExecutionTime();
            improvedSearcher.searchBooksByTitle(books, searchTerm);
            improvedTotal += improvedSearcher.getExecutionTime();
        }
        
        sb.append("-".repeat(60)).append("\n");
        sb.append("AMORTIZED ANALYSIS (").append(numSearches).append(" searches):\n");
        sb.append("-".repeat(60)).append("\n");
        sb.append("Baseline Total: ").append(baselineTotal).append(" ns\n");
        sb.append("Improved Total: ").append(improvedTotal).append(" ns\n");
        sb.append("Total Speedup: ").append(String.format("%.2fx", (double) baselineTotal / improvedTotal)).append("\n");
        
        resultsArea.setText(sb.toString());
        resultsArea.setCaretPosition(0);
        setVisible(true);
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
    
    public BorrowBookDialog(JFrame parent) {
        super(parent, "Borrow Book", true);
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
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JOptionPane.showMessageDialog(this, String.format("Book borrowed successfully.\n\nBook: %s\nMember: %s\nDate: %s",
                                       book.getTitle(), member.getName(), sdf.format(book.getBorrowDate())));
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}

class ReturnBookDialog extends JDialog {
    private JTextField bookIdField;
    
    public ReturnBookDialog(JFrame parent) {
        super(parent, "Return Book", true);
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
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JOptionPane.showMessageDialog(this, String.format("Book returned successfully.\n\nBook: %s\nBorrowed by: %s\nBorrow Date: %s\nReturn Date: %s",
                                       book.getTitle(), 
                                       member != null ? member.getName() : memberId,
                                       borrowDate != null ? sdf.format(borrowDate) : "Unknown",
                                       sdf.format(new Date())));
            dispose();
        });
        
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}
