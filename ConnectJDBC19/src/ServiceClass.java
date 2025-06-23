import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceClass {
    private List<Student> students = new ArrayList<>();
    private int nextId = 1;  // To auto-increment student IDs

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ServiceClass service = new ServiceClass();

        while (true) {
            System.out.println("\n=== Student Database Menu ===");
            System.out.println("1. Insert new student");
            System.out.println("2. Update student name");
            System.out.println("3. Delete student");
            System.out.println("4. Show all students");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    if (service.insertDB(name)) {
                        System.out.println("Student inserted successfully.");
                    } else {
                        System.out.println("Insert failed.");
                    }
                    break;

                case 2:
                    System.out.print("Enter student ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    if (service.updateStudent(updateId, newName)) {
                        System.out.println("Student updated.");
                    } else {
                        System.out.println("Update failed. Student ID not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter student ID to delete: ");
                    int deleteId = scanner.nextInt();
                    if (service.deleteStudent(deleteId)) {
                        System.out.println("Student deleted.");
                    } else {
                        System.out.println("Delete failed. Student ID not found.");
                    }
                    break;

                case 4:
                    List<Student> students = service.readFromDB();
                    if (students != null && !students.isEmpty()) {
                        System.out.println("\n--- Student List ---");
                        for (Student s : students) {
                            System.out.println("ID: " + s.getId() + ", Name: " + s.getName());
                        }
                    } else {
                        System.out.println("No students found.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting program.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private boolean updateStudent(int updateId, String newName) {
        for (Student student : students) {
            if (student.getId() == updateId) {
                student.setName(newName);
                return true;
            }
        }
        return false;
    }

    List<Student> readFromDB() {
        return new ArrayList<>(students);  // Return a copy of the list
    }

    private boolean deleteStudent(int deleteId) {
        return students.removeIf(student -> student.getId() == deleteId);
    }

    boolean insertDB(String name) {
        students.add(new Student(nextId++, name));
        return true;
    }
}

