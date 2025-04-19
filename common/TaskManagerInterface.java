package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TaskManagerInterface extends Remote {
    void addTask(String task) throws RemoteException;
    List<String> getAllTasks() throws RemoteException;
}