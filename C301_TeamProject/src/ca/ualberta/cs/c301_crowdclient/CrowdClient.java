package ca.ualberta.cs.c301_crowdclient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
 * @author Modified by Colin Hunt - colin[at]ualberta.ca
 * University of Alberta, Department of Computing Science
 */
public class CrowdClient {
	
	// Http Connector
	private HttpClient httpclient = new DefaultHttpClient();
	
	// JSON Utilities
	private Gson gson = new Gson();
	
	
	// POST Request
	HttpPost httpPost = new HttpPost("http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T03/");

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
        entity.consumeContent();
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
	    
	    if (responseEntry.getId() == null) {
	        throw new Exception("Error retreiving entry for entry ID: "
	                + idP + ". (Maybe the entry does not exist)");
	    }
	    
        entity.consumeContent();
        return responseEntry;
	    
	}
	   
	/**
	 * Consumes the POST/Insert operation of the service
	 * @return JSON representation of the entry created
	 * @throws Exception
	 */
	public CrowdSourcerEntry insertEntry(CrowdSourcerEntry entryP) throws Exception {
		
		//CrowdSourcerEntry newEntry = new CrowdSourcerEntry();
		List <BasicNameValuePair> nvps = new ArrayList <BasicNameValuePair>();
		nvps.add(new BasicNameValuePair("action", "post"));
		nvps.add(new BasicNameValuePair("summary", entryP.getSummary()));
		nvps.add(new BasicNameValuePair(
		        "content", gson.toJson(entryP.getContent()))
		);
		
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		HttpResponse response = httpclient.execute(httpPost);
	    
	    String status = response.getStatusLine().toString();
	    HttpEntity entity = response.getEntity();
	    
	    System.out.println(status);
	    
	    	    
	    CrowdSourcerEntry newEntry = null;
        if (entity != null) {
	        InputStream is = entity.getContent();
	        String jsonStringVersion = convertStreamToString(is);
	        Type entryType = CrowdSourcerEntry.class;     
	        newEntry = gson.fromJson(jsonStringVersion, entryType);
	    }
	    entity.consumeContent();
        return newEntry;
	}
	
	/** 
	 * Consumes the UPDATE operation of the service
	 * @param entry to update
	 * @throws Exception
	 */
	public void updateEntry(CrowdSourcerEntry entry) throws Exception {
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("action", "update"));
        nvps.add(new BasicNameValuePair("summary", entry.getSummary()));
        nvps.add(new BasicNameValuePair(
                "content", gson.toJson(entry.getContent()))
        );
        nvps.add(new BasicNameValuePair("id", entry.getId()));
//        System.out.println(entry.getSummary());
//        System.out.println(nvps.get(1).getValue());

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        System.out.println(convertStreamToString(httpPost.getEntity().getContent()));
        HttpResponse response = httpclient.execute(httpPost);

        String status = response.getStatusLine().toString();
        HttpEntity entity = response.getEntity();

        System.out.println(status);
        
        CrowdSourcerEntry updatedEntry = null;
        if (entity != null) {
            InputStream is = entity.getContent();
            String jsonStringVersion = convertStreamToString(is);
            Type entryType = CrowdSourcerEntry.class;     
            try {
                updatedEntry = gson.fromJson(jsonStringVersion, entryType);
            } catch (Exception e) {
                // Know there was a problem deserializing the entry so there
                // should be a CrowdSource error returned. Throw the error
                // to the calling method.
                Exception myException = new Exception(
                        "There was an error " 
                        + "getting the entry from JSON. Here is the "
                        + "httpPost entity content: " 
                        + convertStreamToString(httpPost.getEntity()
                                .getContent()) + "\n"
                        + "CrowdSource response:\n" + jsonStringVersion
                );
                myException.setStackTrace(e.getStackTrace());
                throw myException;
            }
        }
        entity.consumeContent();
	}
	
	/** 
     * Consumes the REMOVE operation of the service
     * @param entryId to update
     * @throws Exception
     */
    public void removeEntry(String entryId) throws Exception {
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("action", "remove"));
        nvps.add(new BasicNameValuePair("id", entryId));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse response = httpclient.execute(httpPost);

        String status = response.getStatusLine().toString();
        HttpEntity entity = response.getEntity();

        System.out.println(status);
        
        entity.consumeContent();
    }

	
	/**
	 * Gets the list of entries by using the GET operation of the service
	 * on each entry.
	 * @return List of full crowd sourcer entries.
	 * @throws Exception
	 */
	public List<CrowdSourcerEntry> getEntryList() throws Exception {
	    List<Map<String,String>> shallowEntryList = getShallowList();
	    List<CrowdSourcerEntry> entryList = new ArrayList<CrowdSourcerEntry>(); 
	    for (Map<String,String> shallowEntry : shallowEntryList) {
	        CrowdSourcerEntry entry = getEntry(shallowEntry.get("id"));
	        entryList.add(entry);
	    }
        return entryList;
    }

	/**
	 * Gets the list of entries using just the LIST operation of the service.
	 * The entries in the list do not contain the content field.
	 * @return A list of name/value pairs.
	 * @throws Exception
	 */
	public List<Map<String, String>> getShallowList() throws Exception {
        String jsonEntryList = listEntrys();
        Type listType = new TypeToken<List<Map<String,String>>>(){}.getType();
        List<Map<String, String>> shallowEntryList = 
                gson.fromJson(jsonEntryList, listType);
        return shallowEntryList;
	}
	

}
