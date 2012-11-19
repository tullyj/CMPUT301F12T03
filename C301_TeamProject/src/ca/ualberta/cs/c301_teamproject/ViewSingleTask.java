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

/**
 * Shows items for a task in a list, click item to view. Click "+" button
 * to add files to task/item (fullfilling the task).
 *
 */
public class ViewSingleTask extends Activity {

	private Task task;
	private String taskId;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_single_task);
        
        Intent intent = getIntent();
        taskId = intent.getExtras().getString(ViewTasks.TASK_ID);
        
        task = null;
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
    public void updateList(final Task task){   	
    	final List<TfTaskItem> items = task.getAllItems();
    	ItemListElement[] elements = new ItemListElement[items.size()];
    	String title = "";
    	//String itemType = null;
    	String[] info = new String[2];
    	for(int i = 0; i < items.size(); i++){
    		info = getTypeInfo(items.get(i).getType());
    		title = info[0];
    		//itemT = 
    		elements[i] = new ItemListElement(android.R.drawable.ic_input_get, 
    			title, items.get(i).getDescription());
    		title = "";
    	}
        //final String itemT = itemType;
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.list_multi, elements);
        ListView listView = (ListView) findViewById(R.id.singleTaskList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id){
//				Toast.makeText(getApplicationContext(), 
//					"Opening Item: " + items.get(position).getType().toString(), 
//						Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(getApplicationContext(), ItemList.class);
				// Pass TaskId, and Item Number
				String[] infoT = getTypeInfo(items.get(position).getType());
				
				intent.putExtra("SendItem", new String[]{taskId, infoT[1], 
					infoT[0], items.get(position).getDescription()});
				startActivity(intent);
			}	
        });
    }
    
    private String[] getTypeInfo(ItemType type) {
    	//Toast.makeText(getApplicationContext(), 
		//		"Opening Item: " + type.toString(), Toast.LENGTH_LONG).show();
    	switch(type) {
		    case TEXT:
		    	return new String[]{"Texts", "TEXT"};
			case PHOTO:
				return new String[]{"Photos", "PHOTO"};
			case AUDIO:
				return new String[]{"Audio", "AUDIO"};
			case VIDEO:
				return new String[]{"Videos", "VIDEO"};
    	}
    	return new String[]{"",""};
    }
    
    @Override
	/**
	 * Receives result from adding files to an item.
	 * @param requestCode		Describes type of intent.
	 * @param resultCode		Describes status of intent.
	 * @param data				The intent.
	 */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
            	Toast.makeText(getApplicationContext(), 
    				"Task '" + task.getTitle() + 
    				"'has been modified, uploading to webservice", 
    				Toast.LENGTH_LONG).show();
            }
        }
    }
            
}
