package ca.ualberta.cs.c301_teamproject;

import ca.ualberta.cs.c301_repository.TfTaskRepository;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

/**
 * This class just has the buttons for the main page.
 *
 */
public class MainPage extends Activity {
	
	public static String TYPE = "type";
	public static String IDS = "ids";
	public static final int DIALOG_ABOUT = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
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

    /**
     * Create task was clicked
     * @param view
     */
    public void createTask(View view) {

        Intent intent = new Intent(this, CreateTask.class);
        startActivity(intent);
    }

    /**
     * Your tasks was clicked. Grab the filter parameters then start
     * @param view
     */
    public void yourTasks(View view) {

        Intent intent = new Intent(this, ViewTasks.class);

        // need to send parameters to filter into your tasks
        
        //grabbing all of the local task ids
        MyLocalTaskInformation lt = new MyLocalTaskInformation();
        String[] ids = lt.loadMyTaskIds(getApplicationContext());
        
        //send the local IDs as intent
        intent.putExtra(TYPE, "my");
        intent.putExtra(IDS, ids);
        startActivity(intent);
    }

    /**
     * View all tasks was clicked. No parameters to be sent since we want
     * all of the tasks
     * @param view
     */
    public void allTasks(View view) {

        Intent intent = new Intent(this, ViewTasks.class);

        //grab all of the task IDs
        intent.putExtra(TYPE, "all");     
        startActivity(intent);
    }

    /**
     * When local tasks was clicked. Grab the parameters then start
     * @param view
     */
    public void localTasks(View view) {
        Intent intent = new Intent(this, LocalTaskList.class);
        
        //grab the local ids
        String[] ids = 
                TfTaskRepository.getLocalTaskIds(getApplicationContext());
        intent.putExtra(TYPE, "local");
        intent.putExtra(IDS, ids);
        startActivity(intent);        
    }
}
