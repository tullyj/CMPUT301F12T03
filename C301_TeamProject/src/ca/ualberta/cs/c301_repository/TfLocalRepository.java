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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import ca.ualberta.cs.c301_interfaces.Task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Handles saving and retrieving local tasks to the phone using JSON.
 * @author colinhunt
 *
 */
public class TfLocalRepository /* extends Activity */ {

    // JSON Utilities
    private Gson gson = new Gson();
    
    // Filename for local storage
    private static String STORAGE_FILE = "task_force_local_tasks";

    // List to represent stored tasks
    private static List<TfTask> entryList = 
            new ArrayList<TfTask>();
    
    private final Type listType = 
            new TypeToken<List<TfTask>>(){}.getType();
    
    /**
     * Inserts the given task into the local repository.
     * @param task Task to be inserted.
     * @param c    Context of the calling activity.
     * @return ID of inserted task, or emptry string if failed.
     */
    public String insertTask(Task task, Context c) {
        loadLocalList(c);
        String taskId = UUID.randomUUID().toString();
        task.setTaskId(taskId);
        Boolean success = entryList.add((TfTask) task);
        saveLocalList(c);
        
        if (success) {
            return taskId;
        } else {
            return "";
        }
    }
    
    /**
     * Removes a task with the given task ID.
     * @param taskId ID of task to be removed.
     * @param c      Context of calling activity.
     * @return
     */
    public Boolean removeTask(String taskId, Context c) {
        loadLocalList(c);
        Boolean success = false;
        for (Task task : entryList) {
            if (task.getTaskId().equals(taskId)) {
                success = entryList.remove(task);
                break;
            }
        }
        saveLocalList(c);
        return success;
    }

    /**
     * Gets the list of local tasks.
     * @param c Context of calling application.
     * @return List of tasks.
     */
    public List<TfTask> getTaskList(Context c) {
        loadLocalList(c);
        return entryList;
    }

    /**
     * Update a task stored locally.
     * @param changedTask Task with the updates.
     * @param c           Context of the calling activity.
     */
    public void update(Task changedTask, Context c) {
        loadLocalList(c);
        for (Task oldTask : entryList) {
            if (oldTask.getTaskId().equals(changedTask.getTaskId())) {
                oldTask = changedTask;
                break;
            }
        }
        saveLocalList(c);
    }

    public Task getTask(String taskId, Context c) {
        loadLocalList(c);
        for (Task task : entryList) {
            if (task.getTaskId().equals(taskId))
                return task;
        }
        return null;
    }

    // Save the local list to the phone using given context.
    private void saveLocalList(Context c) {
        if (c == null)
            return;
        try {
            String jsonArray = gson.toJson(entryList, listType);
            
            FileOutputStream fOut = 
                    c.openFileOutput(STORAGE_FILE, Context.MODE_PRIVATE);
            OutputStreamWriter ostream = new OutputStreamWriter(fOut);
            ostream.write(jsonArray);
            ostream.flush();
            ostream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Load the local list from the phone using given context.
    private void loadLocalList(Context c) {
        if (c == null)
            return;
        try {
            FileInputStream fIn = c.openFileInput(STORAGE_FILE);
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader buffreader = new BufferedReader (isr);

            String jsonArray = buffreader.readLine();
            entryList = gson.fromJson(jsonArray, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
