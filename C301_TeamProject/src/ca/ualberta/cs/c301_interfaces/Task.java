/**
 * Task Force Application
 * See github for license and other information: 
 * github.com/tullyj/CMPUT301F12T03/
 * 
 * Task Force is created by: 
 * Colin Hunt, Edwin Chung, 
 * Kris Kushniruk, and Tully Johnson.
 */
package ca.ualberta.cs.c301_interfaces;

import java.util.List;

import ca.ualberta.cs.c301_repository.TfTaskItem;

/**
 * Interface for interacting with a task object.
 * @author colinhunt
 *
 */
public interface Task {
    
    public Boolean addItem(TaskItem item);
    
    public List<TfTaskItem> getAllItems();
    
    public String getTitle();
    
    public void setTitle(String title);
    
    public String getDescription();
    
    public void setDescription(String descr);
    
    public Visibility getVisibility();
    
    public void setVisibility(Visibility v);
    
    public Boolean isFulfilled();
    
    public void setFulfilled(Boolean b);
    
    public Boolean isModified();
    
    public void setModified(Boolean b);
    
    public String getDeviceId();
    
    public void setDeviceId(String deviceId);
    
    public String toString();

    public String getTaskId();
    
    public void setTaskId(String id);
    
    public String getEmail();

    public void setEmail(String email);

    public TaskItem getItemByType(String itemType);
}
