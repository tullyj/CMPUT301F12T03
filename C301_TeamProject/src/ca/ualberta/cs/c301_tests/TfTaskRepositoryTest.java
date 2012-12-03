package ca.ualberta.cs.c301_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfTask;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

/**
 * There must be entries in crowdsourcer for these tests to pass.
 * @author colinhunt
 *
 */
public class TfTaskRepositoryTest {

    @Test
    public void testAddTask() {
        String taskTitle = UUID.randomUUID().toString();
        Task task = new TfTask();
        task.setDescription("Test task");
        task.setTitle(taskTitle);
        String taskId = TfTaskRepository.addTask(task, null);
        Boolean success = false;
        Task gottenTask = null;
        try {
            gottenTask = TfTaskRepository.getTaskById(taskId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception occurred");
        }
        assertNotNull(gottenTask);
        assertEquals(gottenTask.getTitle(), taskTitle);
    }
    
    @Test
    public void testUpdateTask() {
        String taskTitle = UUID.randomUUID().toString();
        String taskTitle2 = UUID.randomUUID().toString();
        Task task = new TfTask();
        task.setDescription("Test for update task");
        task.setTitle(taskTitle);
        String taskId = TfTaskRepository.addTask(task, null);
        
        task.setTaskId(taskId);
        task.setTitle(taskTitle2);
        
        try {
            TfTaskRepository.updateTask(task, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Task gottenTask = null;
        try {
            gottenTask = TfTaskRepository.getTaskById(taskId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(gottenTask);
        assertEquals(gottenTask.getTitle(), taskTitle2);
    }

    @Test
    public void testGetAllTasks() {
        List<Task> taskList = null;
        Task testTask = new TfTask();
        try {
            TfTaskRepository.addTask(testTask, null);
            taskList = TfTaskRepository.getAllTasks();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception occurred");
        }
        assertNotNull(taskList);
        for (Task task : taskList) {
            assertNotNull(task);
        }
    }

}
