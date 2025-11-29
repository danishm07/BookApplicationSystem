import java.io.*;
import java.util.*;


public class FileManager {
    public static void writeToFile(String filename, List<Book> books) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            // Write header
            writer.println("title,author,genre,cost,length,pages,booktype");

            // Write each book
            for (Book book : books) {
                if (book instanceof PrintedBook) {
                    PrintedBook pb = (PrintedBook) book;
                    writer.printf("\"%s\",\"%s\",\"%s\",%.0f,\"N/A\",%d,printedBook%n",
                            book.getTitle(),
                            book.getAuthor(),
                            book.getGenre(),
                            book.getCost(),
                            pb.getNumberOfPages());
                } else if (book instanceof AudioBook) {
                    AudioBook ab = (AudioBook) book;
                    writer.printf("\"%s\",\"%s\",\"%s\",%.0f,%d,\"N/A\",audioBook%n",
                            book.getTitle(),
                            book.getAuthor(),
                            book.getGenre(),
                            book.getCost(),
                            ab.getLengthInMinutes());
                }
            }

            System.out.println("✓ Successfully saved " + books.size() + " books to " + filename);

        } catch (IOException e) {
            System.err.println("✗ Error writing to file: " + e.getMessage());
            throw e; 
        }
    }


    public static List<Book> readFromFile(String filename) throws IOException {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip header line
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    Book book = parseBookLine(line);
                    if (book != null) {
                        books.add(book);
                    }
                } catch (Exception e) {
                    System.err.println("Warning: Could not parse line: " + line);
                    System.err.println("Error: " + e.getMessage());
                }
            }

            System.out.println("✓ Successfully loaded " + books.size() + " books from " + filename);
            return books;

        } catch (FileNotFoundException e) {
            System.err.println("✗ File not found: " + filename);
            throw e;
        } catch (IOException e) {
            System.err.println("✗ Error reading file: " + e.getMessage());
            throw e;
        }
    }

    private static Book parseBookLine(String line) {
        // Remove quotes and split by comma
        String[] parts = line.split(",");

        // Clean up each part (remove quotes and trim)
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].replace("\"", "").trim();
        }

        // Extract common fields
        String title = parts[0];
        String author = parts[1];
        String genre = parts[2];
        String lengthStr = parts[4];  // length or "N/A"
        String pagesStr = parts[5];   // pages or "N/A"
        String bookType = parts[6];   // "printedBook" or "audioBook"

        // Create appropriate book type
        if (bookType.equalsIgnoreCase("printedBook")) {
            int pages = Integer.parseInt(pagesStr);
            return new PrintedBook(title, author, genre, pages);
        } else if (bookType.equalsIgnoreCase("audioBook")) {
            int length = Integer.parseInt(lengthStr);
            return new AudioBook(title, author, genre, length);
        }

        return null;
    }


    public static boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists() && file.isFile();
    }
}