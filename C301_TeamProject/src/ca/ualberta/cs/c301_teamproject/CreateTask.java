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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateTask extends Activity {
	
	private ArrayList<String> currentTaskItems;

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
    public void updateItemList(String item){
    	
    	//updating the currentTaskItems then getting a array of the list
    	ListView items = (ListView)findViewById(R.id.showAttachedItems);
    	addItemToList(item);
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
        startActivityForResult(intent, 1);
    }
    
    
    //this method is called back when startActivityForResult is finished
    //this link below was used for this portion: 
    //http://stackoverflow.com/questions/10407159/android-how-to-manage-start-activity-for-result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	//requestCode = 1 -> createItem made the call for a result
    	//resultCode = RESULT_OK always because we never return bad results from CreateItem
    	if(requestCode  == 1){
    		
    		//grab the results and update the item list
    		String result = data.getStringExtra("result");
    		updateItemList(result);
    		
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
