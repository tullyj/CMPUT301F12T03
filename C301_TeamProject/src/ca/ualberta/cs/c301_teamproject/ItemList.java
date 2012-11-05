package ca.ualberta.cs.c301_teamproject;

import java.util.ArrayList;

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

public class ItemList extends Activity {

	static final int DIALOG_ABOUT = 2;
	
	public ArrayList<ItemListElement> listElements = new ArrayList<ItemListElement>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list);
        
        // Set the progress bar and textview listItemFraction.
        int[] progress = populateList();
        
        double dPerc = ((double)progress[0]) / ((double)progress[1]);
        dPerc *= 100;
        int percentage = ((Double)(dPerc)).intValue();
        //int percentage = ((Double)Math.ceil(progress[0]/progress[1])).intValue() * 100;
        ((ProgressBar) findViewById(R.id.progressBar1)).setProgress(percentage);
        
        String frac = Integer.toString(progress[0]) + "/" + Integer.toString(progress[1]); 
        ((TextView) findViewById(R.id.listItemFraction)).setText(frac);
        
        // When item type is known from passing of intent with extra containing item type.
        ((TextView) findViewById(R.id.listTitle)).setText("Item List");
        ((TextView) findViewById(R.id.listItemDesc)).setText(
        		"Description of item requirements");
        
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
    
//    public void onItemClick(AdapterView<?> parent, View view,
//			int position, long id){
//    	
//    }
    
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
    
    /**
     * Looks through the current item within a task for the list of files.
     * 
     */
    private int[] populateList(){
    	// Count will be number of files currently for a given item.
    	int count = 12;
    	// Total is the target total number for this item.
    	int total = 20;
    	
    	// DEBUG -- for testing
    	for(int i = 0; i < count; i++){
	    	listElements.add(new ItemListElement(
	    		android.R.drawable.ic_input_get, "Test Item " + (i+1), 
	    			"This is a sample description..."));
    	}
    	
    	return new int[]{count, total};
    }
    
    public void inputFileClick(View v){
    	Intent intent = new Intent(this, InputFile.class);
    	intent.putExtra("ItemType", 2);
        startActivity(intent);
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
}
