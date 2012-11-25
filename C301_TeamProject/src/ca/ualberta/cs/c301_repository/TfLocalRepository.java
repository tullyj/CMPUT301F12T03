package ca.ualberta.cs.c301_repository;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import ca.ualberta.cs.c301_crowdclient.CrowdSourcerEntry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TfLocalRepository /* extends Activity */ {

    // JSON Utilities
    private Gson gson = new Gson();
    
    // Filename for local storage
    private static String STORAGE_FILE = "task_force_local_tasks";

    // List to represent stored entries
    private static List<CrowdSourcerEntry> entryList = 
            new ArrayList<CrowdSourcerEntry>();
    
    private final Type listType = 
            new TypeToken<List<CrowdSourcerEntry>>(){}.getType();
    
//    public TfLocalRepository(Context c) {
//        // load the entry list from local storage into the static variable
//        try {
//            FileInputStream fIn = c.openFileInput(STORAGE_FILE);
//            InputStreamReader isr = new InputStreamReader(fIn);
//            BufferedReader buffreader = new BufferedReader (isr);
//            
//            String jsonArray = buffreader.readLine();
//            entryList = gson.fromJson(jsonArray, listType);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    public Boolean insertEntry(CrowdSourcerEntry entry, Context c) {
        Boolean success = entryList.add(entry);
        saveLocalList(c);
        return success;
    }

    private void saveLocalList(Context c) {
        try {
            String jsonArray = gson.toJson(entryList, listType);
            
            FileOutputStream fOut = 
                    c.openFileOutput(STORAGE_FILE, Context.MODE_PRIVATE);
            OutputStreamWriter ostream = new OutputStreamWriter(fOut);
            ostream.write(jsonArray);
            ostream.flush();
            ostream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
