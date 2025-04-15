import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Task {
    String title;
    LocalDate dueDate;

    Task(String title, String dueDateStr) {
        this.title = title;
        this.dueDate = LocalDate.parse(dueDateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String toString() {
        return title + " | " + dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}

public class TaskReminder {
    static ArrayList<Task> tasks = new ArrayList<>();
    static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        loadTasksFromFile();

        int choice;
        do {
            System.out.println("\n📋 Task Reminder");
            System.out.println("1. Add Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. View Today's & Overdue Tasks");
            System.out.println("4. Save & Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Task Title: ");
                    String title = sc.nextLine();
                    System.out.print("Due Date (dd-MM-yyyy): ");
                    String date = sc.nextLine();
                    tasks.add(new Task(title, date));
                    break;
                case 2:
                    System.out.println("\n🗂 All Tasks:");
                    for (Task t : tasks)
                        System.out.println(t);
                    break;
                case 3:
                    System.out.println("\n⏰ Today's and Overdue Tasks:");
                    LocalDate today = LocalDate.now();
                    for (Task t : tasks) {
                        if (!t.dueDate.isAfter(today))
                            System.out.println(t);
                    }
                    break;
                case 4:
                    saveTasksToFile();
                    System.out.println("Tasks saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (choice != 4);

        sc.close();
    }

    static void saveTasksToFile() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
        for (Task t : tasks)
            writer.write(t.title + "|" + t.dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
        writer.close();
    }

    static void loadTasksFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                tasks.add(new Task(parts[0].trim(), parts[1].trim()));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("No previous tasks found.");
        }
    }
}
