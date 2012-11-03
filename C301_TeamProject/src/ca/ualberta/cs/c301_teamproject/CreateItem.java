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

public class CreateItem extends Activity {
	


    @Override
    public void onCreate(Bundle savedInstanceState) {
    
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.create_item);
    	
    	Intent intent = getIntent();
    	String edit = intent.getStringExtra(CreateTask.EDIT);
    	
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
    		String temp[];
    		temp = edit.split("\\|\\|");
    		
    		String type = temp[1];
    		String num = temp[0];
    		String desc = temp[2];
    		
    		EditText n = (EditText)findViewById(R.id.desiredNum);
    		EditText d = (EditText)findViewById(R.id.description);
    		Spinner t = (Spinner)findViewById(R.id.itemType);
    		
    		//need to figure out how to update the spinner still here
    		n.setText(num);
    		d.setText(desc);
    		
    		
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
    
    
    //this method gathers all of the information from the current screen and packages it
    //up to be returned to CreateTask
    public String gatherItemInformation(){
    	
    	String info;
    	Spinner itemType = (Spinner)findViewById(R.id.itemType);
    	EditText num = (EditText)findViewById(R.id.desiredNum);
    	EditText desc = (EditText)findViewById(R.id.description);
    	
    	String type = itemType.getSelectedItem().toString();
    	String numItem = num.getText().toString();
    	String description = desc.getText().toString();
    	
    	info = numItem + "||" + type + "||" + description; 
    	
    	return info;
    }
    
    
    //this method returns to CreateTask and passes the information about the item just created
    public void saveItem(View view){
    	
    	Intent returnIntent = new Intent();
    	returnIntent.putExtra("result",gatherItemInformation());
    	setResult(RESULT_OK,returnIntent);     
    	finish();
    }
    
    

}
