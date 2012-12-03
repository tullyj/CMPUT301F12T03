package ca.ualberta.cs.c301_preview;

import android.app.Activity;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.util.Log;
import android.media.MediaPlayer;
import java.io.IOException;
import ca.ualberta.cs.c301_teamproject.ItemList;
import ca.ualberta.cs.c301_teamproject.MainPage;
import ca.ualberta.cs.c301_teamproject.PromptDialog;
import ca.ualberta.cs.c301_teamproject.R;

/**
 * Plays audio files.
 * @author tullyj
 */
public class PreviewAudio extends Activity {
	
	private static final String LOG_TAG = "PreviewAudio";
	private static final int VERTICAL = 1;
    private Uri mUri;
    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUri = Uri.fromFile(ItemList.currFile);
        
        mPlayButton = new PlayButton(this);
        ImageView speakerImage = new ImageView(this);
        speakerImage.setImageResource(R.drawable.speaker);
        
        // Create the layout for playing audio.
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(VERTICAL);
        ll.addView(mPlayButton, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        ll.addView(speakerImage, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 2));
        setContentView(ll);        
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
    
	private void onPlay(boolean start) {
		if (start) startPlaying();
		else stopPlaying();
	}

	/**
	 * Grabs audio from a data source and starts playback.
	 */
	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(this, mUri);
			mPlayer.prepare();
			mPlayer.start();
			
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}
	
	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }
}
