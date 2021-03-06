/**
 * Task Force Application
 * See github for license and other information: 
 * github.com/tullyj/CMPUT301F12T03/
 * 
 * Task Force is created by: 
 * Colin Hunt, Edwin Chung, 
 * Kris Kushniruk, and Tully Johnson.
 */
package ca.ualberta.cs.c301_repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import ca.ualberta.cs.c301_crowdclient.CrowdClient;
import ca.ualberta.cs.c301_crowdclient.CrowdSourcerEntry;
import ca.ualberta.cs.c301_interfaces.Task;

/**
 * TfTaskRepository provides a facade/interface for accessing CrowdClient and
 * the Crowd Sourcer webservice. It handles adding, updating, and getting of
 * tasks from CrowdClient through static methods. It deals with the input and
 * output expected by the application (namely, task objects).
 * 
 * @author colinhunt
 * 
 */
public class TfTaskRepository {

    private static CrowdClient crowdClient = new CrowdClient();
    private static TfLocalRepository localRepo = new TfLocalRepository();
    private static Task currentTask = null;
    // TODO Can we keep this list up-to-date and then save internally onPause?
    // Then just check if can connect to server, if not, return this list
    private List<Task> taskList = new ArrayList<Task>();

    /**
     * Adds a task to the webservice or updates an existing one. Updating is
     * dependent on the task id already existing or not.
     * 
     * @param task    Task object to add/update
     * @return TaskId ID of task added if success, empty string otherwise.
     */
    public static String addTask(Task task, Context c) {
        try {
            CrowdSourcerEntry entry = new CrowdSourcerEntry();
            // We set the device id into the summary
            entry.setSummary(task.getTitle());
            entry.setDescription(task.getDescription());
            entry.setContent((TfTask) task);
            
            System.out.println("T: " + task.getDescription());
            System.out.println("E: " + entry.getDescription());

            switch (task.getVisibility()) {
                case PUBLIC:
                    CrowdSourcerEntry returnedEntry = 
                            crowdClient.insertEntry(entry);
                    return returnedEntry.getId();                    
                case PRIVATE:
                    localRepo.insertTask(task, c);
                    break;
                default:
                    throw new Exception("Unhandled Visibility "
                            + task.getVisibility().toString());
            }
        } catch (Exception e) {
            System.err.println("<<<Error adding the task>>>");
            e.printStackTrace(System.err);
        }
        return "";
    }
    
    /**
     * Updates the task stored in the webservice or locally with the given task,
     * depending on the visibility of the task.
     * @param task The updated version of the task.
     * @param c    The context of the calling activity.
     * @throws Exception
     */
    public static void updateTask(Task task, Context c) throws Exception {
        if (task.getTaskId().isEmpty()) {
            throw new Exception("Task id is empty in "
                    + "TfTaskRepository.updateTask()");
        }
        switch (task.getVisibility()) {
            case PUBLIC:
                updatePublicTask(task);
                break;
            case PRIVATE:
                updatePrivateTask(task, c);
                break;
            default:
                throw new Exception("Unhandled Visibility "
                        + task.getVisibility().toString());
        }
    }
    
    /**
     * Updates the task stored locally with the given task.
     * @param task The updated version of the task.
     * @param c    The context of the calling activity.
     */
    private static void updatePrivateTask(Task task, Context c) {
        localRepo.update(task, c);
    }
    
    /**
     * Updates a public task on the webservice.
     * @param task The updated version of the task.
     */
    private static void updatePublicTask(Task task) {
        try {
            CrowdSourcerEntry entry = new CrowdSourcerEntry();
            entry.setId(task.getTaskId());
            entry.setSummary(task.getTitle());
            // We set the device id into the description
            // Right now description is not being saved in webservice
            entry.setDescription(task.getDescription());
            entry.setContent((TfTask) task);

            crowdClient.updateEntry(entry);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    /**
     * Gets all tasks that are currently in the webservice.
     * 
     * @return A list of tasks.
     * @throws Exception
     */
    public static List<Task> getAllTasks() throws Exception {
        List<CrowdSourcerEntry> entryList = crowdClient.getEntryList();
        List<Task> taskList = new ArrayList<Task>();
        for (CrowdSourcerEntry entry : entryList) {
            Task task = entry.getContent();
            task.setTaskId(entry.getId());
            taskList.add(task);
        }
        return taskList;
    }

    /**
     * Gets the list of entries as maps in the webservice. Basic, fast
     * representation of the tasks currently stored. No content is returned.
     * 
     * @return A list of maps.
     * @throws Exception
     */
    public static List<Map<String, String>> getShallowEntries()
            throws Exception {
        return crowdClient.getShallowList();
    }

    /**
     * Gets a single task associated with a given a task id from the webservice.
     * 
     * @param taskId Task ID of the task to be returned.
     * @return A task.
     * @throws Exception
     */
    public static Task getTaskById(String taskId) throws Exception {
        CrowdSourcerEntry entry = crowdClient.getEntry(taskId);
        Task task = entry.getContent();
        if (task == null) {
            throw new Exception("TfTaskRepository got a null task in "
                    + "getTaskById() with id=" + taskId);
        }
        if (!task.getTaskId().equals(entry.getId())) {
            task.setTaskId(entry.getId());
        }
        currentTask = task;
        return task;
    }
    
    /**
     * Returns the local task stored with the given taskId.
     * @param taskId Task ID of task to be retrieved.
     * @param c      Context of calling activity.
     * @return The task if found, else returns null.
     * @throws Exception
     */
    public static Task getLocalTaskById(String taskId, Context c) 
            throws Exception {
        return localRepo.getTask(taskId, c);
    }
    
    /**
     * Returns a list of all the local tasks stored on the device.
     * @param c The context of the calling activity.
     * @return The list of all local tasks.
     */
    public static List<TfTask> getLocalTasks(Context c) {
        return localRepo.getTaskList(c);
    }
    
    /**
     * Gets a list of IDs of all tasks stored locally on the phone.
     * @param c Context of calling activity.
     * @return  A string array of task IDs.
     */
    public static String[] getLocalTaskIds(Context c) {
        List<TfTask> temp = localRepo.getTaskList(c);
        ArrayList<String> send = new ArrayList<String>();
        Iterator<TfTask> it = temp.iterator();
        while(it.hasNext()){
            TfTask task = it.next();
            send.add(task.getTaskId());
        }
        return send.toArray(new String[send.size()]);
    }
}
