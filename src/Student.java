import java.util.ArrayList;
import java.util.List;

class Student {
    private String firstName;
    private String lastName;
    private String studentID;
    private String email;
    private String phoneNumber;
    
    private List<String> enrolledCourseIDs; 

    public Student(String firstName, String lastName, String studentID, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.enrolledCourseIDs = new ArrayList<>();
    }

    //Getters
    public String getFirstName() { 
        return firstName; 
    }
    
    public String getLastName() { 
        return lastName; 
    }
    
    public String getStudentID() { 
        return studentID; 
    }

    public String getEmail() { 
        return email; 
    }
    
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    //Setters
    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    }
    
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }

    //Integration Methods
    public void enrollInCourse(String courseID) {
        enrolledCourseIDs.add(courseID);
    }
    
    public List<String> getEnrolledCourses() {
        return enrolledCourseIDs;
    }

    //For display
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
