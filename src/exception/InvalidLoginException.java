package exception;

/**
 * Custom checked exception thrown when authentication fails.
 * OOP Concept Demonstrated: Custom Checked Exception (Inheritance from Exception).
 */
public class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}

