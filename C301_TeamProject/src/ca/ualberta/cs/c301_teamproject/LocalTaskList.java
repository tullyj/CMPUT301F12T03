package ca.ualberta.cs.c301_teamproject;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ca.ualberta.cs.c301_interfaces.Task;
import ca.ualberta.cs.c301_repository.TfTask;
import ca.ualberta.cs.c301_repository.TfTaskRepository;

public class LocalTaskList extends Activity {
    ArrayAdapter<TfTask> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_task_list);
        
        List<TfTask> entryList = 
                TfTaskRepository.getLocalTasks(getApplicationContext());
        adapter = new ArrayAdapter<TfTask>(this, 
                android.R.layout.simple_list_item_1, entryList);
        
        ListView listView = (ListView) findViewById(R.id.listViewLocalTasks);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_local_task_list, menu);
        return true;
    }
}
