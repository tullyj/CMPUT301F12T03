package ca.ualberta.cs.c301_preview;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_teamproject.ItemList;
import ca.ualberta.cs.c301_teamproject.R;
import ca.ualberta.cs.c301_teamproject.ViewSingleTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Takes in a text file and displays the text in that file via TextView
 * @author Edwin Chung
 *
 */
public class PreviewText extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.preview_text);
            
            //get the file from the file list
            File file = ItemList.currFile;
            
            //open up the file, read it and display it via TextView
            TextView tv = (TextView) findViewById(R.id.textPreview);
            
            InputStream fis = null;
            
            try{
                int ch;
                StringBuffer strContent = new StringBuffer("");
            	
                fis = new FileInputStream(file);
                
            	while((ch = fis.read()) != -1) {
            		strContent.append((char)ch);
            	}

            	tv.setText(strContent);
            	
            } catch (Exception e) {
            	System.err.print(e);
            }
            
            
    }
}
