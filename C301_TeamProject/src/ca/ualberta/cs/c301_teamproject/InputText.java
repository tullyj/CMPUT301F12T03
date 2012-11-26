package ca.ualberta.cs.c301_teamproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.ualberta.cs.c301_interfaces.TaskItem;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class creates an input text menu that will take a user's input and
 * generate a text file at a set location and add it to the file list.
 *  
 * @author Edwin Chung
 *
 */
public class InputText extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_text, menu);
        return true;
    }
    /**
     * Will take the user's input and generate a text file in a set directory
     * 
     * @param view
     */
    public void saveChanges(View view) {
    	String inputText = "";
    	
    	//hardcoded filename for now, going to be getNumber() + 1?
    	String fileName = "/001";
    	
    	//hardcoded file directory for now, may be changed later
    	String directory = Environment.getExternalStorageDirectory().toString()
    			+ "/TextFiles";

    	EditText et = (EditText) findViewById(R.id.text_input_box);
    	inputText = et.getText().toString();
    	
    	//create the directory, if it does not exist
    	File folder = new File(directory);
    	if(!folder.exists()){
    		//Toast if created
    		if(folder.mkdirs()){
    			Toast.makeText(getBaseContext(), "folder Created", 
        				Toast.LENGTH_SHORT).show();
    		}
    	}
    	//Toast if not created
//    	else
//    		Toast.makeText(getBaseContext(), "folder not Created", 
//    				Toast.LENGTH_SHORT).show();
    	//create and write the text into the saved file
    	try {
    		File myTextFile = new File(directory + fileName + ".txt");
    		    		
    		myTextFile.createNewFile();
    		FileOutputStream fOut = new FileOutputStream(myTextFile);
    		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
    		myOutWriter.append(inputText);
    		myOutWriter.close();
    		
    		//call method to add to the item list
    		addToList(myTextFile);
    		
    		//item.addFiles(List<File> list = new List<File>(){add(myTextFile)});
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
    public void addToList(File myTextFile){
    	String[] inArgs = getIntent().getStringArrayExtra("ItemArgs");
		TaskItem item = ItemList.getItem(ViewSingleTask.task, inArgs[1]);
		
		ArrayList<File> files = new ArrayList<File>();
		files.add(myTextFile);
		item.addFiles(files);
		
		Intent intent = getIntent();
    	setResult(RESULT_OK, intent);
    }
}
