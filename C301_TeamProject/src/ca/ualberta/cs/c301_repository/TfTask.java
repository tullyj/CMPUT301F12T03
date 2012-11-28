package ca.ualberta.cs.c301_repository;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.c301_crowdclient.CrowdSourcerContent;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_interfaces.Visibility;

/**
 * TfTask encapsulates a task. It holds all information about a task including
 * the list of items (text item, photo item, ...) associated with a task and
 * methods for manipulating them.
 * @author colinhunt
 *
 */
public class TfTask extends CrowdSourcerContent implements Task {
    
    private List<TfTaskItem> itemList = new ArrayList<TfTaskItem>();
    
    private String deviceId = "Device ID";
    
    private String title = "Task Title";
    
    private String description = "Task Description";
    
    private Visibility visibility = Visibility.PUBLIC;
    
    private Boolean fulfilled = false;
    
    private Boolean modified = false;

    private String taskId = "";
    
    private String email = "";
    
    public TfTask() {}
    
    public Boolean addItem(TaskItem item) {
        Boolean success = itemList.add((TfTaskItem) item);
        if (success) {
            setModified(true);
        }
        return success;
    }

    public List<TfTaskItem> getAllItems() {
        return itemList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        setModified(true);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descr) {
        this.description = descr;
        setModified(true);
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility v) {
        this.visibility = v;
        setModified(true);
    }

    public Boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
        setModified(true);
    }

    public Boolean isModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        setModified(true);
    }

    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String id) {
        this.taskId = id;
    }

    @Override
    public String toString() {
        return "Task " + title + ", " + description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
