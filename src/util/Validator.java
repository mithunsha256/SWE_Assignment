package util;

import exception.InvalidFeedbackException;

/**
 * Utility class containing centralized validation logic and business rules.
 * 
 * OOP Concepts Demonstrated:
 * 1. Final Keyword: Constant definitions and final class (prevents inheritance).
 * 2. Static Members: Pure static methods and static constants.
 * 3. Exception Propagation: Validates input and throws domain exceptions.
 */
public final class Validator {

    // Final constants for business rules
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;
    public static final int MIN_COMMENT_LENGTH = 5;
    public static final int MAX_COMMENT_LENGTH = 300;

    // Private constructor to prevent instantiation of utility class
    private Validator() {}

    /**
     * Validates that username is not blank and does not contain commas (for CSV safety).
     */
    public static boolean isValidUsername(String username) {
        if (username == null) return false;
        String trimmed = username.trim();
        return trimmed.length() >= 3 && !trimmed.contains(",") && !trimmed.contains("\"");
    }

    /**
     * Validates that password meets minimum length requirements.
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH && !password.contains(",");
    }

    /**
     * Validates star rating and comment length.
     * Throws InvalidFeedbackException if invalid.
     */
    public static void validateFeedback(int rating, String comment) throws InvalidFeedbackException {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new InvalidFeedbackException("Rating must be an integer between " 
                    + MIN_RATING + " and " + MAX_RATING + ".");
        }
        if (comment == null) {
            throw new InvalidFeedbackException("Feedback comment cannot be null.");
        }
        String trimmedComment = comment.trim();
        if (trimmedComment.length() < MIN_COMMENT_LENGTH || trimmedComment.length() > MAX_COMMENT_LENGTH) {
            throw new InvalidFeedbackException("Comment length must be between " 
                    + MIN_COMMENT_LENGTH + " and " + MAX_COMMENT_LENGTH + " characters (current: " 
                    + trimmedComment.length() + ").");
        }
    }
}

