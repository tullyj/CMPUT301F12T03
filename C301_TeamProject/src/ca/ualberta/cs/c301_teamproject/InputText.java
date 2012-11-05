package ca.ualberta.cs.c301_teamproject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    	else
    		Toast.makeText(getBaseContext(), "folder not Created", 
    				Toast.LENGTH_SHORT).show();
    	//create and write the text into the saved file
    	try {
    		File myTextFile = new File(directory + fileName + ".txt");
    		    		
    		myTextFile.createNewFile();
    		FileOutputStream fOut = new FileOutputStream(myTextFile);
    		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
    		myOutWriter.append(inputText);
    		myOutWriter.close();
    		
    		addToList(myTextFile);
    		
    	} catch (Exception e) {
    		Toast.makeText(getBaseContext(), e.getMessage(), 
    				Toast.LENGTH_SHORT).show();
    	}
    }
    
    public void addToList(File myTextFile){
    	InputFile.files.add(myTextFile);
    }
}
