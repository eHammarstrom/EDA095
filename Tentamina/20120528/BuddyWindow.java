package buddy;

public class BuddyWindow {

    public BuddyWindow() { }

    public synchronized void clear() {
        System.out.println("---------------------");
    }

    public synchronized void addBuddy(String buddy) {
        System.out.println(buddy);
    }
}
