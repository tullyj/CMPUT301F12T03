package ca.ualberta.cs.c301_preview;

import java.io.File;

import ca.ualberta.cs.c301_teamproject.ItemList;
import ca.ualberta.cs.c301_teamproject.R;
import ca.ualberta.cs.c301_teamproject.R.layout;
import ca.ualberta.cs.c301_teamproject.R.menu;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;

public class PreviewPhoto extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_picture);
        File file = ItemList.currFile;
        
        ImageView iv = (ImageView) findViewById(R.id.imageView1);
        
        Uri mUri = Uri.fromFile(file);
        
        iv.setImageURI(mUri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preview_picture, menu);
        return true;
    }
}
