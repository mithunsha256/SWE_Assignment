package service;

import exception.DuplicateFeedbackException;
import exception.InvalidFeedbackException;
import model.Course;
import model.Event;
import model.Facility;
import model.Feedback;
import model.FeedbackTarget;
import storage.Storage;
import util.Validator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Service managing feedback submissions, history retrieval, and target filtering.
 * 
 * OOP Concepts Demonstrated:
 * 1. Method Overloading: submitFeedback(...) provided in two overloaded variants.
 * 2. Polymorphism: Operates on abstract FeedbackTarget and generic Storage<Feedback>.
 * 3. Collections (ArrayList): Stores feedback and predefined targets.
 * 4. Comparator & Sorting: Sorts feedback history by timestamp in reverse chronological order.
 * 5. Custom Checked Exceptions: Throws InvalidFeedbackException and DuplicateFeedbackException.
 */
public class FeedbackService {
    private final List<Feedback> feedbackList;
    private final Storage<Feedback> feedbackStorage;
    private final List<FeedbackTarget> predefinedTargets;

    public FeedbackService(Storage<Feedback> feedbackStorage) {
        this.feedbackStorage = feedbackStorage;
        this.feedbackList = new ArrayList<>();
        this.predefinedTargets = new ArrayList<>();
        initializePredefinedTargets();
        loadFeedback();
    }

    /**
     * Pre-populates system with standard campus targets.
     * Demonstrates polymorphism: Event, Course, and Facility stored in List<FeedbackTarget>.
     */
    private void initializePredefinedTargets() {
        // Events
        predefinedTargets.add(new Event("EVT-101", "Tech Fest 2026", "2026-03-15", "Campus Auditorium"));
        predefinedTargets.add(new Event("EVT-102", "Sports Day 2026", "2026-04-10", "Main Athletic Ground"));
        predefinedTargets.add(new Event("EVT-103", "Cultural Night", "2026-05-02", "Open Air Theatre"));
        predefinedTargets.add(new Event("EVT-104", "Hack-the-Cloud Hackathon", "2026-06-20", "Computer Lab 4"));

        // Courses
        predefinedTargets.add(new Course("CRS-201", "Cloud Computing (CS401)", "CS401", "Prof. Alan Turing"));
        predefinedTargets.add(new Course("CRS-202", "Data Structures & Algorithms (CS201)", "CS201", "Dr. Grace Hopper"));
        predefinedTargets.add(new Course("CRS-203", "Machine Learning & AI (CS450)", "CS450", "Dr. John McCarthy"));
        predefinedTargets.add(new Course("CRS-204", "Database Management Systems (CS302)", "CS302", "Prof. Edgar Codd"));

        // Facilities
        predefinedTargets.add(new Facility("FAC-301", "Central Library", "Academic Block A, 2nd Floor", "08:00 AM - 10:00 PM"));
        predefinedTargets.add(new Facility("FAC-302", "Hostel Mess & Dining", "Student Dining Hall 1", "07:00 AM - 09:30 PM"));
        predefinedTargets.add(new Facility("FAC-303", "Campus Wi-Fi Network", "Across Entire Campus", "24/7"));
        predefinedTargets.add(new Facility("FAC-304", "Student Sports Complex", "West Campus Zone", "06:00 AM - 08:30 PM"));
    }

    private void loadFeedback() {
        try {
            List<Feedback> loaded = feedbackStorage.load();
            feedbackList.addAll(loaded);
        } catch (IOException e) {
            System.err.println("Notice: Initializing fresh feedback records (" + e.getMessage() + ")");
        }
    }

    /**
     * Method Overloading Variant 1:
     * Accepts individual parameters, validates, constructs a Feedback object, and delegates.
     */
    public Feedback submitFeedback(String studentUsername, FeedbackTarget target, int rating, String comment)
            throws InvalidFeedbackException, DuplicateFeedbackException {
        Validator.validateFeedback(rating, comment);

        Feedback fb = new Feedback(
                studentUsername,
                target.getTargetType(),
                target.getId(),
                target.getName(),
                rating,
                comment.trim()
        );

        return submitFeedback(fb);
    }

    /**
     * Method Overloading Variant 2:
     * Accepts fully constructed Feedback object, applies 24-hour duplication check,
     * adds to list, and persists to storage.
     */
    public synchronized Feedback submitFeedback(Feedback feedback) 
            throws InvalidFeedbackException, DuplicateFeedbackException {
        // Validate inputs
        Validator.validateFeedback(feedback.getRating(), feedback.getComment());

        // Business Rule: Student cannot submit feedback for same target more than once in 24 hours
        LocalDateTime now = feedback.getTimestamp();
        for (Feedback existing : feedbackList) {
            if (existing.getStudentUsername().equalsIgnoreCase(feedback.getStudentUsername()) &&
                existing.getTargetId().equalsIgnoreCase(feedback.getTargetId())) {
                
                long hoursSinceLast = ChronoUnit.HOURS.between(existing.getTimestamp(), now);
                if (hoursSinceLast < 24) {
                    long remainingHours = 24 - hoursSinceLast;
                    throw new DuplicateFeedbackException(String.format(
                            "Duplicate feedback rejected: You already reviewed '%s' %d hour(s) ago. Please wait %d more hour(s).",
                            existing.getTargetName(), hoursSinceLast, remainingHours
                    ));
                }
            }
        }

        feedbackList.add(feedback);
        saveFeedback();
        return feedback;
    }

    /**
     * Retrieves feedback history for a specific student.
     * Demonstrates Collections and Comparator sorting (newest first).
     */
    public List<Feedback> getFeedbackByStudent(String username) {
        List<Feedback> studentList = new ArrayList<>();
        for (Feedback fb : feedbackList) {
            if (fb.getStudentUsername().equalsIgnoreCase(username)) {
                studentList.add(fb);
            }
        }

        // Sort descending by timestamp using Comparator
        Collections.sort(studentList, new Comparator<Feedback>() {
            @Override
            public int compare(Feedback f1, Feedback f2) {
                return f2.getTimestamp().compareTo(f1.getTimestamp());
            }
        });

        return studentList;
    }

    /**
     * Returns all feedback records.
     */
    public List<Feedback> getAllFeedback() {
        return new ArrayList<>(feedbackList);
    }

    /**
     * Filters feedback by target type (Event, Course, or Facility).
     */
    public List<Feedback> getFeedbackByTargetType(String targetType) {
        List<Feedback> filtered = new ArrayList<>();
        for (Feedback fb : feedbackList) {
            if (fb.getTargetType().equalsIgnoreCase(targetType)) {
                filtered.add(fb);
            }
        }
        return filtered;
    }

    public synchronized void saveFeedback() {
        try {
            feedbackStorage.save(feedbackList);
        } catch (IOException e) {
            System.err.println("Error saving feedback data: " + e.getMessage());
        }
    }

    public List<FeedbackTarget> getPredefinedTargets() {
        return Collections.unmodifiableList(predefinedTargets);
    }

    public List<FeedbackTarget> getTargetsByType(String type) {
        List<FeedbackTarget> list = new ArrayList<>();
        for (FeedbackTarget target : predefinedTargets) {
            if (target.getTargetType().equalsIgnoreCase(type)) {
                list.add(target);
            }
        }
        return list;
    }

    public FeedbackTarget getTargetById(String id) {
        for (FeedbackTarget target : predefinedTargets) {
            if (target.getId().equalsIgnoreCase(id)) {
                return target;
            }
        }
        return null;
    }
}

