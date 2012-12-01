package ca.ualberta.cs.c301_preview;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;
import ca.ualberta.cs.c301_teamproject.ItemList;
import ca.ualberta.cs.c301_teamproject.R;

public class PreviewVideo extends Activity {
    
    private String filePath;
    private Uri mUri;
    private VideoView mVideo;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_video);

        mUri = Uri.fromFile(ItemList.currFile);
        filePath = ItemList.currFile.getAbsolutePath();
        mVideo = (VideoView) findViewById(R.id.previewvid);
        mVideo.setVideoPath("/mnt/extSdCard/DCIM/Camera/20121121_171024.mp4");
        mVideo.start();

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