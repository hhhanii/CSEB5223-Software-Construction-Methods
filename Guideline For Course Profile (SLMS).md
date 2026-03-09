Guideline For Course Profile (SLMS)

Main.java

Includes a menu to select functions for the Course Profile.

Menu includes:
a.	View All Courses
b.	Add New Courses
c.	Search Course by Code
d.	Edit Course
e.	Delete Course
f.	Exit

View All Courses:
•	Display a list of all available courses.
•	If no courses are available, print: "No courses found".

Add Course:
•	Prompt the user to input a course code.
•	Check if the course code already exists:
•	If it does, print: "Error: Course code already exists" and return.
•	Otherwise, proceed with entering the course name (full name), credits, summary, and MS Teams link.
•	If the course is successfully added, print "Course submitted successfully!".

Search Course by Code:
•	Prompt the user to input a course code to search.
•	Display the course details if found.

Edit Course:
•	Prompt the user to input the course code.
•	If the course is found, display current details and allow the user to edit fields like course name, credits, summary, and MS Teams link.
•	Print "Course updated successfully" after changes.

Delete Course:
•	Prompt the user to input a course code to delete.
•	If the course exists, ask for confirmation ("Type 'YES' to confirm deletion").
•	If confirmed, delete the course and print "Course deleted successfully!".


Course.java

 Private Variables:
•	courseName: The name of the course.
•	courseCode: The code for the course.
•	creditHour: The number of credit hours for the course.
•	summary: A brief summary or description of the course.
•	teamsLink: The MS Teams link associated with the course.

Constructor:
•	Takes all the details (course name, code, credit hours, summary, and MS Teams link) and initializes the class.

Getters:
•	Methods to get the values of courseName, courseCode, creditHour, summary, and teamsLink.

Setters:
•	Methods to set the values of courseName, creditHour, summary, and teamsLink.
 
toString() Method:
•	Returns a formatted string with all the details of the course, making it easy to display course information.


CourseConfig.java

Private Variables:
•	courseList: A List that stores the courses in the system.

Constructor:
•	Initializes courseList and adds a sample course for testing.

Add Course:
•	Adds a new course if the course code doesn't already exist in the list. Returns true on success and false if the course code already exists.

Find Course by Code:
•	Searches for a course by its code and returns the matching Course object if found, otherwise returns null.

Edit Course:
•	Allows updating the course's name, credit hours, summary, and Teams link. Returns true if the update is successful, otherwise false.

Delete Course:
•	Deletes a course from the list by its code. Returns true on successful deletion and false if the course was not found.

Get All Courses:
•	Returns the list of all courses currently stored.

Get Count:
•	Returns the number of courses in the list

