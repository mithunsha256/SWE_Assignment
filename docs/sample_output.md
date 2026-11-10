# Real Console Output - Campus Feedback System

Below is the verbatim console session recorded from a complete test run of the application, demonstrating:
1. First run initialization (automatic creation of `users.csv`, `feedback.csv`, and default admin).
2. Student registration (with duplicate username validation error handling).
3. Student login.
4. Submitting feedback for an **Event**, **Course**, and **Facility**.
5. 24-hour duplicate feedback rejection check (`DuplicateFeedbackException`).
6. Student feedback history display (sorted descending by timestamp).
7. Polymorphic role description display.
8. Admin login (`admin` / `admin123`).
9. Viewing all submitted feedbacks.
10. Filtering feedback by category (**Course**).
11. Analytical summary report via `Reportable` interface (counts, averages, top & lowest rated targets).
12. Admin role details.
13. Clean exit with automatic data persistence and background thread termination.

```text
========================================================
        WELCOME TO CAMPUS FEEDBACK SYSTEM
        Software Engineering & OOP Project
========================================================

--------------------------------------------------------
                     MAIN MENU
--------------------------------------------------------
  1. Student Login
  2. Student Registration (Sign Up)
  3. Admin Login (Default: admin / admin123)
  4. Exit System
--------------------------------------------------------
Enter choice (1-4): 2

>>> STUDENT REGISTRATION <<<
Enter desired username: john_doe
Enter password (min 6 characters): secret123
Enter your full name: John Doe
Enter your student roll number: CS2026-042

[SUCCESS] Registration successful! Welcome, John Doe.
You can now log in using your credentials.

Press Enter to continue...

--------------------------------------------------------
                     MAIN MENU
--------------------------------------------------------
  1. Student Login
  2. Student Registration (Sign Up)
  3. Admin Login (Default: admin / admin123)
  4. Exit System
--------------------------------------------------------
Enter choice (1-4): 2

>>> STUDENT REGISTRATION <<<
Enter desired username: john_doe
Enter password (min 6 characters): secret123
Enter your full name: John Doe
Enter your student roll number: CS2026-042

[REGISTRATION ERROR] Username 'john_doe' is already registered. Please choose another.

Press Enter to continue...

--------------------------------------------------------
                     MAIN MENU
--------------------------------------------------------
  1. Student Login
  2. Student Registration (Sign Up)
  3. Admin Login (Default: admin / admin123)
  4. Exit System
--------------------------------------------------------
Enter choice (1-4): 1

>>> STUDENT LOGIN <<<
Username: john_doe
Password: secret123

[SUCCESS] Login successful! Welcome back, John Doe.

========================================================
 STUDENT PORTAL: John Doe (CS2026-042)
========================================================
  1. Submit Feedback (Event, Course, or Facility)
  2. View My Feedback History
  3. View Role Information (Polymorphism Demo)
  4. Logout
--------------------------------------------------------
Enter choice (1-4): 1

>>> SUBMIT FEEDBACK <<<
Select Target Category:
  1. Campus Event
  2. Academic Course
  3. Campus Facility
  4. Cancel
Choose category (1-4): 1

Available Events:
  1. Tech Fest 2026 [Date: 2026-03-15 | Venue: Campus Auditorium]
  2. Sports Day 2026 [Date: 2026-04-10 | Venue: Main Athletic Ground]
  3. Cultural Night [Date: 2026-05-02 | Venue: Open Air Theatre]
  4. Hack-the-Cloud Hackathon [Date: 2026-06-20 | Venue: Computer Lab 4]
Select target (1-4): 1

Target Selected: Tech Fest 2026 (EVT-101)
Enter Star Rating (1 = Very Poor, 5 = Excellent):
Rating (1-5): 5
Enter your review comment (5 to 300 characters):
Comment: Outstanding organization and brilliant hackathon projects!

[SUCCESS] Thank you! Your feedback has been recorded.
Reference ID: FB-1794237005609
Rating      : [*****] (5/5)

Press Enter to continue...

========================================================
 STUDENT PORTAL: John Doe (CS2026-042)
========================================================
  1. Submit Feedback (Event, Course, or Facility)
  2. View My Feedback History
  3. View Role Information (Polymorphism Demo)
  4. Logout
--------------------------------------------------------
Enter choice (1-4): 1

>>> SUBMIT FEEDBACK <<<
Select Target Category:
  1. Campus Event
  2. Academic Course
  3. Campus Facility
  4. Cancel
Choose category (1-4): 2

Available Courses:
  1. Cloud Computing (CS401) [Code: CS401 | Faculty: Prof. Alan Turing]
  2. Data Structures & Algorithms (CS201) [Code: CS201 | Faculty: Dr. Grace Hopper]
  3. Machine Learning & AI (CS450) [Code: CS450 | Faculty: Dr. John McCarthy]
  4. Database Management Systems (CS302) [Code: CS302 | Faculty: Prof. Edgar Codd]
Select target (1-4): 2

Target Selected: Data Structures & Algorithms (CS201) (CRS-202)
Enter Star Rating (1 = Very Poor, 5 = Excellent):
Rating (1-5): 5
Enter your review comment (5 to 300 characters):
Comment: Exceptional lectures by Dr. Grace Hopper and very challenging assignments!

[SUCCESS] Thank you! Your feedback has been recorded.
Reference ID: FB-1794237005626
Rating      : [*****] (5/5)

Press Enter to continue...

========================================================
 STUDENT PORTAL: John Doe (CS2026-042)
========================================================
  1. Submit Feedback (Event, Course, or Facility)
  2. View My Feedback History
  3. View Role Information (Polymorphism Demo)
  4. Logout
--------------------------------------------------------
Enter choice (1-4): 1

>>> SUBMIT FEEDBACK <<<
Select Target Category:
  1. Campus Event
  2. Academic Course
  3. Campus Facility
  4. Cancel
Choose category (1-4): 3

Available Facilities:
  1. Central Library [Location: Academic Block A, 2nd Floor | Hours: 08:00 AM - 10:00 PM]
  2. Hostel Mess & Dining [Location: Student Dining Hall 1 | Hours: 07:00 AM - 09:30 PM]
  3. Campus Wi-Fi Network [Location: Across Entire Campus | Hours: 24/7]
  4. Student Sports Complex [Location: West Campus Zone | Hours: 06:00 AM - 08:30 PM]
Select target (1-4): 1

Target Selected: Central Library (FAC-301)
Enter Star Rating (1 = Very Poor, 5 = Excellent):
Rating (1-5): 4
Enter your review comment (5 to 300 characters):
Comment: Quiet study spaces and vast collection of books, but Wi-Fi can be improved in corners.

[SUCCESS] Thank you! Your feedback has been recorded.
Reference ID: FB-1794237005629
Rating      : [****-] (4/5)

Press Enter to continue...

========================================================
 STUDENT PORTAL: John Doe (CS2026-042)
========================================================
  1. Submit Feedback (Event, Course, or Facility)
  2. View My Feedback History
  3. View Role Information (Polymorphism Demo)
  4. Logout
--------------------------------------------------------
Enter choice (1-4): 1

>>> SUBMIT FEEDBACK <<<
Select Target Category:
  1. Campus Event
  2. Academic Course
  3. Campus Facility
  4. Cancel
Choose category (1-4): 1

Available Events:
  1. Tech Fest 2026 [Date: 2026-03-15 | Venue: Campus Auditorium]
  2. Sports Day 2026 [Date: 2026-04-10 | Venue: Main Athletic Ground]
  3. Cultural Night [Date: 2026-05-02 | Venue: Open Air Theatre]
  4. Hack-the-Cloud Hackathon [Date: 2026-06-20 | Venue: Computer Lab 4]
Select target (1-4): 1

Target Selected: Tech Fest 2026 (EVT-101)
Enter Star Rating (1 = Very Poor, 5 = Excellent):
Rating (1-5): 4
Enter your review comment (5 to 300 characters):
Comment: Trying to review Tech Fest 2026 a second time within 24 hours.

[SUBMISSION REJECTED] Duplicate feedback rejected: You already reviewed 'Tech Fest 2026' 0 hour(s) ago. Please wait 24 more hour(s).

Press Enter to continue...

========================================================
 STUDENT PORTAL: John Doe (CS2026-042)
========================================================
  1. Submit Feedback (Event, Course, or Facility)
  2. View My Feedback History
  3. View Role Information (Polymorphism Demo)
  4. Logout
--------------------------------------------------------
Enter choice (1-4): 2

>>> MY FEEDBACK HISTORY <<<
Found 3 submission(s) (Sorted newest first):

--- Feedback #1 ---
[FB-1794237005629] Target: Central Library (Facility) | Rating: [****-] (4/5) | By: john_doe | Time: 2026-11-09 20:40:05
Comment: "Quiet study spaces and vast collection of books, but Wi-Fi can be improved in corners."

--- Feedback #2 ---
[FB-1794237005626] Target: Data Structures & Algorithms (CS201) (Course) | Rating: [*****] (5/5) | By: john_doe | Time: 2026-11-09 20:40:05
Comment: "Exceptional lectures by Dr. Grace Hopper and very challenging assignments!"

--- Feedback #3 ---
[FB-1794237005609] Target: Tech Fest 2026 (Event) | Rating: [*****] (5/5) | By: john_doe | Time: 2026-11-09 20:40:05
Comment: "Outstanding organization and brilliant hackathon projects!"


Press Enter to continue...

========================================================
 STUDENT PORTAL: John Doe (CS2026-042)
========================================================
  1. Submit Feedback (Event, Course, or Facility)
  2. View My Feedback History
  3. View Role Information (Polymorphism Demo)
  4. Logout
--------------------------------------------------------
Enter choice (1-4): 3

--- User Role Details (Polymorphic Method Call) ---
Class Type       : Student
Role Name        : Student
Role Description : Student user eligible to submit and review campus feedback.

Press Enter to continue...

========================================================
 STUDENT PORTAL: John Doe (CS2026-042)
========================================================
  1. Submit Feedback (Event, Course, or Facility)
  2. View My Feedback History
  3. View Role Information (Polymorphism Demo)
  4. Logout
--------------------------------------------------------
Enter choice (1-4): 4

Logged out successfully.

--------------------------------------------------------
                     MAIN MENU
--------------------------------------------------------
  1. Student Login
  2. Student Registration (Sign Up)
  3. Admin Login (Default: admin / admin123)
  4. Exit System
--------------------------------------------------------
Enter choice (1-4): 3

>>> ADMIN LOGIN <<<
Admin Username: admin
Admin Password: admin123

[SUCCESS] Admin authenticated! Welcome, System Administrator.

========================================================
 ADMIN PORTAL: System Administrator (Campus Administration)
========================================================
  1. View All Feedback Submissions
  2. Filter Feedback by Target Type (Event / Course / Facility)
  3. View Summary Report & Analytics (Reportable Interface)
  4. View Role Information (Polymorphism Demo)
  5. Logout
--------------------------------------------------------
Enter choice (1-5): 1

>>> ALL FEEDBACK SUBMISSIONS <<<
Total records: 3

[FB-1794237005609] Target: Tech Fest 2026 (Event) | Rating: [*****] (5/5) | By: john_doe | Time: 2026-11-09 20:40:05
Comment: "Outstanding organization and brilliant hackathon projects!"
----------------------------------------------------------------------------------------
[FB-1794237005626] Target: Data Structures & Algorithms (CS201) (Course) | Rating: [*****] (5/5) | By: john_doe | Time: 2026-11-09 20:40:05
Comment: "Exceptional lectures by Dr. Grace Hopper and very challenging assignments!"
----------------------------------------------------------------------------------------
[FB-1794237005629] Target: Central Library (Facility) | Rating: [****-] (4/5) | By: john_doe | Time: 2026-11-09 20:40:05
Comment: "Quiet study spaces and vast collection of books, but Wi-Fi can be improved in corners."
----------------------------------------------------------------------------------------

Press Enter to continue...

========================================================
 ADMIN PORTAL: System Administrator (Campus Administration)
========================================================
  1. View All Feedback Submissions
  2. Filter Feedback by Target Type (Event / Course / Facility)
  3. View Summary Report & Analytics (Reportable Interface)
  4. View Role Information (Polymorphism Demo)
  5. Logout
--------------------------------------------------------
Enter choice (1-5): 2

>>> FILTER FEEDBACK BY TARGET TYPE <<<
  1. Filter by Events
  2. Filter by Courses
  3. Filter by Facilities
  4. Back
Select filter option (1-4): 2

Feedback records for category: Course
Total matching records: 1

[FB-1794237005626] Target: Data Structures & Algorithms (CS201) (Course) | Rating: [*****] (5/5) | By: john_doe | Time: 2026-11-09 20:40:05
Comment: "Exceptional lectures by Dr. Grace Hopper and very challenging assignments!"
----------------------------------------------------------------------------------------

Press Enter to continue...

========================================================
 ADMIN PORTAL: System Administrator (Campus Administration)
========================================================
  1. View All Feedback Submissions
  2. Filter Feedback by Target Type (Event / Course / Facility)
  3. View Summary Report & Analytics (Reportable Interface)
  4. View Role Information (Polymorphism Demo)
  5. Logout
--------------------------------------------------------
Enter choice (1-5): 3

========================================================================================
                             CAMPUS FEEDBACK EXECUTIVE SUMMARY
========================================================================================
  * Total Feedback Submissions : 3
  * Overall Average Rating     : 4.67 / 5.00 ([*****])
----------------------------------------------------------------------------------------
  BREAKDOWN PER TARGET:
  Type       | Target Name                      | ID         | Count    | Avg Rating  
  --------------------------------------------------------------------------------------
  Facility   | Campus Wi-Fi Network             | FAC-303    | 0        | N/A (0)     
  Facility   | Central Library                  | FAC-301    | 1        | 4.00 / 5.0  
  Course     | Cloud Computing (CS401)          | CRS-201    | 0        | N/A (0)     
  Event      | Cultural Night                   | EVT-103    | 0        | N/A (0)     
  Course     | Data Structures & Algorithms ... | CRS-202    | 1        | 5.00 / 5.0  
  Course     | Database Management Systems (... | CRS-204    | 0        | N/A (0)     
  Event      | Hack-the-Cloud Hackathon         | EVT-104    | 0        | N/A (0)     
  Facility   | Hostel Mess & Dining             | FAC-302    | 0        | N/A (0)     
  Course     | Machine Learning & AI (CS450)    | CRS-203    | 0        | N/A (0)     
  Event      | Sports Day 2026                  | EVT-102    | 0        | N/A (0)     
  Facility   | Student Sports Complex           | FAC-304    | 0        | N/A (0)     
  Event      | Tech Fest 2026                   | EVT-101    | 1        | 5.00 / 5.0  
----------------------------------------------------------------------------------------
  TARGET PERFORMANCE HIGHLIGHTS:
  [+] HIGHEST RATED : Data Structures & Algorithms (CS201) (Course) - 5.00 / 5.00 (1 review)
  [-] LOWEST RATED  : Central Library (Facility) - 4.00 / 5.00 (1 review)
========================================================================================


Press Enter to continue...

========================================================
 ADMIN PORTAL: System Administrator (Campus Administration)
========================================================
  1. View All Feedback Submissions
  2. Filter Feedback by Target Type (Event / Course / Facility)
  3. View Summary Report & Analytics (Reportable Interface)
  4. View Role Information (Polymorphism Demo)
  5. Logout
--------------------------------------------------------
Enter choice (1-5): 4

--- User Role Details (Polymorphic Method Call) ---
Class Type       : Admin
Role Name        : Admin
Role Description : Campus Administrator with access to all feedback records, analytics, and reports.

Press Enter to continue...

========================================================
 ADMIN PORTAL: System Administrator (Campus Administration)
========================================================
  1. View All Feedback Submissions
  2. Filter Feedback by Target Type (Event / Course / Facility)
  3. View Summary Report & Analytics (Reportable Interface)
  4. View Role Information (Polymorphism Demo)
  5. Logout
--------------------------------------------------------
Enter choice (1-5): 5

Logged out from Admin Portal.

--------------------------------------------------------
                     MAIN MENU
--------------------------------------------------------
  1. Student Login
  2. Student Registration (Sign Up)
  3. Admin Login (Default: admin / admin123)
  4. Exit System
--------------------------------------------------------
Enter choice (1-4): 4

Thank you for using Campus Feedback System. Goodbye!
[System] Data successfully saved to data/ folder. Session closed.
```

