/**
 * Task Force Application
 * See github for license and other information: 
 * github.com/tullyj/CMPUT301F12T03/
 * 
 * Task Force is created by: 
 * Colin Hunt, Edwin Chung, 
 * Kris Kushniruk, and Tully Johnson.
 */
package ca.ualberta.cs.c301_crowdclient;

import ca.ualberta.cs.c301_repository.TfTask;

/**
 * Entry to store in the webservice. Contains a task object as content.
 * @author Victor Guana - guana[at]ualberta.ca
 * @author Modified by Colin Hunt - colin[at]ualberta.ca
 * University of Alberta, Department of Computing Science
 */
public class CrowdSourcerEntry {
	
	private String summary;
	
	/** Content is a TfTask object because gson needs to have a concrete type */
	private TfTask content;
	
	/** This is the task id */
	private String id;
	
	private String description;
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public TfTask getContent() {
		return content;
	}

	public void setContent(TfTask content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString(){
		return summary;
	}
	
	public Boolean equals(CrowdSourcerEntry other) {
        return summary.equals(other.summary) 
            && id.equals(other.id) 
            && description.equals(other.description);
	}
}
