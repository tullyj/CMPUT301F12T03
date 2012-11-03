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
import com.google.gson.reflect.TypeToken;

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
//	public void testServiceMethods(){
//
//		// Example Simple Entry
//		CrowdSourcerEntry entry = initializeEntry();
//		
//		try {
//			CrowdSourcerEntry newT = this.insertEntry(entry);
//			System.out.println("Inserted Entry -> " + newT.getId());
//			
//			CrowdSourcerEntry newTClone = this.getEntry(newT.getId());
//			System.out.println("Double Checking by Listing -> " + newTClone.getId());
//			
//			String lot= this.listEntrys();
//			System.out.println("List of Entrys in the CrowdSourcer -> " + lot);
//
//		} 
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		finally {
//		    //httpPost.releaseConnection(); //causes method not found error
//		}
//	}
	
	/**
	 * Initializes a simple mock entry
	 * @return
	 */
	private CrowdSourcerEntry initializeEntry() {
		
		Content c = new Content();
		c.setA("a ahoy");
		c.setB("b ahoy");
		c.setC("c ahoy");
		c.setD("c ahoy");
		
		CrowdSourcerEntry t = new CrowdSourcerEntry();
		t.setDescription("Long description of the entry...");
		t.setSummary("Short summary of the entry...");
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
	 * @return JSON representation of the entry list
	 * @throws Exception
	 */
	public String listEntrys() throws Exception{
		
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
	 * @return Entry object given the id idP
	 * @throws Exception
	 */
	public CrowdSourcerEntry getEntry(String idP) throws Exception{
		
		CrowdSourcerEntry responseEntry = new CrowdSourcerEntry();
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
	        Type entryType = CrowdSourcerEntry.class;     
	        responseEntry = gson.fromJson(jsonStringVersion, entryType);
	    }
	    //EntityUtils.consume(entity); //causes method not found error
        return responseEntry;
	    
	}
	   
	/**
	 * Consumes the POST/Insert operation of the service
	 * @return JSON representation of the entry created
	 * @throws Exception
	 */
	public void insertEntry(CrowdSourcerEntry entryP) throws Exception {
		
		//CrowdSourcerEntry newEntry = new CrowdSourcerEntry();
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("action", "post"));
		nvps.add(new BasicNameValuePair("summary", entryP.getSummary()));
		nvps.add(new BasicNameValuePair("content", gson.toJson(entryP.getContent())));
		
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = httpclient.execute(httpPost);
	    
	    String status = response.getStatusLine().toString();
	    HttpEntity entity = response.getEntity();
	    
	    System.out.println(status);
	    
//	    if (entity != null) {
//	        InputStream is = entity.getContent();
//	        String jsonStringVersion = convertStreamToString(is);
//	        Type entryType = CrowdSourcerEntry.class;     
//	        newEntry = gson.fromJson(jsonStringVersion, entryType);
//	    }
	    //EntityUtils.consume(entity); //causes method not found error
        //return newEntry;
	}

	public void updateEntry(CrowdSourcerEntry entry) throws Exception {
	    // TODO Auto-generated method stub
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("action", "update"));
        nvps.add(new BasicNameValuePair("summary", entry.getSummary()));
        nvps.add(new BasicNameValuePair("content", gson.toJson(entry.getContent())));
        nvps.add(new BasicNameValuePair("id", entry.getId()));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse response = httpclient.execute(httpPost);

        String status = response.getStatusLine().toString();
        HttpEntity entity = response.getEntity();

        System.out.println(status);
	}

	public List<CrowdSourcerEntry> getEntryList() throws Exception {
        // TODO Auto-generated method stub
	    String jsonEntryList = listEntrys();
	    
	    Type listType = new TypeToken<List<CrowdSourcerEntry>>(){}.getType();
	    List<CrowdSourcerEntry> entryList = gson.fromJson(jsonEntryList, listType);
        return entryList;
    }

	

}
