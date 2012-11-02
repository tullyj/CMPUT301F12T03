package ca.ualberta.cs.c301_interfaces;

import java.util.List;


public interface Task {
    
    public Boolean addItem(TaskItem item);
    
    public List<TaskItem> getAllItems();
    
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
}
