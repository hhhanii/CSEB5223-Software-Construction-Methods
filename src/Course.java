public class Course {
    private String courseName, courseCode, summary, teamsLink;
    private int creditHour;
    
    //Constructor
    public Course(String courseName, String courseCode, int creditHour, String summary, String teamsLink) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.creditHour = creditHour;
        this.summary = summary;
        this.teamsLink = teamsLink;
    }
    
    //Getters
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
    
    //Setters
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
    
    @Override
    public String toString() {
        return "------------------------------------------\n" +
               "Course Code : " + courseCode + "\n" +
               "Course Name : " + courseName + "\n" +
               "Credit Hours: " + creditHour + "\n" +
               "Summary     : " + summary + "\n" +
               "Teams Link  : " + teamsLink + "\n" +
               "------------------------------------------\n";
    }
}