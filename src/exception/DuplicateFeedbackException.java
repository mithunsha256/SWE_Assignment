package exception;

/**
 * Custom checked exception thrown when a student attempts to submit feedback
 * for the exact same target more than once within a 24-hour window.
 * OOP Concept Demonstrated: Custom Checked Exception.
 */
public class DuplicateFeedbackException extends Exception {
    public DuplicateFeedbackException(String message) {
        super(message);
    }
}

