package ca.ualberta.cs.c301_teamproject;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ca.ualberta.cs.c301_repository.TfTask;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

public class LocalTaskList extends Activity {
    ArrayAdapter<TfTask> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_task_list);
        
        List<TfTask> entryList = 
                TfTaskRepository.getLocalTasks(getApplicationContext());
        adapter = new ArrayAdapter<TfTask>(this, 
                android.R.layout.simple_list_item_1, entryList);
        
        ListView listView = (ListView) findViewById(R.id.listViewLocalTasks);
        listView.setAdapter(adapter);
        
        Iterator<TfTask> it = entryList.iterator();
        
        while(it.hasNext()){
            
            TfTask task = it.next();
            
            String title = task.getTitle();
            String desc = task.getDescription();
            String em = task.getEmail();
            
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, title + "\n" + desc + "\n" + em, duration);
            toast.show();
        
        }
     
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

    public Dialog onCreateDialog(int id){    
        if (id == MainPage.DIALOG_ABOUT) {
            // Show details about Task Force.
            PromptDialog mDialog = new PromptDialog();
            return mDialog.aboutPrompt(this);
        }
        return null;
    }
}
