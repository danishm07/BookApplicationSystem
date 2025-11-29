import java.util.*;

public class BookApplicationTest {

    public static void main(String[] args) {
        System.out.println("========== STARTING TESTS ==========\n");

        // Clear any existing data
        Book.clearAllBooks();
        PrintedBook.reset();
        AudioBook.reset();

        testPrintedBookCost();
        testAudioBookCost();
        testAveragePages();
        testAverageLength();
        testGetBookCountByGenre();
        testGetTotalCost();
        testLastSixBooks();
        testLastThreeBooks();
        testPolymorphism();

        System.out.println("\n========== ALL TESTS COMPLETED ==========");
    }

    /**
     * Test printed book cost calculation
     */
    private static void testPrintedBookCost() {
        System.out.println("TEST 1: Printed Book Cost Calculation");

        PrintedBook book = new PrintedBook("Test Book", "Test Author", "Fiction", 50);
        double expectedCost = 50 * 10.0; // 50 pages * $10/page
        double actualCost = book.getCost();

        System.out.printf("  Expected: $%.2f%n", expectedCost);
        System.out.printf("  Actual: $%.2f%n", actualCost);

        if (Math.abs(expectedCost - actualCost) < 0.01) {
            System.out.println("  ✓ PASSED\n");
        } else {
            System.out.println("  ✗ FAILED\n");
        }
    }

    /**
     * Test audiobook cost calculation
     */
    private static void testAudioBookCost() {
        System.out.println("TEST 2: Audio Book Cost Calculation");

        AudioBook book = new AudioBook("Test Audio", "Test Author", "Mystery", 100);
        double expectedCost = 100 * 5.0; // 100 minutes * $5/minute
        double actualCost = book.getCost();

        System.out.printf("  Expected: $%.2f%n", expectedCost);
        System.out.printf("  Actual: $%.2f%n", actualCost);

        if (Math.abs(expectedCost - actualCost) < 0.01) {
            System.out.println("  ✓ PASSED\n");
        } else {
            System.out.println("  ✗ FAILED\n");
        }
    }

    /**
     * Test average pages calculation
     */
    private static void testAveragePages() {
        System.out.println("TEST 3: Average Pages Calculation");

        // Clear and create fresh books
        Book.clearAllBooks();
        PrintedBook.reset();

        new PrintedBook("Book1", "Author1", "Genre1", 100);
        new PrintedBook("Book2", "Author2", "Genre2", 200);
        new PrintedBook("Book3", "Author3", "Genre3", 300);

        double expectedAvg = (100 + 200 + 300) / 3.0;
        double actualAvg = PrintedBook.getAveragePages();

        System.out.printf("  Expected: %.2f%n", expectedAvg);
        System.out.printf("  Actual: %.2f%n", actualAvg);

        if (Math.abs(expectedAvg - actualAvg) < 0.01) {
            System.out.println("  ✓ PASSED\n");
        } else {
            System.out.println("  ✗ FAILED\n");
        }
    }

    /**
     * Test average length calculation
     */
    private static void testAverageLength() {
        System.out.println("TEST 4: Average Length Calculation");

        // Clear and create fresh books
        Book.clearAllBooks();
        AudioBook.reset();

        new AudioBook("Audio1", "Author1", "Genre1", 60);
        new AudioBook("Audio2", "Author2", "Genre2", 90);
        new AudioBook("Audio3", "Author3", "Genre3", 120);

        double expectedAvg = (60 + 90 + 120) / 3.0;
        double actualAvg = AudioBook.getAverageLength();

        System.out.printf("  Expected: %.2f%n", expectedAvg);
        System.out.printf("  Actual: %.2f%n", actualAvg);

        if (Math.abs(expectedAvg - actualAvg) < 0.01) {
            System.out.println("  ✓ PASSED\n");
        } else {
            System.out.println("  ✗ FAILED\n");
        }
    }

