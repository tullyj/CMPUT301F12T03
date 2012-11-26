package ca.ualberta.cs.c301_teamproject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;


public class MyLocalTasks extends Activity{
	
	private static final String MYTASKS = "myTasks.sav";

	public void saveTaskId(String text, Context c){

		try{
			FileOutputStream os = c.openFileOutput(MYTASKS, Context.MODE_APPEND);
			os.write(new String(text).getBytes());
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
