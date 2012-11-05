/**
 * 
 */
package ca.ualberta.cs.c301_tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_interfaces.Visibility;
import ca.ualberta.cs.c301_repository.TfTask;
import ca.ualberta.cs.c301_repository.TfTaskItem;

/**
 * @author colinhunt
 *
 */
public class TfTaskTest {

    /**
     * Test method for {@link ca.ualberta.cs.c301_repository.TfTask#addItem(ca.ualberta.cs.c301_interfaces.TaskItem)}.
     */
    @Test
    public void testAddItem() {
        TfTask task = new TfTask();
        TaskItem item = new TfTaskItem(ItemType.TEXT, 5, "test item");
        task.addItem(item);
        List<TfTaskItem> itemList = task.getAllItems();
        assertTrue(itemList.size() == 1);
        TaskItem gottenItem = itemList.get(1);
        assertEquals(gottenItem.getType(), item.getType());
        assertEquals(gottenItem.getNumber(), item.getNumber());
        assertEquals(gottenItem.getDescription(), item.getDescription());
    }

    /**
     * Test method for {@link ca.ualberta.cs.c301_repository.TfTask#getAllItems()}.
     */
    @Test
    public void testGetAllItems() {
        TfTask task = new TfTask();
        TaskItem item1 = new TfTaskItem(ItemType.TEXT, 1, "test item 1");
        TaskItem item2 = new TfTaskItem(ItemType.PHOTO, 2, "test item 1");
        TaskItem item3 = new TfTaskItem(ItemType.AUDIO, 3, "test item 1");
        task.addItem(item1);
        task.addItem(item2);
        task.addItem(item3);
        List<TfTaskItem> gottenItems = task.getAllItems();
        assertTrue(gottenItems.size() == 3);
        assertEquals(gottenItems.get(0).getType(), item1.getType());
        assertEquals(gottenItems.get(1).getType(), item2.getType());
        assertEquals(gottenItems.get(2).getType(), item3.getType());
    }

    /**
     * Test method for {@link ca.ualberta.cs.c301_repository.TfTask#setTitle(java.lang.String)}.
     */
    @Test
    public void testSetTitle() {
        TfTask task = new TfTask();
        String testTitle = "Test title";
        task.setTitle(testTitle);
        assertEquals(task.getTitle(), testTitle);
    }

    /**
     * Test method for {@link ca.ualberta.cs.c301_repository.TfTask#setDescription(java.lang.String)}.
     */
    @Test
    public void testSetDescription() {
        TfTask task = new TfTask();
        String testDescription = "Test description";
        task.setTitle(testDescription);
        assertEquals(task.getDescription(), testDescription);
    }

    /**
     * Test method for {@link ca.ualberta.cs.c301_repository.TfTask#setVisibility(ca.ualberta.cs.c301_interfaces.Visibility)}.
     */
    @Test
    public void testSetVisibility() {
        TfTask task = new TfTask();
        assertEquals(task.getVisibility(), Visibility.PUBLIC);
        Visibility testVisibility = Visibility.PRIVATE;
        task.setVisibility(testVisibility);
        assertEquals(task.getVisibility(), testVisibility);
    }

    /**
     * Test method for {@link ca.ualberta.cs.c301_repository.TfTask#setFulfilled(java.lang.Boolean)}.
     */
    @Test
    public void testSetFulfilled() {
        TfTask task = new TfTask();
        assertFalse(task.isFulfilled());
        task.setFulfilled(true);
        assertTrue(task.isFulfilled());
    }

    /**
     * Test method for {@link ca.ualberta.cs.c301_repository.TfTask#setModified(java.lang.Boolean)}.
     */
    @Test
    public void testSetModified() {
        TfTask task = new TfTask();
        assertFalse(task.isModified());
        task.setModified(true);
        assertTrue(task.isModified());
    }

    /**
     * Test method for {@link ca.ualberta.cs.c301_repository.TfTask#setDeviceId(java.lang.String)}.
     */
    @Test
    public void testSetDeviceId() {
        TfTask task = new TfTask();
        String testDeviceId = "Test device ID";
        task.setDeviceId(testDeviceId);
        assertEquals(task.getDeviceId(), testDeviceId);
    }

    /**
     * Test method for {@link ca.ualberta.cs.c301_repository.TfTask#setTaskId(java.lang.String)}.
     */
    @Test
    public void testSetTaskId() {
        TfTask task = new TfTask();
        String testTaskId = "Test task ID";
        task.setTaskId(testTaskId);
        assertEquals(task.getTaskId(), testTaskId);
    }

}
