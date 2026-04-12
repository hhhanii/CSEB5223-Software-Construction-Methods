public class Course {
    private String courseName, courseCode, summary, teamsLink;
    private int creditHour;
    private String courseType;
    
    //constructor
    public Course(String courseName, String courseCode, int creditHour, String summary, String teamsLink) {
        this(courseName, courseCode, creditHour, summary, teamsLink, "core");
    }
    
    public Course(String courseName, String courseCode, int creditHour, String summary, String teamsLink, String courseType) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.creditHour = creditHour;
        this.summary = summary;
        this.teamsLink = teamsLink;
        this.courseType = validateCourseType(courseType);
    }
    
    //validate course type
    private String validateCourseType(String type) {
        if (type == null) 
            return "core";
        if (type.equals("core") || type.equals("elective") || type.equals("university")) {
            return type;
        }
        System.out.println("Warning: Invalid course type " + type + ". Defaulting to 'core'.");
        return "core";
    }
    
    //getters
    public String getCourseName() {
        return courseName;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public int getCreditHour() {
        return creditHour;
    }
    public String getSummary() {
        return summary;
    }
    public String getTeamsLink() {
        return teamsLink;
    }
    public String getCourseType() {
        return courseType;
    }
    
    //setters
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public void setCreditHour(int creditHour) {
        this.creditHour = creditHour;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public void setTeamsLink(String teamsLink) {
        this.teamsLink = teamsLink;
    }
    public void setCourseType(String courseType) {
        this.courseType = validateCourseType(courseType);
    }
    
    @Override
    public String toString() {
        return "------------------------------------------\n" +
               "Course Code : " + courseCode + "\n" +
               "Course Name : " + courseName + "\n" +
               "Type        : " + courseType + "\n" +
               "Credit Hours: " + creditHour + "\n" +
               "Summary     : " + summary + "\n" +
               "Teams Link  : " + teamsLink + "\n" +
               "------------------------------------------\n";
    }
}