import java.util.Scanner;
import java.util.List; 

public class EnrollmentMenu {
    private final EnrollmentManager enrollmentMgr;
    private final Scanner scanner;
    private final CacheApi cacheApi;
    
    public EnrollmentMenu(EnrollmentManager enrollmentMgr, Scanner scanner, CacheApi cacheApi) {
        this.enrollmentMgr = enrollmentMgr;
        this.scanner = scanner;
        this.cacheApi = cacheApi;
    }
    
    //enrollment menu
    public void enrollmentExecute() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n=== ENROLLMENT MANAGEMENT ===");
            System.out.println("1. Add Course to Student");
            System.out.println("2. Add Student to Course");
            System.out.println("3. Find Student's Courses");
            System.out.println("4. List Student's Courses");
            System.out.println("5. Find Course's Students");
            System.out.println("6. List Course's Students");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1": addCourseToStudent(); break;
                case "2": addStudentToCourse(); break;
                case "3": findStudentCourses(); break;
                case "4": listStudentCourses(); break;
                case "5": findCourseStudents(); break;
                case "6": listCourseStudents(); break;
                case "7": keepRunning = false; break;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    //auto-suggestion
    private void showStudentSuggestions(String prefix) {
        if (prefix.length() < 2) return;
        List<String> hints = cacheApi.suggest(prefix, "student");
        if (!hints.isEmpty()) {
            System.out.println("Student suggestions: " + hints);
        }
    }
    
    //auto-suggestion
    private void showCourseSuggestions(String prefix) {
        if (prefix.length() < 2) return;
        List<String> hints = cacheApi.suggest(prefix, "course");
        if (!hints.isEmpty()) {
            System.out.println("Course suggestions: " + hints);
        }
    }
    
    //add course to student
    private void addCourseToStudent() {
        System.out.println("\n-- Enroll Student in Course --");
        
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim().toUpperCase();
        if (studentId.length() >= 2) showStudentSuggestions(studentId);
        
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();
        if (courseCode.length() >= 2) showCourseSuggestions(courseCode);
        
        enrollmentMgr.addCourse(studentId, courseCode);
    }
    
    //add student to course
    private void addStudentToCourse() {
        System.out.println("\n-- Add Student to Course --");
        
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();
        if (courseCode.length() >= 2) showCourseSuggestions(courseCode);
        
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine().trim().toUpperCase();
        if (studentId.length() >= 2) showStudentSuggestions(studentId);
        
        enrollmentMgr.addStudent(courseCode, studentId);
    }
    
    //find students' courses
    private void findStudentCourses() {
        System.out.print("\nEnter Student ID to search: ");
        String studentId = scanner.nextLine().trim().toUpperCase();
        
        if (studentId.length() >= 2) showStudentSuggestions(studentId);
        
        String cacheKey = "courses_" + studentId;
        Object cached = cacheApi.getCached(cacheKey);
        if (cached instanceof Course[]) {
            Course[] courses = (Course[]) cached;
            if (courses.length > 0) {
                System.out.println("\nCourses for Student " + studentId + " (from cache):");
                for (Course c : courses) System.out.println("  • " + c);
            } else {
                System.out.println("No courses found for student '" + studentId + "'.");
            }
            return;
        }
        
        Course[] courses = enrollmentMgr.findCourse(studentId);
        
        if (courses.length > 0) {
            System.out.println("\nCourses for Student " + studentId + ":");
            for (Course c : courses) System.out.println("  • " + c);
            cacheApi.cache(cacheKey, courses);
        } else {
            System.out.println("No courses found for student '" + studentId + "'.");
        }
    }
    
    //list students' courses
    private void listStudentCourses() {
        System.out.print("\nEnter Student ID: ");
        String studentId = scanner.nextLine().trim().toUpperCase();
        enrollmentMgr.listCourses(studentId);
    }
    
    //find courses' students
    private void findCourseStudents() {
        System.out.print("\nEnter Course Code to search: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();
        
        if (courseCode.length() >= 2) showCourseSuggestions(courseCode);
        
        //check cache first
        String cacheKey = "students_" + courseCode;
        Object cached = cacheApi.getCached(cacheKey);
        if (cached instanceof Student[]) {
            Student[] students = (Student[]) cached;
            if (students.length > 0) {
                System.out.println("\nStudents in Course " + courseCode + " (from cache):");
                for (Student s : students) System.out.println("  • " + s);
            } else {
                System.out.println("No students found for course '" + courseCode + "'.");
            }
            return;
        }
        
        Student[] students = enrollmentMgr.findStudent(courseCode);
        
        if (students.length > 0) {
            System.out.println("\nStudents in Course " + courseCode + ":");
            for (Student s : students) System.out.println("  • " + s);
            cacheApi.cache(cacheKey, students);
        } else {
            System.out.println("No students found for course '" + courseCode + "'.");
        }
    }
    
    //list courses' students
    private void listCourseStudents() {
        System.out.print("\nEnter Course Code: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();
        enrollmentMgr.listStudents(courseCode);
    }
}