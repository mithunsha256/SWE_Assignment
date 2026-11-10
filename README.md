# Campus Feedback System

A lightweight, console-based Java application developed as a Software Engineering college mini-project and submitted as an Object-Oriented Programming (OOP) Lab Record. Built purely with Core Java (JDK 17+), without external dependencies, build tools, frameworks, or databases.

---

## 📌 Project Overview

The **Campus Feedback System** allows college students to provide structured ratings and reviews for key campus entities, categorized into:
- **Events:** Tech Fest 2026, Sports Day 2026, Cultural Night, Hack-the-Cloud Hackathon
- **Courses:** Cloud Computing (CS401), Data Structures & Algorithms (CS201), Machine Learning & AI (CS450), Database Management Systems (CS302)
- **Facilities:** Central Library, Hostel Mess & Dining, Campus Wi-Fi Network, Student Sports Complex

Campus Administrators can view, filter, and analyze student submissions through aggregated performance reports and identify the highest and lowest rated targets.

---

## ✨ Features

- **Role-Based Access Control:** Dedicated portals for **Students** and **Administrators**.
- **Student Sign-Up & Login:** Secure authentication with duplicate username detection and minimum password length enforcement.
- **Default Administrator Account:** Automatically initialized on first execution (`admin` / `admin123`).
- **Interactive Console Menu:** Resilient menu navigation using `Scanner` with input protection against mismatch exceptions.
- **Target Feedback Submission:** Predefined target catalog, 1–5 star ratings, and 5–300 character feedback comments.
- **24-Hour Duplication Rule:** Prevents spamming by barring duplicate reviews for the same target by the same student within 24 hours.
- **Personal Feedback History:** Reverse-chronological history display sorted using custom `java.util.Comparator`.
- **Administrative Filtering & Analytics:** Filter feedback by category and generate executive summaries with counts, overall average ratings, and highest/lowest performing entities.
- **Plain Text / CSV Persistence:** Data saved to `data/users.csv` and `data/feedback.csv` using RFC 4180-compliant CSV escaping.
- **Background Auto-Save Thread:** Demonstrates Java multithreading via a daemon thread periodically persisting data.

---

## 🏛️ OOP Concepts Demonstrated

This project is tailored for academic lab evaluation and viva examinations. Every core OOP concept is implemented in clear, beginner-friendly code:

| OOP Concept | Implementation Highlights |
|---|---|
| **Encapsulation** | Private fields, validation invariants, public getters and setters across all models. |
| **Inheritance & Abstract Classes** | `abstract class User` $\rightarrow$ `Student`, `Admin`<br>`abstract class FeedbackTarget` $\rightarrow$ `Event`, `Course`, `Facility` |
| **Interfaces & Polymorphism** | `Storage<T>` implemented by `UserFileStorage` and `FeedbackFileStorage`<br>`Reportable` implemented by `ReportService` |
| **Method Overriding** | `@Override` of `getRoleDescription()`, `toString()`, and `generateReport()` |
| **Method Overloading** | Overloaded `submitFeedback(...)` and `generateReport(...)` signatures |
| **Constructors & Chaining** | Primary and overloaded constructors chaining with `this(...)` and `super(...)` |
| **Static & Final Modifiers** | Static counter in `User`, static constants and utility methods in `Validator`, `final` entity IDs |
| **Custom Checked Exceptions** | `InvalidLoginException`, `DuplicateUserException`, `InvalidFeedbackException`, `DuplicateFeedbackException` |
| **Resource Management** | Safe `try-catch-finally` handling and deterministic `try-with-resources` stream closures |
| **Collections & Sorting** | `ArrayList`, `HashMap`, and `Comparator` sorting |
| **File I/O** | `BufferedReader` and `BufferedWriter` reading and writing CSV files |
| **Multithreading** | Daemon background thread (`AutoSaveService`) running `Runnable` |

For a complete line-by-line mapping table, see [docs/oop_concepts.md](docs/oop_concepts.md).

---

## 📂 Folder Structure

```
SWE_A1/
├── bin/                          # Compiled Java bytecode (.class files)
├── data/                         # CSV storage (auto-created if missing)
│   ├── feedback.csv              # Feedback records
│   └── users.csv                 # Student and Admin accounts
├── docs/                         # Software Engineering documentation
│   ├── class_diagram.md          # Complete Mermaid class diagram
│   ├── oop_concepts.md           # OOP concepts mapping table for lab records
│   ├── requirements.md           # Software Requirements Specification (SRS)
│   ├── sample_output.md          # Real console test run transcript
│   └── viva_questions.md         # Comprehensive Viva Voce Q&A preparation guide
├── src/                          # Java source code
│   ├── exception/                # Custom checked exceptions
│   │   ├── DuplicateFeedbackException.java
│   │   ├── DuplicateUserException.java
│   │   ├── InvalidFeedbackException.java
│   │   └── InvalidLoginException.java
│   ├── main/                     # Application entry point
│   │   └── Main.java
│   ├── model/                    # Domain entities and OOP hierarchies
│   │   ├── Admin.java
│   │   ├── Course.java
│   │   ├── Event.java
│   │   ├── Facility.java
│   │   ├── Feedback.java
│   │   ├── FeedbackTarget.java
│   │   ├── Student.java
│   │   └── User.java
│   ├── service/                  # Business logic and analytical services
│   │   ├── AuthService.java
│   │   ├── AutoSaveService.java
│   │   ├── FeedbackService.java
│   │   ├── Reportable.java
│   │   └── ReportService.java
│   ├── storage/                  # Persistence contracts and CSV implementations
│   │   ├── FeedbackFileStorage.java
│   │   ├── Storage.java
│   │   └── UserFileStorage.java
│   └── util/                     # Validation rules and console helpers
│       ├── InputHelper.java
│       └── Validator.java
├── build_and_run.bat             # One-click Windows build & run script
├── build_and_run.sh              # One-click Linux/macOS build & run script
└── README.md                     # Project overview and run guide
```

---

## 🚀 How to Compile and Run

The application uses standard Java SE. No build tools or third-party packages are required.

### Prerequisites
- Java Development Kit (JDK 17 or later, e.g. JDK 21 / 25).
- Verify installation by running:
  ```bash
  javac -version
  java -version
  ```

---

### Step 1: Compile the Project

From the root project directory (`SWE_A1`), compile all Java source files into the `bin` directory:

#### On Windows (PowerShell):
```powershell
javac -d bin (Get-ChildItem -Recurse -Filter *.java src | Select-Object -ExpandProperty FullName)
```

#### On Windows (Command Prompt - CMD):
```cmd
if not exist bin mkdir bin
javac -d bin src/exception/*.java src/model/*.java src/storage/*.java src/service/*.java src/util/*.java src/main/*.java
```

#### On Linux / macOS / Bash:
```bash
mkdir -p bin
javac -d bin $(find src -name "*.java")
```

---

### Step 2: Run the Application

Launch the main console program with:

```bash
java -cp bin main.Main
```

---

## 🔑 Default Credentials

- **Admin Account (Auto-Created):**
  - **Username:** `admin`
  - **Password:** `admin123`
- **Student Account:**
  - Create any new student account using option **2 (Student Registration)** from the main menu.

---

## 📋 Documentation Quick Links
- [Software Requirements Specification (SRS)](docs/requirements.md)
- [Mermaid Class Diagram](docs/class_diagram.md)
- [OOP Concepts Lab Record Mapping](docs/oop_concepts.md)
- [Complete Console Test Run Output](docs/sample_output.md)

