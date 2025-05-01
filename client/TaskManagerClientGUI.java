package client;

import common.TaskManagerInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.util.List;

public class TaskManagerClientGUI extends JFrame {
    private TaskManagerInterface manager;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskField;

    public TaskManagerClientGUI() {
        try {
            String serverIp = "192.168.56.1"; // Adjust as needed
            manager = (TaskManagerInterface) Naming.lookup("rmi://" + serverIp + "/TaskManagerService");

            setTitle("Task Manager");
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            // Task input
            JPanel inputPanel = new JPanel(new BorderLayout());
            taskField = new JTextField();
            JButton addButton = new JButton("Add Task");
            inputPanel.add(taskField, BorderLayout.CENTER);
            inputPanel.add(addButton, BorderLayout.EAST);

            // Task list
            taskListModel = new DefaultListModel<>();
            taskList = new JList<>(taskListModel);
            JScrollPane scrollPane = new JScrollPane(taskList);

            // Delete button
            JButton deleteButton = new JButton("Delete Selected");

            add(inputPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            add(deleteButton, BorderLayout.SOUTH);

            // Load tasks
            refreshTasks();

            // Event handlers
            addButton.addActionListener(e -> {
                String task = taskField.getText().trim();
                if (!task.isEmpty()) {
                    try {
                        manager.addTask(task);
                        taskField.setText("");
                        refreshTasks();
                    } catch (Exception ex) {
                        showError(ex);
                    }
                }
            });

            deleteButton.addActionListener(e -> {
                String selected = taskList.getSelectedValue();
                if (selected != null) {
                    try {
                        if (manager.deleteTask(selected)) {
                            refreshTasks();
                        } else {
                            JOptionPane.showMessageDialog(this, "Task not found.");
                        }
                    } catch (Exception ex) {
                        showError(ex);
                    }
                }
            });

            setVisible(true);

        } catch (Exception e) {
            showError(e);
        }
    }

    private void refreshTasks() {
        try {
            taskListModel.clear();
            List<String> tasks = manager.getAllTasks();
            if (tasks.isEmpty()) {
                taskListModel.addElement("No tasks available.");
            }
            for (String task : tasks) {
                taskListModel.addElement(task);
            }
        } catch (Exception e) {
            showError(e);
        }
    }

    private void showError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskManagerClientGUI::new);
    }
}
