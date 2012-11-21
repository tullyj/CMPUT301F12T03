package ca.ualberta.cs.c301_teamproject;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_interfaces.TaskItem;
import ca.ualberta.cs.c301_repository.TfTask;
import ca.ualberta.cs.c301_repository.TfTaskItem;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Displays a listview of files for a given item in a task.
 * @author tullyj
 */
public class ItemList extends Activity {

	static final int DIALOG_AUDIO = 1;
	static final int DIALOG_PHOTO = 2;
	static final int DIALOG_VIDEO = 5;
	static final int DIALOG_ABOUT = 3;
	static final int TEXT_INTENT = 6;
	static final int FILE_INTENT = 7;
	private String taskId;
	private ItemType itemType;
	private TaskItem item;
	
	public ArrayList<ItemListElement> listElements = new ArrayList<ItemListElement>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);        
        
        
        // Get Task and Item
        String[] inArgs = getIntent().getStringArrayExtra("SendItem");
        
        // When item type is known from passing of intent with extra containing item type.
        taskId = inArgs[0];
        // Set title of Type of list
        ((TextView) findViewById(R.id.listTitle)).setText(inArgs[2] + " List");
        // Set Description from item.getDescription()
        ((TextView) findViewById(R.id.listItemDesc)).setText(inArgs[3]);
        
        if(inArgs[1].equals("TEXT")) itemType = ItemType.TEXT;
        else if(inArgs[1].equals("PHOTO")) itemType = ItemType.PHOTO;
        else if(inArgs[1].equals("VIDEO")) itemType = ItemType.VIDEO;
        else itemType = ItemType.AUDIO;
        
        // Set the progress bar and textview listItemFraction.
        int[] progress = populateList();
        
        double dPerc = ((double)progress[0]) / ((double)progress[1]);
        dPerc *= 100;
        int percentage = ((Double)(dPerc)).intValue();
        ((ProgressBar) findViewById(R.id.progressBar1)).setProgress(percentage);
        
        String frac = Integer.toString(progress[0]) + "/" + Integer.toString(progress[1]); 
        ((TextView) findViewById(R.id.listItemFraction)).setText(frac);
        
        
        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_item_list, menu);
        return true;
    }
    
    @Override
    /**
     * When the menu button item "About" is selected display about dialog.
     * @param item	item clicked.
     */
    public boolean onOptionsItemSelected(MenuItem item){
    	Dialog helpDialog = onCreateDialog(DIALOG_ABOUT);
        helpDialog.show();
        return true;
    }
    
    /**
     * Creates a dialog for this activity (InputFile).
     * @param id		Selects what dialog to create/return.
     * @return 			Dialog to be displayed.
     */
    public Dialog onCreateDialog(int id){   
		if(id == DIALOG_ABOUT){
			PromptDialog mDialog = new PromptDialog();
			return mDialog.aboutPrompt(this);
		}
		return null;
    }
    
    public static TaskItem getItem(Task task, String itemType) {
    	List<TfTaskItem> itemList = task.getAllItems();
        for(int i = 0; i < itemList.size(); i++){
        	if(itemList.get(i).getType().toString().equals(itemType))
        		return itemList.get(i);
        }
        return null;
    }
    
    /**
     * Looks through the current item within a task for the list of files.
     */
    private int[] populateList(){        
    	Task task = null;
        try {
            task = TfTaskRepository.getTaskById(taskId);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        
        item = getItem(task, itemType.toString());
    	
        // Total is the target total number for this item.
        int total = item.getNumber();
    	List<File> files = item.getAllFiles();
    	// Count will be number of files currently for a given item.
    	int count = files.size();
    	
    	for(int i = 0; i < count; i++){
    		listElements.add(new ItemListElement(
    			android.R.drawable.ic_input_get, itemType + " " + (i+1), 
    				getTime(files.get(i).lastModified())));
    	}
    	
    	return new int[]{count, total};
    }
    
    public String getTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format.format(date).toString();
    }
    
    /**
     * Starts intent (screen) for inputing files of the given item type.
     * @param v
     */
    public void inputFileClick(View v){
    	Intent intent = null;
    	int num = getItemNum(itemType);
    	if(num != TEXT_INTENT)
	    	intent = new Intent(this, InputFile.class);
    	else
    		intent = new Intent(this, InputText.class);
    	intent.putExtra("ItemArgs", new String[]{String.valueOf(num), 
    		taskId, itemType.toString()});
    	
        startActivityForResult(intent, FILE_INTENT);
    }
    
    /**
     * Given an ItemType enum, find the corresponding const int to choose 
     * file type for the item.
     * @param type
     * @return	int of item type.
     */
    private int getItemNum(ItemType type){
    	switch(type){
	    	case TEXT:
				return TEXT_INTENT;
	    	case PHOTO:
				return DIALOG_PHOTO;
			case AUDIO:
				return DIALOG_AUDIO;
			case VIDEO:
				return DIALOG_VIDEO;
			default:
				return 0;
    	}
    	//return DIALOG_AUDIO;
    }
    
	/**
	 * Refreshes, or recreates, the listview on the ItemList screen.
	 */
    public void updateList(){   	
    	
    	ItemListElement[] elements = new ItemListElement[listElements.size()];
        listElements.toArray(elements);
        
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.list_multi, elements);
        ListView listView = (ListView) findViewById(R.id.importList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id){
				Toast.makeText(getApplicationContext(), 
					"Opening Item: " + listElements.get(position).getTitle(), 
						Toast.LENGTH_LONG).show();
			}	
        });
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == FILE_INTENT){
    		Task task = new TfTask();
	        try {
	            task = TfTaskRepository.getTaskById(data.getStringExtra("SavingTask"));
	        } catch (Exception e) {
	            System.err.println(e.getMessage());
	            e.printStackTrace();
	        }
	        
	        if(task.isModified())
	        	TfTaskRepository.addTask(task);
    		
    		finish();
    	}
    }
}
