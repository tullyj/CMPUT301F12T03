package ca.ualberta.cs.c301_crowdclient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.google.gson.Gson;

/**
 * CrowdSource Service Client (Teaser)
 * @author Victor Guana - guana[at]ualberta.ca
 * University of Alberta, Department of Computing Science
 */
public class CrowdClient {
	
	// Http Connector
	private HttpClient httpclient = new DefaultHttpClient();
	
	// JSON Utilities
	private Gson gson = new Gson();
	
	
	// POST Request
	HttpPost httpPost = new HttpPost("http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T03/");
	
	/**
	 * Sends messages to the crowd service and retrieves its responses
	 */
	public void testServiceMethods(){

		// Example Simple Task
		Task task = initializeTask();
		
		try {
			Task newT = this.insertTask(task);
			System.out.println("Inserted Task -> " + newT.getId());
			
			Task newTClone = this.getTask(newT.getId());
			System.out.println("Double Checking by Listing -> " + newTClone.getId());
			
			String lot= this.listTasks();
			System.out.println("List of Tasks in the CrowdSourcer -> " + lot);

		} 
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
		    //httpPost.releaseConnection(); //causes method not found error
		}
	}
	
	/**
	 * Initializes a simple mock task
	 * @return
	 */
	private Task initializeTask() {
		
		Content c = new Content();
		c.setA("a ahoy");
		c.setB("b ahoy");
		c.setC("c ahoy");
		c.setD("c ahoy");
		
		Task t = new Task();
		t.setDescription("Long description of the task...");
		t.setSummary("Short summary of the task...");
		t.setContent(c);	
		return t;
	}

	/*
	 * To convert the InputStream to String we use the BufferedReader.readLine()
	 * method. We iterate until the BufferedReader return null which means
	 * there's no more data to read. Each line will appended to a StringBuilder
	 * and returned as String.
	 * (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
	 */
	private  String convertStreamToString(InputStream is) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				is.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * Consumes the LIST operation of the service
	 * @return JSON representation of the task list
	 * @throws Exception
	 */
	public String listTasks() throws Exception{
		
		String jsonStringVersion = new String();
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("action", "list"));
		
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = httpclient.execute(httpPost);
	    
	    String status = response.getStatusLine().toString();
	    HttpEntity entity = response.getEntity();
	    
	    System.out.println(status);
	    
	    if (entity != null) {
	        InputStream is = entity.getContent();
	        jsonStringVersion = convertStreamToString(is);
	    }
	    
	    // and ensure it is fully consumed
	    //EntityUtils.consume(entity); //causes method not found error
	    return jsonStringVersion;
	}
	
	/**
	 * Consumes the GET operation of the service
	 * @return Task object given the id idP
	 * @throws Exception
	 */
	public Task getTask(String idP) throws Exception{
		
		Task responseTask = new Task();
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("action", "get"));
		nvps.add(new BasicNameValuePair("id", idP));
		
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = httpclient.execute(httpPost);
	    
	    String status = response.getStatusLine().toString();
	    HttpEntity entity = response.getEntity();
	    
	    System.out.println(status);
	    
	    if (entity != null) {
	        InputStream is = entity.getContent();
	        String jsonStringVersion = convertStreamToString(is);
	        Type taskType = Task.class;     
	        responseTask = gson.fromJson(jsonStringVersion, taskType);
	    }
	    //EntityUtils.consume(entity); //causes method not found error
        return responseTask;
	    
	}
	   
	/**
	 * Consumes the POST/Insert operation of the service
	 * @return JSON representation of the task created
	 * @throws Exception
	 */
	public Task insertTask(Task taskP) throws Exception{
		
		Task newTask = new Task();
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("action", "post"));
		nvps.add(new BasicNameValuePair("summary", taskP.getSummary()));
		nvps.add(new BasicNameValuePair("content", gson.toJson(taskP.getContent())));
		
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = httpclient.execute(httpPost);
	    
	    String status = response.getStatusLine().toString();
	    HttpEntity entity = response.getEntity();
	    
	    System.out.println(status);
	    
	    if (entity != null) {
	        InputStream is = entity.getContent();
	        String jsonStringVersion = convertStreamToString(is);
	        Type taskType = Task.class;     
	        newTask = gson.fromJson(jsonStringVersion, taskType);
	    }
	    //EntityUtils.consume(entity); //causes method not found error
        return newTask;
	}
	

}
