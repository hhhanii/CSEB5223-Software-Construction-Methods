import java.util.ArrayList;
import java.util.List;

public class CourseConfig {
    private List<Course> courseList;
    
    public CourseConfig() {
        this.courseList = new ArrayList<>();
        //test
        courseList.add(new Course("Object Oriented Programming", "CSEB3313", 3, "Basic Java concepts", "https://teams.microsoft.com/cseb3213"));
    }
    
    //Add Course
    public boolean addCourse(Course course) {
        //check to prevent duplicate IDs 
        if (findCourseByCode(course.getCourseCode()) != null) {
            return false;
        }
        courseList.add(course);
        return true;
    }

    //Search Function by Course Code 
    public Course findCourseByCode(String code) {
        for (Course c : courseList) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }
    
    //Edit Function
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
    
    //Delete Function
    public boolean deleteCourse(String code) {
        Course course = findCourseByCode(code);
        if (course == null) {
            return false;
        }
        return courseList.remove(course);  
    }
    
    //Display All Courses 
    public List<Course> getAllCourses() {
        return courseList;
    }
    
    public int getCount() {
        return courseList.size();
    }
}