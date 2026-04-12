import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SLMS {
    private static List<Student> studentList = new ArrayList<>();
    private static final CourseConfig config = new CourseConfig();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        //sample tests
        config.addCourse(new Course("Software Security", "CCSB5113", 3, "Cybersecurity in software", "https://ccsb5113.msteams"));
        config.addCourse(new Course("Software Quality", "CSEB4113", 3, "Software Quality Assurance", "https://cseb4113.msteams"));

        boolean execute = true;
        
        System.out.println("=== STUDENT LEARNING MANAGEMENT SYSTEM ===");
        
        while (execute) {
            System.out.println("\n1. Course Management");
            System.out.println("2. Student Management");
            System.out.println("3. Exit");
            System.out.print("Select module: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1": CourseMenu courseMenu = new CourseMenu(config, scanner);
                          courseMenu.courseExecute(); 
                          break;
                case "2": StudentMenu studentMenu = new StudentMenu(studentList, scanner);
                          studentMenu.studentExecute(); 
                          break;
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
}