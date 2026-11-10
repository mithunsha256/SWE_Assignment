package model;

/**
 * Concrete class representing a campus Event target.
 * 
 * OOP Concepts Demonstrated:
 * 1. Inheritance: Extends FeedbackTarget.
 * 2. Super Keyword: Calls super(id, name).
 * 3. Method Overriding: Implements getTargetType() and getDetails().
 * 4. Encapsulation: Private event-specific fields (date, venue).
 */
public class Event extends FeedbackTarget {
    private String date;
    private String venue;

    public Event(String id, String name, String date, String venue) {
        super(id, name);
        this.date = date;
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    @Override
    public String getTargetType() {
        return "Event";
    }

    @Override
    public String getDetails() {
        return "Date: " + date + " | Venue: " + venue;
    }
}

