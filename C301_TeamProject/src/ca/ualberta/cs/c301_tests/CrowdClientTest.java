package ca.ualberta.cs.c301_tests;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import ca.ualberta.cs.c301_crowdclient.CrowdClient;
import ca.ualberta.cs.c301_crowdclient.CrowdSourcerEntry;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfTask;

public class CrowdClientTest extends TestCase {
    private CrowdClient crowdClient = new CrowdClient();

    public void testGetShallowList() {
        CrowdClient crowdClient = new CrowdClient();
        try {
            List<Map<String,String>> shallowEntryList = crowdClient.getShallowList();
            assertNotNull(shallowEntryList);
            assertFalse(shallowEntryList.isEmpty());
            for (Map<String,String> map : shallowEntryList) {
                assertTrue(map.containsKey("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void testGetEntryList() {
        List<CrowdSourcerEntry> entryList = null;
        try {
            entryList = crowdClient.getEntryList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(entryList);
        assertFalse(entryList.isEmpty());
        for (CrowdSourcerEntry entry : entryList) {
            assertNotNull(entry.getContent());
        }
    }
    
    public void testUpdateEntry() {
        String entrySummary = "TestSummary";
        String changedSummary = "ChangedSummary";
//        try {
//            crowdClient.removeEntry(entryDescr);
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("Exception removing the test entry before");
//        }
        
        CrowdSourcerEntry entry = new CrowdSourcerEntry();
        CrowdSourcerEntry returnedEntry = new CrowdSourcerEntry();
        CrowdSourcerEntry gottenEntry = new CrowdSourcerEntry();
        entry.setSummary(entrySummary);
        
        try {
            returnedEntry = crowdClient.insertEntry(entry);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception on inserting entry");
        }
        
        assertEquals(returnedEntry.getSummary(), entrySummary);
        entry.setSummary(changedSummary);
      
        entry.setId(returnedEntry.getId());
//        entry.setSummary("IS_THIS_WORKING");
        // Have to set some content because update sets content or we get null
        // errors from the webservice
        Task task = new TfTask();
        entry.setContent((TfTask) task);
        try {
            crowdClient.updateEntry(entry);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception on updating the entry in testUpdateEntry");
        }
        
        try {
            gottenEntry = crowdClient.getEntry(returnedEntry.getId());
        } catch (Exception e1) {
            e1.printStackTrace();
            fail("Exception on getting the updated entry");
        }
        
        assertEquals(gottenEntry.getId(), returnedEntry.getId());
        assertEquals(gottenEntry.getSummary(), changedSummary);
        
        //cleanup
        try {
            crowdClient.removeEntry(returnedEntry.getId());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception removing the test entry after");
        }
    }
}
