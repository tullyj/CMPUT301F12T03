package ca.ualberta.cs.c301_teamproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;

/**
 * Custom dialog screens for Task Force.
 * @author tullyj
 */
public class PromptDialog {
	
	/**
	 * Displays dialog about the program. 
	 * @param context
	 * @return	Dialog
	 */
    public Dialog aboutPrompt(final Context context){
    	AlertDialog.Builder builder = new AlertDialog.Builder(context);

    	builder.setTitle(R.string.about_title);
    	builder.setIcon(R.drawable.taskforcebar);
    	builder.setMessage(R.string.about_task_force);
    	builder.setPositiveButton(android.R.string.ok,
    		new DialogInterface.OnClickListener(){
    			public void onClick(DialogInterface dialog, int which){
					// on click function...
					return;
    			}
    		});
    	return builder.create();
    }
    
    /**
     * Default prompt to be used when giving options to the user.
     * @param context		Context in the application.
     * @param numButtons	Number of buttons to the prompt.
     * @param title			Title of the prompt.
     * @param message		Message text of the prompt.
     * @param buttonStrings	Starting at 0 being the button on the left after the
     * 						cancel button.
     * @return				Dialog, .show() this dialog to display.
     */
    public Dialog defPrompt(final Context context, int numButtons, String title, 
    	String message, String[] buttonStrings){
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		builder.setTitle(title);
		builder.setMessage(message);
		
		// Add "Cancel" button
		builder.setNegativeButton(R.string.import_cancel, new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		           // User clicked Cancel button
		       }
		});
		
		for(int i = 0; i < buttonStrings.length - 1; i++){
			// Add button
			builder.setNeutralButton(buttonStrings[i], new DialogInterface.OnClickListener() {
			       public void onClick(DialogInterface dialog, int id) {
			           // User clicked "Import from File" button
			       }
			});
		}
		
		// Add positive button
		builder.setPositiveButton(buttonStrings[buttonStrings.length - 1], 
				new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		           // User clicked
		       }
		});		
		return builder.create();
    }
}
