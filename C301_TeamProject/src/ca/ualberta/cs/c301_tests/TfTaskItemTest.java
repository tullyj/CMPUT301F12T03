/**
 * Task Force Application
 * See github for license and other information: 
 * github.com/tullyj/CMPUT301F12T03/
 * 
 * Task Force is created by: 
 * Colin Hunt, Edwin Chung, 
 * Kris Kushniruk, and Tully Johnson.
 */
package ca.ualberta.cs.c301_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_repository.TfTaskItem;

public class TfTaskItemTest {

    @Test
    public void testTfTaskItem() {
        TfTaskItem item = new TfTaskItem(ItemType.AUDIO, 5, "Test item");
        assertEquals(item.getType(), ItemType.AUDIO);
        int num = item.getNumber();
        assertEquals(num, 5);
        assertEquals(item.getDescription(), "Test item");
    }

    @Test
    public void testAddFiles() {
        TfTaskItem item = new TfTaskItem(ItemType.AUDIO, 5, "Test item");
        List<File> files = new ArrayList<File>();
        File object = null;
        files.add(object);
        files.add(object);
        files.add(object);
        try {
            item.addFiles(files);
        } catch (Exception e) {
            fail("Exception occured when adding files");
        }
    }

    @Test
    public void testGetAllFiles() {
        TfTaskItem item = new TfTaskItem(ItemType.AUDIO, 5, "Test item");
        List<File> files = new ArrayList<File>();
        File object = null;
        files.add(object);
        files.add(object);
        files.add(object);
        try {
            item.addFiles(files);
        } catch (Exception e) {
            fail("Exception occured when adding files");
        }
        List<File> gottenFiles = item.getAllFiles();
        assertEquals(gottenFiles.size(), 3);
    }


}
