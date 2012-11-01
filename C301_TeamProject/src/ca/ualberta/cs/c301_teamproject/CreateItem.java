package ca.ualberta.cs.c301_teamproject;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateItem extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.create_item);
        
        //creating the spinner for the item choices
    	Spinner spinner = (Spinner) findViewById(R.id.itemType);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    	R.array.item_choices, android.R.layout.simple_spinner_item);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
        
        
        
        
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
