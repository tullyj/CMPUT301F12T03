package ca.ualberta.cs.c301_interfaces;

import java.util.List;

public interface TaskRepository {

    public void addTask();
    
    public List<Task> getTasksById(String id);
    
    public List<Task> getAllTasks();
}
