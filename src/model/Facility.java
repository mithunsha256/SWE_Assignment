package model;

/**
 * Concrete class representing a campus Facility target.
 * 
 * OOP Concepts Demonstrated:
 * 1. Inheritance: Extends FeedbackTarget.
 * 2. Super Keyword: Calls super(id, name).
 * 3. Method Overriding: Implements getTargetType() and getDetails().
 * 4. Encapsulation: Facility location and operating hours.
 */
public class Facility extends FeedbackTarget {
    private String location;
    private String operatingHours;

    public Facility(String id, String name, String location, String operatingHours) {
        super(id, name);
        this.location = location;
        this.operatingHours = operatingHours;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }

    @Override
    public String getTargetType() {
        return "Facility";
    }

    @Override
    public String getDetails() {
        return "Location: " + location + " | Hours: " + operatingHours;
    }
}

