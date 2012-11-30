package ca.ualberta.cs.c301_teamproject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;


public class MyLocalTaskInformation extends Activity{
	
	public static final String MYTASKS = "myTasks.sav";
	public static final String MYLIKES = "myLikes.sav";
	
	public void removeLikedTask(String id, Context c) {
	    
	    ArrayList<String> ids = getLikedTasks(c);
	    
	    ids.remove(id);
	    
	    try{
            FileOutputStream fOut = c.openFileOutput(MYLIKES, MODE_PRIVATE);
            ObjectOutputStream objOut = new ObjectOutputStream(fOut);
            objOut.writeObject(ids);
            objOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	    
	}

	public void saveLikedTasks(String id, Context c) {
	    
	    ArrayList<String> use = new ArrayList<String>();
	        
	    ArrayList<String> ids = getLikedTasks(c);
	  	    

	    //if we dont have a file saved already
	    if(ids.size()==0){
	        
	        use.add(id);
	        
	        try{
	            FileOutputStream fOut = c.openFileOutput(MYLIKES, MODE_PRIVATE);
	            ObjectOutputStream objOut = new ObjectOutputStream(fOut);
	            objOut.writeObject(use);
	            objOut.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	    
	    //we have data    
	    }else{
	        
	        ids.add(id);
	        
	        try{
                FileOutputStream fOut = c.openFileOutput(MYLIKES, MODE_PRIVATE);
                ObjectOutputStream objOut = new ObjectOutputStream(fOut);
                objOut.writeObject(ids);
                objOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
	    }	    	    
	}
	
	
	@SuppressWarnings("unchecked")
    public ArrayList<String> getLikedTasks(Context c) {
	    
	    ArrayList<String> myIDS = new ArrayList<String>();
	    
	    try{
    	    FileInputStream fIn = c.openFileInput(MYLIKES);
    	    ObjectInputStream objIn = new ObjectInputStream(fIn);

            myIDS = (ArrayList<String>) objIn.readObject();
    	    objIn.close();
    	    
	    } catch (FileNotFoundException e) {
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        
	    }
	    
	    
	    return myIDS;
	}
	
	
	public void saveTaskId(String id, Context c){
	        
        try{
			FileOutputStream os = c.openFileOutput(MYTASKS, Context.MODE_APPEND);
			os.write(new String(id).getBytes());
			os.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	    	
	}
	
	public String[] loadMyTaskIds(Context c){
		
		ArrayList<String> myIDS = new ArrayList<String>();
		
		try{
			FileInputStream is = c.openFileInput(MYTASKS);
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String line = in.readLine();
			while(line != null){
				myIDS.add(line);
				line = in.readLine();
			}
			is.close();
			in.close();
			
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		
		
		return myIDS.toArray(new String[myIDS.size()]);
	}

}
