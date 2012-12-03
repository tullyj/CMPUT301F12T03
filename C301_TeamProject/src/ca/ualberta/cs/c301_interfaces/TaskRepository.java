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
