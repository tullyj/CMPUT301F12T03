package ca.ualberta.cs.c301_tests;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;
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
        TfTaskRepository.addTask(task);
        Boolean success = false;
        try {
            List<Map<String,String>> mapList = TfTaskRepository.getShallowEntries();
            for (Map<String,String> map : mapList) {
                if (map.get("summary") == taskTitle) {
                    success = true;
                    break;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception occurred");
        }
        assertTrue(success);
    }

    @Test
    public void testGetTasksByDeviceId() {
        String deviceId = UUID.randomUUID().toString();
        Task task = new TfTask();
        task.setDeviceId(deviceId);
        TfTaskRepository.addTask(task);
        try {
            List<Task> gottenTask = TfTaskRepository.getTasksByDeviceId(deviceId);
            assertEquals(gottenTask.size(), 1);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred");
        }
    }

    @Test
    public void testGetAllTasks() {
        List<Task> taskList = null;
        Task testTask = new TfTask();
        try {
            TfTaskRepository.addTask(testTask);
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
