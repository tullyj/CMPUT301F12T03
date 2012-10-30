package ca.ualberta.cs.c301_teamproject;

import com.example.c301_teamproject.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;


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
		
		public void createTask(View view){
			
			Intent intent = new Intent(this, CreateTask.class);
			startActivity(intent);
			
		}
		
		public void yourTasks(View view){
			
			Intent intent = new Intent(this, ViewTasks.class);
			
			//need to send parameters to filter into your tasks
			startActivity(intent);
			
		}
		
		public void allTasks(View view){
			
			Intent intent = new Intent(this, ViewTasks.class);
			
			//need to send parameters to filter into all tasks
			startActivity(intent);
			
			
		}
	    

}
