import java.io.IOException;
import java.util.*;


public class BookApplication {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayWelcome();

        boolean running = true;

        while (running) {
            displayMenu();

            try {
                int choice = getUserChoice();

                switch (choice) {
                    case 1:
                        addPrintedBook();
                        break;
                    case 2:
                        addAudioBook();
                        break;
                    case 3:
                        displayLastSixBooks();
                        break;
                    case 4:
                        displayStatistics();
                        break;
                    case 5:
                        PrintedBook.displayLastThreePrinted();
                        break;
                    case 6:
                        AudioBook.displayLastThreeAudio();
                        break;
                    case 7:
                        displayGenreBreakdown();
                        break;
                    case 8:
                        saveToFile();
                        break;
                    case 9:
                        loadFromFile();
                        break;
                    case 10:
                        running = false;
                        displayGoodbye();
                        break;
                    default:
                        System.out.println("✗ Invalid choice. Please enter 1-10.");
                }

            } catch (InputMismatchException e) {
                System.out.println("✗ Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            } catch (Exception e) {
                System.out.println("✗ An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }

    //welcome message
    private static void displayWelcome() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║    WELCOME TO BOOK APPLICATION       ║");
        System.out.println("║    Track Your Reading Journey!       ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
    }

    //main menu
    private static void displayMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1.  Add Printed Book");
        System.out.println("2.  Add Audio Book");
        System.out.println("3.  Display Last 6 Books");
        System.out.println("4.  Display Statistics");
        System.out.println("5.  Display Last 3 Printed Books");
        System.out.println("6.  Display Last 3 Audio Books");
        System.out.println("7.  Display Genre Breakdown");
        System.out.println("8.  Save Books to File");
        System.out.println("9.  Load Books from File");
        System.out.println("10. Exit");
        System.out.println("================================");
        System.out.print("Enter your choice: ");
    }

    /**
     * Get user's menu choice with validation
     */
    private static int getUserChoice() {
        return scanner.nextInt();
    }


    private static void addPrintedBook() {
        scanner.nextLine();

        System.out.println("\n----- Add Printed Book -----");

        try {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();

            System.out.print("Enter author: ");
            String author = scanner.nextLine();

            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();

            System.out.print("Enter number of pages: ");
            int pages = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            // Input validation
            if (pages <= 0) {
                System.out.println("✗ Number of pages must be positive!");
                return;
            }

            // Create book
            Book book = new PrintedBook(title, author, genre, pages);

            System.out.println("✓ Successfully added: " + book);

        } catch (InputMismatchException e) {
            System.out.println("✗ Invalid input! Pages must be a number.");
            scanner.nextLine(); // Clear buffer
        }
    }


    private static void addAudioBook() {
        scanner.nextLine(); // Clear buffer

        System.out.println("\n----- Add Audio Book -----");

        try {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();

            System.out.print("Enter author: ");
            String author = scanner.nextLine();

            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();

            System.out.print("Enter length in minutes: ");
            int length = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            // Input validation
            if (length <= 0) {
                System.out.println("✗ Length must be positive!");
                return;
            }

            // Create book - demonstrates INHERITANCE and POLYMORPHISM
            Book book = new AudioBook(title, author, genre, length);

            System.out.println("✓ Successfully added: " + book);

        } catch (InputMismatchException e) {
            System.out.println("✗ Invalid input! Length must be a number.");
            scanner.nextLine(); // Clear buffer
        }
    }


    private static void displayLastSixBooks() {
        // Create a temporary book to access interface method
        // Demonstrates POLYMORPHISM - interface method works with any Book type
        if (!Book.getAllBooks().isEmpty()) {
            Book.getAllBooks().get(0).displayLastSixBooks();
        } else {
            System.out.println("\nNo books in the system yet.");
        }
    }


    private static void displayStatistics() {
        System.out.println("\n========== BOOK STATISTICS ==========");

        int totalBooks = Book.getAllBooks().size();
        System.out.println("Total Books: " + totalBooks);
        System.out.println("Printed Books: " + PrintedBook.getPrintedBookCount());
        System.out.println("Audio Books: " + AudioBook.getAudioBookCount());

        System.out.println("\n----- Cost Information -----");
        System.out.printf("Total Cost (All Books): $%.2f%n",
                new BookInterface() {}.getTotalCost());
        System.out.printf("Total Cost (Printed): $%.2f%n",
                PrintedBook.getTotalPrintedBooksCost());
        System.out.printf("Total Cost (Audio): $%.2f%n",
                AudioBook.getTotalAudioBookCost());

        System.out.println("\n----- Averages -----");
        if (PrintedBook.getPrintedBookCount() > 0) {
            System.out.printf("Average Pages (Printed Books): %.2f%n",
                    PrintedBook.getAveragePages());
        } else {
            System.out.println("Average Pages: N/A (no printed books)");
        }

        if (AudioBook.getAudioBookCount() > 0) {
            System.out.printf("Average Length (Audio Books): %.2f minutes%n",
                    AudioBook.getAverageLength());
        } else {
            System.out.println("Average Length: N/A (no audio books)");
        }

        System.out.println("=====================================\n");
    }


    private static void displayGenreBreakdown() {
        GenreCount genreCount = new BookInterface() {}.getBookCountByGenre();

        System.out.println("\n===== BOOKS BY GENRE =====");

        if (genreCount.isEmpty()) {
            System.out.println("No books in the system.");
        } else {
            genreCount.display();
        }

        System.out.println("==========================\n");
    }


    private static void saveToFile() {
        scanner.nextLine(); // Clear buffer

        System.out.print("\nEnter filename to save to (e.g., books.txt): ");
        String filename = scanner.nextLine();

        try {
            FileManager.writeToFile(filename, Book.getAllBooks());
        } catch (IOException e) {
            System.out.println("✗ Failed to save file: " + e.getMessage());
        }
    }


    private static void loadFromFile() {
        scanner.nextLine(); // Clear buffer

        System.out.print("\nEnter filename to load from (e.g., books.txt): ");
        String filename = scanner.nextLine();

        if (!FileManager.fileExists(filename)) {
            System.out.println("✗ File does not exist: " + filename);
            return;
        }

        System.out.print("⚠ This will replace current books. Continue? (yes/no): ");
        String confirm = scanner.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Load cancelled.");
            return;
        }

        try {
            // Clear existing books
            Book.clearAllBooks();
            PrintedBook.reset();
            AudioBook.reset();

            // Load from file
            List<Book> loadedBooks = FileManager.readFromFile(filename);

            System.out.println("✓ Loaded " + loadedBooks.size() + " books successfully!");

        } catch (IOException e) {
            System.out.println("✗ Failed to load file: " + e.getMessage());
        }
    }


    private static void displayGoodbye() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   Thank you for using Book App!      ║");
        System.out.println("║   Happy Reading! 📚                   ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
    }
}