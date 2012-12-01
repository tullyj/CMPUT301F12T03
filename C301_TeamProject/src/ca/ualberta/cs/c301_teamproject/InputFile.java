package ca.ualberta.cs.c301_teamproject;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_repository.TfTaskRepository;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

	static final int DIALOG_AUDIO = 1;
	static final int DIALOG_PHOTO = 2;
	static final int DIALOG_VIDEO = 5;
	static final int DIALOG_ABOUT = 3;
	static final int DIALOG_FILE = 4;
	static int itemType;
	//private Task task;
	private TaskItem item;
	//static boolean fromFile = false;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private static final int CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE = 300;
	private static final int FILE_ACTIVITY_REQUEST_CODE = 400;
	public ArrayList<File> files = new ArrayList<File>();
	public String filePath = null;
	//public ArrayList<File> newFiles = new ArrayList<File>();
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_file);
        
        String[] inArgs = getIntent().getStringArrayExtra("ItemArgs");

        //String taskId = inArgs[1];
        //if(Integer.parseInt(inArgs[0]) > 0)
        // Get item type in regards to final int representations.
        itemType = Integer.parseInt(inArgs[0]);
         
        item = ViewSingleTask.task.getItemByType(inArgs[1]);
//        if(getIntent().getIntExtra("FromFile", 0) == 4)
//        	fromFile = true;
//        else{
        importFile((View) findViewById(R.layout.input_file));
