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
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class creates an input text menu that will take a user's input and
 * generate a text file at a set location and add it to the file list.
 *  
 * @author Edwin Chung
 */
public class InputText extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_text);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * When the menu button item "About" is selected display about dialog.
     * @param item  item clicked.
     */
    public boolean onOptionsItemSelected(MenuItem item){
        Dialog helpDialog = onCreateDialog(MainPage.DIALOG_ABOUT);
        helpDialog.show();
        return true;
    }

    public Dialog onCreateDialog(int id) {    
        if (id == MainPage.DIALOG_ABOUT) {
            // Show details about Task Force.
            PromptDialog mDialog = new PromptDialog();
            return mDialog.aboutPrompt(this);
        }
        return null;
    }
    /**
     * Will take the user's input and generate a text file in a set directory
     * 
     * @param view
     */
    public void saveChanges(View view) {
    	String inputText = "";
    	
    	//hardcoded filename
    	String fileName = "/001";
    	
    	//hardcoded file directory
    	String directory = Environment.getExternalStorageDirectory().toString()
    			+ "/TextFiles";

    	EditText et = (EditText) findViewById(R.id.text_input_box);
    	inputText = et.getText().toString();
    	
    	//create the directory, if it does not exist
    	File folder = new File(directory);
    	if (!folder.exists()){
    		//Toast if created
    		if (folder.mkdirs()){
    			Toast.makeText(getBaseContext(), "folder Created", 
        				Toast.LENGTH_SHORT).show();
    		}
    	}

    	try {
    		File myTextFile = new File(directory + fileName + ".txt");
    		    		
    		myTextFile.createNewFile();
    		FileOutputStream fOut = new FileOutputStream(myTextFile);
    		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
    		myOutWriter.append(inputText);
    		myOutWriter.close();
    		
    		//call method to add to the item list
    		addToList(myTextFile);
    		
    	} catch (Exception e) {
    		Toast.makeText(getBaseContext(), e.getMessage(), 
    				Toast.LENGTH_SHORT).show();
    	}
    	finish();
    }
    
    /**
     * Adds the text file to the list of files
     * 
     * @param myTextFile			text file to be added
     */
    public void addToList(File myTextFile) {
    	String[] inArgs = getIntent().getStringArrayExtra("ItemArgs");
		TaskItem item = ViewSingleTask.task.getItemByType(inArgs[1]);
		
		ArrayList<File> files = new ArrayList<File>();
		files.add(myTextFile);
		try {
            item.addFiles(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		Intent intent = getIntent();
    	setResult(RESULT_OK, intent);
    }
}
