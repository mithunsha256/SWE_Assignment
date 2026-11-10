package exception;

/**
 * Custom checked exception thrown when registering an existing username.
 * OOP Concept Demonstrated: Custom Checked Exception.
 */
public class DuplicateUserException extends Exception {
    public DuplicateUserException(String message) {
        super(message);
    }
}

