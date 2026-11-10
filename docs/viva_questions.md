# Viva Voce Preparation Guide
## Project: Campus Feedback System (Core Java & OOP)

This guide provides targeted questions and high-scoring answers commonly asked by external examiners and lab evaluators for Java, Object-Oriented Programming (OOP), and Software Engineering mini-projects.

---

### Category 1: Abstraction & Object-Oriented Design

#### Q1: Why is `User` designed as an `abstract class` instead of an `interface` or a concrete class?
> **Answer:**  
> - **Why not a concrete class?** In our campus domain, a user is never just a generic "User"; they are either a `Student` or an `Admin`. Making `User` abstract prevents developers from instantiating incomplete, generic users (`new User(...)`).
> - **Why not an interface?** All users share common concrete state (e.g., `id`, `username`, `password`, `fullName`, `role`) and common concrete behaviors (getters, setters, `toString()`). In Java, an abstract class allows sharing common code and instance attributes, whereas interfaces prior to default methods were purely abstract contracts, and even now cannot hold non-static instance state.

#### Q2: What is the difference between an Abstract Class and an Interface in Java?
> **Answer:**  
> 1. **State / Instance Variables:** Abstract classes can have stateful instance variables (`private String username`). Interfaces can only have `public static final` constants.
> 2. **Constructors:** Abstract classes have constructors invoked via `super(...)` by subclasses. Interfaces do not have constructors.
> 3. **Multiple Inheritance:** A class can implement multiple interfaces, but can extend only one abstract class (`single inheritance`).
> - **In this project:** `User` and `FeedbackTarget` are abstract classes because they encapsulate shared state and constructors. `Storage<T>` and `Reportable` are interfaces because they define pure behavioral contracts implemented by completely unrelated classes.

---

### Category 2: Encapsulation & Keywords