//        	fromFile = false;
//        }
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
    	Dialog importDialog;
    	if(itemType == DIALOG_PHOTO){
    		importDialog = onCreateDialog(DIALOG_PHOTO);
    	}else if(itemType == DIALOG_VIDEO){
    		importDialog = onCreateDialog(DIALOG_VIDEO);
    	}else if(itemType == DIALOG_AUDIO){
    		importDialog = onCreateDialog(DIALOG_AUDIO);
    	}else{
    		return;
    	}
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
     * Saves the files to an item. Returns to the view single task activity.
     * @param v
     */
    public void saveClick(View v){
    	if(files.size() > 0){
	    	Toast.makeText(getApplicationContext(), 
	    		"Adding Files to Item of Task\n" +
	    		"Then returning to Task Items Screen", Toast.LENGTH_LONG).show();
	    	ViewSingleTask.task.setModified(true);
	    	Intent intent = getIntent();
	    	setResult(RESULT_OK, intent);
	    	try {
                item.addFiles(files);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), 
                        "Unable to update files of item." , 
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
	    	finish();
    	}else{
    		Toast.makeText(getApplicationContext(), 
    	    		"Please add a file before saving." , Toast.LENGTH_LONG).show();
    	}
    }
    
    /**
     * Creates a dialog for this activity (InputFile).
     * @param id		Selects what dialog to create/return.
     * @return 			Dialog to be displayed.
     */
    public Dialog onCreateDialog(int id){     
    	//Context context = getApplicationContext();
    	if (id <= DIALOG_VIDEO && id != DIALOG_ABOUT){
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			if(id == DIALOG_PHOTO){
	    		builder.setTitle("Import Photo");
				builder.setMessage("How would you like to add a photo?");				
				// Add "Take a Photo" button
				builder.setPositiveButton(R.string.import_capturepic, 
				        new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int id) {
				           // User clicked "Take a Photo" button
				    	   // create Intent to take a picture and return control to the calling application
				    	   Intent photoIntent = 
				    	           new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				    	   String directory = 
				    	           Environment.getExternalStorageDirectory().getAbsolutePath();
//				    	   File folder = new File(directory);
//				    	   folder.mkdirs();
//				    	   Uri mUri = Uri.fromFile(new File(directory));
//				    	   Uri mUri = Uri.fromFile(new File(
//				    	       Environment.getExternalStorageDirectory().getAbsolutePath()));
				    	   filePath = "/sdcard/temp/";
				    	   Uri mUri = Uri.fromFile(new File(filePath));
				    	   //Uri mUri = getOutputMediaFileUri(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
				    	   //File image
//				    	   photoIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				    	   photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, (Uri) mUri);
//				    	   photoIntent.putExtra(MediaStore.Images.Media.TITLE, "TFImage");
				    	   // start the image capture Intent
				    	   startActivityForResult(photoIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				       }
				});
			}else if(id == DIALOG_VIDEO){
	    		builder.setTitle("Import Video");
				builder.setMessage("How would you like to add a video?");				
				// Add "Take a Photo" button
				builder.setPositiveButton(R.string.import_capturevideo, new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int id) {
				           // User clicked "Take a Photo" button
				    	   // create Intent to take a picture and return control to the calling application
				    	   Intent photoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				    	   
				    	   filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
				    	           + "/temp/";
//				    	   filePath = "sdcard/temp/";
				    	   Uri mUri = Uri.fromFile(new File(filePath));
				    	   //Uri mUri = Uri.fromFile(new File("/sdcard/temp"));
				    	   photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
				    	   // start the image capture Intent
				    	   startActivityForResult(photoIntent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
				       }
				});
			}else if(id == DIALOG_AUDIO){
				builder.setTitle("Import Audio");
				builder.setMessage("How would you like to add audio?");				
				// Add "Record Audio" button
				builder.setPositiveButton(R.string.import_captureaudio, new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int id) {
				           // User clicked "Record Audio" button
				    	   //Intent intent = new Intent(getApplicationContext(), InputAudio.class);
				    	   Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
				    	   //Uri mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
				    	   filePath = "/sdcard/temp/";
				    	   Uri mUri = Uri.fromFile(new File(filePath));
				    	   
				    	   intent.putExtra("AudioExtra", mUri);
				    	   // start the AUDIO capture Intent
				    	   startActivityForResult(intent, CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE);
				       }
				});     
			}
			
			// Add "Import from File" button
			builder.setNeutralButton(R.string.import_fromfile, new DialogInterface.OnClickListener() {
			       public void onClick(DialogInterface dialog, int id) {
			    	   // Start the FileBrowser activity
			    	   Intent intent = new Intent(getApplicationContext(), FileBrowser.class);
			    	   intent.putExtra("FileType", itemType);
			           // need to send parameters to filter into all tasks
			           startActivityForResult(intent, FILE_ACTIVITY_REQUEST_CODE);
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
	    //super.onActivityResult(requestCode, resultCode, data);
	    
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ||
        	requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            	try{
//            	    String string = data.getData().toString();
//            	    File file = new File(string);
//            	    FileInputStream fstream = new FileInputStream(file);
//            	    DataInputStream dataIs = new DataInputStream(fstream);
//            	    if (data == null) 
//                        throw new Exception();
            		files.add(new File(filePath));
            	    //if (data == null) 
            	    //    throw new Exception();
            		//Object obj = data.getExtras().get("data");
            		//String str = data.getExtras().get(
            		//        MediaStore.Images.Media.TITLE).toString();
            		//newFiles.add(new File(str));
            	}catch(Exception e){
            		try{
            			if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
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
	            				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
	            					String.valueOf(iID));
	            			files.add(new File(uriImage.getPath()));
	            			//newFiles.add(new File(uriImage.getPath()));
	            			e.printStackTrace();
            			} else 
            			    throw new Exception();
            			
            		} catch(Exception ex){
            			// prompt user that file was not found
                    	Toast.makeText(this, "Unable to find file", Toast.LENGTH_LONG).show();
                    	e.printStackTrace();
            		}
            	}
            	updateList();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            	Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE) {
        	if (resultCode == RESULT_OK) {
        		try{
        			files.add(new File(filePath));
        			//newFiles.add(new File(data.getData().getPath()));
        			updateList();
        		} catch(Exception ex){
        			// capture failed, advise user
                	Toast.makeText(this, "Unable to find Audio file", Toast.LENGTH_LONG).show();
        		}	
        	}
        	
        } else if (requestCode == FILE_ACTIVITY_REQUEST_CODE) {
        	if (resultCode == RESULT_OK){
        		files.add(new File(data.getStringExtra("FromFile")));
        		//newFiles.add(new File(data.getStringExtra("FromFile")));
        		updateList();
        	}
        }
    }
    
	/**
	 * Refreshes, or recreates, the listview on the Input File screen.
	 */
    public void updateList(){        
        String[] filenames = new String[files.size()];
        for(int i = 0; i < files.size(); i++){
        	filenames[i] = files.get(i).getName();
        }
        
        ListView listView = (ListView) this.findViewById(R.id.importList);		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        	android.R.layout.simple_list_item_1, filenames);
        listView.setAdapter(adapter);
    }
}
