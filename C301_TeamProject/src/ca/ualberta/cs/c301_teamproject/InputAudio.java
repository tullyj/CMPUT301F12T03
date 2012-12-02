package ca.ualberta.cs.c301_teamproject;

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
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

/* http://developer.android.com/guide/topics/media/audio-capture.html */
public class InputAudio extends Activity {
	
	private static final String LOG_TAG = "InputAudio";
	private static final int VERTICAL = 1;
    private static String mFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;
    
    private SaveButton   mSaveButton = null;

	private void onRecord(boolean start) {
		if(start) startRecording();
		else stopRecording();
	}
	
	private void onPlay(boolean start) {
		if (start) startPlaying();
		else stopPlaying();
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
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
	
	private void startRecording() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
		
		mRecorder.start();
	}
	
	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}
	
	class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
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
    
    class SaveButton extends Button {
        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onSave();
            }
        };

        public SaveButton(Context ctx) {
            super(ctx);
            setText(R.string.save_changes);
            setOnClickListener(clicker);
        }
    }
    
    public InputAudio() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/Audio" + InputFile.inFileCount + ".3gp";
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ImageView micImage = new ImageView(this);
        micImage.setImageResource(R.drawable.mic);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(VERTICAL);
        mRecordButton = new RecordButton(this);
        ll.addView(mRecordButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        mSaveButton = new SaveButton(this);
        ll.addView(mSaveButton,
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        ll.addView(micImage,
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 0));        
        setContentView(ll);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
	
    private void onSave() {
    	//Intent mIntent = parseUri(Uri.fromFile(new File(mFileName)), 0);
    	Intent mIntent = getIntent();
    	mIntent.putExtra("AudioExtra", Uri.fromFile(new File(mFileName)));
    	
    	setResult(RESULT_OK, mIntent);
    	finish();
    }
}
