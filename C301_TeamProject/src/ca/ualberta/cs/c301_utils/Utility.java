package ca.ualberta.cs.c301_utils;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import android.os.Environment;
import android.util.Base64;

public class Utility {

    /* http://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-in-java */
    public static String fileToBase64(File file) throws Exception {
        byte[] mByte = new byte[(int)file.length()];
        DataInputStream dataIs = new DataInputStream(new FileInputStream(file));
        dataIs.readFully(mByte);
        dataIs.close();
        return Base64.encodeToString(mByte, Base64.DEFAULT);
    }
    
    public static File base64ToFile(String input, String fileName) {
        String directory = 
                Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName = directory + fileName;

        BufferedOutputStream bs = null;
        File file = new File(fileName);
        
        try {
           FileOutputStream fs = new FileOutputStream(file);
           bs = new BufferedOutputStream(fs);
           
           byte[] mByte = Base64.decode(input, Base64.DEFAULT);
           
           bs.write(mByte);
           bs.close();
           bs = null;

        } catch (Exception e) {
           e.printStackTrace();
        }
        return file;
    }

}
