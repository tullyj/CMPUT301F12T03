package ca.ualberta.cs.c301_teamproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

/**
 * This class just has the buttons for the main page.
 * NOTE: there are extra buttons on this page for demonstration purposes
 *
 */
public class MainPage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createTask(View view) {

        Intent intent = new Intent(this, CreateTask.class);
        startActivity(intent);

    }

    public void yourTasks(View view) {

        Intent intent = new Intent(this, ViewTasks.class);

        // need to send parameters to filter into your tasks
        startActivity(intent);

    }

    public void allTasks(View view) {

        Intent intent = new Intent(this, ViewTasks.class);

        // need to send parameters to filter into all tasks
        startActivity(intent);

    }

    //for debugging input file menu
    public void goToInputFile(View view) {

        Intent intent = new Intent(this, ItemList.class);

        // need to send parameters to filter into all tasks
        startActivity(intent);

    }
    
    //for debugging input text menu
    public void goToInputText(View view){
    	
        Intent intent = new Intent(this, InputText.class);

        // need to send parameters to filter into all tasks
        startActivity(intent);
    }
}
