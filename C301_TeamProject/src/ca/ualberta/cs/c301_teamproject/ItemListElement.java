package ca.ualberta.cs.c301_teamproject;

/**
 * Class for elements of multi-element listview in ItemList.
 * Learned from: http://www.ezzylearning.com/tutorial.aspx?tid=1763429 
 * @author tullyj
 *
 */
public class ItemListElement {
	public int icon;
	public String title;
	public String sub;
	
	public ItemListElement(){
		super();
	}
	
	/**
	 * Contructor for ItemListElement, contains icon, title, and sub text.
	 * @param icon
	 * @param title
	 * @param sub
	 */
	public ItemListElement(int icon, String title, String sub){
		super();
		this.icon = icon;
		this.title = title;
		this.sub = sub;
	}
	
	public String getTitle(){
		return this.title;
	}
}
