package ca.ualberta.cs.c301_teamproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * This class will create a item for a task. Always called with startActivityForResult
 * @author topched
 *
 */
public class CreateItem extends Activity {
	
	public static int itemCount = 3;
	private String[] currentItemTypes;
	private boolean currentType = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    
    	/**
    	 * The onCreate will populate the fields if we are editing a item
    	 */
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.create_item);
    	
    	Intent intent = getIntent();
    	String edit = intent.getStringExtra(CreateTask.EDIT);
    	
    	currentItemTypes = intent.getStringArrayExtra(CreateTask.TYPES);
    	
    	//if edit = no then it is a fresh task not an edit
    	if(edit.equals("no")){
    		
    		//creating the spinner for the item choices
        	Spinner spinner = (Spinner) findViewById(R.id.itemType);
        	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        	R.array.item_choices, android.R.layout.simple_spinner_item);
        	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	spinner.setAdapter(adapter);
    		
    	}else{
    		//if we get into here the return code will be 2 and we need to populate the fields with info
    		String[] temp = intent.getStringArrayExtra("item");
    		
    		//map for item
    		//0 -> number of item
    		//1 -> item type
    		//2 -> description
    		String type = temp[1];
    		String num = temp[0];
    		String desc = temp[2];
    		
    		//we have a type selected already
    		currentType = true;
    		
    		EditText n = (EditText)findViewById(R.id.desiredNum);
    		EditText d = (EditText)findViewById(R.id.description);
    		Spinner t = (Spinner)findViewById(R.id.itemType);
    		
    		//need to figure out how to update the spinner still here
    		n.setText(num);
    		d.setText(desc);
    		
        	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        	R.array.item_choices, android.R.layout.simple_spinner_item);
        	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        	t.setAdapter(adapter);
        	
        	int i = adapter.getPosition(type);
        	t.setSelection(i);
        	t.setClickable(false);
   		
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
     * This method gathers all of the information from the current screen
     * @return	A string that represents the item
     */
    //up to be returned to CreateTask
    public String[] gatherItemInformation(){
    	
    	String[] info = new String[itemCount];
    	Spinner itemType = (Spinner)findViewById(R.id.itemType);
    	EditText num = (EditText)findViewById(R.id.desiredNum);
    	EditText desc = (EditText)findViewById(R.id.description);
    	
    	String type = itemType.getSelectedItem().toString();
    	String numItem = num.getText().toString();
    	String description = desc.getText().toString();
    	
    	boolean valid = validateInput(numItem, description, type);
    	
    	if(valid){
    		
    		//map for info
    		//0 -> number of item
    		//1 -> item type
    		//2 -> description
	    	info[0] = numItem;
	    	info[1] = type;
	    	info[2] = description;
	    	
	    	return info;
    	}else{
    		return null;
    	}
    }
    
    /**
     * This method just validates the input
     * @param num		The number of the item
     * @param desc		The description of the item
     * @param type      The type of the spinner
     * @return		A string representing the item
     */
    public boolean validateInput(String num, String desc, String type){
    	
    	boolean valid = true;
    	int number = 0;
    	String error = "";
    	
    	//checking the current item types
    	//only want to check if we are NOT editing an entry
    	if(!currentType){
        	for(int i = 0;i<currentItemTypes.length;i++){
        	    
        	    if(currentItemTypes[i].equals(type)){
        	        valid = false;
        	        error += "You already have an item of this type.";
        	    }
        	    
        	}
    	}

    	if(num.length()>0){
    		number = Integer.parseInt(num);
    	}else{
    		error += "Please enter a number\n";
    		valid = false;
    	}
    	
    	if(desc.equals("")){
    		error += "Please enter a description\n";
    		valid = false;
    	}else if(number > 50){
    		error += "Item count can't exceed 50\n";
    		valid = false;
    	}
    	
    	//if valid input return true, else toast then return false
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
     * This method returns to CreateTask and passes the information about the item just created
     * or edited
     * @param view	The current view
     */
    public void saveItem(View view){
    	
    	String info[] = gatherItemInformation();
    	if(info != null){
	    	Intent returnIntent = new Intent();
	    	returnIntent.putExtra("result",info);
	    	setResult(RESULT_OK,returnIntent);     
	    	finish();
    	}
    }
    
    

}