package ca.ualberta.cs.c301_teamproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    private boolean allTasks = false;
    private boolean myTasks = false;
    private boolean likedTasks = false;
    private String[] passedTaskIds;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tasks);
        
        String showMy = "<u>Your Tasks<u>";
        String showAll = "<u>All Tasks<u>";
        String showLike = "<u>Your Liked Tasks<u>";
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        //grab the intent to see which filter we are using
        Intent intent = getIntent();
        String type = intent.getStringExtra(MainPage.TYPE);

        passedTaskIds = intent.getStringArrayExtra(MainPage.IDS);
        
        TextView showType = (TextView)findViewById(R.id.showCurrentTaskType);
        
        
        
        //we know we want local tasks only
        if(type.equals("my")){
        	myTasks = true;
        	allTasks = false;
        	likedTasks = false;
        	
        	showType.setText(Html.fromHtml(showMy));
        }
        
        //we know we want all tasks here
        if(type.equals("all")){
        	allTasks = true;
        	myTasks = false;
        	likedTasks = false;
        	
        	showType.setText(Html.fromHtml(showAll));
        }
        
        if(type.equals("liked")){
            allTasks = false;
            myTasks = false;
            likedTasks = true;
            
            showType.setText(Html.fromHtml(showLike));
        }
        
      
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
    
    //used to remove specific entries
    public void filterMyTasks(){
    	
    	//iterate over the list
    	Iterator<CrowdSourcerEntry> it = shallowEntryList.iterator();
    	List<CrowdSourcerEntry> temp = new ArrayList<CrowdSourcerEntry>();
    	
    	//looping through the entries and grabbing
    	while(it.hasNext()){
    		
    		CrowdSourcerEntry entry = it.next();
    		String id = entry.getId();
    		
    		for(int i = 0;i<passedTaskIds.length;i++){
    			
    			if(passedTaskIds[i].equals(id)){
    				temp.add(entry);
    			}
    			
    		}
	
    	}
    	
    	shallowEntryList = temp;
   	
    }
    
    public void filterClicked(View view){
        
        final AlertDialog.Builder filter =
                new AlertDialog.Builder(ViewTasks.this);

        filter.setTitle("Filter Task List");
        filter.setItems(R.array.filter_choices, new DialogInterface.OnClickListener() {
            
            public void onClick(DialogInterface dialog, int which) {
                      
                String[] get = getResources().getStringArray(R.array.filter_choices);
                
                //get the type selected
                String type = get[which];
                
                //pass the value to be re-loaded
                reloadWithNewTasks(type);
                
            }
            
            
        });
   
        filter.show();
    }
    
    public void reloadWithNewTasks(String type){
        
        Intent intent = new Intent(this, ViewTasks.class);
        MyLocalTaskInformation lt = new MyLocalTaskInformation();
        String[] ids;
        
        if(type.equals("View Your Tasks")){
            
            //getting my task ids 
            ids = lt.loadMyTaskIds(getApplicationContext());
            
            intent.putExtra(MainPage.TYPE, "my");
            intent.putExtra(MainPage.IDS, ids);
            startActivity(intent);
            
        }else if(type.equals("View All Tasks")){
            
            //all task ids to be loaded dont need to grab any
            intent.putExtra(MainPage.TYPE, "all");
            startActivity(intent);
            
        }else if(type.equals("View Your Liked Tasks")){
            
            //getting my liked tasks
            ArrayList<String> temp = new ArrayList<String>();
            temp = lt.getLikedTasks(getApplicationContext());
            
            ids = temp.toArray(new String[temp.size()]);
            
            intent.putExtra(MainPage.TYPE, "liked");
            intent.putExtra(MainPage.IDS, ids);
            startActivity(intent);
            
            
        }else if(type.equals("View Your Local Tasks")){
            
            
        }
       
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
	        
			//if we only want to view local tasks only
			if(myTasks || likedTasks){
				filterMyTasks();
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
    }//end of load tasks
    
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