#### Q3: How is Encapsulation demonstrated in this project?
> **Answer:**  
> Encapsulation bundles data (fields) and methods that operate on that data into a single unit, hiding internal state.  
> In [`User.java`](file:///g:/Projects/SWE_A1/src/model/User.java) and [`Feedback.java`](file:///g:/Projects/SWE_A1/src/model/Feedback.java), all member variables are marked `private`. External classes can only access or modify them through public getters and setters. Centralized validation logic in [`Validator.java`](file:///g:/Projects/SWE_A1/src/util/Validator.java) guards these fields, preventing corrupted states (e.g., ratings outside 1–5 or passwords shorter than 6 characters).

#### Q4: What is the difference between `this` and `super`?
> **Answer:**  
> - `this` refers to the current class instance. Used for resolving variable shadowing (e.g., `this.username = username`) and constructor chaining (e.g., `this(...)`).
> - `super` refers to the immediate parent class. Used to invoke parent constructors (e.g., `super(id, username, password, fullName, "Student")` in [`Student.java`](file:///g:/Projects/SWE_A1/src/model/Student.java)).

#### Q5: What is the significance of the `final` keyword in this project?
> **Answer:**  
> - **Final Variable / Field:** In [`FeedbackTarget.java`](file:///g:/Projects/SWE_A1/src/model/FeedbackTarget.java), `private final String id, name;` makes the identity immutable once initialized.
> - **Final Class:** [`Validator.java`](file:///g:/Projects/SWE_A1/src/util/Validator.java) is declared `public final class Validator` to prevent inheritance and subclassing, as it is a pure static utility class with a `private` constructor.
> - **Final Constants:** Constants like `MIN_PASSWORD_LENGTH = 6` are `public static final`.

#### Q6: What does the `static` keyword mean and where is it used?
> **Answer:**  
> `static` members belong to the class itself rather than any individual object instance.
> 1. `User.userCounter`: Shared across all instances to track the total number of users created in memory.
> 2. `Feedback.FORMATTER`: A single shared `DateTimeFormatter` instance to avoid costly repeated object creations.
> 3. `Validator.validateFeedback(...)`: Utility methods callable without instantiating the class.

---

### Category 3: Polymorphism & Inheritance

#### Q7: What is the difference between Method Overriding and Method Overloading?
> **Answer:**  
> | Feature | Method Overriding (Dynamic / Runtime) | Method Overloading (Static / Compile-Time) |
> |---|---|---|
> | **Location** | Between parent and child classes. | Within the same class. |
> | **Signature** | Must have identical name and parameters. | Must have the same name, but different parameters. |
> | **Binding** | Resolved at runtime via Dynamic Method Dispatch. | Resolved at compile time based on argument types. |
> | **Project Example** | `getRoleDescription()` in `Student` and `Admin`. | `submitFeedback(...)` in `FeedbackService` and `generateReport()` in `ReportService`. |

#### Q8: Explain Dynamic Method Dispatch in your project.
> **Answer:**  
> Dynamic method dispatch is the mechanism by which a call to an overridden method is resolved at runtime rather than compile time.  
> In [`Main.java`](file:///g:/Projects/SWE_A1/src/main/Main.java):
> ```java
> User user = authService.login(username, password);
> System.out.println(user.getRoleDescription());
> ```
> Here, `user` is declared as reference type `User`, but at runtime, it may hold a `Student` or `Admin` instance. Java inspects the actual object on the heap and invokes the appropriate `getRoleDescription()` method dynamically.

---

### Category 4: Storage, Collections & Data Structures

#### Q9: Why did you use `Storage<T>` as a generic interface with two implementing classes?
> **Answer:**  
> This satisfies the **Dependency Inversion Principle** (from SOLID) and demonstrates interface polymorphism.  
> Services like `AuthService` and `FeedbackService` depend on the abstraction `Storage<T>` rather than a rigid file class. If we ever want to switch from CSV files to binary files or database storage, we create a new class implementing `Storage<T>` without modifying any service code.

#### Q10: Why is `HashMap` used for users and `ArrayList` for feedback?
> **Answer:**  
> - **`HashMap<String, User>`**: User authentication requires frequent username lookups. A `HashMap` provides constant time average complexity $O(1)$ for `get(username)` and `containsKey(username)`.
> - **`ArrayList<Feedback>`**: Feedback records are sequential and append-heavy. An `ArrayList` offers $O(1)$ amortized append time and is convenient for sequential filtering, sorting, and reporting.

#### Q11: How does sorting work with `java.util.Comparator`?
> **Answer:**  
> We use custom `Comparator` implementations:
> 1. In `FeedbackService.getFeedbackByStudent(...)`, we sort submissions descending by timestamp (`f2.getTimestamp().compareTo(f1.getTimestamp())`) so the newest feedback appears first.
> 2. In `ReportService`, we sort `TargetStat` objects by `getAverageRating()` in descending order to identify the highest and lowest rated campus entities.

---

### Category 5: Exception Handling

#### Q12: Why did you create custom checked exceptions instead of using runtime exceptions?
> **Answer:**  
> Custom checked exceptions (extending `java.lang.Exception`) represent recoverable, anticipated business errors that the compiler forces callers to handle:
> - `InvalidLoginException`: Signals bad credentials, prompting the user to re-enter.
> - `DuplicateUserException`: Warns when a chosen username already exists during registration.
> - `InvalidFeedbackException`: Caught when rating or comment length constraints fail.
> - `DuplicateFeedbackException`: Enforces the 24-hour business rule.
> By declaring these in the method signature (`throws ...`), we make the API self-documenting and prevent unhandled application crashes.

#### Q13: How does `try-with-resources` work, and why is it superior to `try-catch-finally`?
> **Answer:**  
> `try-with-resources` works with any class implementing `java.lang.AutoCloseable` (e.g., `BufferedReader`, `BufferedWriter`).  
> When execution exits the `try` block—whether normally or due to an exception—Java automatically invokes `.close()`. This eliminates manual boilerplate in `finally` blocks and prevents operating system file handle leaks.

---

### Category 6: Multithreading & Concurrency

#### Q14: Why is `AutoSaveService` run as a `daemon` thread?
> **Answer:**  
> In Java, user threads keep the JVM running even after the `main()` method terminates. A **daemon thread** is a background service provider that does not prevent the JVM from exiting. When the user selects "Exit System", the JVM terminates immediately without hanging on the background worker thread.

#### Q15: Why is `submitFeedback()` marked `synchronized`?
> **Answer:**  
> Because the background auto-save thread may read/save data while a student is submitting new feedback. The `synchronized` keyword ensures thread safety and prevents race conditions or `ConcurrentModificationException`.

