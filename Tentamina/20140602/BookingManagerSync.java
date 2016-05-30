package booking;

public class BookingManagerSync {
    private BookingManager bm;

    public BookingManagerSync(BookingManager bm) {
        this.bm = bm;
    }

    public synchronized boolean create(String name) {
        return bm.create(name);
    }

    public synchronized boolean book(
        String resourceName,
        String date,
        String start_time,
        String end_time,
        String description) {
        return bm.book(resourceName,
            date,
            start_time,
            end_time,
            description);
    }

    public synchronized boolean delete(
        String resourceName,
        String date,
        String start_time) {
        return bm.delete(resourceName,
            date,
            start_time);
    }

    public synchronized String[] show(String resourceName, String date) {
        return bm.show(resourceName,
            date);
    }

}
