package model;

/**
 * Abstract base class representing any entity that can receive feedback
 * (Events, Courses, Facilities).
 * 
 * OOP Concepts Demonstrated:
 * 1. Abstraction: Defines contract and shared identity for all feedback targets.
 * 2. Encapsulation: Private final fields protecting target identity.
 * 3. Final Keyword: Immutable ID and name fields.
 */
public abstract class FeedbackTarget {
    // Encapsulation with final immutability
    private final String id;
    private final String name;

    public FeedbackTarget(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Abstract methods defining polymorphic interface for targets
    public abstract String getTargetType(); // Returns "Event", "Course", or "Facility"
    public abstract String getDetails();    // Returns specific information

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s", getTargetType(), name, id, getDetails());
    }
}

