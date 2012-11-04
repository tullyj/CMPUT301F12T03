package ca.ualberta.cs.c301_teamproject;

import java.io.File;
import java.util.ArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Import or capture files/items to be added to a task.
 * @author tullyj
 */
public class InputFile extends Activity {

	static final int DIALOG_AUDIO = 0;
	static final int DIALOG_PHOTO = 1;
	static final int DIALOG_ABOUT = 2;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public ArrayList<String> listValues = new ArrayList<String>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_file);
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * When "+" is clicked create a dialog for obtaining the desired item.
     * @param v		Current view.
     */
    public void importFile(View v){    	
    	Dialog importDialog = onCreateDialog(DIALOG_PHOTO);
//    	Dialog importDialog = onCreateDialog(DIALOG_AUDIO);
        importDialog.show();
    }
    
    @Override
    /**
     * When the menu button item "About" is selected display about dialog.
     * @param item	item clicked.
     */
    public boolean onOptionsItemSelected(MenuItem item){
    	Dialog helpDialog = onCreateDialog(DIALOG_ABOUT);
        helpDialog.show();
        return true;
    }
    
    /**
     * Creates a dialog for this activity (InputFile).
     * @param id		Selects what dialog to create/return.
     * @return 			Dialog to be displayed.
     */
    public Dialog onCreateDialog(int id){     
    	//Context context = getApplicationContext();
    	if(id == DIALOG_PHOTO || id == DIALOG_AUDIO){
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			if(id == DIALOG_PHOTO){
	    		builder.setTitle("Import Photo");
				builder.setMessage("How would you like to add a photo?");				
				// Add "Take a Photo" button
				builder.setPositiveButton(R.string.import_capturepic, new DialogInterface.OnClickListener() {
				    @SuppressLint("SdCardPath")
					public void onClick(DialogInterface dialog, int id) {
				           // User clicked "Take a Photo" button
				    	   // create Intent to take a picture and return control to the calling application
				    	   Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				    	   //Uri mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()));
				    	   Uri mUri = Uri.fromFile(new File("/sdcard/temp"));
				    	   photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
				    	   // start the image capture Intent
				    	   startActivityForResult(photoIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				       }
				});
			}else{
				builder.setTitle("Import Audio");
				builder.setMessage("How would you like to add audio?");				
				// Add "Record Audio" button
//				builder.setPositiveButton(R.string.import_capturepic, new DialogInterface.OnClickListener() {
//				       public void onClick(DialogInterface dialog, int id) {
//				           // User clicked "Take a Photo" button
//				    	   // create Intent to take a picture and return control to the calling application
//				    	   Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//				    	   // start the image capture Intent
//				    	   startActivityForResult(photoIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//				       }
//				});     
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
			PromptDialog mDialog = new PromptDialog();
			return mDialog.aboutPrompt(this);
		}
    	return null;
    }
    
	@Override
	/**
	 * Receives result from camera capture intent.
	 * @param requestCode		Describes type of intent.
	 * @param resultCode		Describes status of intent.
	 * @param data				The intent.
	 */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	try{
            			listValues.add(data.getData().getPath());
            	}catch(Exception e){
            		try{
            			/* from: http://kevinpotgieter.wordpress.com/2011/03/30/null-intent-passed-back-on-samsung-galaxy-tab/ */
            			String[] projection = {
            					MediaStore.Images.Thumbnails._ID,
            					 MediaStore.Images.Thumbnails.IMAGE_ID,
            					 MediaStore.Images.Thumbnails.KIND,
            					 MediaStore.Images.Thumbnails.DATA};
            			
            			String sort = MediaStore.Images.Thumbnails._ID + " DESC";
            			String selection = MediaStore.Images.Thumbnails.KIND + "="  
            				+ MediaStore.Images.Thumbnails.MINI_KIND;
            			
            			long iID = 0l;

            			@SuppressWarnings("deprecation")
						Cursor myCursor = this.managedQuery( 
            				MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, 
            					projection, selection, null, sort);
            			myCursor.moveToFirst();
            			iID = myCursor.getLong(myCursor.getColumnIndexOrThrow(
            					MediaStore.Images.Thumbnails.IMAGE_ID));
            			myCursor.close();
            			
            			Uri uriImage = Uri.withAppendedPath(
            					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(iID));
            			listValues.add(uriImage.getPath());
            			myCursor.close();
            			
            		} catch(Exception ex){
            			// Image capture failed, advise user
                    	Toast.makeText(this, "Unable to find image file", Toast.LENGTH_LONG).show();
            		}
            	}
            	updateList();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            	Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
    
	/**
	 * Refreshes, or recreates, the listview on the Input File screen.
	 */
    public void updateList(){
    	String[] values = new String[listValues.size()];
        listValues.toArray(values);
        
        ListView listView = (ListView) this.findViewById(R.id.importList);		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        	android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);
    }
}
