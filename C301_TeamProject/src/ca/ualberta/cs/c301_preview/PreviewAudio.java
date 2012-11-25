package ca.ualberta.cs.c301_preview;

import android.app.Activity;
import android.widget.LinearLayout;
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
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

/* http://developer.android.com/guide/topics/media/audio-capture.html */
public class PreviewAudio extends Activity {
	
	private static final String LOG_TAG = "PreviewAudio";
    //private static String mFileName = null;
    
    private static File file;

    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;
	
	private void onPlay(boolean start) {
		if (start) startPlaying();
		else stopPlaying();
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(file.getAbsolutePath());
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        setContentView(ll);

        Intent intent = getIntent();
        file = new File(intent.getData().getPath());
    }
	
//    private void onSave() {
//    	//Intent mIntent = parseUri(Uri.fromFile(new File(mFileName)), 0);
//    	//Intent mIntent = getIntent();
//    	//mIntent.putExtra("AudioExtra", Uri.fromFile(new File(mFileName)));
//    	
//    	//setResult(RESULT_OK, mIntent);
//    	finish();
//    }
}
