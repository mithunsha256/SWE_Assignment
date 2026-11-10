package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Model class representing feedback submitted by a student.
 * 
 * OOP Concepts Demonstrated:
 * 1. Encapsulation: Private fields with accessor methods.
 * 2. Constructor Overloading: Multiple constructors chained using this().
 * 3. Static Members: DateTimeFormatter shared across all instances.
 * 4. Method Overriding: Overrides Object.toString().
 */
public class Feedback {
    private String feedbackId;
    private String studentUsername;
    private String targetType; // Event, Course, Facility
    private String targetId;
    private String targetName;
    private int rating; // 1 to 5
    private String comment; // 5 to 300 chars
    private LocalDateTime timestamp;

    // Static member: shared date-time formatter
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Complete constructor for loading existing feedback.
     */
    public Feedback(String feedbackId, String studentUsername, String targetType, 
                    String targetId, String targetName, int rating, String comment, 
                    LocalDateTime timestamp) {
        this.feedbackId = feedbackId;
        this.studentUsername = studentUsername;
        this.targetType = targetType;
        this.targetId = targetId;
        this.targetName = targetName;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    /**
     * Overloaded constructor: Creates a new feedback entry with auto-generated ID and current time.
     * Demonstrates Constructor Overloading and 'this(...)' invocation.
     */
    public Feedback(String studentUsername, String targetType, String targetId, 
                    String targetName, int rating, String comment) {
        this("FB-" + System.currentTimeMillis(), studentUsername, targetType, 
             targetId, targetName, rating, comment, LocalDateTime.now());
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Helper to render ratings as visual stars (e.g., [*****] (5/5)).
     * Uses ASCII characters for universal terminal compatibility across all operating systems.
     */
    public String getStarDisplay() {
        StringBuilder stars = new StringBuilder("[");
        for (int i = 1; i <= 5; i++) {
            stars.append(i <= rating ? "*" : "-");
        }
        stars.append("] (").append(rating).append("/5)");
        return stars.toString();
    }

    @Override
    public String toString() {
        return String.format("[%s] Target: %s (%s) | Rating: %s | By: %s | Time: %s\nComment: \"%s\"",
                feedbackId, targetName, targetType, getStarDisplay(),
                studentUsername, timestamp.format(FORMATTER), comment);
    }
}
