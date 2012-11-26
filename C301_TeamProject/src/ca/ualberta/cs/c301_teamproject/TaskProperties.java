package ca.ualberta.cs.c301_teamproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is just used to gather the task properties
 * @author topched
 *
 */
public class TaskProperties extends Activity{
	
	public String edit = "";
	public static int propertyCount = 4;
	public String TWITTER = "Twitter";
	public String TEXT = "Text";
	public String TWITTER_HANDLE = "Twitter handle";
	public String TELE_NUM = "Phone #";
	public String EMAIL = "E-mail";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.task_properties);
        
        //grabbing the intent
        Intent intent = getIntent();
        edit = intent.getStringExtra(CreateTask.EDIT);
        
        RadioGroup vis = (RadioGroup)findViewById(R.id.visibilityRadioGroup);
        Spinner spinner = (Spinner) findViewById(R.id.responseType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	        	R.array.response_choices, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	
    	
    	//setting the spinner listener
    	//this is here mainly for show and so she doesnt break
    	spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				//grabbing the type selected
				String type = (String) parent.getItemAtPosition(position);
				
				//show some future plans stuff here
				updateTypeField(type);
				
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		
    	});
        
        //edit = no first time properties was clicked
        if(edit.equals("no")){
        	
	        //setting the default visibility to public        
			vis.check(R.id.public_button);
   	
	    //we have properties already just fill in the fields	
        }else{
        	
        	String[] p = intent.getStringArrayExtra(CreateTask.PROPERTIES);
        	
        	//map for properties
        	//0 -> reply
	    	//1 -> visibility
	    	//2 -> description
	    	//3 -> type
        	String type = p[3];
        	String v = p[1];
        	String desc = p[2];
        	String send = p[0];
        	
        	EditText d = (EditText)findViewById(R.id.description);
        	d.setText(desc);
        	
        	EditText s = (EditText)findViewById(R.id.notificationResponse);
        	s.setText(send);
        	
        	int i = adapter.getPosition(type);
        	spinner.setSelection(i);
        	
        	if(v.equals("Public")){
        		vis.check(R.id.public_button);
        	}else{
        		vis.check(R.id.private_button);
        	}
        	
    	
        }
		
	}
	
	public void updateTypeField(String type){
		
		//here is where future plans show
		TextView showType = (TextView)findViewById(R.id.showNotificationType);
		EditText hint = (EditText)findViewById(R.id.notificationResponse);
		
		String handle = "@TaskForce";
		String email = "NoReply@TaskForce.com";
		String num = "555-382-5968";
		//
		if(type.equals(TWITTER)){
			
			showType.setText(TWITTER_HANDLE);
			hint.setHint(handle);
//			hint.setFocusable(false);
//			hint.setClickable(false);
			
		}else if(type.equals(TEXT)){
			
			showType.setText(TELE_NUM);
			hint.setHint(num);
//			hint.setFocusable(false);
//			hint.setClickable(false);
			
		}else if(type.equals(EMAIL)){
			
			showType.setText(EMAIL);
			hint.setHint(email);
//			hint.setFocusable(true);
//			hint.setClickable(true);
//			hint.requestFocus();
		}
		
		//toast for future plans
		if(type.equals(TWITTER) || type.equals(TEXT)){
			
			
			Context context = getApplicationContext();
			CharSequence text = "Feature not yet available!\nComing Soon!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
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
    
    
    public String[] gatherProperties(){
    	
    	String[] send = new String[propertyCount];
    	
    	Spinner notificationType = (Spinner)findViewById(R.id.responseType);
    	EditText sendTo = (EditText)findViewById(R.id.notificationResponse);
    	EditText desc = (EditText)findViewById(R.id.description);
    	
    	RadioGroup getVisibilty = (RadioGroup)findViewById(R.id.visibilityRadioGroup);
    	//getting the selection from the radio buttons aka the visibilty
    	int choice = getVisibilty.getCheckedRadioButtonId();
    	RadioButton visChoice = (RadioButton)findViewById(choice);
    	
    	
    	//grabbing the properties
    	String reply = sendTo.getText().toString();
    	String visibility = visChoice.getText().toString();
    	String description = desc.getText().toString();
    	String type = notificationType.getSelectedItem().toString();
    	
    	//putting the properties into the return array
    	//0 -> where to send the notification
    	//1 -> visibility
    	//2 -> description
    	//3 -> type
    	send[0] = reply;
    	send[1] = visibility;
    	send[2] = description;
    	send[3] = type;
    	
    	
    	return send;
    }
	
	
    public void returnTaskProperties(View view){
    	
    	Intent returnIntent = new Intent();
    	
    	
    	//grabbing the properties
    	String[] properties = gatherProperties();
    	
    	boolean validInput = validateInput(properties);
    
    	if(validInput){
	    	returnIntent.putExtra("result",properties);
	    	
	    	setResult(RESULT_OK,returnIntent);
	    	finish();
    	}
    	
    	
    }
    
    public boolean validateInput(String[] properties){
    	
    	boolean valid = true;
    	String error = "";
    	
    	if(properties[1].equals("")){
    		error += "Please select a visibility";
    		valid = false;
    	}else if(properties[2].equals("")){
    		error += "Please enter a description";
    		valid = false;
    	}else if(properties[3].equals("")){
    		error += "Please select a type";
    		valid = false;
    	}else if(properties[3].equals(TWITTER)){
    		error += "Twitter response not yet available.\nPlease make another selection.";
    		valid = false;
    	}else if(properties[3].equals(TEXT)){
    		error += "Text response not yet available.\nPlease make another selection";
    		valid = false;
    	}else if(properties[0].equals("")){
    		error += "Please enter an email address";
    		valid = false;
    	} 
    	
    	
    	if(valid){
    		return true;
    	}else{
    		Context context = getApplicationContext();
    		Toast toast = Toast.makeText(context, error, Toast.LENGTH_SHORT);
    		toast.show();
    		return false;
    	}
    }
	
	

}