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

public class PreviewText extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.preview_text);
            File file = ItemList.currFile;
            
            TextView tv = (TextView) findViewById(R.id.textPreview);

            InputStream fis = null;

            try{
            	fis = new FileInputStream(file);
            	System.out.println("DEBUG: file size = " + file.getTotalSpace());
            	System.out.println("DEBUG: available? = " + fis.available());

            	int ch;
                StringBuffer strContent = new StringBuffer("");
            	
            	while((ch = fis.read()) != -1) {
            		strContent.append((char)ch);
            	}

            	System.out.println("DEBUG: Text = " + strContent);
            	tv.setText(strContent);
            	
            } catch (Exception e) {
            	System.err.print(e);
            }
            
            
    }
}
