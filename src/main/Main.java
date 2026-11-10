package main;

import exception.DuplicateFeedbackException;
import exception.DuplicateUserException;
import exception.InvalidFeedbackException;
import exception.InvalidLoginException;
import model.Admin;
import model.Feedback;
import model.FeedbackTarget;
import model.Student;
import model.User;
import service.AuthService;
import service.AutoSaveService;
import service.FeedbackService;
import service.ReportService;
import service.Reportable;
import storage.FeedbackFileStorage;
import storage.Storage;
import storage.UserFileStorage;
import util.InputHelper;

import java.util.List;
import java.util.Scanner;

/**
 * Main application entry point for the Campus Feedback System.
 * Contains the console menu loop and handles top-level user interactions.
 * 
 * OOP Concepts Demonstrated:
 * 1. Encapsulation: Modular separation of menu routines.
 * 2. Polymorphism: References through interfaces (Storage<T>, Reportable) and base User.
 * 3. Exception Handling: Comprehensive try-catch-finally blocks with custom checked exceptions.
 * 4. Method Overriding in Action: Dynamic method dispatch for user.getRoleDescription().
 */
public class Main {
    private static Scanner scanner;
    private static InputHelper input;
    private static AuthService authService;
    private static FeedbackService feedbackService;
    private static ReportService reportService;
    private static AutoSaveService autoSaveService;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        input = new InputHelper(scanner);

        // Initialize Polymorphic Storage instances
        Storage<User> userStorage = new UserFileStorage("data/users.csv");
        Storage<Feedback> feedbackStorage = new FeedbackFileStorage("data/feedback.csv");

        // Initialize Services
        authService = new AuthService(userStorage);
        feedbackService = new FeedbackService(feedbackStorage);
        reportService = new ReportService(feedbackService);

        // Start background auto-save daemon thread (demonstrates multithreading)
        autoSaveService = new AutoSaveService(authService, feedbackService, 30);
        autoSaveService.start();

        System.out.println("========================================================");
        System.out.println("        WELCOME TO CAMPUS FEEDBACK SYSTEM");
        System.out.println("        Software Engineering & OOP Project");
        System.out.println("========================================================");

