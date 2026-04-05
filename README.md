# Complaint_Management_System
I’m building this Java app to handle customer complaints. Right now, I’m focusing on the 'brain' of the system—making sure the logic for users and complaints is solid and that the app handles errors properly without crashing. It’s a work in progress, but the core engine is ready

Complaint Management System (Java)
Work In Progress (WIP)
This project is currently in active development. The core business logic and in-memory data structures are complete. The next phases include Database Persistence (JDBC) and a Graphical User Interface (Swing).

Project Overview
A system designed to manage customer complaints, track resolution status, and provide administrative oversight. The project emphasizes clean code, separation of concerns, and robust error handling.
Features (Completed)
User Identity System: Registration and login functionality with unique ID generation.
Session Management: Tracks the current authenticated user across different service modules.
Complaint Lifecycle: Authenticated users can file complaints linked directly to their unique User ID.
Custom Exceptions: Implements a sophisticated error-handling layer (e.g., UserAlreadyExistException, IllegalArgumentException) to prevent data corruption and improve user experience.
Role-Based Architecture: Defined UserRole (Admin/Customer) system to prepare for access control logic.
Tech Stack and Concepts
Language: Java

Architecture: Manager-Model Design Pattern

Collections: HashMaps for O(1) search efficiency.

Principles: Encapsulation, Polymorphism, and Instance-based Memory Management.
Project Structure
model/: Data entities (User, Complaint, UserRole).
Service/: Business logic and session handling (UserManager, ComplaintManager).
exceptions/: Custom error classes.

Main.java: The integration bridge and console entry point.
How to Run (Linux/Terminal)

To compile the project from the root directory:
code
Bash
javac -d . model/*.java exceptions/*.java Service/*.java Main.java
To run the console application:
code
Bash
java Main
