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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfTaskItem;
import ca.ualberta.cs.c301_repository.TfTaskRepository;
import ca.ualberta.cs.c301_utils.Utility;

/**
 * This class shows items for a task in a list, click item to view. Click "+" 
 * button to add files to task/item (fulfilling the task). You can also like
 * and unlike a task from this activity.
 * @author topched
 * @author tullyj
 * @author colin
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
        
        if (localTemp.equals("yes"))
            isLocal = true;
        else
            isLocal = false;
        
        //check if i already like the task
        boolean likeTask = doILikeThisTask();
        
        //if i already like the task show unlike
        //else if i dont like it show like
        if (likeTask)
            updateButtonText("unlike");
        else
            updateButtonText("like");           
        
        //load the single task async style
        new loadSingleTask().execute();     
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_list, menu);
        return true;
    }
    
    @Override
    /**
     * When the menu button item "About" is selected display about dialog.
     * @param item  item clicked.
     */
    public boolean onOptionsItemSelected(MenuItem item){
        Dialog helpDialog = onCreateDialog(MainPage.DIALOG_ABOUT);
        helpDialog.show();
        return true;
    }

    public Dialog onCreateDialog(int id){    
        if (id == MainPage.DIALOG_ABOUT) {
            // Show details about Task Force.
            PromptDialog mDialog = new PromptDialog();
            return mDialog.aboutPrompt(this);
        }
        return null;
    }
    
    /**
     * This method just modifies text on buttons. 
     * @param val   The flag to know what to update the text to
     */
    public void updateButtonText(String val) {
    
        if (val.equals("like"))
            like.setText(R.string.like_task);
        
        if(val.equals("unlike"))
            like.setText(R.string.unlike_task);
    }
    
	/**
	 * Refreshes, or recreates, the listview on the ItemList screen.
	 */
    public void updateList(final Task task) {   	    	
    	final List<TfTaskItem> items = task.getAllItems();
    	ItemListElement[] elements = new ItemListElement[items.size()];
    	String title = "";
    	String[] info = new String[2];
    	for (int i = 0; i < items.size(); i++) {
    		info = getTypeInfo(items.get(i).getType());
    		title = info[0];
    		//itemT = 
    		elements[i] = new ItemListElement(
    		        Utility.getIconFromType(items.get(i).getType()), 
    		        title, items.get(i).getDescription());
    		title = "";
    	}
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.list_multi, 
                elements);
        ListView listView = (ListView) findViewById(R.id.singleTaskList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
				
				Intent intent = new Intent(getApplicationContext(), ItemList.class);
				// Pass TaskId, and Item Number
				String[] infoT = getTypeInfo(items.get(position).getType());
				
				intent.putExtra("SendItem", new String[] {infoT[1], 
						infoT[0], items.get(position).getDescription()});
				
				startActivity(intent);
			}	
        });
    }
    
    /**
     * Get a custom formatted name string of the ItemType.
     * @param type  Type of item (ItemType)
     * @return
     */
    private String[] getTypeInfo(ItemType type) {
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
    public void likeTaskAction(View view) {
        
        boolean like = doILikeThisTask();
        MyLocalTaskInformation save = new MyLocalTaskInformation();
        
        //if we already like the task we now want to unlike it
        if(like) {
        
            //remove the value from the list
            save.removeLikedTask(taskId, getApplicationContext());
            
            //set the text on the button to like again
            updateButtonText("like");           
            
        //if we dont like the task add it to the list
        } else {
            save.saveLikedTasks(taskId, getApplicationContext());
            updateButtonText("unlike");
        }                
    }
    
    /**
     * This method simply returns a boolean if "you" like this current task
     * @return  true iff "you" like this task
     */
    public boolean doILikeThisTask() {
        
        //re grab the arraylist incase an item was added
        MyLocalTaskInformation lti = new MyLocalTaskInformation();
        myLikedIds = lti.getLikedTasks(getApplicationContext());
        
        Iterator<String> it = myLikedIds.iterator();
        
        while (it.hasNext()) {
            if(it.next().equals(taskId))
                return true;
        }
        
        return false;      
    }
    
    public void saveTaskLocally(View view){
        
        //need to save or update the task locally
      
    }
    
    /**
     * Async task for loading of a single task either from the web service
     * or if it is a local task we load from the device
     */
    private class loadSingleTask extends AsyncTask<String, String, String> {
    	Dialog load = new Dialog(ViewSingleTask.this);
    	MyLocalTaskInformation lti = new MyLocalTaskInformation();
    	//private Task sTask = null;
    	
		@Override
		protected String doInBackground(String... params) {
		    //we are grabbing from the web service
		    if (!isLocal) {
    			//grabbing the task from the repository
    	        try {
    	        	task = TfTaskRepository.getTaskById(taskId);
    	        } catch (Exception e) {
    	            System.err.println(e.getMessage());
    	            e.printStackTrace();
    	        }
    	     
    	    //we are grabbing a local task
		    } else {
		        //grabbing a local task
		        try {
		            task = TfTaskRepository.getLocalTaskById(taskId, 
		                    getApplicationContext());
		        } catch (Exception e) {
		            System.err.println(e.getMessage());
		            e.printStackTrace();
		        }
		    }
	        
	        //grabbing the likedIDS	        
	        myLikedIds = lti.getLikedTasks(getApplicationContext());
	        	        
			return null;
		}
    	
    	protected void onPreExecute() {
    		
    		load.setContentView(R.layout.save_load_dialog);
    		load.setTitle("Loading task");
    		load.show();
    	}
    	
    	@Override
    	protected void onPostExecute(String result) {
    		super.onPostExecute(result);
    		
    		//set the title and update the listview with the task
    		TextView title = (TextView) findViewById(R.id.showTaskTitle);
            title.setText(task.getTitle());
            updateList(task);
    		
    		load.dismiss();
    	}
    } 
}
