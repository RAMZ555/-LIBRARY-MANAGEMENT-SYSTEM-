package Main;

import model.Book;
import model.User;
import service.LibraryService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Scanner;
import java.util.Set;

 public class Main {

    public static void main(String[] args) {
        // Check file permissions
        checkFilePermissions();

        LibraryService libraryService = new LibraryService();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Library Management System");
                System.out.println("1. Add Book");
                System.out.println("2. Add User");
                System.out.println("3. Issue Book");
                System.out.println("4. Return Book");
                System.out.println("5. View All Books");
                System.out.println("6. View All Users");
                System.out.println("7. View All Transactions");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        addBook(scanner, libraryService);
                        break;
                    case 2:
                        addUser(scanner, libraryService);
                        break;
                    case 3:
                        issueBook(scanner, libraryService);
                        break;
                    case 4:
                        returnBook(scanner, libraryService);
                        break;
                    case 5:
                        viewAllBooks(libraryService);
                        break;
                    case 6:
                        viewAllUsers(libraryService);
                        break;
                    case 7:
                        viewAllTransactions(libraryService);
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        }
    }

    private static void checkFilePermissions() {
        // Specify the path to the file
        Path filePath = Path.of("resources/bookstext.txt");

        try {
            // Get the file permissions
            Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(filePath);

            // Print the permissions
            System.out.println("File permissions: " + permissions);
        } catch (Exception e) {
            // Handle any exceptions
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void addBook(Scanner scanner, LibraryService libraryService) {
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        libraryService.addBook(new Book(bookId, title, author, true));
    }

    private static void addUser(Scanner scanner, LibraryService libraryService) {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        libraryService.addUser(new User(userId, name));
    }

    private static void issueBook(Scanner scanner, LibraryService libraryService) {
        System.out.print("Enter book ID to issue: ");
        int issueBookId = scanner.nextInt();
        System.out.print("Enter user ID: ");
        int issueUserId = scanner.nextInt();
        libraryService.issueBook(issueBookId, issueUserId);
    }

    private static void returnBook(Scanner scanner, LibraryService libraryService) {
        System.out.print("Enter book ID to return: ");
        int returnBookId = scanner.nextInt();
        System.out.print("Enter user ID: ");
        int returnUserId = scanner.nextInt();
        libraryService.returnBook(returnBookId, returnUserId);
    }

    private static void viewAllBooks(LibraryService libraryService) {
        System.out.println("All Books:");
        libraryService.getAllBooks().forEach(System.out::println);
    }

    private static void viewAllUsers(LibraryService libraryService) {
        System.out.println("All Users:");
        libraryService.getAllUsers().forEach(System.out::println);
    }

    private static void viewAllTransactions(LibraryService libraryService) {
        System.out.println("All Transactions:");
        libraryService.getAllTransactions().forEach(System.out::println);
    }
}
