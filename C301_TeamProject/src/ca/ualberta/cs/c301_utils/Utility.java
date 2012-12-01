package ca.ualberta.cs.c301_utils;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import android.os.Environment;
import android.util.Base64;

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

}
