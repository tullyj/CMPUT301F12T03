package ca.ualberta.cs.c301_repository;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.c301_crowdclient.CrowdClient;
import ca.ualberta.cs.c301_crowdclient.CrowdSourcerEntry;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_interfaces.TaskRepository;

public class TfTaskRepository implements TaskRepository {
    
    private CrowdClient crowdClient = new CrowdClient();
    
    // TODO Can we keep this list up-to-date and then save internally onPause?
    // Then just check if can connect to server, if not, return this list
    private List<Task> taskList = new ArrayList<Task>();

    public void addTask(Task task) {
        try {
            CrowdSourcerEntry entry = new CrowdSourcerEntry();
            String taskId = task.getTaskId();
            if (!taskId.isEmpty()) {
                entry.setId(taskId);            
            }
            // We set the device id into the summary
            entry.setSummary(task.getDeviceId());
            entry.setDescription(task.getDescription());
            entry.setContent(task);
            
            crowdClient.insertEntry(entry);
        } catch (Exception e) {
            System.err.println("<<<Error adding the task>>>");
            e.printStackTrace(System.err);
        }
    }

    public List<Task> getTasksByDeviceId(String deviceId) {
        List<Task> taskList = getAllTasks();
        for (Task task : taskList) {
            if (task.getDeviceId() == deviceId) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    public List<Task> getAllTasks() {
        List<CrowdSourcerEntry> entryList = crowdClient.getEntryList();
        List<Task> taskList = new ArrayList<Task>();
        for (CrowdSourcerEntry entry : entryList) {
            Task task = (Task) entry.getContent();
            taskList.add(task);
        }
        return taskList;
    }

}
