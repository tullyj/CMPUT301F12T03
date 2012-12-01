package ca.ualberta.cs.c301_teamproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfTaskItem;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

/**
 * Shows items for a task in a list, click item to view. Click "+" button
 * to add files to task/item (fulfilling the task).
 *
 */
public class ViewSingleTask extends Activity {

	public static Task task = null;
	private String taskId;
	public ArrayList<String> myLikedIds;
	public Button like;
	private boolean isLocal = false;


	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_single_task);
        
        myLikedIds = new ArrayList<String>();
        like = (Button)findViewById(R.id.likeTask);
        
        Intent intent = getIntent();
        taskId = intent.getExtras().getString(ViewTasks.TASK_ID);
        String localTemp = intent.getExtras().getString(ViewTasks.LOCAL);
        
        if(localTemp.equals("yes")){
            isLocal = true;
        }else{
            isLocal = false;
        }
        
        
        boolean likeTask = doILikeThisTask();
        
        //if i already like the task show unlike
        //else if i dont like it show like
        if(likeTask){
            updateButtonText("unlike");
        }else{
            updateButtonText("like");           
        }
        
        //load the single task async style
        new loadSingleTask().execute();     
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
     * This method just modifies text on buttons. 
     * @param val   The flag to know what to update the text to
     */
    public void updateButtonText(String val){
    
        if(val.equals("like")){
            like.setText(R.string.like_task);
        }
        
        if(val.equals("unlike")){
            like.setText(R.string.unlike_task);
        }      
    }
    
	/**
	 * Refreshes, or recreates, the listview on the ItemList screen.
	 */
    public void updateList(final Task task){   	
    	
        Toast.makeText(getApplicationContext(), 
        		task.getTaskId(), 
					Toast.LENGTH_LONG).show();
    	
    	
    	final List<TfTaskItem> items = task.getAllItems();
    	ItemListElement[] elements = new ItemListElement[items.size()];
    	String title = "";
    	//String itemType = null;
    	String[] info = new String[2];
    	for(int i = 0; i < items.size(); i++){
    		info = getTypeInfo(items.get(i).getType());
    		title = info[0];
    		//itemT = 
    		elements[i] = new ItemListElement(android.R.drawable.ic_input_get, 
    			title, items.get(i).getDescription());
    		title = "";
    	}
        //final String itemT = itemType;
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.list_multi, elements);
        ListView listView = (ListView) findViewById(R.id.singleTaskList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id){
//				Toast.makeText(getApplicationContext(), 
//					"Opening Item: " + items.get(position).getType().toString(), 
//						Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(getApplicationContext(), ItemList.class);
				// Pass TaskId, and Item Number
				String[] infoT = getTypeInfo(items.get(position).getType());
				
				intent.putExtra("SendItem", new String[]{infoT[1], 
						infoT[0], items.get(position).getDescription()});
				
				
				Toast.makeText(getApplicationContext(), 
    					task.getTaskId(), 
    						Toast.LENGTH_LONG).show();
				
				
				startActivity(intent);
			}	
        });
    }
    
    private String[] getTypeInfo(ItemType type) {
    	//Toast.makeText(getApplicationContext(), 
		//		"Opening Item: " + type.toString(), Toast.LENGTH_LONG).show();
    	switch(type) {
		    case TEXT:
		    	return new String[]{"Texts", "TEXT"};
			case PHOTO:
				return new String[]{"Photos", "PHOTO"};
			case AUDIO:
				return new String[]{"Audio", "AUDIO"};
			case VIDEO:
				return new String[]{"Videos", "VIDEO"};
    	}
    	return new String[]{"",""};
    }
    
    /**
     * Called when the like/unlike button clicked. Either add or remove
     * the id from "you" liked list then update the text on the button to
     * reflect the change
     */
    public void likeTaskAction(View view){
        
        boolean like = doILikeThisTask();
        MyLocalTaskInformation save = new MyLocalTaskInformation();
        
        //if we already like the task we now want to unlike it
        if(like){
        
            //remove the value from the list
            save.removeLikedTask(taskId, getApplicationContext());
            
            //set the text on the button to like again
            updateButtonText("like");           
            
        //if we dont like the task add it to the list
        }else{
            save.saveLikedTasks(taskId, getApplicationContext());
            updateButtonText("unlike");
        }
                
    }
    
    /**
     * This method simply returns a boolean if "you" like this current task
     * @return  true iff "you" like this task
     */
    public boolean doILikeThisTask(){
        
        //re grab the arraylist incase an item was added
        MyLocalTaskInformation lti = new MyLocalTaskInformation();
        myLikedIds = lti.getLikedTasks(getApplicationContext());
        
        Iterator<String> it = myLikedIds.iterator();
        
        while(it.hasNext()){
            
            if(it.next().equals(taskId))
                return true;
        }
        
        return false;      
    }
    
    public void saveTaskLocally(View view){
        
        //need to save or update the task locally
      
    }
    
    /**
     * Async task for loading of a single task
     *
     */
    private class loadSingleTask extends AsyncTask<String, String, String>{
    	
    	Dialog load = new Dialog(ViewSingleTask.this);
    	MyLocalTaskInformation lti = new MyLocalTaskInformation();
    	//private Task sTask = null;
    	

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
		    //we are grabbing from the web service
		    if(!isLocal){
    			//grabbing the task from the repository
    	        try {
    	        	task = TfTaskRepository.getTaskById(taskId);
    	        } catch (Exception e) {
    	            System.err.println(e.getMessage());
    	            e.printStackTrace();
    	        }
    	     
    	    //we are grabbing a local task
		    }else{
		        
		        try {
		            task = TfTaskRepository.getLocalTaskById(taskId, getApplicationContext());
		        } catch (Exception e) {
		            System.err.println(e.getMessage());
		            e.printStackTrace();
		        }
		        
		    }
	        
	        //grabbing the likedIDS	        
	        myLikedIds = lti.getLikedTasks(getApplicationContext());
	        	        
			return null;
		}
    	
    	protected void onPreExecute(){
    		
    		load.setContentView(R.layout.save_load_dialog);
    		load.setTitle("Loading task");
    		load.show();
    	}
    	
    	@Override
    	protected void onPostExecute(String result){
    		
    		super.onPostExecute(result);
    		
    		TextView title = (TextView) findViewById(R.id.showTaskTitle);
            title.setText(task.getTitle());
            updateList(task);
    		
    		load.dismiss();
    	}
    } 
}
