import java.util.*;


public class AudioBook extends Book {

    
    private int lengthInMinutes;
    private static int totalLength = 0;
    private static int audioBookCount = 0;
    private static Queue<AudioBook> lastThreeAudio = new LinkedList<>();

    private static final double COST_PER_MINUTE = 5.0;

    public AudioBook(String title, String author, String genre, int lengthInMinutes) {
        super(title, author, genre); 
        this.lengthInMinutes = lengthInMinutes;

        // Update static counters
        totalLength += lengthInMinutes;
        audioBookCount++;

        // Store in last three
        storeBookInfo();
    }


    @Override
    public double getCost() {
        return lengthInMinutes * COST_PER_MINUTE;
    }


    @Override
    public void storeBookInfo() {
        if (lastThreeAudio.size() >= 3) {
            lastThreeAudio.poll(); // Remove oldest
        }
        lastThreeAudio.offer(this); // Add newest
    }

    
    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    //average length of all audio books
    public static double getAverageLength() {
        if (audioBookCount == 0) {
            return 0.0;
        }
        return (double) totalLength / audioBookCount;
    }

    //calculating total cost of audio books
    public static double getTotalAudioBookCost() {
        double total = 0.0;
        for (Book book : Book.getAllBooks()) {
            if (book instanceof AudioBook) { 
                total += book.getCost();
            }
        }
        return total;
    }

    //last three audio books
    public static void displayLastThreeAudio() {
        System.out.println("\n===== LAST 3 AUDIO BOOKS =====");

        if (lastThreeAudio.isEmpty()) {
            System.out.println("No audiobooks in the system.");
        } else {
            int count = 1;
            for (AudioBook book : lastThreeAudio) {
                System.out.println(count + ". " + book.toDetailedString());
                count++;
            }
        }
        System.out.println("===============================\n");
    }


    public String toDetailedString() {
        return String.format("[AUDIO] %s by %s | Genre: %s | Length: %d min | Cost: $%.2f",
                getTitle(), getAuthor(), getGenre(), lengthInMinutes, getCost());
    }


    @Override
    public String toString() {
        return String.format("[AUDIO] %s by %s [%s] - %d minutes - $%.2f",
                getTitle(), getAuthor(), getGenre(), lengthInMinutes, getCost());
    }

    //getting audiobook count
    public static int getAudioBookCount() {
        return audioBookCount;
    }


    public static void reset() {
        totalLength = 0;
        audioBookCount = 0;
        lastThreeAudio.clear();
    }
}