        try {
            boolean exit = false;
            while (!exit) {
                printMainMenu();
                int choice = input.readInt("Enter choice (1-4): ", 1, 4);
                switch (choice) {
                    case 1:
                        handleStudentLogin();
                        break;
                    case 2:
                        handleStudentRegistration();
                        break;
                    case 3:
                        handleAdminLogin();
                        break;
                    case 4:
                        exit = true;
                        System.out.println("\nThank you for using Campus Feedback System. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } catch (Exception e) {
            System.err.println("Unexpected runtime error: " + e.getMessage());
        } finally {
            // Demonstrates finally block: Ensures resources and background threads are closed
            if (autoSaveService != null) {
                autoSaveService.stop();
            }
            authService.saveUsers();
            feedbackService.saveFeedback();
            scanner.close();
            System.out.println("[System] Data successfully saved to data/ folder. Session closed.");
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--------------------------------------------------------");
        System.out.println("                     MAIN MENU");
        System.out.println("--------------------------------------------------------");
        System.out.println("  1. Student Login");
        System.out.println("  2. Student Registration (Sign Up)");
        System.out.println("  3. Admin Login (Default: admin / admin123)");
        System.out.println("  4. Exit System");
        System.out.println("--------------------------------------------------------");
    }

    // =========================================================================
    // STUDENT WORKFLOWS
    // =========================================================================

    private static void handleStudentRegistration() {
        System.out.println("\n>>> STUDENT REGISTRATION <<<");
        String username = input.readString("Enter desired username: ");
        String password = input.readString("Enter password (min 6 characters): ");
        String fullName = input.readString("Enter your full name: ");
        String rollNumber = input.readString("Enter your student roll number: ");

        try {
            Student student = authService.registerStudent(username, password, fullName, rollNumber);
            System.out.println("\n[SUCCESS] Registration successful! Welcome, " + student.getFullName() + ".");
            System.out.println("You can now log in using your credentials.");
        } catch (DuplicateUserException e) {
            // Checked exception handling
            System.out.println("\n[REGISTRATION ERROR] " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("\n[VALIDATION ERROR] " + e.getMessage());
        }
        input.pressEnterToContinue();
    }

    private static void handleStudentLogin() {
        System.out.println("\n>>> STUDENT LOGIN <<<");
        String username = input.readString("Username: ");
        String password = input.readString("Password: ");

        try {
            User user = authService.login(username, password);
            if (!(user instanceof Student)) {
                System.out.println("\n[LOGIN ERROR] This account is an Admin account. Please use Admin Login.");
                input.pressEnterToContinue();
                return;
            }
            Student student = (Student) user;
            System.out.println("\n[SUCCESS] Login successful! Welcome back, " + student.getFullName() + ".");
            runStudentMenu(student);
        } catch (InvalidLoginException e) {
            System.out.println("\n[LOGIN ERROR] " + e.getMessage());
            input.pressEnterToContinue();
        }
    }

    private static void runStudentMenu(Student student) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n========================================================");
            System.out.println(" STUDENT PORTAL: " + student.getFullName() + " (" + student.getRollNumber() + ")");
            System.out.println("========================================================");
            System.out.println("  1. Submit Feedback (Event, Course, or Facility)");
            System.out.println("  2. View My Feedback History");
            System.out.println("  3. View Role Information (Polymorphism Demo)");
            System.out.println("  4. Logout");
            System.out.println("--------------------------------------------------------");

            int choice = input.readInt("Enter choice (1-4): ", 1, 4);
            switch (choice) {
                case 1:
                    handleSubmitFeedback(student);
                    break;
                case 2:
                    handleViewStudentHistory(student);
                    break;
                case 3:
                    // Polymorphic method call
                    System.out.println("\n--- User Role Details (Polymorphic Method Call) ---");
                    System.out.println("Class Type       : " + student.getClass().getSimpleName());
                    System.out.println("Role Name        : " + student.getRole());
                    System.out.println("Role Description : " + student.getRoleDescription());
                    input.pressEnterToContinue();
                    break;
                case 4:
                    logout = true;
                    System.out.println("\nLogged out successfully.");
                    break;
            }
        }
    }

    private static void handleSubmitFeedback(Student student) {
        System.out.println("\n>>> SUBMIT FEEDBACK <<<");
        System.out.println("Select Target Category:");
        System.out.println("  1. Campus Event");
        System.out.println("  2. Academic Course");
        System.out.println("  3. Campus Facility");
        System.out.println("  4. Cancel");

        int catChoice = input.readInt("Choose category (1-4): ", 1, 4);
        if (catChoice == 4) return;

        String targetType = (catChoice == 1) ? "Event" : (catChoice == 2) ? "Course" : "Facility";
        List<FeedbackTarget> targets = feedbackService.getTargetsByType(targetType);

        String pluralLabel = targetType.equals("Facility") ? "Facilities" : targetType + "s";
        System.out.println("\nAvailable " + pluralLabel + ":");
        for (int i = 0; i < targets.size(); i++) {
            FeedbackTarget target = targets.get(i);
            System.out.printf("  %d. %s [%s]\n", (i + 1), target.getName(), target.getDetails());
        }

        int targetIndex = input.readInt("Select target (1-" + targets.size() + "): ", 1, targets.size()) - 1;
        FeedbackTarget selectedTarget = targets.get(targetIndex);

        System.out.println("\nTarget Selected: " + selectedTarget.getName() + " (" + selectedTarget.getId() + ")");
        System.out.println("Enter Star Rating (1 = Very Poor, 5 = Excellent):");
        int rating = input.readInt("Rating (1-5): ", 1, 5);

        System.out.println("Enter your review comment (5 to 300 characters):");
        String comment = input.readString("Comment: ");

        try {
            // Demonstrates Method Overloading in FeedbackService
            Feedback submitted = feedbackService.submitFeedback(
                    student.getUsername(),
                    selectedTarget,
                    rating,
                    comment
            );

            System.out.println("\n[SUCCESS] Thank you! Your feedback has been recorded.");
            System.out.println("Reference ID: " + submitted.getFeedbackId());
            System.out.println("Rating      : " + submitted.getStarDisplay());
        } catch (DuplicateFeedbackException e) {
            // 24-hour duplication business rule enforced
            System.out.println("\n[SUBMISSION REJECTED] " + e.getMessage());
        } catch (InvalidFeedbackException e) {
            System.out.println("\n[VALIDATION REJECTED] " + e.getMessage());
        }

        input.pressEnterToContinue();
    }

    private static void handleViewStudentHistory(Student student) {
        System.out.println("\n>>> MY FEEDBACK HISTORY <<<");
        List<Feedback> history = feedbackService.getFeedbackByStudent(student.getUsername());

        if (history.isEmpty()) {
            System.out.println("You have not submitted any feedback yet.");
        } else {
            System.out.println("Found " + history.size() + " submission(s) (Sorted newest first):\n");
            for (int i = 0; i < history.size(); i++) {
                System.out.printf("--- Feedback #%d ---\n", (i + 1));
                System.out.println(history.get(i));
                System.out.println();
            }
        }
        input.pressEnterToContinue();
    }

    // =========================================================================
    // ADMIN WORKFLOWS
    // =========================================================================

    private static void handleAdminLogin() {
        System.out.println("\n>>> ADMIN LOGIN <<<");
        String username = input.readString("Admin Username: ");
        String password = input.readString("Admin Password: ");

        try {
            User user = authService.login(username, password);
            if (!(user instanceof Admin)) {
                System.out.println("\n[LOGIN ERROR] This account is not an Administrator account.");
                input.pressEnterToContinue();
                return;
            }
            Admin admin = (Admin) user;
            System.out.println("\n[SUCCESS] Admin authenticated! Welcome, " + admin.getFullName() + ".");
            runAdminMenu(admin);
        } catch (InvalidLoginException e) {
            System.out.println("\n[LOGIN ERROR] " + e.getMessage());
            input.pressEnterToContinue();
        }
    }

    private static void runAdminMenu(Admin admin) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n========================================================");
            System.out.println(" ADMIN PORTAL: " + admin.getFullName() + " (" + admin.getDepartment() + ")");
            System.out.println("========================================================");
            System.out.println("  1. View All Feedback Submissions");
            System.out.println("  2. Filter Feedback by Target Type (Event / Course / Facility)");
            System.out.println("  3. View Summary Report & Analytics (Reportable Interface)");
            System.out.println("  4. View Role Information (Polymorphism Demo)");
            System.out.println("  5. Logout");
            System.out.println("--------------------------------------------------------");

            int choice = input.readInt("Enter choice (1-5): ", 1, 5);
            switch (choice) {
                case 1:
                    handleViewAllFeedback();
                    break;
                case 2:
                    handleFilterFeedbackByType();
                    break;
                case 3:
                    handleViewReport();
                    break;
                case 4:
                    System.out.println("\n--- User Role Details (Polymorphic Method Call) ---");
                    System.out.println("Class Type       : " + admin.getClass().getSimpleName());
                    System.out.println("Role Name        : " + admin.getRole());
                    System.out.println("Role Description : " + admin.getRoleDescription());
                    input.pressEnterToContinue();
                    break;
                case 5:
                    logout = true;
                    System.out.println("\nLogged out from Admin Portal.");
                    break;
            }
        }
    }

