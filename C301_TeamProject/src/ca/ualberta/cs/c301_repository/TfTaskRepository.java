package ca.ualberta.cs.c301_repository;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.c301_crowdclient.CrowdClient;
import ca.ualberta.cs.c301_crowdclient.CrowdSourcerContent;
import ca.ualberta.cs.c301_crowdclient.CrowdSourcerEntry;
import ca.ualberta.cs.c301_interfaces.Task;

public class TfTaskRepository {
    
    private static CrowdClient crowdClient = new CrowdClient();
    
    // TODO Can we keep this list up-to-date and then save internally onPause?
    // Then just check if can connect to server, if not, return this list
    private List<Task> taskList = new ArrayList<Task>();

    public static void addTask(Task task) {
        try {
            CrowdSourcerEntry entry = new CrowdSourcerEntry();
            // We set the device id into the summary
            entry.setSummary(task.getDeviceId());
            entry.setDescription(task.getDescription());
            entry.setContent(task);

            String taskId = task.getTaskId();
            if (taskId.isEmpty()) {
                crowdClient.insertEntry(entry);                
            } else {
                entry.setId(taskId);
                crowdClient.updateEntry(entry);
            }
        } catch (Exception e) {
            System.err.println("<<<Error adding the task>>>");
            e.printStackTrace(System.err);
        }
    }
    
    public static List<Task> getTasksByDeviceId(String deviceId) throws Exception {
        List<Task> taskList = getAllTasks();
        for (Task task : taskList) {
            if (task.getDeviceId() == deviceId) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    public static List<Task> getAllTasks() throws Exception {
        List<CrowdSourcerEntry> entryList = crowdClient.getEntryList();
        List<Task> taskList = new ArrayList<Task>();
        for (CrowdSourcerEntry entry : entryList) {
//            Task task = (TfTask) entry.getContent();
            CrowdSourcerContent task = entry.getContent();

            String taskId = entry.getId();
            if (task == null) {
            	System.err.println("<<<TASK IS NULL!!!>>>");
            }
            //taskList.add(task);
        }
        return taskList;
    }
    
    public static String listEntries() throws Exception {
    	return crowdClient.listEntrys();
    }

}
