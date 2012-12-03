package ca.ualberta.cs.c301_tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

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
        Task task = new TfTask();
        task.setDescription("TestDescription");
        String taskId = localRepo.insertTask(task, null);
        List<TfTask> taskList = localRepo.getTaskList(null);
        assertFalse(taskList.isEmpty());
        localRepo.removeTask(taskId, null);
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