    private static void handleViewAllFeedback() {
        System.out.println("\n>>> ALL FEEDBACK SUBMISSIONS <<<");
        List<Feedback> all = feedbackService.getAllFeedback();
        if (all.isEmpty()) {
            System.out.println("No feedback submissions found in the system.");
        } else {
            System.out.println("Total records: " + all.size() + "\n");
            for (Feedback fb : all) {
                System.out.println(fb);
                System.out.println("----------------------------------------------------------------------------------------");
            }
        }
        input.pressEnterToContinue();
    }

    private static void handleFilterFeedbackByType() {
        System.out.println("\n>>> FILTER FEEDBACK BY TARGET TYPE <<<");
        System.out.println("  1. Filter by Events");
        System.out.println("  2. Filter by Courses");
        System.out.println("  3. Filter by Facilities");
        System.out.println("  4. Back");

        int choice = input.readInt("Select filter option (1-4): ", 1, 4);
        if (choice == 4) return;

        String targetType = (choice == 1) ? "Event" : (choice == 2) ? "Course" : "Facility";
        List<Feedback> filtered = feedbackService.getFeedbackByTargetType(targetType);

        System.out.println("\nFeedback records for category: " + targetType);
        if (filtered.isEmpty()) {
            System.out.println("No feedback found for category '" + targetType + "'.");
        } else {
            System.out.println("Total matching records: " + filtered.size() + "\n");
            for (Feedback fb : filtered) {
                System.out.println(fb);
                System.out.println("----------------------------------------------------------------------------------------");
            }
        }
        input.pressEnterToContinue();
    }

    private static void handleViewReport() {
        // Polymorphism: Calling generateReport through Reportable interface
        Reportable reportGenerator = reportService;
        System.out.println();
        System.out.println(reportGenerator.generateReport());
        input.pressEnterToContinue();
    }
}