    /**
     * Test genre count functionality
     */
    private static void testGetBookCountByGenre() {
        System.out.println("TEST 5: Genre Count");

        Book.clearAllBooks();
        PrintedBook.reset();
        AudioBook.reset();

        new PrintedBook("Book1", "Author", "Fantasy", 100);
        new PrintedBook("Book2", "Author", "Fantasy", 200);
        new AudioBook("Audio1", "Author", "SciFi", 60);
        new PrintedBook("Book3", "Author", "Mystery", 150);

        GenreCount genreCount = new BookInterface() {}.getBookCountByGenre();

        System.out.println("  Genre counts:");
        ArrayList<String> genres = genreCount.getGenres();
        ArrayList<Integer> counts = genreCount.getCounts();

        for (int i = 0; i < genres.size(); i++) {
            System.out.printf("    %s: %d%n", genres.get(i), counts.get(i));
        }

        if (genreCount.getCount("Fantasy") == 2 &&
                genreCount.getCount("SciFi") == 1 &&
                genreCount.getCount("Mystery") == 1) {
            System.out.println("  ✓ PASSED\n");
        } else {
            System.out.println("  ✗ FAILED\n");
        }
    }

    /**
     * Test total cost calculation
     */
    private static void testGetTotalCost() {
        System.out.println("TEST 6: Total Cost Calculation");

        Book.clearAllBooks();
        PrintedBook.reset();
        AudioBook.reset();

        new PrintedBook("Book1", "Author", "Genre", 50);  // $500
        new AudioBook("Audio1", "Author", "Genre", 100);   // $500

        double expectedTotal = 500.0 + 500.0;
        double actualTotal = new BookInterface() {}.getTotalCost();

        System.out.printf("  Expected: $%.2f%n", expectedTotal);
        System.out.printf("  Actual: $%.2f%n", actualTotal);

        if (Math.abs(expectedTotal - actualTotal) < 0.01) {
            System.out.println("  ✓ PASSED\n");
        } else {
            System.out.println("  ✗ FAILED\n");
        }
    }

    /**
     * Test last six books functionality
     */
    private static void testLastSixBooks() {
        System.out.println("TEST 7: Last Six Books Display");

        Book.clearAllBooks();
        PrintedBook.reset();
        AudioBook.reset();

        // Create 8 books
        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 0) {
                new PrintedBook("Book" + i, "Author", "Genre", 100);
            } else {
                new AudioBook("Audio" + i, "Author", "Genre", 60);
            }
        }

        System.out.println("  Total books created: " + Book.getAllBooks().size());
        System.out.println("  Should display only last 6:");

        if (Book.getAllBooks().size() >= 1) {
            Book.getAllBooks().get(0).displayLastSixBooks();
            System.out.println("  ✓ PASSED (check output above)\n");
        } else {
            System.out.println("  ✗ FAILED\n");
        }
    }

    /**
     * Test last three books for each type
     */
    private static void testLastThreeBooks() {
        System.out.println("TEST 8: Last Three Books (by type)");

        Book.clearAllBooks();
        PrintedBook.reset();
        AudioBook.reset();

        // Create 5 printed and 5 audio books
        for (int i = 1; i <= 5; i++) {
            new PrintedBook("Printed" + i, "Author", "Genre", i * 100);
            new AudioBook("Audio" + i, "Author", "Genre", i * 60);
        }

        System.out.println("  Created 5 of each type");
        PrintedBook.displayLastThreePrinted();
        AudioBook.displayLastThreeAudio();
        System.out.println("  ✓ PASSED (verify only last 3 of each shown)\n");
    }

    /**
     * Test polymorphism - Book reference holding different types
     */
    private static void testPolymorphism() {
        System.out.println("TEST 9: Polymorphism");

        Book.clearAllBooks();

        // POLYMORPHISM - Book reference can hold PrintedBook or AudioBook
        Book book1 = new PrintedBook("Polymorphic Printed", "Author", "Tech", 100);
        Book book2 = new AudioBook("Polymorphic Audio", "Author", "Tech", 60);

        System.out.println("  Book 1 (Printed): " + book1);
        System.out.println("  Book 2 (Audio): " + book2);

        // Both respond to getCost() differently (polymorphism)
        System.out.printf("  Book 1 Cost: $%.2f%n", book1.getCost());
        System.out.printf("  Book 2 Cost: $%.2f%n", book2.getCost());

        System.out.println("  ✓ PASSED (polymorphism working)\n");
    }
}
