package ca.ualberta.cs.c301_tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

public class TfTaskRepositoryTest {

    @Test
    public void testAddTask() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetTasksByDeviceId() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAllTasks() {
        List<Task> taskList = null;
        try {
            taskList = TfTaskRepository.getAllTasks();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(taskList);
        for (Task task : taskList) {
            assertNotNull(task);
        }
    }

}
