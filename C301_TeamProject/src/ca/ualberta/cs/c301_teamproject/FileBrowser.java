package ca.ualberta.cs.c301_teamproject;

import java.io.File;
import java.net.URI;

import java.util.ArrayList;

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



public class FileBrowser extends ListActivity {

 

 private List<String> item = null;

 private List<String> path = null;

 private String root="/";

 private TextView myPath;

 //http://android-er.blogspot.ca/2010/01/implement-simple-file-explorer-in.html

    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.file_browser);

        myPath = (TextView)findViewById(R.id.path);

        getDir(root);

    }

    

    private void getDir(String dirPath)

    {

     myPath.setText("Location: " + dirPath);

     

     item = new ArrayList<String>();

     path = new ArrayList<String>();

     

     File f = new File(dirPath);

     File[] files = f.listFiles();

     

     if(!dirPath.equals(root))

     {



      item.add(root);

      path.add(root);

      

      item.add("../");

      path.add(f.getParent());

            

     }

     

     for(int i=0; i < files.length; i++)

     {

       File file = files[i];

       path.add(file.getPath());

       if(file.isDirectory())

        item.add(file.getName() + "/");

       else

        item.add(file.getName());

     }



     ArrayAdapter<String> fileList =

      new ArrayAdapter<String>(this, R.layout.row, item);

     setListAdapter(fileList);

    }



 @Override

 protected void onListItemClick(ListView l, View v, int position, long id) {

  

  final File file = new File(path.get(position));

  

  if (file.isDirectory())

  {

   if(file.canRead())

    getDir(path.get(position));

   else

   {

    new AlertDialog.Builder(this)

    .setIcon(R.drawable.taskforcebar)

    .setMessage("[" + file.getName() + "] folder can't be read!")

    .setPositiveButton("OK", 

      new DialogInterface.OnClickListener() {


       public void onClick(DialogInterface dialog, int which) {

        // TODO Auto-generated method stub

       }

      }).show();

   }

  }

  else

  {

   new AlertDialog.Builder(this)

    .setIcon(R.drawable.taskforcebar)

    .setMessage("[" + file.getName() + "]")

    .setPositiveButton("OK", 

      new DialogInterface.OnClickListener() {

       public void onClick(DialogInterface dialog, int which) {

        // TODO Auto-generated method stub
    	   File addFile = file.getAbsoluteFile();
    	   InputFile.files.add(addFile);
    	   //go back to input list
    	   Intent intent = new Intent(getApplicationContext(), InputFile.class);
    	   intent.putExtra("FromFile", 4);
    	   startActivity(intent);
       }

      }).show();

  }

 }

}
