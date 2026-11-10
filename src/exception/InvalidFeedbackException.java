package exception;

/**
 * Custom checked exception thrown when feedback validation fails (e.g., rating out of range, comment too short/long).
 * OOP Concept Demonstrated: Custom Checked Exception.
 */
public class InvalidFeedbackException extends Exception {
    public InvalidFeedbackException(String message) {
        super(message);
    }
}

