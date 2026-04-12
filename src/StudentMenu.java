import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private final List<Student> studentList;
    private final Scanner scanner;
    
    public StudentMenu(List<Student> studentList, Scanner scanner) {
        this.studentList = studentList;
        this.scanner = scanner;
    }
    
    public void studentExecute() {
        boolean keepRunning = true;
        while(keepRunning) {  
            System.out.println("\n=== STUDENT PROFILE MANAGEMENT ===");
            System.out.println("1. Add Student Profile");
            System.out.println("2. Search Student");
            System.out.println("3. Edit Student");
            System.out.println("4. Delete Student");
            System.out.println("5. View All Students");
            System.out.println("6. Back to Main Menu");  
            
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
        
            switch (choice) {
                case "1": addStudent(); break;
                case "2": searchStudent(); break;
                case "3": editStudent(); break;
                case "4": deleteStudent(); break;
                case "5": viewAllStudents(); break;
                case "6": keepRunning = false; break; 
                default: System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    //linear search for student by ID
    public int findStudentIndexById(String id) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getStudentID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1; // Not found
    }
    
    private void addStudent() {
        System.out.println("\n-- Add New Student --");
        
        System.out.print("First Name: ");
        String fName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lName = scanner.nextLine();
        System.out.print("Student ID: ");
        String sID = scanner.nextLine().trim().toUpperCase();

        //check for duplicate ID
        if (findStudentIndexById(sID) != -1) {
            System.out.println("Error: Student ID '" + sID + "' already exists!");
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        Student newStudent = new Student(fName, lName, sID, email, phone);
        studentList.add(newStudent);
        
        System.out.println("Student profile submitted successfully!");
        viewAllStudents(); //display after action
    }

    private void searchStudent() {
        System.out.print("\nEnter Student ID to search: ");
        String searchID = scanner.nextLine().trim().toUpperCase();

        int index = findStudentIndexById(searchID);

        if (index != -1) {
            System.out.println("\nStudent Found:");
            displaySingleStudent(studentList.get(index));
        } else {
            System.out.println("Student not found with ID: " + searchID);
        }
    }

    private void editStudent() {
        System.out.print("\nEnter Student ID to edit: ");
        String searchID = scanner.nextLine().trim().toUpperCase();
        int index = findStudentIndexById(searchID);

        if (index != -1) {
            Student s = studentList.get(index);
            System.out.println("\n--- Edit Profile (ID cannot be changed) ---");
            System.out.println("Current Name: " + s.getFullName());
            
            System.out.print("New First Name (or press Enter to keep): ");
            String newFName = scanner.nextLine();
            if (!newFName.isEmpty()) s.setFirstName(newFName);
            
            System.out.print("New Last Name (or press Enter to keep): ");
            String newLName = scanner.nextLine();
            if (!newLName.isEmpty()) s.setLastName(newLName);
            
            System.out.print("New Email (or press Enter to keep): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) s.setEmail(newEmail);
            
            System.out.print("New Phone (or press Enter to keep): ");
            String newPhone = scanner.nextLine();
            if (!newPhone.isEmpty()) s.setPhoneNumber(newPhone);

            System.out.println("\nProfile updated successfully!");
            displaySingleStudent(s);
            viewAllStudents(); //display after action
        } else {
            System.out.println("Student not found.");
        }
    }

    private void deleteStudent() {
        System.out.print("\nEnter Student ID to delete: ");
        String searchID = scanner.nextLine().trim().toUpperCase();
        int index = findStudentIndexById(searchID);

        if (index != -1) {
            System.out.print("Are you sure you want to delete " + 
                           studentList.get(index).getFullName() + "? (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                studentList.remove(index); 
                System.out.println("Student deleted successfully!");
                viewAllStudents(); //display changes
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private void viewAllStudents() {
        System.out.println("\n=== All Student Records ===");
        if (studentList.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }

        System.out.printf("%-12s %-25s %-30s %-15s%n", "ID", "Name", "Email", "Phone");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Student s : studentList) {
            System.out.printf("%-12s %-25s %-30s %-15s%n", 
                s.getStudentID(), 
                s.getFullName(), 
                s.getEmail(), 
                s.getPhoneNumber());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }

    private void displaySingleStudent(Student s) {
        System.out.println("================================");
        System.out.println("ID    : " + s.getStudentID());
        System.out.println("Name  : " + s.getFullName());
        System.out.println("Email : " + s.getEmail());
        System.out.println("Phone : " + s.getPhoneNumber());
        System.out.println("================================");
    }
}