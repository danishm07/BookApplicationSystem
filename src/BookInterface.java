import java.util.*;


public interface BookInterface {


     //Default method to display the last 6 books added to the system, works with any book type

    default void displayLastSixBooks() {
        List<Book> allBooks = Book.getAllBooks();

        System.out.println("\n========== LAST 6 BOOKS ADDED ==========");

        if (allBooks.isEmpty()) {
            System.out.println("No books in the system.");
            return;
        }

        // Get last 6 books (or fewer if less than 6 exist)
        int start = Math.max(0, allBooks.size() - 6);
        List<Book> lastSix = allBooks.subList(start, allBooks.size());

        // Display in reverse order (most recent first)
        for (int i = lastSix.size() - 1; i >= 0; i--) {
            Book book = lastSix.get(i);
            System.out.println((lastSix.size() - i) + ". " + book);
        }
        System.out.println("=========================================\n");
    }

    /**
     * Returns the count of books in each genre using parallel ArrayLists
     * @return GenreCount object containing genres and their counts
     */
    default GenreCount getBookCountByGenre() {
        GenreCount genreCount = new GenreCount();

        for (Book book : Book.getAllBooks()) {
            String genre = book.getGenre();
            genreCount.addGenre(genre);
        }

        return genreCount;
    }

    default double getTotalCost() {
        double total = 0.0;

        for (Book book : Book.getAllBooks()) {
            total += book.getCost();
        }

        return total;
    }
}


class GenreCount {
    private ArrayList<String> genres;
    private ArrayList<Integer> counts;

    public GenreCount() {
        genres = new ArrayList<>();
        counts = new ArrayList<>();
    }


    public void addGenre(String genre) {
        // Check if genre already exists
        int index = findGenreIndex(genre);

        if (index >= 0) {
            // Genre exists, increment count
            counts.set(index, counts.get(index) + 1);
        } else {
            // New genre, add it
            genres.add(genre);
            counts.add(1);
        }
    }


    private int findGenreIndex(String genre) {
        for (int i = 0; i < genres.size(); i++) {
            if (genres.get(i).equals(genre)) {
                return i;
            }
        }
        return -1;
    }


    public int getCount(String genre) {
        int index = findGenreIndex(genre);
        if (index >= 0) {
            return counts.get(index);
        }
        return 0;
    }


    public ArrayList<String> getGenres() {
        return genres;
    }


    public ArrayList<Integer> getCounts() {
        return counts;
    }


    public boolean isEmpty() {
        return genres.isEmpty();
    }


    public int size() {
        return genres.size();
    }


    public void display() {
        for (int i = 0; i < genres.size(); i++) {
            System.out.printf("%-15s: %d book(s)%n", genres.get(i), counts.get(i));
        }
    }
}