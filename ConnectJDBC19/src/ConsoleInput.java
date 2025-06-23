import java.util.List;
import java.util.Scanner;

public class ConsoleInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceClass service = new ServiceClass();

        System.out.println("=== Student Management System ===");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        // Insert student
        if (service.insertDB(name)) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Failed to add student.");
            return;
        }

        // Display all students
        List<Student> students = service.readFromDB();

        if (students != null && !students.isEmpty()) {
            System.out.println("\n--- Current Students ---");
            for (Student student : students) {
                System.out.println("ID: " + student.getId() + ", Name: " + student.getName());
            }
        } else {
            System.out.println("No students found in the database.");
        }

        scanner.close();
    }
}