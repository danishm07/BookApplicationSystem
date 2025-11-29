import java.util.*;

public abstract class Book implements BookInterface {


    private String title;
    private String author;
    private String genre;

    // Static list to track all books in the system
    private static List<Book> allBooks = new ArrayList<>();

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        allBooks.add(this); // Add to static list
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    //accessing all books
    public static List<Book> getAllBooks() {
        return allBooks;
    }

    //clearing all books
    public static void clearAllBooks() {
        allBooks.clear();
    }

    public abstract double getCost();


    public abstract void storeBookInfo();

    @Override
    public String toString() {
        return String.format("%s by %s [%s] - $%.2f",
                title, author, genre, getCost());
    }
}