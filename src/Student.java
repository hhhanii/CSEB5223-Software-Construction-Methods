class Student {
    private String firstName;
    private String lastName;
    private String studentID;
    private String email;
    private String phoneNumber;
    
    private static final int MAX_COURSES = 20;
    private String[] enrolledCourseIDs;
    private int enrolledCount;
    
    public Student(String firstName, String lastName, String studentID, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.email = email;
        this.phoneNumber = phoneNumber;
        
        this.enrolledCourseIDs = new String[MAX_COURSES];
        this.enrolledCount = 0;
    }

    //getters
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
    
    //setters
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

    //integration methods
    public boolean enrollInCourse(String courseID) {
        if (courseID == null || courseID.trim().isEmpty()) {
            System.out.println("Error: Course ID cannot be empty.");
            return false;
        }
        
        //check duplicate enrollment
        for (int i = 0; i < enrolledCount; i++) {
            if (enrolledCourseIDs[i].equalsIgnoreCase(courseID)) {
                System.out.println("Warning: Student already enrolled in course " + courseID + ".");
                return false;
            }
        }
        
        //check capcity
        if (enrolledCount >= MAX_COURSES) {
            System.out.println("Error: Student cannot enroll in more than " + MAX_COURSES + " courses.");
            return false;
        }
        
        enrolledCourseIDs[enrolledCount++] = courseID.trim().toUpperCase();
        return true;
    }
    
    public String[] getEnrolledCourses() {
        String[] result = new String[enrolledCount];
        for (int i = 0; i < enrolledCount; i++) {
            result[i] = enrolledCourseIDs[i];
        }
        return result;
    }
    
    //check if enrolled in specific course
    public boolean isEnrolledIn(String courseID) {
        for (int i = 0; i < enrolledCount; i++) {
            if (enrolledCourseIDs[i].equalsIgnoreCase(courseID)) {
                return true;
            }
        }
        return false;
    }

    //for display
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Email: %s", studentID, getFullName(), email);
    }
}
