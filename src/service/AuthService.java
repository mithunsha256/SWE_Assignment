package service;

import exception.DuplicateUserException;
import exception.InvalidLoginException;
import model.Admin;
import model.Student;
import model.User;
import storage.Storage;
import util.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service managing user registration, authentication, and user persistence.
 * 
 * OOP Concepts Demonstrated:
 * 1. Encapsulation: Protects internal userMap and storage references.
 * 2. Polymorphism: Interacts with Storage<User> interface rather than concrete file class.
 * 3. Collections (HashMap & ArrayList): Fast lookups using Map, conversion to List.
 * 4. Custom Exceptions: Throws DuplicateUserException and InvalidLoginException.
 */
public class AuthService {
    // Collections: HashMap for fast O(1) user lookup by lowercase username
    private final Map<String, User> userMap;
    // Polymorphic storage reference
    private final Storage<User> userStorage;

    public AuthService(Storage<User> userStorage) {
        this.userStorage = userStorage;
        this.userMap = new HashMap<>();
        loadUsers();
        initDefaultAdmin();
    }

    private void loadUsers() {
        try {
            List<User> loaded = userStorage.load();
            for (User u : loaded) {
                userMap.put(u.getUsername().toLowerCase(), u);
            }
        } catch (IOException e) {
            System.err.println("Notice: Initializing fresh user database (" + e.getMessage() + ")");
        }
    }

    /**
     * Creates a default administrator account if none exists.
     */
    private void initDefaultAdmin() {
        boolean hasAdmin = false;
        for (User u : userMap.values()) {
            if ("Admin".equalsIgnoreCase(u.getRole())) {
                hasAdmin = true;
                break;
            }
        }

        if (!hasAdmin) {
            Admin defaultAdmin = new Admin("admin", "admin123", "System Administrator", "Campus Administration");
            userMap.put(defaultAdmin.getUsername().toLowerCase(), defaultAdmin);
            saveUsers();
        }
    }

    /**
     * Registers a new student.
     * Demonstrates checked exception throwing (DuplicateUserException).
     */
    public Student registerStudent(String username, String password, String fullName, String rollNumber)
            throws DuplicateUserException, IllegalArgumentException {
        if (!Validator.isValidUsername(username)) {
            throw new IllegalArgumentException("Username must be at least 3 characters and cannot contain commas or quotes.");
        }
        if (!Validator.isValidPassword(password)) {
            throw new IllegalArgumentException("Password must be at least " + Validator.MIN_PASSWORD_LENGTH + " characters long.");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be blank.");
        }
        if (rollNumber == null || rollNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Roll number cannot be blank.");
        }

        String key = username.trim().toLowerCase();
        if (userMap.containsKey(key)) {
            throw new DuplicateUserException("Username '" + username + "' is already registered. Please choose another.");
        }

        Student student = new Student(username.trim(), password, fullName.trim(), rollNumber.trim());
        userMap.put(key, student);
        saveUsers();
        return student;
    }

    /**
     * Authenticates user credentials.
     * Demonstrates checked exception throwing (InvalidLoginException).
     */
    public User login(String username, String password) throws InvalidLoginException {
        if (username == null || password == null) {
            throw new InvalidLoginException("Username and password must not be null.");
        }

        User user = userMap.get(username.trim().toLowerCase());
        if (user == null || !user.getPassword().equals(password)) {
            throw new InvalidLoginException("Invalid username or password. Please try again.");
        }
        return user;
    }

    public void saveUsers() {
        try {
            userStorage.save(new ArrayList<>(userMap.values()));
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }
}

