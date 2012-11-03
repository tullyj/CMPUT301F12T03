package ca.ualberta.cs.c301_teamproject;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class InputFile extends Activity {

	static final int DIALOG_AUDIO = 0;
	static final int DIALOG_PHOTO = 1;
	static final int DIALOG_ABOUT = 2;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_file);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void importFile(View v){
    	// DEBUG -- this is to test button 
    	//print("about to import a file", 1, getApplicationContext());
    	
    	Dialog importDialog = onCreateDialog(DIALOG_PHOTO);
        importDialog.show();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	Dialog helpDialog = onCreateDialog(DIALOG_ABOUT);
        helpDialog.show();
        return true;
    }
    
    public Dialog onCreateDialog(int id){     
    	//Context context = getApplicationContext();
    	if(id == DIALOG_PHOTO || id == DIALOG_AUDIO){
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			if(id == DIALOG_PHOTO){
	    		builder.setTitle("Import Photo");
				builder.setMessage("How would you like to add a photo?");				
				// Add "Take a Photo" button
				builder.setPositiveButton(R.string.import_capturepic, new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int id) {
				           // User clicked "Take a Photo" button
				    	   // create Intent to take a picture and return control to the calling application
				    	   Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				    	   // start the image capture Intent
				    	   startActivityForResult(photoIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				       }
				});
			}else{
				builder.setTitle("Import Audio");
				builder.setMessage("How would you like to add audio?");				
				// Add "Take a Photo" button
				builder.setPositiveButton(R.string.import_capturepic, new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int id) {
				           // User clicked "Take a Photo" button
				    	   // create Intent to take a picture and return control to the calling application
				    	   Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				    	   // start the image capture Intent
				    	   startActivityForResult(photoIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				       }
				});     
			}
			
			// Add "Import from File" button
			builder.setNeutralButton(R.string.import_fromfile, new DialogInterface.OnClickListener() {
			       public void onClick(DialogInterface dialog, int id) {
			           // User clicked "Import from File" button
			       }
			});
			
			// Add "Cancel" button
			builder.setNegativeButton(R.string.import_cancel, new DialogInterface.OnClickListener() {
			       public void onClick(DialogInterface dialog, int id) {
			           // User clicked Cancel button
			       }
			});
			return builder.create();
		}else if(id == DIALOG_ABOUT){
//			String title = "About Task Force";
//    		String message = "Created by "; 
			//aboutDialog mDialog = new aboutDialog();
			//return mDialog.aboutPrompt(this);
		}
    	return null;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                         data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            	Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    
    
	/*
	 * Used to give "Toast Notifications".
	 * learned from: http://developer.android.com/guide/topics/ui/notifiers/toasts.html#Basics
	 */
    public void print(CharSequence text, int durSet, Context context){
    	int duration;
		if(durSet != 0)
			duration = Toast.LENGTH_LONG;
		else
			duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
	}
}
