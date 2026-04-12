import java.util.List;
import java.util.Scanner;

public class CourseMenu {
    private final CourseConfig config;
    private final Scanner scanner;
    
    public CourseMenu(CourseConfig config, Scanner scanner) {
        this.config = config;
        this.scanner = scanner;
    }
    
    //main
    public void courseExecute() {
        boolean keepRunning = true;
        while (keepRunning) {
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
                case "6": keepRunning = false; break;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    //view all courses
    private void viewAllCourses() {
        List<Course> courses = config.getAllCourses();
        System.out.println("\n=== All Registered Courses (" + config.getCount() + ") ===");
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        for (Course c : courses) {
            System.out.println(c); 
        }
    }
    
    //add course
    private void addCourse() {
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
    
    //search course
    private void searchCourse() {
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
    
    //edit course
    private void editCourse() {
        System.out.print("Enter Course Code to edit: ");
        String code = scanner.nextLine().trim().toUpperCase();
        Course course = config.findCourseByCode(code);
        
        if (course == null) {
            System.out.println("Course not found: " + code);
            return;
        }
        
        //display current details
        System.out.println("\nCurrent Course Details:");
        System.out.println(course);
        System.out.println("(Note: Course Code cannot be changed)");
        
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
    
    //delete course
    private void deleteCourse() {
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
}