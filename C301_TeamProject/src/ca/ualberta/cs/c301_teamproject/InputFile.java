package ca.ualberta.cs.c301_teamproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class InputFile extends Activity {

	static final int DIALOG_PHOTO = 1;
	
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
    	Dialog helpDialog = onCreateDialog(DIALOG_PHOTO);
        helpDialog.show();
        return true;
    }
    
    public Dialog onCreateDialog(int id){     
    	//Context context = getApplicationContext();
    	if(id == DIALOG_PHOTO){
    		String title = "Import Photo";
    		String message = "How would you like to add a photo?"; 

			 /*
			 * Welcome message to user.
			 * learned from: http://developer.android.com/guide/topics/ui/dialogs.html
			 */
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setTitle(title);
			builder.setMessage(message);
			
			// Add "Take a Photo" button
			builder.setPositiveButton(R.string.import_capturepic, new DialogInterface.OnClickListener() {
			       public void onClick(DialogInterface dialog, int id) {
			           // User clicked "Take a Photo" button
			       }
			});
			
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
		}
    	return null;
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
