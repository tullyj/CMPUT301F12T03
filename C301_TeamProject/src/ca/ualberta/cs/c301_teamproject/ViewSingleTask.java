package ca.ualberta.cs.c301_teamproject;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfTaskItem;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

/**
 * Shows items for a task in a list, click item to view. Click "+" button
 * to add files to task/item (fulfilling the task).
 *
 */
public class ViewSingleTask extends Activity {

	public static Task task;
	private String taskId;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_single_task);
        
        Intent intent = getIntent();
        taskId = intent.getExtras().getString(ViewTasks.TASK_ID);
        
        //load the single task async style
        new loadSingleTask().execute();
        
        
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
				
				intent.putExtra("SendItem", new String[]{infoT[1], 
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
    
    /**
     * Async task for loading of a single task
     *
     */
    private class loadSingleTask extends AsyncTask<String, String, String>{
    	
    	Dialog load = new Dialog(ViewSingleTask.this);

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			task = null;
	        try {
	            task = TfTaskRepository.getTaskById(taskId);
	        } catch (Exception e) {
	            System.err.println(e.getMessage());
	            e.printStackTrace();
	        }
	        
			return null;
		}
    	
    	protected void onPreExecute(){
    		
    		load.setContentView(R.layout.save_load_dialog);
    		load.setTitle("Loading task");
    		load.show();
    		
    	}
    	
    	@Override
    	protected void onPostExecute(String result){
    		
    		super.onPostExecute(result);
    		
    		TextView title = (TextView) findViewById(R.id.showTaskTitle);
            title.setText(task.getTitle());
            updateList(task);
    		
    		load.dismiss();

    	}
	
    }
  
}
