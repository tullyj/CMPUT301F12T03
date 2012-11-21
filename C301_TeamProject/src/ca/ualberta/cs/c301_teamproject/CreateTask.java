package ca.ualberta.cs.c301_teamproject;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_interfaces.Visibility;
import ca.ualberta.cs.c301_repository.TfTask;
import ca.ualberta.cs.c301_repository.TfTaskItem;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

/**
 * This class is responsible for grabbing all of the information needed for creating a task 
 * then passes it off to the repository for creation
 * @author topched
 *
 */

public class CreateTask extends Activity {
	
	private ArrayList<String> currentTaskItems;
	private int editPosition;
	public static String EDIT = "edit";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);
        
        // Need to do this to avoid network on main thread exception
        //http://stackoverflow.com/questions/6343166/android-os-networkonmainthreadexception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        //setting the default radio button to public
        RadioGroup vis = (RadioGroup)findViewById(R.id.visibiltyChoice);
        vis.check(R.id.publicRadioButton);
        
        //only create this list on create, does not get re-created when it returns from adding a item
        currentTaskItems = new ArrayList<String>();
        
        //create listener for list
        ListView items = (ListView)findViewById(R.id.showAttachedItems);
        
        //setting the click listener
        items.setOnItemClickListener(new OnItemClickListener(){
      	
	        //called when the listView is clicked, we will be updating the item selected by going back
        	//to the CreateItem screen with its info to be populated
	      	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
	      				
	      			//global int so we can edit the correct position upon return
	      			editPosition = position;
	      			updateItemList(position);
     							
	      		}
	      	      			
	      	});
        
        //setting the on long click listener - this is used for deleting
        items.setOnItemLongClickListener(new OnItemLongClickListener(){
      	
        	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
	      		
        		editPosition = position;
        		final AlertDialog.Builder eraseItem = new AlertDialog.Builder(CreateTask.this);
        		//eraseItem.setContentView(R.layout.confirm_remove_dialog);
        		eraseItem.setTitle("Erase item?");
        		
        		//setting the confirm buttong
        		eraseItem.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){

        			//we know to remove the task here
					public void onClick(DialogInterface arg0, int arg1) {
						
						currentTaskItems.remove(editPosition);
						updateListViewAndCount();
						
						}
        			    			
        		});//end of confirm button
        		
        		eraseItem.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

        			//cancel was clicked -- do nothiing
					public void onClick(DialogInterface dialog, int which) {
						
						
						}
        				       			
        		});//end of canel button
        		    		
	      		eraseItem.show();
        		return true;
      		}
        	      	
        });//end of on long click listener 
        
          
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
     * This method adds an item to the current tasks ArrayList
     * @param Item	item to be added to the task
     */
    public void addItemToList(String item){
    	
    	currentTaskItems.add(item);
    }
    
    
    /**
     * This method will return an array of strings, which is the item list for the
     * current task
     * @return	A string array containing the item list for the current task
     */
    public String[] getItemList(){
    	
    	return currentTaskItems.toArray(new String[currentTaskItems.size()]);
    }
    
    
    /**
     * When a item is clicked on the list view. Grabs the information for the item and
     * then passes that off to CreateItem for editing. When it returns to this activity
     * onActivityResult will be called with requestCode = 2
     * @param position	Position of list item clicked
     */
    public void updateItemList(int position){
    	
    	String temp[] = getItemList();
    	String value = temp[position];
    	
    	Intent intent = new Intent(this, CreateItem.class);
    	intent.putExtra(EDIT, value);
    	startActivityForResult(intent, 2);
    	   					
    }
    
    
    /**
     * This method updates the list view and the count of the current items
     */
    public void updateListViewAndCount(){
    	
    	//updating the currentTaskItems then getting a array of the list
    	ListView items = (ListView)findViewById(R.id.showAttachedItems);
    	String[] result = getItemList();
    	
    	ArrayList<ItemListElement> item = formatOutputForList(result);
    	ItemListElement[] elm = new ItemListElement[item.size()];
    	item.toArray(elm);
    	
    	//updating the list view
    	ItemListAdapter adapter = new ItemListAdapter(this, R.layout.list_multi, elm);
        items.setAdapter(adapter);
    	
    	//update the item count
    	EditText num = (EditText)findViewById(R.id.showCurrentItemNum);
    	int value = result.length;
    	String val = Integer.toString(value);
    	num.setText(val);
	
    }
    
  /**
   * Method for converting our input to ItemListElement
   * @param in		Array containing all the elements
   * @return		Returns an ArrayList<ItemListElement>
   */
    public ArrayList<ItemListElement> formatOutputForList(String[] in){
    	
    	ArrayList<ItemListElement> item = new ArrayList<ItemListElement>();
    	
    	for(int i = 0;i<in.length;i++){
    		String[]temp = in[i].split("\\|\\|");
    		String top = temp[0] + " " + temp[1] + " files(s)";
    		String bottom = "Description: " + temp[2];
    		  	
    		item.add(new ItemListElement(android.R.drawable.ic_input_get, top, bottom));
    	}
    	
    	return item;
    	
    }
    
    /**
     * This method is called when the "+" button is clicked. When it returns to this activity onActivityResult
     * will be called with requestCode = 1
     * @param view	The current view
     */
    public void createItem(View view) {
    	
        Intent intent = new Intent(this, CreateItem.class);
        intent.putExtra(EDIT, "no");
        startActivityForResult(intent, 1);
    }
     

    
    /**
     * This method is called back when startActivityForResult is finished
     *   this link below was used for this portion: 
     *   http://stackoverflow.com/questions/10407159/android-how-to-manage-start-activity-for-result
     * @param requestCode	Selects either creating item(1) or editing item(2)
     * @param resultCode	Status of the result
     * @param data			The data coming back from CreateItem	
     */
  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	//requestCode = 1 -> createItem made the call for a result
    	if(requestCode  == 1){
    		
    		if(resultCode == RESULT_OK){
    		//grab the results and update the item list then update the listview and number of items
    		String result = data.getStringExtra("result");
    		addItemToList(result);
    		updateListViewAndCount();
    		}
    		
    	}
    	
    	//requestCode = 2 -> we know that we were updating a item
    	//this is where the global int position comes into play
    	if(requestCode == 2){
    		
		
    		if(resultCode == RESULT_OK){
    		String result = data.getStringExtra("result");
    		currentTaskItems.set(editPosition, result);
    		updateListViewAndCount();
    		}
    		   		
    	}  	
    }
    
    
    /**
     * This method gathers all of the information about the current task, then
     * creates the task
     * @return A task object with each item added to it
     */
    public Task gatherTaskInfo(){
    	
    	String title, numItem, visibility;
    	RadioButton visChoice;
    	
    	EditText getTitle = (EditText)findViewById(R.id.titleTextBox);
    	EditText itemNum = (EditText)findViewById(R.id.showCurrentItemNum);
    	RadioGroup getVisibilty = (RadioGroup)findViewById(R.id.visibiltyChoice);
    	
    	
    	//getting the selection from the radio buttons aka the visibilty
    	int choice = getVisibilty.getCheckedRadioButtonId();
    	visChoice = (RadioButton)findViewById(choice);
    	
    	//all the task items to string
    	title = getTitle.getText().toString();
    	numItem = itemNum.getText().toString();
    	visibility = visChoice.getText().toString();
    	String[] items = getItemList();
    	
    	//validate input
    	boolean valid = validateInput(title, visibility, items);
    	
    	//if valid input create a task and return it
    	if(valid){
	    	//creating a task
	    	Task t = new TfTask();
	    	t.setTitle(title);
	    	
	
	    	//setting the visibility enum
	    	if(visibility == "Public"){
	    		
	    		t.setVisibility(Visibility.PUBLIC);
	  		
	    	}else{
	 		
	    		t.setVisibility(Visibility.PRIVATE);  		
	    	}
	    	
	    	
	    	//creating each individual item
	    	addItemsToTask(t);
	    	
	    	// TODO need to add the device id to the task
	    	//put link in git wiki reference page -- need to decide route
	    	
	    	return t;
    	}else{
    		//task not correct
    		return null;
    	}

    }
    
    
    /**
     * This method just validates the input for the task
     * @param title			The task title
     * @param visibility	The visibility
     * @param items			An array of items
     * @return	true if valid, toast then false otherwise
     */
    public boolean validateInput(String title, String visibility, String[] items){
    	
    	boolean valid = true;
    	String error = "";
    	
    	if(title.equals("")){
    		error += "Invalid title\n";
    		valid = false;
    	}else if(visibility.equals("")){
    		error += "Select either Public or Private\n";
    		valid = false;
    	}else if(items.length == 0){
    		error += "Please attach an item to your task\n";
    		valid = false;
    	}
    	
    	//if valid return else toast
    	if(valid){
    		return true;
    	}else{

    		Context context = getApplicationContext();
    		Toast toast = Toast.makeText(context, error, Toast.LENGTH_SHORT);
    		toast.show();
    		return false;
    	}
    }
    
    /**
     * This method adds all the items to the task
     * @param task	The task to add the items to
     */
    public void addItemsToTask(Task task){
    	
    	
    	String[] temp = getItemList();
    	String n, t, description;
    	
    	for(int i = 0;i<temp.length;i++){
    		
    		String[] temp2 = temp[i].split("\\|\\|");
    		n = temp2[0];
    		t = temp2[1];
    		description = temp2[2];
    		ItemType type;
    		
    		Integer num = Integer.valueOf(n);
    		
    		if(t.equals("Photo")){
    			type = ItemType.PHOTO;
    		}else if(t.equals("Audio")){
    			type = ItemType.AUDIO;
    		}else if(t.equals("Video")){
    			type = ItemType.VIDEO;
    		}else{
    			type = ItemType.TEXT;
    		}
    		
    		//create the item
    		TaskItem item = new TfTaskItem(type, num, description);
    		task.addItem(item);
    		
    		
    	}
    	
	
    }
    /**
     * Called when the "Save Task" button is clicked. This method creates the task then saves it
     * NOTE: Need to make this step async, not working yet so not added
     * @param view	The current view
     */
    public void saveTask (View view) {
            	
    	Task task = gatherTaskInfo();
    	//calls to crowd sourcer only if a task is returned
    	if(task != null){
    		new saveTask().execute(task);
		
    	}
    	
    }
    
    /**
     * This is here for now until general async is figured out. The screen doesnt freeze
     *
     */
    private class saveTask extends AsyncTask<Task, String, String>{
    	
    	Dialog save = new Dialog(CreateTask.this);
        

		@Override
		protected String doInBackground(Task... arg0) {
			
			//add the task to the repository
			TfTaskRepository.addTask(arg0[0]);
			return null;
		}
    	
    	protected void onPreExecute(){
    		
    		//show this saving spinner
            save.setContentView(R.layout.save_load_dialog);
            save.setTitle("Saving Task");
    		save.show();
    			
    	}
    	
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
    		
			//close the dialog and the activity
			save.dismiss();
    		finish();
		}

	

    }

}
