package ca.ualberta.cs.c301_crowdclient;

import java.util.List;

import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_interfaces.Visibility;
import ca.ualberta.cs.c301_repository.TfTaskItem;

/**
 * Interface to allow any class to be used as content in a CrowdSourcerEntry
 * @author Colin Hunt - colin[at]ualberta.ca
 * University of Alberta, Department of Computing Science
 */
public class CrowdSourcerContent implements Task {
    public CrowdSourcerContent() {}

    public Boolean addItem(TaskItem item) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<TfTaskItem> getAllItems() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setTitle(String title) {
        // TODO Auto-generated method stub
        
    }

    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setDescription(String descr) {
        // TODO Auto-generated method stub
        
    }

    public Visibility getVisibility() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setVisibility(Visibility v) {
        // TODO Auto-generated method stub
        
    }

    public Boolean isFulfilled() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setFulfilled(Boolean b) {
        // TODO Auto-generated method stub
        
    }

    public Boolean isModified() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setModified(Boolean b) {
        // TODO Auto-generated method stub
        
    }

    public String getDeviceId() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setDeviceId(String deviceId) {
        // TODO Auto-generated method stub
        
    }

    public String getTaskId() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setTaskId(String id) {
        // TODO Auto-generated method stub
        
    }
}
