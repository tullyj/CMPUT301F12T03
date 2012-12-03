package ca.ualberta.cs.c301_tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfLocalRepository;
import ca.ualberta.cs.c301_repository.TfTask;

public class TfLocalRepositoryTest {
    private TfLocalRepository localRepo = new TfLocalRepository();
    @Test
    public void testInsertAndRetrieveTask() {
        Task task = new TfTask();
        task.setDescription("TestDescription");
        String taskId = localRepo.insertTask(task, null);
        Task returnedTask = localRepo.getTask(taskId, null);
        localRepo.removeTask(taskId, null);
        assertTrue(task.getDescription().equals(returnedTask.getDescription()));
    }

    @Test
    public void testGetTaskList() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdate() {
        Task task = new TfTask();
        task.setDescription("TestDescription");
        String taskId = localRepo.insertTask(task, null);
        task.setTaskId(taskId);
        task.setDescription("ChangedDescription");
        localRepo.update(task, null);
        Task returnedTask = localRepo.getTask(taskId, null);
        localRepo.removeTask(taskId, null);
        assertTrue(task.getDescription().equals(returnedTask.getDescription()));
    }

}
