package ca.ualberta.cs.c301_teamproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * This class just has the buttons for the main page.
 * NOTE: there are extra buttons on this page for demonstration purposes
 *
 */
public class MainPage extends Activity {
	
	public static String LOCAL = "local";
	public static String IDS = "ids";

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
        
        //grabbing all of the local task ids
        MyLocalTasks lt = new MyLocalTasks();
        String[] ids = lt.loadMyTaskIds(getApplicationContext());
        
        //send the local IDs as intent
        intent.putExtra(LOCAL, "yes");
        intent.putExtra(IDS, ids);
        startActivity(intent);

    }

    public void allTasks(View view) {

        Intent intent = new Intent(this, ViewTasks.class);

        //grab all of the task IDs
        intent.putExtra(LOCAL, "no");
        
        
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
    
    // temporary button to list local tasks for testing
    //sends a test email right now
    public void localTasks(View view) {
        Intent intent = new Intent(this, LocalTaskList.class);
        //startActivity(intent);
        
        //sending a test email - you have to manually send email
        //for send automatic email here would be the solution - link split between lines
        //http://stackoverflow.com/questions/2020088/sending-email-in-
        //android-using-javamail-api-without-using-the-default-built-in-a/2033124#2033124
//        String to = "test@test.ca";
//        String subject = "Task Force Notification";
//        String message = "This is task force informing you that ";
//        message += "your task appears to be fulfilled!";
//
//        Intent email = new Intent(Intent.ACTION_SEND);
//        email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
//        // email.putExtra(Intent.EXTRA_CC, new String[]{ to});
//        // email.putExtra(Intent.EXTRA_BCC, new String[]{to});
//        email.putExtra(Intent.EXTRA_SUBJECT, subject);
//        email.putExtra(Intent.EXTRA_TEXT, message);
//        
//
//        // need this to prompts email client only
//        email.setType("message/rfc822");
//
//        startActivity(Intent.createChooser(email, "Choose an Email client :"));
//        
     
        
    }
}
