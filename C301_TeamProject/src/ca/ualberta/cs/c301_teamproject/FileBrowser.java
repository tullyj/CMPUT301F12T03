package ca.ualberta.cs.c301_teamproject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileBrowser extends ListActivity {
private List<String> item = null;
private List<String> path = null;
private String root="/";
private TextView myPath;

static private int id;

final private List<String> imageExtensions = 
		 Arrays.asList("png", "jpg", "jpeg", "gif", "bmp");
final private List<String> audioExtensions =
		 Arrays.asList("wav", "mid", "mp3", "wma", "flac", "ogg");
final private List<String> videoExtensions =
		 Arrays.asList("avi", "mp4", "wmv", "mkv", "3gp");

    /**
     * This class is a modified version of the code given by Androider+ in the
     * following URL:
     * 
     * http://android-er.blogspot.ca/2010/01
     * 								/implement-simple-file-explorer-in.html
     * 
     * @author Edwin Chung
     */

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_browser);
        myPath = (TextView)findViewById(R.id.path);
        id = getIntent().getIntExtra("FileType", 0);
        getDir(root);
    }

    /**
     * Gets the directory of the file or folder.
     * 
     * @param dirPath		current directory
     */
    private void getDir(String dirPath) {
         myPath.setText("Location: " + dirPath);
	
         item = new ArrayList<String>();
         path = new ArrayList<String>();
	
         File f = new File(dirPath);
         File[] files = f.listFiles();  
	
         if(!dirPath.equals(root)) {
	
         item.add(root);
         path.add(root);
	
         item.add("../");
         path.add(f.getParent());
         }

         //populate the list of files/folders to be used by the adaptor
         for(int i=0; i < files.length; i++) {
           File file = files[i];
           path.add(file.getPath());
	       
           if(file.isDirectory())
               item.add(file.getName() + "/");
           else
               item.add(file.getName());
         }
	
         //create an adaptor to display the files using "row" layout
         ArrayAdapter<String> fileList =
         new ArrayAdapter<String>(this, R.layout.row, item);
         setListAdapter(fileList);
    }

     @Override

     /**
      * Is called when an item in the browser is clicked
      */
    protected void onListItemClick(ListView l, View v, int position, long id) {
        final File file = new File(path.get(position));

        if (file.isDirectory()) {
            if(file.canRead())
                getDir(path.get(position));
            else {
                new AlertDialog.Builder(this).setIcon(R.drawable.taskforcebar)
                .setMessage("[" + file.getName() + "] folder can't be read!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    }
                }).show();
            }
        }
        else {
            new AlertDialog.Builder(this)
            .setIcon(R.drawable.taskforcebar)
            .setMessage("[" + file.getName() + "]")
            .setPositiveButton("OK", 
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    File addFile = file.getAbsoluteFile();
                    if(checkExt(addFile.getName())){
                    	//InputFile.files.add(addFile);
                        //go back to input list
                        //Intent intent = new Intent(getApplicationContext(), 
                         //   InputFile.class);
                        Intent intent = getIntent();
                        intent.putExtra("FromFile", addFile.getAbsolutePath());
                    	setResult(RESULT_OK, intent);
                    	finish();
                    }
                    else
                		Toast.makeText(getApplicationContext(), 
                				"Incompatible file type", 
                				Toast.LENGTH_LONG).show();

                }
            }).show();
            }
    }
     
    public boolean checkExt(String fileName){
    	String ext = getExt(fileName).toLowerCase();
    	
    	if(audioExtensions.contains(ext) && id == 1)
    		return true;
    	else if(imageExtensions.contains(ext) && id == 2)
    		return true;
    	else if(videoExtensions.contains(ext) && id == 5)
    		return true;
    	else
    		return false;
    }
    
    public String getExt(String fileName){
    	String ext = "";
    	String temp = fileName;
    	int period_pos = temp.lastIndexOf('.');
    	//if the file name has no periods
    	if(period_pos == -1)
    		return ext;
    	else {
    		//extract extension
    		ext = temp.substring(period_pos + 1);
//    		Toast.makeText(getApplicationContext(), 
//    				ext, Toast.LENGTH_LONG).show();
    	}
    	return ext;
    }
}

