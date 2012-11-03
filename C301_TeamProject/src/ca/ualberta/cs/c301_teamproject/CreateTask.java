package ca.ualberta.cs.c301_teamproject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
    
    
    //just adding a item to the task list
    public void addItemToList(String item){
    	
    	currentTaskItems.add(item);
    }
    
    
    //this method will return all the items currently attached to the task
    public String[] getItemList(){
    	
    	return currentTaskItems.toArray(new String[currentTaskItems.size()]);
    }
    
    
    //updateItemList will add the new item to the list, get an array of the new list, update the list
    //view and also update the item count
    public void updateItemList(int position){
    	
    	String temp[] = getItemList();
    	String value = temp[position];
    	
    	Intent intent = new Intent(this, CreateItem.class);
    	intent.putExtra(EDIT, value);
    	startActivityForResult(intent, 2);
    	
    	
    					
    }
    
    
    //this method updates the list view and the count of the current items
    public void updateListViewAndCount(){
    	
    	//updating the currentTaskItems then getting a array of the list
    	ListView items = (ListView)findViewById(R.id.showAttachedItems);
    	String[] result = getItemList();
    	
    	//updating the list view
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, result);
    	items.setAdapter(adapter);
    	
    	//update the item count
    	EditText num = (EditText)findViewById(R.id.showCurrentItemNum);
    	int value = result.length;
    	String val = Integer.toString(value);
    	num.setText(val);
    	
    	
    }
    
    

    //this method starts an activity for a result. onActivityResult is called when it returns
    public void createItem(View view) {
    	
        Intent intent = new Intent(this, CreateItem.class);
        intent.putExtra(EDIT, "no");
        startActivityForResult(intent, 1);
    }
    

    
    //this method is called back when startActivityForResult is finished
    //this link below was used for this portion: 
    //http://stackoverflow.com/questions/10407159/android-how-to-manage-start-activity-for-result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	//requestCode = 1 -> createItem made the call for a result
    	//resultCode = RESULT_OK always because we never return bad results from CreateItem
    	if(requestCode  == 1){
    		
    		//grab the results and update the item list then update the listview and number of items
    		String result = data.getStringExtra("result");
    		addItemToList(result);
    		updateListViewAndCount();
    		
    	}
    	
    	//requestCode = 2 -> we know that we were updating a item
    	//this is where the global int position comes into play
    	if(requestCode == 2){
    		
    		//test to see if i made it
    		Context context = getApplicationContext();
        	String error = "i am code 2";
    		Toast toast = Toast.makeText(context, error, Toast.LENGTH_SHORT);
    		toast.show();
    		
    		//at this point we have the position we need to update and the data that
    		//needs to be updated. Just update the arrayList instead of adding to it
    		//this shouldnt be to hard
    		
    		
    		
    	}
    	
    	
    }
    
    
    //this method gathers all of the information about the current task
    //need to still grab all the info from the list (list not done yet)
    public void gatherTaskInfo(){
    	
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
    	
    	
    	//to get all the items call un-comment line below
    	//String[] items = getItemList();
    	//not sure how we want to deal with the array thats returned yet
    	
    	
    	//just toasting the task right now to make sure the info is there
    	Context context = getApplicationContext();
    	String error = title + "\n" + numItem + "\n" + visibility;
		Toast toast = Toast.makeText(context, error, Toast.LENGTH_SHORT);
		toast.show();
    	
  
    }
    
    //commented out your lines colin just for testing
    public void saveTask (View view) {
        
    	//CrowdClient c = new CrowdClient();
        //c.testServiceMethods();
    	
    	gatherTaskInfo();
    }

}
