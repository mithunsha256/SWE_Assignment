package model;

/**
 * Concrete subclass representing a Student user.
 * 
 * OOP Concepts Demonstrated:
 * 1. Inheritance: Extends User base class.
 * 2. Super keyword: Invokes parent constructor via super(...).
 * 3. Method Overriding: Implements abstract getRoleDescription() and overrides toString().
 * 4. Encapsulation: Private student-specific attribute (rollNumber) with getters/setters.
 */
public class Student extends User {
    private String rollNumber;

    /**
     * Primary constructor with explicit ID.
     */
    public Student(String id, String username, String password, String fullName, String rollNumber) {
        super(id, username, password, fullName, "Student");
        this.rollNumber = rollNumber;
    }

    /**
     * Overloaded constructor: Invokes base overloaded constructor via super.
     */
    public Student(String username, String password, String fullName, String rollNumber) {
        super(username, password, fullName, "Student");
        this.rollNumber = rollNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    /**
     * Method Overriding: Fulfills the abstract method contract from User.
     */
    @Override
    public String getRoleDescription() {
        return "Student user eligible to submit and review campus feedback.";
    }

    @Override
    public String toString() {
        return String.format("Student [ID: %s, Username: %s, Name: %s, Roll No: %s]",
                getId(), getUsername(), getFullName(), rollNumber);
    }
}

