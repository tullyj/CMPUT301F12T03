package ca.ualberta.cs.c301_preview;

import ca.ualberta.cs.c301_teamproject.R;
import ca.ualberta.cs.c301_teamproject.R.layout;
import ca.ualberta.cs.c301_teamproject.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PreviewPicture extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_picture);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preview_picture, menu);
        return true;
    }
}
