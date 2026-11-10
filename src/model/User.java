package model;

/**
 * Abstract base class representing a generic user in the system.
 * 
 * OOP Concepts Demonstrated:
 * 1. Abstraction & Abstract Class: Defines common state and abstract method getRoleDescription().
 * 2. Encapsulation: Private fields with public getters and setters.
 * 3. Constructors & Constructor Overloading: Multiple constructors chained using this().
 * 4. Static Members: userCounter tracks total User objects created.
 */
public abstract class User {
    // Encapsulation: Private member variables
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String role; // "Student" or "Admin"

    // Static member: shared across all instances
    private static int userCounter = 0;

    /**
     * Primary constructor.
     * Uses 'this' keyword to differentiate instance variables from parameters.
     */
    public User(String id, String username, String password, String fullName, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        userCounter++;
    }

    /**
     * Overloaded constructor: Auto-generates an ID if not provided.
     * Demonstrates Constructor Overloading and 'this(...)' constructor chaining.
     */
    public User(String username, String password, String fullName, String role) {
        this("U" + System.currentTimeMillis() + "-" + (++userCounter), username, password, fullName, role);
    }

    // Abstract method: Must be overridden by subclasses (Polymorphism)
    public abstract String getRoleDescription();

    // Static method to access static counter
    public static int getUserCounter() {
        return userCounter;
    }

    // Getters and Setters demonstrating encapsulation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s, Username: %s)", role, fullName, id, username);
    }
}

