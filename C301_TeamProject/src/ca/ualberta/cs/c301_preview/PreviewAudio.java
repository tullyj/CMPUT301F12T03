package ca.ualberta.cs.c301_preview;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
//import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.media.MediaPlayer.OnPreparedListener;
//import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import ca.ualberta.cs.c301_teamproject.ItemList;
import ca.ualberta.cs.c301_teamproject.R;

/* http://developer.android.com/guide/topics/media/audio-capture.html */
public class PreviewAudio extends Activity {
	
	private static final String LOG_TAG = "PreviewAudio";
    //private static String mFileName = null;
	private static final int VERTICAL = 1;
    
    //private static File file;
    private String filePath;
    private Uri mUri;
    
    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent intent = getIntent();
//        mUri = getIntent().getData();
        mUri = Uri.fromFile(ItemList.currFile);
        //filePath = intent.getStringExtra("PreviewItem");
        filePath = ItemList.currFile.getAbsolutePath();
        //file = new File(intent.getData().getPath());
        
        mPlayButton = new PlayButton(this);
        ImageView speakerImage = new ImageView(this);
        speakerImage.setImageResource(R.drawable.speaker);
        
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(VERTICAL);
        ll.addView(mPlayButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        ll.addView(speakerImage,
        	new LinearLayout.LayoutParams(
        			ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 2));
        setContentView(ll);

        Toast.makeText(getApplicationContext(), 
    			"Starting preview with: " + filePath, Toast.LENGTH_LONG).show();
        
    }
    
	private void onPlay(boolean start) {
		if (start) startPlaying();
		else stopPlaying();
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			//mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			FileInputStream fis = new FileInputStream(ItemList.currFile);
	        mPlayer.setDataSource(fis.getFD());
			//mPlayer.setDataSource(this, mUri);
			//mPlayer.setDataSource("/mnt/sdCard/Music/Test.mp3");
			mPlayer.setOnPreparedListener((OnPreparedListener) this);
			mPlayer.prepare();
			mPlayer.start();
			fis.close();
			Toast.makeText(getApplicationContext(), 
	    			filePath, Toast.LENGTH_LONG).show();
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
