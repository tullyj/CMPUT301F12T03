package ca.ualberta.cs.c301_preview;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_teamproject.ItemList;
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
            Intent intent = getIntent();
            int position = intent.getIntExtra("position", 9999);
            
            TaskItem item =
            		ItemList.getItem(ViewSingleTask.task, 
            						ItemType.TEXT.toString());
            
            File file = item.getAllFiles().get(position);
            TextView tv = (TextView) findViewById(R.id.textView1);
            //TextView tv = new TextView(getApplicationContext());
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
            	//text = strContent.toString();
            	System.out.println("DEBUG: Text = " + strContent);
            	tv.setText(strContent);
            	
            } catch (Exception e) {
            	System.err.print(e);
            }
            
            
    }
}
