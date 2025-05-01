package client;

import common.TaskManagerInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class TaskManagerClient {
    public static void main(String[] args) {
        try {
            String serverIp = "192.168.56.1"; // Replace with server IP if remote
            TaskManagerInterface manager = (TaskManagerInterface) Naming.lookup("rmi://" + serverIp + "/TaskManagerService");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n1. Add Task\n2. View Tasks\n3. Delete Task\n4. Exit");
                System.out.print("Choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter task: ");
                        String task = scanner.nextLine();
                        manager.addTask(task);
                    }
                    case 2 -> {
                        List<String> tasks = manager.getAllTasks();
                        System.out.println("Tasks:");
                        if (tasks.isEmpty()) {
                            System.out.println("No tasks available.");
                        }
                        for (String t : tasks) {
                            System.out.println("- " + t);
                        }
                    }
                    case 3 -> {
                        System.out.print("Enter task to delete: ");
                        String task = scanner.nextLine();
                        if (manager.deleteTask(task)) {
                            System.out.println("Task deleted successfully.");
                        } else {
                            System.out.println("Task not found.");
                        }
                    }
                    case 4 -> System.exit(0);
                }
            }
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
        }
    }
}
