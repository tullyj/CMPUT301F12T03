package ca.ualberta.cs.c301_tests;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import ca.ualberta.cs.c301_crowdclient.CrowdClient;
import ca.ualberta.cs.c301_crowdclient.CrowdSourcerEntry;

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public void testGetEntryList() {
        List<CrowdSourcerEntry> entryList = null;
        try {
            entryList = crowdClient.getEntryList();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(entryList);
        assertFalse(entryList.isEmpty());
        for (CrowdSourcerEntry entry : entryList) {
            assertNotNull(entry.getContent());
        }
    }
    

}
