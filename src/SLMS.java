import java.util.Scanner;

public class SLMS {
    private static final int MAX_STUDENTS = 50;
    private static Student[] studentList = new Student[MAX_STUDENTS];
    private static int studentCount = 0;
    
    private static final CourseConfig config = new CourseConfig();
    private static final Scanner scanner = new Scanner(System.in);
    
    private static CacheApi cacheApi;
    
    public static void main(String[] args) {
        cacheApi = new CacheApi(config, studentList, studentCount);
        
        //sample tests
        config.addCourse(new Course("Software Security", "CCSB5113", 3, "Cybersecurity in software", "https://ccsb5113.msteams"));
        config.addCourse(new Course("Software Quality", "CSEB4113", 3, "Software Quality Assurance", "https://cseb4113.msteams"));
        
        addStudentRecord("BSW01084721", "Wan", "Amira", "wanamira33@test.com", "019-1234567");
        addStudentRecord("AK1085432", "Nurin", "Zahirah", "zahirah@test.com", "012-1234567");

        boolean execute = true;
        
        System.out.println("=== STUDENT LEARNING MANAGEMENT SYSTEM ===");
        
        while (execute) {
            System.out.println("\n1. Course Management");
            System.out.println("2. Student Management");
            System.out.println("3. Enrollment");
            System.out.println("4. Exit");
            System.out.print("Select module: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1": CourseMenu courseMenu = new CourseMenu(config, scanner, cacheApi);
                          courseMenu.courseExecute(); 
                          break;
                case "2": StudentMenu studentMenu = new StudentMenu(studentList, studentCount, scanner, cacheApi);
                          studentMenu.studentExecute(); 
                          break;
                case "3": 
                    runEnrollmentMenu();
                    break;
                case "4":
                    execute = false;
                    System.out.println("System exited. Goodbye!");
                    break;
                default: 
                    System.out.println("Invalid selection. Please try again.");
            }
        }
        scanner.close();
    }
    
     //add student to array (with duplicate check)
    private static boolean addStudentRecord(String id, String fName, String lName, String email, String phone) {
        // Check capacity
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Error: Student registry is full.");
            return false;
        }
        
        //check for duplicate ID
        if (findStudentIndexById(id) != -1) {
            System.out.println("Error: Student ID '" + id + "' already exists.");
            return false;
        }
        
        //find first null slot or append
        Student newStudent = new Student(fName, lName, id, email, phone);
        for (int i = 0; i < studentList.length; i++) {
            if (studentList[i] == null) {
                studentList[i] = newStudent;
                studentCount++;
                //cache the new student
                cacheApi.cache(id, newStudent);
                cacheApi.cache(fName.toLowerCase(), newStudent);
                return true;
            }
        }
        return false;
    }
    
    //find student index by ID 
    public static int findStudentIndexById(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (studentList[i] != null && studentList[i].getStudentID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }
    
    //get copy of active students
    public static Student[] getAllStudents() {
        Student[] result = new Student[studentCount];
        for (int i = 0; i < studentCount; i++) {
            result[i] = studentList[i];
        }
        return result;
    }
    
    //enrollment menu
    private static void runEnrollmentMenu() {
        EnrollmentManager enrollmentMgr = new EnrollmentManager(config, studentList, studentCount);
        EnrollmentMenu enrollmentMenu = new EnrollmentMenu(enrollmentMgr, scanner, cacheApi);
        enrollmentMenu.enrollmentExecute();
    }
}