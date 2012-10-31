package ca.ualberta.cs.c301_crowdclient;

/**
 * Task Value Object
 * @author Victor Guana - guana[at]ualberta.ca
 * University of Alberta, Department of Computing Science
 */
public class Task {
	
	private String summary;
	private Content content;
	private String id;
	private String description;
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
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
		return summary+", "+content+", "+id+", "+description+", "+content.toString();
	}
}
