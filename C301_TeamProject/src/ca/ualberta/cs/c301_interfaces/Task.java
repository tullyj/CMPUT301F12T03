package ca.ualberta.cs.c301_interfaces;

import java.util.List;


public interface Task {
    
    public void addItem();
    
    public List<TaskItem> getAllItems();
    
    public String getTitle();
    
    public void setTitle(String title);
    
    public String getDescription();
    
    public void setDescription(String descr);
    
    public Visibility getVisibility();
    
    public void setVisibility(Visibility v);
    
    public Boolean isFulfilled();
    
    public void setFulfilled(Boolean b);
}
