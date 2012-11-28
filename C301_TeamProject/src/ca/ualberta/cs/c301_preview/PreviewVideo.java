package ca.ualberta.cs.c301_preview;

import java.io.FileInputStream;
import java.io.IOException;

import ca.ualberta.cs.c301_preview.PreviewAudio.PlayButton;
import ca.ualberta.cs.c301_teamproject.ItemList;
import ca.ualberta.cs.c301_teamproject.R;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

public class PreviewVideo extends Activity {
	
	private static final String LOG_TAG = "PreviewVideo";
    //private static String mFileName = null;
//	private static final int VERTICAL = 1;
    
    //private static File file;
    private String filePath;
    private Uri mUri;
//    private SurfaceView mSurfaceView;
    private VideoView mVideo;
    
    //private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.preview_video);
//        Intent intent = getIntent();
//        mUri = getIntent().getData();
        mUri = Uri.fromFile(ItemList.currFile);
        //filePath = intent.getStringExtra("PreviewItem");
        filePath = ItemList.currFile.getAbsolutePath();
        //file = new File(intent.getData().getPath());
        
        //mPlayButton = new PlayButton(this);
        //mSurfaceView = (SurfaceView) findViewById(R.id.surfaceVideo);
//        
//        
//        mVideo = (VideoView) findViewById(R.id.previewvid);
//        mVideo.setVideoPath("/mnt/extSdCard/DCIM/Camera/20121121_171024.mp4");
//        mVideo.start();
//        
        
        
//        LinearLayout ll = new LinearLayout(this);
//        //ll.setOrientation(VERTICAL);
////        ll.addView(mPlayButton,
////            new LinearLayout.LayoutParams(
////                ViewGroup.LayoutParams.MATCH_PARENT,
////                ViewGroup.LayoutParams.WRAP_CONTENT, 0));
//        ll.addView(mSurfaceView,
//        	new LinearLayout.LayoutParams(
//        			ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT, 2));
//        setContentView(ll);

        Toast.makeText(getApplicationContext(), 
    			"Starting preview with: " + filePath, Toast.LENGTH_LONG).show();
        
    }
    
//	private void onPlay(boolean start) {
//		if (start) startPlaying();
//		else stopPlaying();
//	}
//
//	private void startPlaying() {
//		mPlayer = new MediaPlayer();
//		try {
//			//mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//			//FileInputStream fis = new FileInputStream(ItemList.currFile);
//	        mPlayer.setDataSource("/mnt/extSdCard/Videos/Top.Gear.50.Years.of.Bond.Cars.720p.HDTV.x264-FoV.mkv");
//			//mPlayer.setDataSource(this, mUri);
//			mPlayer.setOnPreparedListener((OnPreparedListener) this);
//			mPlayer.prepare();
//			mPlayer.start();
//			//fis.close();
//			Toast.makeText(getApplicationContext(), 
//	    			filePath, Toast.LENGTH_LONG).show();
//		} catch (IOException e) {
//			Log.e(LOG_TAG, "prepare() failed");
//		}
//	}
//	
//	private void stopPlaying() {
//		mPlayer.release();
//		mPlayer = null;
//	}
//
//    class PlayButton extends Button {
//        boolean mStartPlaying = true;
//
//        OnClickListener clicker = new OnClickListener() {
//            public void onClick(View v) {
//                onPlay(mStartPlaying);
//                if (mStartPlaying) {
//                    setText("Stop playing");
//                } else {
//                    setText("Start playing");
//                }
//                mStartPlaying = !mStartPlaying;
//            }
//        };
//
//        public PlayButton(Context ctx) {
//            super(ctx);
//            setText("Start playing");
//            setOnClickListener(clicker);
//        }
//    }

//	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void surfaceCreated(SurfaceHolder holder) {
//		/* http://stackoverflow.com/questions/4835060/android-mediaplayer-how-to-use-surfaceview-or-mediaplayer-to-play-video-in-cor */
//		
//		try {
//			mPlayer.setDataSource("/mnt/extSdCard/DCIM/Camera/20121126_132533.mp4");
//			mPlayer.prepare();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	    //Get the dimensions of the video
//	    int videoWidth = mPlayer.getVideoWidth();
//	    int videoHeight = mPlayer.getVideoHeight();
//
//	    //Get the width of the screen
//	    @SuppressWarnings("deprecation")
//		int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
//
//	    //Get the SurfaceView layout parameters
//	    android.view.ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
//
//	    //Set the width of the SurfaceView to the width of the screen
//	    lp.width = screenWidth;
//
//	    //Set the height of the SurfaceView to match the aspect ratio of the video 
//	    //be sure to cast these as floats otherwise the calculation will likely be 0
//	    lp.height = (int) (((float)videoHeight / (float)videoWidth) * (float)screenWidth);
//
//	    //Commit the layout parameters
//	    mSurfaceView.setLayoutParams(lp);        
//
//	    //Start video
//	    mPlayer.start();
//		
//	}
//
//	public void surfaceDestroyed(SurfaceHolder holder) {
//		// TODO Auto-generated method stub
//		
//	}


}