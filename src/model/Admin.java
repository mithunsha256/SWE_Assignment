package model;

/**
 * Concrete subclass representing an Administrator user.
 * 
 * OOP Concepts Demonstrated:
 * 1. Inheritance: Extends User base class.
 * 2. Super keyword: Invokes parent constructor using super(...).
 * 3. Method Overriding: Implements abstract getRoleDescription() and overrides toString().
 * 4. Encapsulation: Private admin-specific attribute (department) with getters/setters.
 */
public class Admin extends User {
    private String department;

    /**
     * Primary constructor with explicit ID.
     */
    public Admin(String id, String username, String password, String fullName, String department) {
        super(id, username, password, fullName, "Admin");
        this.department = department;
    }

    /**
     * Overloaded constructor: Invokes base overloaded constructor via super.
     */
    public Admin(String username, String password, String fullName, String department) {
        super(username, password, fullName, "Admin");
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Method Overriding: Fulfills abstract method defined in User.
     */
    @Override
    public String getRoleDescription() {
        return "Campus Administrator with access to all feedback records, analytics, and reports.";
    }

    @Override
    public String toString() {
        return String.format("Admin [ID: %s, Username: %s, Name: %s, Dept: %s]",
                getId(), getUsername(), getFullName(), department);
    }
}

