package ca.ualberta.cs.c301_repository;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.c301_crowdclient.CrowdClient;
import ca.ualberta.cs.c301_crowdclient.CrowdSourcerEntry;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_interfaces.TaskRepository;

public class TfTaskRepository implements TaskRepository {
    
    private CrowdClient crowdClient = new CrowdClient();
    
    private List<Task> taskList = new ArrayList<Task>();

    public void addTask(Task task) {
        try {
            CrowdSourcerEntry entry = new CrowdSourcerEntry();
            String taskId = task.getTaskId();
            if (!taskId.isEmpty()) {
                entry.setId(taskId);            
            }
            entry.setDescription(task.getDescription());
            entry.setContent(task);
            
            crowdClient.insertEntry(entry);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.err);
        }
    }

    public List<Task> getTasksById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Task> getAllTasks() {
        // TODO Auto-generated method stub
        return null;
    }

}
