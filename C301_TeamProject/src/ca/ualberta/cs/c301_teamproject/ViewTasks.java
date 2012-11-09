package ca.ualberta.cs.c301_teamproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ca.ualberta.cs.c301_crowdclient.CrowdSourcerEntry;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

/**
 * This class is used for viewing tasks. When fully implemented the screen wont freeze when loading
 * 
 *
 */
public class ViewTasks extends Activity {
    
    public static final String TASK_ID = "ca.ualberta.cs.c301_teamproject.TASK_ID";
    ArrayAdapter<CrowdSourcerEntry> adapter;
    private List<Map<String,String>> mapList;
    private List<CrowdSourcerEntry> shallowEntryList = new ArrayList<CrowdSourcerEntry>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tasks);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
      
        	//starting the loading of the tasks
        	new loadTasks().execute();
        	
        
        	//creating the list view click listener
            ListView listView = (ListView) findViewById(R.id.taskList);
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                        int position, long id) {
                    viewTask(position, shallowEntryList);
                }
            });
           
    }
       
    
    /**
     * 
     * Async task for loading. Works now
     *
     */
    private class loadTasks extends AsyncTask<String, String, String>{
    	
    	Dialog load = new Dialog(ViewTasks.this);


		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
				//get the tasks
			    try {
			    mapList = TfTaskRepository.getShallowEntries();
				shallowEntryList = new ArrayList<CrowdSourcerEntry>();
	            for (Map<String,String> map : mapList) {
	               CrowdSourcerEntry entry = new CrowdSourcerEntry();
	               entry.setId(map.get("id"));
	               entry.setDescription(map.get("description"));
	               entry.setSummary(map.get("summary"));
	               shallowEntryList.add(entry);
	            }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            
			return null;
		}
		
		protected void onPreExecute(){
			
			//show this loading spinner
            load.setContentView(R.layout.save_load_dialog);
            load.setTitle("Loading Tasks");
    		load.show();
    		
    	}
    	
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			//update the list view and then dismiss the loading spinner
			updateListView();
			load.dismiss();
		
		}	
    }
    
   public void updateListView(){
    	
    	adapter = new ArrayAdapter<CrowdSourcerEntry>(this, android.R.layout.simple_list_item_1, shallowEntryList);
    	ListView listView = (ListView)findViewById(R.id.taskList);
    	listView.setAdapter(adapter);
	
    }
   

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void viewTask(int position, 
            List<CrowdSourcerEntry> shallowEntryList) {
        CrowdSourcerEntry shallowEntry = shallowEntryList.get(position);
        Intent intent = new Intent(this, ViewSingleTask.class);
        intent.putExtra(TASK_ID, shallowEntry.getId());
        startActivity(intent);
    }

}
