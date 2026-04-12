import java.util.List;
import java.util.Scanner;

public class CourseMenu {
    private final CourseConfig config;
    private final Scanner scanner;
    private final CacheApi cacheApi;
    
    public CourseMenu(CourseConfig config, Scanner scanner, CacheApi cacheApi) {
        this.config = config;
        this.scanner = scanner;
        this.cacheApi = cacheApi;
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
    
    //auto-suggestion helper
    private void showCourseSuggestions(String prefix) {
        if (prefix.length() < 2) return;
        List<String> hints = cacheApi.suggest(prefix, "course");
        if (!hints.isEmpty()) {
            System.out.println("Suggestions: " + hints); 
        }
    }
    
    //view all courses
    private void viewAllCourses() {
        Course[] courses = config.getAllCourses();
        System.out.println("\n=== All Registered Courses (" + config.getCount() + ") ===");
        
        if (courses.length == 0) {
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
        
        //course type input 
        String courseType = "core";
        System.out.print("Enter Course Type (core/elective/university) [default: core]: ");
        String typeInput = scanner.nextLine().trim().toLowerCase();
        if (!typeInput.isEmpty()) {
            if (typeInput.equals("core") || typeInput.equals("elective") || typeInput.equals("university")) {
                courseType = typeInput;
            } else {
                System.out.println("Invalid type. Defaulting to 'core'.");
            }
        }
        
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
        
        Course newCourse = new Course(name, code, credits, summary, link, courseType);
        
        if (config.addCourse(newCourse)) {
            System.out.println("Course submitted successfully!");
            cacheApi.cache(code, newCourse);
            cacheApi.cache(name.toLowerCase(), newCourse);
            viewAllCourses();
        } else {
            System.out.println("Failed to add Course.");
        }
    }
    
    //search course
    private void searchCourse() {
        System.out.print("Enter Course Code to search: ");
        String code = scanner.nextLine().trim().toUpperCase();
        
        //show suggestions 
        if (code.length() >= 2) {
            showCourseSuggestions(code);
        }
        
        Object cached = cacheApi.getCached(code);
        if (cached instanceof Course) {
            System.out.println("\nFound (from cache):");
            System.out.println((Course) cached);
            return;
        }
        
        Course course = config.findCourseByCode(code);
        
        if (course != null) {
            System.out.println("\nFound:");
            System.out.println(course);
            cacheApi.cache(code, course);
            cacheApi.cache(course.getCourseName().toLowerCase(), course);
        } else {
            System.out.println("Course not found with Code: " + code);
        }
    }
    
    //edit course - w course type
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
        
        System.out.print("Enter new Course Type (core/elective/university) or press Enter to keep current: ");
        String newTypeInput = scanner.nextLine().trim().toLowerCase();
        String newType = course.getCourseType();
        if (!newTypeInput.isEmpty()) {
            if (newTypeInput.equals("core") || newTypeInput.equals("elective") || newTypeInput.equals("university")) {
               newType = newTypeInput; 
            } else {
                System.out.println("Invalid type. Keeping current type: " + course.getCourseType());
            }
        }
        
        System.out.print("Enter new Summary (or press Enter to keep current): ");
        String newSum = scanner.nextLine();
        if (newSum.isEmpty()) newSum = course.getSummary();
            
        System.out.print("Enter new Teams Link (or press Enter to keep current): ");
        String newLink = scanner.nextLine();
        if (newLink.isEmpty()) newLink = course.getTeamsLink();
        
        if (config.editCourse(code, newName, newCredits, newSum, newLink)) {
            course.setCourseType(newType);
            System.out.println("Course updated successfully!");
            
            //update cache
            cacheApi.cache(code, course);
            cacheApi.cache(newName.toLowerCase(), course);
            
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
                cacheApi.cache(code, null); //remove from cache
                viewAllCourses();
            } else {
                System.out.println("Error deleting course.");
            } 
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}