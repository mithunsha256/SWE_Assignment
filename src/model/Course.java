package model;

/**
 * Concrete class representing an academic Course target.
 * 
 * OOP Concepts Demonstrated:
 * 1. Inheritance: Extends FeedbackTarget.
 * 2. Super Keyword: Calls super(id, name).
 * 3. Method Overriding: Implements getTargetType() and getDetails().
 * 4. Encapsulation: Course code and instructor fields.
 */
public class Course extends FeedbackTarget {
    private String courseCode;
    private String instructor;

    public Course(String id, String name, String courseCode, String instructor) {
        super(id, name);
        this.courseCode = courseCode;
        this.instructor = instructor;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    @Override
    public String getTargetType() {
        return "Course";
    }

    @Override
    public String getDetails() {
        return "Code: " + courseCode + " | Faculty: " + instructor;
    }
}

