public class EnrollmentManager {
    private static final int MAX_REL = 1000;
    
    private String[] relStudentIds = new String[MAX_REL];
    private String[] relCourseCodes = new String[MAX_REL];
    private int relCount = 0;
    
    private final CourseConfig config;
    private final Student[] students;
    private final int studentCount;
    
    public EnrollmentManager(CourseConfig config, Student[] students, int studentCount) {
        this.config = config;
        this.students = students;
        this.studentCount = studentCount;
    }
    
    private int findStudentIndex(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null && students[i].getStudentID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }
    
    //check if relationship exists
    private boolean isEnrolled(String studentId, String courseCode) {
        for (int i = 0; i < relCount; i++) {
            if (relStudentIds[i].equalsIgnoreCase(studentId) && 
                relCourseCodes[i].equalsIgnoreCase(courseCode)) {
                return true;
            }
        }
        return false;
    }
    
    //add course to student
    public boolean addCourse(String studentId, String courseCode) {
        if (findStudentIndex(studentId) == -1) {
            System.out.println("Error: Student '" + studentId + "' not found.");
            return false;
        }
        
        if (config.findCourseByCode(courseCode) == null) {
            System.out.println("Error: Course '" + courseCode + "' not found.");
            return false;
        }
        
        if (isEnrolled(studentId, courseCode)) {
            System.out.println("Warning: Student already enrolled in course '" + courseCode + "'.");
            return false;
        }
        
        //capacity check
        if (relCount >= MAX_REL) {
            System.out.println("Error: Enrollment registry is full.");
            return false;
        }
        
        //add to parallel arrays
        relStudentIds[relCount] = studentId;
        relCourseCodes[relCount] = courseCode;
        relCount++;
        
        int sIndex = findStudentIndex(studentId);
        if (sIndex != -1) {
            students[sIndex].enrollInCourse(courseCode);
        }
        
        System.out.println("Enrolled student '" + studentId + "' in course '" + courseCode + "'.");
        return true;
    }
    
    //add student to course 
    public boolean addStudent(String courseCode, String studentId) {
        return addCourse(studentId, courseCode);
    }
    
    //find courses by student ID
    public Course[] findCourse(String studentId) {
        if (findStudentIndex(studentId) == -1) return new Course[0];
        
        Course[] temp = new Course[config.getCount()];
        int count = 0;
        
        for (int i = 0; i < relCount; i++) {
            if (relStudentIds[i].equalsIgnoreCase(studentId)) {
                Course c = config.findCourseByCode(relCourseCodes[i]);
                if (c != null) temp[count++] = c;
            }
        }
        
        Course[] result = new Course[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }
    
    //list courses for students
    public void listCourses(String studentId) {
        if (findStudentIndex(studentId) == -1) {
            System.out.println("Error: Student '" + studentId + "' not found.");
            return;
        }
        
        Course[] enrolled = findCourse(studentId);
        
        if (enrolled.length == 0) {
            System.out.println("Student '" + studentId + "' has no assigned courses.");
            return;
        }
        
        System.out.println("\nCourses for Student " + studentId + ":");
        for (Course c : enrolled) {
            System.out.println("  • " + c);
        }
    }
    
    //find students by course id
    public Student[] findStudent(String courseCode) {
        if (config.findCourseByCode(courseCode) == null) return new Student[0];
        
        Student[] temp = new Student[studentCount];
        int count = 0;
        
        for (int i = 0; i < relCount; i++) {
            if (relCourseCodes[i].equalsIgnoreCase(courseCode)) {
                int sIndex = findStudentIndex(relStudentIds[i]);
                if (sIndex != -1) temp[count++] = students[sIndex];
            }
        }
        
        Student[] result = new Student[count];
        System.arraycopy(temp, 0, result, 0, count);
        return result;
    }
    
    //list students for course
    public void listStudents(String courseCode) {
        if (config.findCourseByCode(courseCode) == null) {
            System.out.println("Error: Course '" + courseCode + "' not found.");
            return;
        }
        
        Student[] enrolled = findStudent(courseCode);
        
        if (enrolled.length == 0) {
            System.out.println("Course '" + courseCode + "' has no assigned students.");
            return;
        }
        
        System.out.println("\nStudents in Course " + courseCode + ":");
        for (Student s : enrolled) {
            System.out.println("  • " + s);
        }
    }
}