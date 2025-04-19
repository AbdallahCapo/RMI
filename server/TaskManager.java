package server;

import common.TaskManagerInterface;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class TaskManager extends UnicastRemoteObject implements TaskManagerInterface {

    private final String filePath = "tasks.txt";

    protected TaskManager() throws RemoteException {
        super();
    }

    @Override
    public synchronized void addTask(String task) throws RemoteException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(task);
            writer.newLine();
            System.out.println("Task added: " + task);
        } catch (IOException e) {
            throw new RemoteException("Error writing to file", e);
        }
    }

    @Override
    public synchronized List<String> getAllTasks() throws RemoteException {
        List<String> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(line);
            }
        } catch (IOException e) {
            throw new RemoteException("Error reading file", e);
        }
        return tasks;
 }
}