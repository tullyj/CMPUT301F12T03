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
import ca.ualberta.cs.c301_repository.TfTask;
import ca.ualberta.cs.c301_repository.TfTaskRepository;
import ca.ualberta.cs.c301_utils.Utility;

/**
 * This class is used for viewing a list of all the tasks. This activity is
 * used with different types of filters. For example you can view you tasks,
 * your local tasks, all tasks, your liked tasks. This activity is also
 * used to start ViewSingleTask, when a specific task is selected from the
 * list view
 * @author topched
 * @author colin
 */
public class ViewTasks extends Activity {
    
    public static final String TASK_ID = 
            "ca.ualberta.cs.c301_teamproject.TASK_ID";
    ArrayAdapter<CrowdSourcerEntry> adapter;
    ArrayAdapter<TfTask> adapterLocal;
    private List<Map<String,String>> mapList;
    private List<CrowdSourcerEntry> shallowEntryList = 
            new ArrayList<CrowdSourcerEntry>();
    private List<TfTask> localEntryList = new ArrayList<TfTask>();
    private boolean allTasks = false;
    private boolean myTasks = false;
    private boolean likedTasks = false;
    private boolean localTasks = false;
    private String[] passedTaskIds;
    public static final String LOCAL = "local";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tasks);
        
        //strings used for changing the title above the list view
        String showMy = "<u>Your Tasks<u>";
        String showAll = "<u>All Tasks<u>";
        String showLike = "<u>Your Liked Tasks<u>";
        String showLocal = "<u>Your Local Tasks<u>";
        
        StrictMode.ThreadPolicy policy = 
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
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
        	localTasks = false;
        	
        	showType.setText(Html.fromHtml(showMy));
        }
        
        //we know we want all tasks here
        if(type.equals("all")){
        	allTasks = true;
        	myTasks = false;
        	likedTasks = false;
        	localTasks = false;
        	
        	showType.setText(Html.fromHtml(showAll));
        }
        
        //we want liked tasks here
        if(type.equals("liked")){
            allTasks = false;
            myTasks = false;
            likedTasks = true;
            localTasks = false;
            
            showType.setText(Html.fromHtml(showLike));
        }
        
        //we want our local tasks
        if(type.equals("local")){
            allTasks = false;
            myTasks = false;
            likedTasks = false;
            localTasks = true;
            
            showType.setText(Html.fromHtml(showLocal));
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
    
    /**
     * This method is used to filer the tasks. It simply only grabs
     * the passedTaskIds from all of list of all the ids
     */
    public void filterMyTasks() {
    	
    	//iterate over the list
    	Iterator<CrowdSourcerEntry> it = shallowEntryList.iterator();
    	List<CrowdSourcerEntry> temp = new ArrayList<CrowdSourcerEntry>();
    	
    	//looping through the entries and grabbing only the ones we need
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
    
    /**
     * This method is called when the filter button is clicked. Show a dialog
     * to grab some information from the user then re-load viewTasks with
     * a new set of parameters
     * @param view
     */
    public void filterClicked(View view) {
        
        final AlertDialog.Builder filter =
                new AlertDialog.Builder(ViewTasks.this);

        filter.setTitle("Filter Task List");
        filter.setItems(R.array.filter_choices, 
                new DialogInterface.OnClickListener() {
            
            public void onClick(DialogInterface dialog, int which) {
                      
                String[] get = 
                        getResources().getStringArray(R.array.filter_choices);
                
                //get the type selected
                String type = get[which];
                
                //pass the value to be re-loaded
                reloadWithNewTasks(type);               
            }     
        });
   
        filter.show();
    }
    
    /**
     * This is called from filterClicked. This simply takes in the "type" of
     * filter we want to apply to the task list. This methods re-loads
     * viewTasks with the new parameters
     * @param type  Type of filter to apply
     */
    public void reloadWithNewTasks(String type) {
        
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
            
            //grab the local task ids
            ids = TfTaskRepository.getLocalTaskIds(getApplicationContext());
            
            intent.putExtra(MainPage.TYPE, "local");
            intent.putExtra(MainPage.IDS, ids);
            startActivity(intent);
        }      
    }
    
    /**
     * Private AsyncTask used for loading the task list
     *
     */
    private class loadTasks extends AsyncTask<String, String, String> {
    	
    	Dialog load = new Dialog(ViewTasks.this);

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
		    //if we are loading from the repository
		    if(!localTasks) {
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
    		
    		//we are loading locally
		    }else {		        
		        localEntryList = 
		                TfTaskRepository.getLocalTasks(getApplicationContext());    
		    }
	        
			//if we need to filter some tasks
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
   
   /**
    * This method just updates the list view. It makes a decision on what to do
    * based off if we are viewing local tasks or not 
    */
   public void updateListView() {
       
       ListView listView = (ListView)findViewById(R.id.taskList);
       ArrayList<ItemListElement> show = new ArrayList<ItemListElement>();

    
       //we have public tasks so print those
       if(!localTasks){
           
           for(int i = 0;i<shallowEntryList.size();i++){
               
               CrowdSourcerEntry entry = shallowEntryList.get(i);
               
               String title = entry.getSummary();
               //String desc = entry.getDescription();
               String top = title;
               String bottom = "Click task to view and fulfill";
               
               show.add(new ItemListElement(Utility.getIconFromString("Task"),top,bottom));
               
               
           }
           
           ItemListElement[] elm = new ItemListElement[show.size()];
           show.toArray(elm);
           
        	ItemListAdapter a = new ItemListAdapter(this,R.layout.list_multi, elm);
        	listView.setAdapter(a);
       }
       
       //we have local tasks so print those 
       if(localTasks){
           
           List<TfTask> entryList = 
                   TfTaskRepository.getLocalTasks(getApplicationContext());
           
           for(int j = 0;j<entryList.size();j++){
               
               TfTask t = entryList.get(j);
               
               String title = t.getTitle();
               String desc = t.getDescription();
               String top = title;
               String bottom = "Description: " + desc;
               
               show.add(new ItemListElement(
                       Utility.getIconFromString("Task"),top,bottom));
               
               
           }
           
           ItemListElement[] elm2 = new ItemListElement[show.size()];
           show.toArray(elm2);
           ItemListAdapter a = new ItemListAdapter(this,R.layout.list_multi, elm2);
           listView.setAdapter(a);
       }	
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
    
    /**
     * This method is called when a item is selected from the list view
     * @param position              The position selected
     * @param shallowEntryList      List of tasks
     */
    private void viewTask(int position, 
            List<CrowdSourcerEntry> shallowEntryList) {
        
        Intent intent = new Intent(this, ViewSingleTask.class);
        
        //if we clicked on a public task
        if(!localTasks){
            CrowdSourcerEntry shallowEntry = shallowEntryList.get(position);
            intent.putExtra(TASK_ID, shallowEntry.getId());
            intent.putExtra(LOCAL, "no");
            startActivity(intent);
                       
        //we clicked a private task    
        }else{
            TfTask t = localEntryList.get(position);
            intent.putExtra(TASK_ID, t.getTaskId());
            intent.putExtra(LOCAL, "yes");
            startActivity(intent);
        }
    }
}
