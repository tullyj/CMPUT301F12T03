/**
 * Task Force Application
 * See github for license and other information: 
 * github.com/tullyj/CMPUT301F12T03/
 * 
 * Task Force is created by: 
 * Colin Hunt, Edwin Chung, 
 * Kris Kushniruk, and Tully Johnson.
 */
package ca.ualberta.cs.c301_preview;

import java.io.File;

import ca.ualberta.cs.c301_teamproject.ItemList;
import ca.ualberta.cs.c301_teamproject.MainPage;
import ca.ualberta.cs.c301_teamproject.PromptDialog;
import ca.ualberta.cs.c301_teamproject.R;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * Takes in an image and displays it via ImageView as a preview function.
 * @author Edwin Chung
 *
 */
public class PreviewPhoto extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_picture);
        
        //get the file from the item list
        File file = ItemList.currFile;
        
        //display the image via ImageView
        ImageView iv = (ImageView) findViewById(R.id.imageView1);
        Uri mUri = Uri.fromFile(file);
        iv.setImageURI(mUri);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * When the menu button item "About" is selected display about dialog.
     * @param item  item clicked.
     */
    public boolean onOptionsItemSelected(MenuItem item){
        Dialog helpDialog = onCreateDialog(MainPage.DIALOG_ABOUT);
        helpDialog.show();
        return true;
    }

    public Dialog onCreateDialog(int id){    
        if (id == MainPage.DIALOG_ABOUT) {
            // Show details about Task Force.
            PromptDialog mDialog = new PromptDialog();
            return mDialog.aboutPrompt(this);
        }
        return null;
    }
}
