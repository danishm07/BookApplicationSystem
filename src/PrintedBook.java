import java.util.*;



public class PrintedBook extends Book {

    private int numberOfPages;

    // Static fields to track all printed books
    private static int totalPages = 0;
    private static int printedBookCount = 0;
    private static Queue<PrintedBook> lastThreePrinted = new LinkedList<>();
    private static final double COST_PER_PAGE = 10.0;


    public PrintedBook(String title, String author, String genre, int numberOfPages) {
        super(title, author, genre); // INHERITANCE - call parent constructor
        this.numberOfPages = numberOfPages;

        // Update static counters
        totalPages += numberOfPages;
        printedBookCount++;

        // Store in last three
        storeBookInfo();
    }

    //caluclating cost
    @Override
    public double getCost() {
        return numberOfPages * COST_PER_PAGE;
    }

    //storing books in a queue of the last three printed
    @Override
    public void storeBookInfo() {
        if (lastThreePrinted.size() >= 3) {
            lastThreePrinted.poll(); // Remove oldest
        }
        lastThreePrinted.offer(this); // Add newest
    }


    public int getNumberOfPages() {
        return numberOfPages;
    }

    //calcualtes average amt of pages
    public static double getAveragePages() {
        if (printedBookCount == 0) {
            return 0.0;
        }
        return (double) totalPages / printedBookCount;
    }

    //calcualting total cost of all printed books
    public static double getTotalPrintedBooksCost() {
        double total = 0.0;
        for (Book book : Book.getAllBooks()) {
            if (book instanceof PrintedBook) { // POLYMORPHISM - type checking
                total += book.getCost();
            }
        }
        return total;
    }

    //displaying last three printed books
    public static void displayLastThreePrinted() {
        System.out.println("\n===== LAST 3 PRINTED BOOKS =====");

        if (lastThreePrinted.isEmpty()) {
            System.out.println("No printed books in the system.");
        } else {
            int count = 1;
            for (PrintedBook book : lastThreePrinted) {
                System.out.println(count + ". " + book.toDetailedString());
                count++;
            }
        }
        System.out.println("=================================\n");
    }

   
    public String toDetailedString() {
        return String.format("[PRINTED] %s by %s | Genre: %s | Pages: %d | Cost: $%.2f",
                getTitle(), getAuthor(), getGenre(), numberOfPages, getCost());
    }


    @Override
    public String toString() {
        return String.format("[PRINTED] %s by %s [%s] - %d pages - $%.2f",
                getTitle(), getAuthor(), getGenre(), numberOfPages, getCost());
    }

    //get count of printed books
    public static int getPrintedBookCount() {
        return printedBookCount;
    }

    //resetting static counters
    public static void reset() {
        totalPages = 0;
        printedBookCount = 0;
        lastThreePrinted.clear();
    }
}