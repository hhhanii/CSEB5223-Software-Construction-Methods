public class CourseConfig {
    private static final int MAX_CAPACITY = 50;
    private Course[] courseList;
    private int courseCount;
    
    public CourseConfig() {
        this.courseList = new Course[MAX_CAPACITY];
        this.courseCount = 0;
        
        //test
        addCourse(new Course("Object Oriented Programming", "CSEB3313", 3, "Basic Java concepts", "https://cseb3313.msteams"));
    }
    
    //add course
    public boolean addCourse(Course course) {
        //check capacity 
        if (courseCount >= MAX_CAPACITY) {
            System.out.println("Error: Course registry is full (max " + MAX_CAPACITY + ").");
            return false;
        }
        
        //check to prevent duplicate IDs 
        if (findCourseByCode(course.getCourseCode()) != null) {
            System.out.println("Error: Course code " + course.getCourseCode() + " already exists.");
            return false;
        }
        
        courseList[courseCount] = course;
        courseCount++;
        return true;
    }

    //search function by course code 
    public Course findCourseByCode(String code) {
        if (code == null) return null;
        for (int i = 0; i < courseCount; i++) {
            if (courseList[i].getCourseCode().equalsIgnoreCase(code)) {
                return courseList[i];
            }
        }
        return null;
    }
    
    //edit function
    public boolean editCourse(String code, String newName, int newCredits, String newSummary, String newTeamsLink) {
        Course course = findCourseByCode(code);
        if (course == null) {
            return false;
        }
        course.setCourseName(newName);
        course.setCreditHour(newCredits);
        course.setSummary(newSummary);
        course.setTeamsLink(newTeamsLink);
        return true;
    }
    
    //delete function
    public boolean deleteCourse(String code) {
        int index = -1;
        for (int i = 0; i < courseCount; i++) {
            if (courseList[i].getCourseCode().equalsIgnoreCase(code)) {
                index = i;
                break;
            }
        }
        
        if (index == -1) {
            return false;
        } 
        
        for (int i = index; i < courseCount - 1; i++) {
            courseList[i] = courseList[i + 1];
        }
        
        courseList[courseCount - 1] = null;
        courseCount--;
        return true;
    }
    
    //display all courses 
    public Course[] getAllCourses() {
        Course[] result = new Course[courseCount];
        for (int i = 0; i < courseCount; i++) {
            result[i] = courseList[i];
        }
        return result;
    }
    
    public int getCount() {
        return courseCount;
    }
    
    public boolean isEmpty() {
        return courseCount == 0;
    }
}