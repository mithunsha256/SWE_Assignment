# Class Diagram - Campus Feedback System

Below is the architectural and object-oriented class diagram for the Campus Feedback System, illustrating class hierarchies, interfaces, relationships, and encapsulation boundaries.

```mermaid
classDiagram
    %% Abstract base and model hierarchies
    class User {
        <<abstract>>
        -String id
        -String username
        -String password
        -String fullName
        -String role
        -static int userCounter
        +User(id, username, password, fullName, role)
        +User(username, password, fullName, role)
        +getRoleDescription()* String
        +getUserCounter() int$
        +getId() String
        +getUsername() String
        +getPassword() String
        +getFullName() String
        +getRole() String
        +toString() String
    }

    class Student {
        -String rollNumber
        +Student(id, username, password, fullName, rollNumber)
        +Student(username, password, fullName, rollNumber)
        +getRollNumber() String
        +setRollNumber(rollNumber) void
        +getRoleDescription() String
        +toString() String
    }

    class Admin {
        -String department
        +Admin(id, username, password, fullName, department)
        +Admin(username, password, fullName, department)
        +getDepartment() String
        +setDepartment(department) void
        +getRoleDescription() String
        +toString() String
    }

    User <|-- Student
    User <|-- Admin

    class FeedbackTarget {
        <<abstract>>
        -String id
        -String name
        +FeedbackTarget(id, name)
        +getId() String
        +getName() String
        +getTargetType()* String
        +getDetails()* String
        +toString() String
    }

    class Event {
        -String date
        -String venue
        +Event(id, name, date, venue)
        +getDate() String
        +getVenue() String
        +getTargetType() String
        +getDetails() String
    }

    class Course {
        -String courseCode
        -String instructor
        +Course(id, name, courseCode, instructor)
        +getCourseCode() String
        +getInstructor() String
        +getTargetType() String
        +getDetails() String
    }

    class Facility {
        -String location
        -String operatingHours
        +Facility(id, name, location, operatingHours)
        +getLocation() String
        +getOperatingHours() String
        +getTargetType() String
        +getDetails() String
    }

    FeedbackTarget <|-- Event
    FeedbackTarget <|-- Course
    FeedbackTarget <|-- Facility

    class Feedback {
        -String feedbackId
        -String studentUsername
        -String targetType
        -String targetId
        -String targetName
        -int rating
        -String comment
        -LocalDateTime timestamp
        +static DateTimeFormatter FORMATTER$
        +Feedback(feedbackId, studentUsername, targetType, targetId, targetName, rating, comment, timestamp)
        +Feedback(studentUsername, targetType, targetId, targetName, rating, comment)
        +getStarDisplay() String
        +toString() String
    }

    %% Interfaces and storage implementations
    class Storage~T~ {
        <<interface>>
        +save(List~T~ items)* void
        +load()* List~T~
    }

    class UserFileStorage {
        -String filePath
        +UserFileStorage(filePath)
        +save(List~User~ items) void
        +load() List~User~
    }

    class FeedbackFileStorage {
        -String filePath
        +FeedbackFileStorage(filePath)
        +save(List~Feedback~ items) void
        +load() List~Feedback~
    }

    Storage <|.. UserFileStorage
    Storage <|.. FeedbackFileStorage

    class Reportable {
        <<interface>>
        +generateReport()* String
    }

    class ReportService {
        -FeedbackService feedbackService
        +ReportService(feedbackService)
        +generateReport() String
        +generateReport(filterTargetType) String
    }

    Reportable <|.. ReportService

    %% Services and Threads
    class AuthService {
        -Map~String, User~ userMap
        -Storage~User~ userStorage
        +AuthService(userStorage)
        +registerStudent(username, password, fullName, rollNumber) Student
        +login(username, password) User
        +saveUsers() void
        +getAllUsers() List~User~
    }

    class FeedbackService {
        -List~Feedback~ feedbackList
        -Storage~Feedback~ feedbackStorage
        -List~FeedbackTarget~ predefinedTargets
        +FeedbackService(feedbackStorage)
        +submitFeedback(studentUsername, target, rating, comment) Feedback
        +submitFeedback(feedback) Feedback
        +getFeedbackByStudent(username) List~Feedback~
        +getAllFeedback() List~Feedback~
        +getFeedbackByTargetType(targetType) List~Feedback~
        +getPredefinedTargets() List~FeedbackTarget~
        +getTargetById(id) FeedbackTarget
    }

    class AutoSaveService {
        -AuthService authService
        -FeedbackService feedbackService
        -int intervalSeconds
        -boolean running
        -Thread workerThread
        +AutoSaveService(authService, feedbackService, intervalSeconds)
        +start() void
        +stop() void
        +run() void
    }

    %% Utilities and Exceptions
    class Validator {
        <<final>>
        +static final int MIN_PASSWORD_LENGTH$
        +static final int MIN_RATING$
        +static final int MAX_RATING$
        +static final int MIN_COMMENT_LENGTH$
        +static final int MAX_COMMENT_LENGTH$
        +isValidUsername(username)$ boolean
        +isValidPassword(password)$ boolean
        +validateFeedback(rating, comment)$ void
    }

    class InputHelper {
        -Scanner scanner
        +InputHelper(scanner)
        +readString(prompt) String
        +readInt(prompt, min, max) int
        +pressEnterToContinue() void
    }

    class Main {
        -static Scanner scanner$
        -static InputHelper input$
        -static AuthService authService$
        -static FeedbackService feedbackService$
        -static ReportService reportService$
        -static AutoSaveService autoSaveService$
        +main(String[] args)$ void
    }

    Main ..> AuthService
    Main ..> FeedbackService
    Main ..> ReportService
    Main ..> AutoSaveService
    Main ..> InputHelper
    AuthService ..> Storage
    FeedbackService ..> Storage
    FeedbackService o-- FeedbackTarget
    FeedbackService o-- Feedback
    ReportService ..> FeedbackService
```

