package ca.ualberta.cs.c301_preview;

import android.app.Activity;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;
import ca.ualberta.cs.c301_teamproject.ItemList;
import ca.ualberta.cs.c301_teamproject.MainPage;
import ca.ualberta.cs.c301_teamproject.PromptDialog;
import ca.ualberta.cs.c301_teamproject.R;

/**
 * Plays a video from a mUri.
 * @author tullyj
 *
 */
public class PreviewVideo extends Activity {
    
    private Uri mUri;
    private VideoView mVideo;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_video);

        mUri = Uri.fromFile(ItemList.currFile);
        mVideo = (VideoView) findViewById(R.id.previewvid);
        mVideo.setVideoURI(mUri);
        mVideo.start();
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