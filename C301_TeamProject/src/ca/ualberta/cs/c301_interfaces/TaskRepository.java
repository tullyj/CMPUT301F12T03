package ca.ualberta.cs.c301_interfaces;

import java.util.List;

/**
 * Interface for interacting with the task repository object.
 * @author colinhunt
 *
 */
public interface TaskRepository {

    public void addTask(Task task);
    
    public List<Task> getTasksByDeviceId(String deviceId);
    
    public List<Task> getAllTasks();
}
