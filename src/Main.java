import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Register Member");
            System.out.println("4. Remove Member");
            System.out.println("5. Lend Book");
            System.out.println("6. Return Book");
            System.out.println("7. Display Book Names");
            System.out.println("8. Display Member Names");
            System.out.println("9. View Lending Information");
            System.out.println("10. Display Overdue Books");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    library.addBook(bookId, title, author);
                    break;
                case 2:
                    System.out.print("Enter Book ID to remove: ");
                    int removeBookId = scanner.nextInt();
                    library.removeBook(removeBookId);
                    break;
                case 3:
                    System.out.print("Enter Member ID: ");
                    int memberId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Member Name: ");
                    String memberName = scanner.nextLine();
                    library.registerMember(memberId, memberName);
                    break;
                case 4:
                    System.out.print("Enter Member ID to remove: ");
                    int removeMemberId = scanner.nextInt();
                    library.removeMember(removeMemberId);
                    break;
                case 5:
                    library.lendBook();
                    break;
                case 6:
                    System.out.print("Enter Book ID to return: ");
                    int returnBookId = scanner.nextInt();
                    System.out.print("Enter Member ID: ");
                    int returnMemberId = scanner.nextInt();
                    library.returnBook(returnBookId, returnMemberId);
                    break;
                case 7:
                    library.displayBookNames();
                    break;
                case 8:
                    library.displayMemberNames();
                    break;
                case 9:
                    library.viewLendingInformation();
                    break;
                case 10:
                    library.displayOverdueBooks();
                    break;
                case 11:
                    System.out.println("Exiting Library Management System.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
