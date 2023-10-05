import java.text.SimpleDateFormat;
import java.util.*;

public class Library {
    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();
    private List<LendingRecord> lendingRecords = new ArrayList<>();

    public void addBook(int bookId, String title, String author) {
    try {
        if (books.containsKey(bookId)) {
            throw new IllegalArgumentException("Book with the same ID already exists.");
        }
        if (title.isEmpty() || author.isEmpty()) {
            throw new IllegalArgumentException("Title and author must not be empty.");
        }
        
        // Add the book
        Book book = new Book(bookId, title, author);
        books.put(bookId, book);
        System.out.println("Book added Successfully.");
    } catch (IllegalArgumentException e) {
        System.err.println("Error: " + e.getMessage());
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

    public void registerMember(int memberId, String name) {
        Member member = new Member(memberId, name);
        members.put(memberId, member);
        System.out.println("Member registered successfully.");
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
        
        // Input book ID
        System.out.print("Enter Book ID to lend: ");
        int lendBookId = scanner.nextInt();
        
        // Check if the book exists
        if (!books.containsKey(lendBookId)) {
            System.out.println("Book not found.");
            return;
        }
        
        Book book = books.get(lendBookId);
        
        // Check if the book is available
        if (!book.isAvailable()) {
            System.out.println("Book is already checked out.");
            return;
        }
        
        // Input member ID
        System.out.print("Enter Member ID: ");
        int lendMemberId = scanner.nextInt();
        
        // Check if the member exists
        if (!members.containsKey(lendMemberId)) {
            System.out.println("Member not found.");
            return;
        }
        
        // Input and validate the due date
        Date dueDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        while (dueDate == null) {
            System.out.print("Enter Due Date (yyyy-MM-dd): ");
            String dueDateStr = scanner.next();
            
            try {
                dueDate = sdf.parse(dueDateStr);
                
                // Check if the due date is in the future
                if (dueDate.before(new Date())) {
                    System.out.println("Due date cannot be a past date.");
                    dueDate = null; // Reset dueDate to null to re-enter the date
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
        
        // Record the lending
        LendingRecord record = new LendingRecord(lendBookId, lendMemberId, dueDate);
        lendingRecords.add(record);
        
        // Update the book's availability
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
            long daysOverdue = (record.getReturnDate().getTime() - record.getDueDate().getTime())/ (24 * 60 * 60 * 1000);
            if (daysOverdue > 0) {
                double fine = daysOverdue <= 7 ? 50 * daysOverdue : (7 * 50) + ((daysOverdue - 7) * 100);
                System.out.println("Book ID: " + record.getBookId() + ", Member ID: " + record.getMemberId()
                        + ", Days Overdue: " + daysOverdue + ", Fine: Rs. " + fine);
            }
        }

        if (!overdueBooksFound) {
        System.out.println("There are no overdue books.");
        }
    }
}
