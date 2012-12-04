/**
 * Task Force Application
 * See github for license and other information: 
 * github.com/tullyj/CMPUT301F12T03/
 * 
 * Task Force is created by: 
 * Colin Hunt, Edwin Chung, 
 * Kris Kushniruk, and Tully Johnson.
 */
package ca.ualberta.cs.c301_teamproject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_interfaces.Visibility;
import ca.ualberta.cs.c301_repository.TfTaskItem;
import ca.ualberta.cs.c301_utils.Utility;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * Import or capture files/items to be added to a task.
 * @author tullyj
 * @author cedwin
 */
public class InputFile extends Activity {

    private static final int DIALOG_AUDIO = 1;
	private static final int DIALOG_PHOTO = 2;
	private static final int DIALOG_VIDEO = 5;
	private static final long MAX_TASK_BYTES = 700000;
	private static int itemType;
	public static int inFileCount = 0;
	private TaskItem item;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
	private static final int CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE = 300;
	private static final int FILE_ACTIVITY_REQUEST_CODE = 400;
	public ArrayList<File> files = new ArrayList<File>();
	public String filePath = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_file);
        
        String[] inArgs = getIntent().getStringArrayExtra("ItemArgs");
        
        // Get item type in regards to final int representations.
        itemType = Integer.parseInt(inArgs[0]);
        item = ViewSingleTask.task.getItemByType(inArgs[1]);
        importFile((View) findViewById(R.layout.input_file));
        
        // Initiate the counter for the file naming. ie: "Photo1"
        inFileCount = 0;

        Toast.makeText(InputFile.this, "Long click to delete newly added files.", 
                Toast.LENGTH_LONG).show();
        
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
    public void importFile(View v) {    	
    	Dialog importDialog;
    	if (itemType == DIALOG_PHOTO) {
    		importDialog = onCreateDialog(DIALOG_PHOTO);
    	} else if (itemType == DIALOG_VIDEO) {
    		importDialog = onCreateDialog(DIALOG_VIDEO);
    	} else if (itemType == DIALOG_AUDIO) {
    		importDialog = onCreateDialog(DIALOG_AUDIO);
    	} else {
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
    	Dialog helpDialog = onCreateDialog(MainPage.DIALOG_ABOUT);
        helpDialog.show();
        return true;
    }
    
    /**
     * Saves the files to an item. Returns to the view single task activity.
     * @param v
     */
    public void saveClick(View v){
        
        //check if the new inserted file(s) takes up too much space (bytes
        long insertFilesSize = 0;
        for (File file : files)
            insertFilesSize += file.length();
        long newTotalSize = getTotalTaskSize() + insertFilesSize;
        // Don't push to web service if adding files is too big for task.
        if (newTotalSize > MAX_TASK_BYTES && ViewSingleTask.task.getVisibility()
                == Visibility.PUBLIC) {
            String message = "Sorry, insufficient space \n" +
                    "The file(s) are: " + insertFilesSize + " bytes. \n" +
                    "You only have " + (MAX_TASK_BYTES - getTotalTaskSize()) +
                    " bytes left. \n" + "Please Upgrade Your Account " +
                    "(coming soon).";
            
            //create a dialog to display the message
            new AlertDialog.Builder(this).setIcon(R.drawable.taskforcebar)
            .setMessage(message).setPositiveButton("OK", 
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, 
                    int which) {
                    //close the dialog, (do nothing)
                }
            }).show();
                    
            updateList();
        // If at least 1 file to upload, update files of task item.
        } else if (files.size() > 0) {
	    	ViewSingleTask.task.setModified(true);
	    	Intent intent = getIntent();
	    	setResult(RESULT_OK, intent);
	    	// Add files to item of task.
	    	try {
                item.addFiles(files);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), 
                        "Unable to update files of item." , 
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
	    	finish();
	    // No files to add, prompt user to add files.
    	} else {
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
        filePath = null;
    	if ((id <= DIALOG_VIDEO) && (id != MainPage.DIALOG_ABOUT)) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
			if (id == DIALOG_PHOTO) {
	    		builder.setTitle("Import Photo");
				builder.setMessage("How would you like to add a photo?");				
				// Add "Take a Photo" button
				builder.setPositiveButton(R.string.import_capturepic, 
				        new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int id) {
				           // User clicked "Take a Photo" button
				    	   // create Intent to take a picture and return control
				           // to the calling application
				    	   Intent photoIntent = 
				    	           new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				    	   // increment counter for naming files.
				    	   inFileCount++;
				    	   filePath = Environment.getExternalStorageDirectory()
				    	           .getAbsolutePath() + "/Photo" + inFileCount;
				    	   Uri mUri = Uri.fromFile(new File(filePath));
				    	   // Give the location of file to intent
				    	   photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, 
				    	           (Uri) mUri);
				    	   // Set the size limit for photo intent.
				    	   photoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 
				    	           MAX_TASK_BYTES);
				    	   // start the image capture Intent
				    	   startActivityForResult(photoIntent, 
				    	           CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				       }
				});
			} else if (id == DIALOG_VIDEO) {
	    		builder.setTitle("Import Video");
				builder.setMessage("How would you like to add a video?");				
				// Add "Take a Photo" button
				builder.setPositiveButton(R.string.import_capturevideo, 
				        new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
			           // User clicked "Take a Video" button
			    	   // create Intent to take a picture and 
				       // return control to the calling application
			    	   Intent videoIntent = new Intent(
			    	           MediaStore.ACTION_VIDEO_CAPTURE);
			    	   // increment counter for naming files.
			    	   inFileCount++;
			    	   filePath = Environment.getExternalStorageDirectory().
			    	           getAbsolutePath() + "/Video" + inFileCount;
			    	   Uri mUri = Uri.fromFile(new File(filePath));
			    	   // Give the location of file to intent
			    	   videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
			    	   // Set the quality to MMS for video intent.
			    	   videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
			    	   // start the image capture Intent
			    	   startActivityForResult(videoIntent, 
			    	           CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
			       }
				});
			} else if (id == DIALOG_AUDIO) {
				builder.setTitle("Import Audio");
				builder.setMessage("How would you like to add audio?");				
				// Add "Record Audio" button
				builder.setPositiveButton(R.string.import_captureaudio, 
				        new DialogInterface.OnClickListener() {
			       public void onClick(DialogInterface dialog, int id) {
			           // User clicked "Record Audio" button
			    	   Intent intent = new Intent(getApplicationContext(), 
			    	           InputAudio.class);
			    	   // increment counter for naming files.
			    	   inFileCount++;
			    	   filePath = Environment.getExternalStorageDirectory().
			    	           getAbsolutePath();
			           filePath += "/Audio" + inFileCount + ".3ga";
			    	   // start the AUDIO capture Intent
			    	   startActivityForResult(intent, 
			    	           CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE);
			       }
				});     
			}
			// Add "Import from File" button
			builder.setNeutralButton(R.string.import_fromfile, 
			        new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		    	   // Start the FileBrowser activity
		    	   Intent intent = new Intent(getApplicationContext(), 
		    	           FileBrowser.class);
		    	   intent.putExtra("FileType", itemType);
		           // need to send parameters to filter into all tasks
		           startActivityForResult(intent, FILE_ACTIVITY_REQUEST_CODE);
		       }
			});
			// Add "Cancel" button
			builder.setNegativeButton(R.string.import_cancel, 
			        new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int id) {
		           // User clicked Cancel button
		       }
			});
			return builder.create();
		} else if (id == MainPage.DIALOG_ABOUT) {
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
	    if (requestCode == FILE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK){
                files.add(new File(data.getStringExtra("FromFile")));
                updateList();
            }
        // Only one procedure from requestCodes is needed when receiving files.
	    } else {
            if (resultCode == RESULT_OK) {
                try {
                    files.add(new File(filePath));
                } catch (Exception e) {
                    onConfigurationChanged(null);
                    Toast.makeText(this, "Adding file unsuccessful, " +
                            "please try again.", 
                            Toast.LENGTH_LONG).show();
                }
            	updateList();
            } else {
                // Capture of file failed, advise user
            	Toast.makeText(this, "Unable to capture file, please try again.", 
            	        Toast.LENGTH_LONG).show();
            }
        }
    }
    
	/**
	 * Refreshes, or recreates, the listview on the Input File screen.
	 */
    public void updateList(){        
        String[] filenames = new String[files.size()];
        for (int i = 0; i < files.size(); i++)
        	filenames[i] = files.get(i).getName();
        
        ListView listView = (ListView) this.findViewById(R.id.importList);		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        	android.R.layout.simple_list_item_1, filenames);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> parent, View view, 
                    final int position, long id) {
                final AlertDialog.Builder eraseItem = 
                        new AlertDialog.Builder(InputFile.this);
                eraseItem.setTitle("Erase item?");
                
                //setting the confirm button and click listener
                eraseItem.setPositiveButton("Confirm", 
                        new DialogInterface.OnClickListener() {

                    //we know to remove the task here
                    public void onClick(DialogInterface arg0, int arg1) {
                        
                        //remove the task then update the UI
                        files.remove(position);
                        updateList();                       
                        }                               
                });//end of confirm button
                
                //setting the cancel button and click listener
                eraseItem.setNegativeButton("Cancel", 
                        new DialogInterface.OnClickListener() {

                    //cancel was clicked -- do nothiing
                    public void onClick(DialogInterface dialog, int which) {
                    }                                       
                });//end of cancel button
                
                //show the alert dialog
                eraseItem.show();
                return true;
            }               
        });
    }
    
    /**
     * Gets the total number of bytes due to all files within the task 
     * (includes all file types)
     * @return      the total number of bytes a task is taking 
     *              due to files in it
     */
    private long getTotalTaskSize() {
        //retrieve all items
        List<TfTaskItem> listTask = ViewSingleTask.task.getAllItems();
        long totalSize = 0;
        for (TaskItem item : listTask) {
            List<File> listFile = item.getAllFiles();
            for (File file : listFile) 
                totalSize += file.length();
        }
        return totalSize;
    }
}
