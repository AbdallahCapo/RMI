package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class TaskManagerServer {
    public static void main(String[] args) {
        try {
            TaskManager taskManager = new TaskManager();
            LocateRegistry.createRegistry(1099); // Default RMI port
            Naming.rebind("rmi://localhost/TaskManagerService", taskManager);
            System.out.println("Server started. TaskManagerService bound in registry.");
        } catch (MalformedURLException | RemoteException e) {
        }
    }
}