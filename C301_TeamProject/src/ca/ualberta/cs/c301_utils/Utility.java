/**
 * Task Force Application
 * See github for license and other information: 
 * github.com/tullyj/CMPUT301F12T03/
 * 
 * Task Force is created by: 
 * Colin Hunt, Edwin Chung, 
 * Kris Kushniruk, and Tully Johnson.
 */
package ca.ualberta.cs.c301_utils;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.os.Environment;
import android.util.Base64;
import ca.ualberta.cs.c301_interfaces.ItemType;
import ca.ualberta.cs.c301_preview.PreviewAudio;
import ca.ualberta.cs.c301_preview.PreviewPhoto;
import ca.ualberta.cs.c301_preview.PreviewText;
import ca.ualberta.cs.c301_preview.PreviewVideo;
import ca.ualberta.cs.c301_teamproject.R;

/**
 * Contains various utility methods used throughout the application.
 * @author Colin Hunt
 * @author Tully Johnson 
 *
 */
public class Utility {

    /* http://stackoverflow.com/questions/1264709/
     *   convert-inputstream-to-byte-in-java
     */
    
    /**
     * Converts a file and encodes it as a Base64 string.
     * Uses method found on 
     * http://stackoverflow.com/questions/1264709/
     *   convert-inputstream-to-byte-in-java 
     * @param file File to be encoded.
     * @return Base64 string version of the file.
     * @throws Exception
     */
    public static String fileToBase64(File file) throws Exception {
        byte[] mByte = new byte[(int)file.length()];
        DataInputStream dataIs = new DataInputStream(new FileInputStream(file));
        dataIs.readFully(mByte);
        dataIs.close();
        return Base64.encodeToString(mByte, Base64.DEFAULT);
    }
    
    /**
     * Converts a Base64 string encoded file back to a regular file and stores
     * it in the Android external storage directory.
     * @param base64File The Base64 encoded string of the file.
     * @param fileName   The filename to save the file as.
     * @return File pointer where the file is saved.
     */
    public static File base64ToFile(String base64File, String fileName) {
        String directory = 
                Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName = directory + "/" + fileName;

        BufferedOutputStream bs = null;
        File file = new File(fileName);
        
        try {
           FileOutputStream fs = new FileOutputStream(file);
           bs = new BufferedOutputStream(fs);
           
           byte[] mByte = Base64.decode(base64File, Base64.DEFAULT);
           
           bs.write(mByte);
           bs.close();
           bs = null;

        } catch (Exception e) {
           e.printStackTrace();
        }
        return file;
    }
    
    /**
     * Based on ItemType give an extension to write files of given type.
     * @param type
     * @return
     */
    public static String getFileExtFromType(ItemType type) {
        switch (type) {
            case TEXT:
                return ".txt";
            case PHOTO:
                return ".jpg";
            case AUDIO:
                return ".3ga";
            case VIDEO:
                return ".3gp";
            default:
                return null;
        }
    }
    
    /**
     * Get drawable icon from ItemType.
     * @param type
     * @return
     */
    public static int getIconFromType(ItemType type) {
        switch (type) {
        case TEXT:
            return R.drawable.text_icon;
        case PHOTO:
            return R.drawable.photo_icon;
        case AUDIO:
            return R.drawable.audio_icon;
        case VIDEO:
            return R.drawable.video_icon;
        default:
            return 0;
        }
    } 
    
    /**
     * Get drawable icon from String format of item type.
     * @param type
     * @return
     */
    public static int getIconFromString (String type) {
        if (type.equals("Photo")) 
            return R.drawable.photo_icon;
        else if (type.equals("Audio")) 
            return R.drawable.audio_icon;
        else if (type.equals("Video")) 
            return R.drawable.video_icon;
        else if (type.equals("Task"))
            return R.drawable.notepad_icon;
        else 
            return R.drawable.text_icon;
    } 
    
    /**
     * Return class to preview a given ItemType.
     * @return  the class to preview.
     */
    public static Class<?> getPreviewClass(ItemType itemType) {
        switch (itemType) {
        case TEXT:
            return PreviewText.class;
        case PHOTO:
            return PreviewPhoto.class;
        case AUDIO:
            return PreviewAudio.class;
        case VIDEO:
            return PreviewVideo.class;
        default:
            return null;
        }
    }
}
