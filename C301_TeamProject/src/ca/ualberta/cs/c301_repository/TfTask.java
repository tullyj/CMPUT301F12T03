package ca.ualberta.cs.c301_repository;

import java.util.ArrayList;
import java.util.List;

import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_interfaces.Visibility;

public class TfTask implements Task {
    
    private List<TaskItem> itemList = new ArrayList<TaskItem>();
    
    private String deviceId = "Device ID";
    
    private String title = "Task Title";
    
    private String description = "Task Description";
    
    private Visibility visibility = Visibility.PUBLIC;
    
    private Boolean fulfilled = false;
    
    private Boolean modified = false;
    
    public Boolean addItem(TaskItem item) {
        Boolean success = itemList.add(item);
        if (success) {
            setModified(true);
        }
        return success;
    }

    public List<TaskItem> getAllItems() {
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

    @Override
    public String toString() {
        return "Task " + title + ", " + description;
    }

}
