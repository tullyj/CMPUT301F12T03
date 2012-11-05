package ca.ualberta.cs.c301_teamproject;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfTaskItem;
import ca.ualberta.cs.c301_repository.TfTaskRepository;
import ca.ualberta.cs.c301_interfaces.ItemType;

public class ViewSingleTask extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_single_task);
        
        Intent intent = getIntent();
        String taskId = intent.getExtras().getString(ViewTasks.TASK_ID);
        
        Task task = null;
        try {
            task = TfTaskRepository.getTaskById(taskId);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        
        TextView title = (TextView) findViewById(R.id.showTaskTitle);
        title.setText(task.getTitle());
        
        updateList(task);
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
    
	/**
	 * Refreshes, or recreates, the listview on the ItemList screen.
	 */
    public void updateList(Task task){   	
    	final List<TfTaskItem> items = task.getAllItems();
    	ItemListElement[] elements = new ItemListElement[items.size()];
    	String title = "";
    	for(int i = 0; i < items.size(); i++){
//    		listElements.add(new ItemListElement(
//    	    		android.R.drawable.ic_input_get, "Test Item " + (i+1), 
//    	    			"This is a sample description..."));
    		switch(items.get(i).getType()){
    			case TEXT:
    				title = "Texts";
    				break;
    			case PHOTO:
    				title = "Photos";
    				break;
    			case AUDIO:
    				title = "Audio";
    				break;
    			
    		}
    		elements[i] = new ItemListElement(android.R.drawable.ic_input_get, 
    			title, items.get(i).getDescription());
    	}
        
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.list_multi, elements);
        ListView listView = (ListView) findViewById(R.id.singleTaskList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id){
				Toast.makeText(getApplicationContext(), 
					"Opening Item: " + items.get(position).getType().toString(), 
						Toast.LENGTH_LONG).show();
			}	
        });
    }

}
