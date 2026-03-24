import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SLMS {
    private static List<Student> studentList = new ArrayList<>();
    private static final CourseConfig config = new CourseConfig();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        //Sample tests
        config.addCourse(new Course("Software Security", "CCSB5113", 3, "Cybersecurity in software", "https://ccsb5113.msteams"));
        config.addCourse(new Course("Software Quality", "CSEB4113", 3, "Software Quality Assurance", "https://cseb4113.msteams"));

        boolean execute = true;
        
        System.out.println("=== STUDENT LEARNING MANAGEMENT SYSTEM ===");
        
        while (execute) {
            System.out.println("\n1. Course Management");
            System.out.println("2. Student Management");
            System.out.println("3. Exit");
            System.out.print("Select module: ");
            
            String profileChoice = scanner.nextLine();
            
            switch (profileChoice) {
                case "1": printCourseMenu(); break;
                case "2": printStudentMenu(); break;
                case "3": 
                    execute = false; 
                    System.out.println("System exited. Goodbye!");
                    break;
                default: 
                    System.out.println("Invalid selection. Please try again.");
            }
        }
        scanner.close();
    }
    
    // ==================== COURSE MENU ====================
    private static void printCourseMenu() {
        boolean courseMenuExecute = true;
        while(courseMenuExecute) {
            System.out.println("\n=== COURSE PROFILE MANAGEMENT ===");
            System.out.println("1. View All Courses");
            System.out.println("2. Add New Course");
            System.out.println("3. Search Course by Code");
            System.out.println("4. Edit Course");
            System.out.println("5. Delete Course");
            System.out.println("6. Back to Main Menu");
            
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1": viewAllCourses(); break;
                case "2": addCourse(); break;
                case "3": searchCourse(); break;
                case "4": editCourse(); break;
                case "5": deleteCourse(); break;
                case "6": courseMenuExecute = false; break;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ==================== STUDENT MENU ====================
    private static void printStudentMenu() {
        boolean studentMenuExecute = true;
        while(studentMenuExecute) {  
            System.out.println("\n=== STUDENT PROFILE MANAGEMENT ===");
            System.out.println("1. Add Student Profile");
            System.out.println("2. Search Student");
            System.out.println("3. Edit Student");
            System.out.println("4. Delete Student");
            System.out.println("5. View All Students");
            System.out.println("6. Back to Main Menu");  
            
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
        
            switch (choice) {
                case "1": addStudent(); break;
                case "2": searchStudent(); break;
                case "3": editStudent(); break;
                case "4": deleteStudent(); break;
                case "5": viewAllStudents(); break;
                case "6": studentMenuExecute = false; break; 
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ==================== COURSE FUNCTIONS ====================
    private static void viewAllCourses() {
        List<Course> courses = config.getAllCourses();
        System.out.println("\n=== All Registered Courses (" + config.getCount() + ") ===");
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        for (Course c : courses) {
            System.out.println(c); // Uses Course.toString()
        }
    }
    
    private static void addCourse() {
        System.out.println("\n-- Add New Course --");
        
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine().trim().toUpperCase();
        
        if (config.findCourseByCode(code) != null) {
            System.out.println("Error: Course Code '" + code + "' already exists!");
            return;
        }
        
        System.out.print("Enter Course Name (Full): ");
        String name = scanner.nextLine();
        
        int credits = 0;
        try {
            System.out.print("Enter Credit Hours: ");
            credits = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number for credits!");
            return;
        }
        
        System.out.print("Enter Course Summary: ");
        String summary = scanner.nextLine();
        
        System.out.print("Enter MS Teams Link: ");
        String link = scanner.nextLine();
        
        Course newCourse = new Course(name, code, credits, summary, link);
        if (config.addCourse(newCourse)) {
            System.out.println("Course submitted successfully!");
            viewAllCourses();
        } else {
            System.out.println("Failed to add Course.");
        }
    }
    
    private static void searchCourse() {
        System.out.print("Enter Course Code to search: ");
        String code = scanner.nextLine().trim().toUpperCase();
        Course course = config.findCourseByCode(code);
        
        if (course != null) {
            System.out.println("\nFound:");
            System.out.println(course);
        } else { 
            System.out.println("Course not found with Code: " + code);
        }
    }
    
    private static void editCourse() {
        System.out.print("Enter Course Code to edit: ");
        String code = scanner.nextLine().trim().toUpperCase();
        Course course = config.findCourseByCode(code);
        
        if (course == null) {
            System.out.println("Course not found: " + code);
            return;
        }
        
        // Display current details
        System.out.println("\nCurrent Course Details:");
        System.out.println(course);
        System.out.println("(Note: Course Code cannot be changed)");
        
        // Edit fields with "keep current" option
        System.out.print("Enter new Course Name (or press Enter to keep current): ");
        String newName = scanner.nextLine();
        if (newName.isEmpty()) newName = course.getCourseName();
            
        System.out.print("Enter new Credit Hours (or press Enter to keep current): ");
        String credInput = scanner.nextLine();
        int newCredits = course.getCreditHour();
        if (!credInput.isEmpty()) {
            try {
                newCredits = Integer.parseInt(credInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid credit number, keeping old value.");
            }
        }
        
        System.out.print("Enter new Summary (or press Enter to keep current): ");
        String newSum = scanner.nextLine();
        if (newSum.isEmpty()) newSum = course.getSummary();
            
        System.out.print("Enter new Teams Link (or press Enter to keep current): ");
        String newLink = scanner.nextLine();
        if (newLink.isEmpty()) newLink = course.getTeamsLink();
        
        if (config.editCourse(code, newName, newCredits, newSum, newLink)) {
            System.out.println("Course updated successfully!");
            System.out.println("\nUpdated Details:");
            System.out.println(config.findCourseByCode(code));
            viewAllCourses();
        } else {
            System.out.println("Error updating course.");
        }
    }
    
    private static void deleteCourse() {
        System.out.print("Enter Course Code to delete: ");
        String code = scanner.nextLine().trim().toUpperCase();
        
        Course course = config.findCourseByCode(code);
        
        if (course == null) {
            System.out.println("Course not found: " + code);
            return;
        }
        
        System.out.println("\nAre you sure you want to delete this course?");
        System.out.println(course);
        System.out.print("Type 'YES' to confirm deletion: ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("YES")) {
            if (config.deleteCourse(code)) {
                System.out.println("Course deleted successfully!");
                viewAllCourses();
            } else {
                System.out.println("Error deleting course.");
            } 
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // ==================== STUDENT FUNCTIONS ====================
    private static void addStudent() {
        System.out.println("\n-- Add New Student --");
        
        System.out.print("First Name: ");
        String fName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lName = scanner.nextLine();
        System.out.print("Student ID: ");
        String sID = scanner.nextLine().trim().toUpperCase();

        // Check for duplicate ID
        if (findStudentIndexById(sID) != -1) {
            System.out.println("Error: Student ID '" + sID + "' already exists!");
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        Student newStudent = new Student(fName, lName, sID, email, phone);
        studentList.add(newStudent);
        
        System.out.println("Student profile submitted successfully!");
        viewAllStudents(); // Display after action
    }

    private static void searchStudent() {
        System.out.print("\nEnter Student ID to search: ");
        String searchID = scanner.nextLine().trim().toUpperCase();

        int index = findStudentIndexById(searchID);

        if (index != -1) {
            System.out.println("\nStudent Found:");
            displaySingleStudent(studentList.get(index));
        } else {
            System.out.println("Student not found with ID: " + searchID);
        }
    }

    private static void editStudent() {
        System.out.print("\nEnter Student ID to edit: ");
        String searchID = scanner.nextLine().trim().toUpperCase();
        int index = findStudentIndexById(searchID);

        if (index != -1) {
            Student s = studentList.get(index);
            System.out.println("\n--- Edit Profile (ID cannot be changed) ---");
            System.out.println("Current Name: " + s.getFullName());
            
            System.out.print("New First Name (or press Enter to keep): ");
            String newFName = scanner.nextLine();
            if (!newFName.isEmpty()) s.setFirstName(newFName);
            
            System.out.print("New Last Name (or press Enter to keep): ");
            String newLName = scanner.nextLine();
            if (!newLName.isEmpty()) s.setLastName(newLName);
            
            System.out.print("New Email (or press Enter to keep): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) s.setEmail(newEmail);
            
            System.out.print("New Phone (or press Enter to keep): ");
            String newPhone = scanner.nextLine();
            if (!newPhone.isEmpty()) s.setPhoneNumber(newPhone);

            System.out.println("\nProfile updated successfully!");
            displaySingleStudent(s);
            viewAllStudents(); // Display after action
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void deleteStudent() {
        System.out.print("\nEnter Student ID to delete: ");
        String searchID = scanner.nextLine().trim().toUpperCase();
        int index = findStudentIndexById(searchID);

        if (index != -1) {
            System.out.print("Are you sure you want to delete " + 
                           studentList.get(index).getFullName() + "? (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                studentList.remove(index); 
                System.out.println("Student deleted successfully!");
                viewAllStudents(); // Display changes
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void viewAllStudents() {
        System.out.println("\n=== All Student Records ===");
        if (studentList.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }

        System.out.printf("%-12s %-25s %-30s %-15s%n", "ID", "Name", "Email", "Phone");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Student s : studentList) {
            System.out.printf("%-12s %-25s %-30s %-15s%n", 
                s.getStudentID(), 
                s.getFullName(), 
                s.getEmail(), 
                s.getPhoneNumber());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    private static void displaySingleStudent(Student s) {
        System.out.println("================================");
        System.out.println("ID    : " + s.getStudentID());
        System.out.println("Name  : " + s.getFullName());
        System.out.println("Email : " + s.getEmail());
        System.out.println("Phone : " + s.getPhoneNumber());
        System.out.println("================================");
    }
    
    //Linear search for Student by ID
    private static int findStudentIndexById(String id) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1; // Not found
    }
}