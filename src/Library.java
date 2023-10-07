import java.text.SimpleDateFormat;
import java.util.*;

public class Library {
    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();
    private List<LendingRecord> lendingRecords = new ArrayList<>();

    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter Book ID: ");
            int bookId = scanner.nextInt();
            
            if (books.containsKey(bookId)) {
                System.out.println("Book with the same ID already exists.");
            } else {
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter Author: ");
                String author = scanner.nextLine();
                
                if (title.isEmpty() || author.isEmpty()) {
                    System.out.println("Title and author must not be empty.");
                } else {
                    // Add the book
                    Book book = new Book(bookId, title, author);
                    books.put(bookId, book);
                    System.out.println("Book added successfully.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid Book ID.");
        }
    }
    


    public void removeBook(int bookId) {
        if (books.containsKey(bookId)) {
            books.remove(bookId);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found in the library.");
        }
    }

    public void registerMember() {
    Scanner scanner = new Scanner(System.in);
    
        try {
            System.out.print("Enter Member ID: ");
            int memberId = scanner.nextInt();
            
            if (members.containsKey(memberId)) {
                System.out.println("Member with the same ID already exists.");
            } else {
                scanner.nextLine(); // Consume the newline character
                System.out.print("Enter Name: ");
                String name = scanner.nextLine();
                
                if (name.isEmpty()) {
                    System.out.println("Name must not be empty.");
                } else {
                    // Register the member
                    Member member = new Member(memberId, name);
                    members.put(memberId, member);
                    System.out.println("Member registered successfully.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid Member ID.");
        }
    }


    public void removeMember(int memberId) {
        if (members.containsKey(memberId)) {
            members.remove(memberId);
            System.out.println("Member removed successfully.");
        } else {
            System.out.println("Member not found in the records.");
        }
    }

    public void lendBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID to lend: ");
        int lendBookId = scanner.nextInt();
        
        // Check whether book exists
        if (!books.containsKey(lendBookId)) {
            System.out.println("Book not found.");
            return;
        }
        
        Book book = books.get(lendBookId);
        
        // Check whether book has checked out 
        if (!book.isAvailable()) {
            System.out.println("Book is already checked out.");
            return;
        }
        
        // Input member ID
        System.out.print("Enter Member ID: ");
        int lendMemberId = scanner.nextInt();
        
        // Check whether member exists
        if (!members.containsKey(lendMemberId)) {
            System.out.println("Member not found.");
            return;
        }
        
        // Input due date
        Date dueDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        while (dueDate == null) {
            System.out.print("Enter Due Date (yyyy-MM-dd): ");
            String dueDateStr = scanner.next();
            
            try {
                dueDate = sdf.parse(dueDateStr);
                
                // Check due date
                if (dueDate.before(new Date())) {
                    System.out.println("Due date cannot be a past date.");
                    dueDate = null; // Reset dueDate to null
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
        
        // Record the lending
        LendingRecord record = new LendingRecord(lendBookId, lendMemberId, dueDate);
        lendingRecords.add(record);
        
        // Update book's availability
        book.setAvailable(false);
        
        System.out.println("Book lent successfully.");
    }

    public void returnBook(int bookId, int memberId) {
        Scanner scanner = new Scanner(System.in);
        for (LendingRecord record : lendingRecords) {
            if (record.getBookId() == bookId && record.getMemberId() == memberId) {
                Book book = books.get(bookId);
                book.setAvailable(true);
                Date returnDate = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                while (returnDate == null) {
                    System.out.print("Enter Return Date (yyyy-MM-dd): ");
                    String dueDateStr = scanner.nextLine();
                    try {
                        returnDate = sdf.parse(dueDateStr);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
                    }
                }
                record.setReturnDate(returnDate);
                if (returnDate.before(record.getDueDate()) || returnDate.equals(record.getDueDate())) {
                    // Remove the lending record if the return date is before or equal to the due date
                    lendingRecords.remove(record);}
                System.out.println("Book returned successfully.");
                return;
            }
        }
        System.out.println("Book or member not found in lending records.");
    }

    public void displayBookNames() {
        System.out.println("Books in the library:");
        for (Book book : books.values()) {
            System.out.println(book.getTitle());
        }
    }

    public void displayMemberNames() {
        System.out.println("Registered members:");
        for (Member member : members.values()) {
            System.out.println(member.getName());
        }
    }

    public void viewLendingInformation() {
        System.out.println("Lending information:");
        for (LendingRecord record : lendingRecords) {
            System.out.println("Book ID: " + record.getBookId() + ", Member ID: " + record.getMemberId());
        }
    }

    public void displayOverdueBooks() {
        System.out.println("Overdue books:");
        boolean overdueBooksFound = false;
        for (LendingRecord record : lendingRecords) {
            if (record.getReturnDate()!=null) {
                long daysOverdue = (record.getReturnDate().getTime() - record.getDueDate().getTime())/ (24 * 60 * 60 * 1000);
                if (daysOverdue > 0) {
                    double fine = daysOverdue <= 7 ? 50 * daysOverdue : (7 * 50) + ((daysOverdue - 7) * 100);
                    System.out.println("Book ID: " + record.getBookId() + ", Member ID: " + record.getMemberId()
                            + ", Days Overdue: " + daysOverdue + ", Fine: Rs. " + fine);
    
                    // Set overdueBooksFound to true when overdue books are found
                    overdueBooksFound = true;
                }
            }
        }

        if (!overdueBooksFound) {
        System.out.println("There are no overdue books.");
        }
    }

    public void searchBookInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search Book Information");
        System.out.println("1. Search by Book ID");
        System.out.println("2. Search by Title");
        System.out.println("3. Search by Author");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                // Search by Book ID
                System.out.print("Enter Book ID: ");
                int bookId = scanner.nextInt();
                Book bookById = books.get(bookId);
                
                if (bookById != null) {
                    System.out.println("Book ID: " + bookById.getBookId() + ", Title: " + bookById.getTitle()+", Author: " + bookById.getAuthor());
                } else {
                    System.out.println("Book not found with ID: " + bookId);
                }
                break;
    
            case 2:
                // Search by Title
                System.out.print("Enter Title: ");
                String title = scanner.next();
                boolean titleFound = false;
                
                for (Book book : books.values()) {
                    if (book.getTitle().equalsIgnoreCase(title)) {
                        System.out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle() +", Author: " + book.getAuthor());
                        titleFound = true;
                    }
                }
                
                if (!titleFound) {
                    System.out.println("No books found with the title: " + title);
                }
                break;
    
            case 3:
                // Search by Author
                System.out.print("Enter Author: ");
                String author = scanner.next();
                boolean authorFound = false;
                
                for (Book book : books.values()) {
                    if (book.getAuthor().equalsIgnoreCase(author)) {
                        System.out.println("Book ID: " + book.getBookId() + ", Title: " + book.getTitle() +", Author: " + book.getAuthor());
                        authorFound = true;
                    }
                }
                
                if (!authorFound) {
                    System.out.println("No books found by the author: " + author);
                }
                break;
    
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                break;
        }
    }

    public void searchMemberInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Search Member Information");
        System.out.println("1. Search by Member ID");
        System.out.println("2. Search by Name");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                // Search by Member ID
                System.out.print("Enter Member ID: ");
                int memberId = scanner.nextInt();
                Member memberById = members.get(memberId);
                
                if (memberById != null) {
                    System.out.println("Member ID: " + memberById.getMemberId() + ", Name: " + memberById.getName());
                } else {
                    System.out.println("Member not found with ID: " + memberId);
                }
                break;
    
            case 2:
                // Search by Name
                System.out.print("Enter Name: ");
                String name = scanner.next();
                boolean nameFound = false;
                
                for (Member member : members.values()) {
                    if (member.getName().equalsIgnoreCase(name)) {
                        System.out.println("Member ID: " + member.getMemberId() + ", Name: " + member.getName());
                        nameFound = true;
                    }
                }
                
                if (!nameFound) {
                    System.out.println("No members found with the name: " + name);
                }
                break;
    
            case 3:
                // Implement other search criteria options as needed
                break;
    
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                break;
        }
    }
    
    
}
