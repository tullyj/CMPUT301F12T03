package ca.ualberta.cs.c301_teamproject;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.Toast;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

public class ViewTasks extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tasks);
        
    	Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, "test", duration);
		toast.show();
        
        
        //testing grabbing tasks
        try {
			List<Task> taskList = TfTaskRepository.getAllTasks();
			
			for(Task task: taskList){
				


				toast = Toast.makeText(context, task.getTitle(), duration);
				toast.show();
				
				
				
			}
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
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

}
