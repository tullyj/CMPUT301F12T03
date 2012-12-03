/**
 * Task Force Application
 * See github for license and other information: 
 * github.com/tullyj/CMPUT301F12T03/
 * 
 * Task Force is created by: 
 * Colin Hunt, Edwin Chung, 
 * Kris Kushniruk, and Tully Johnson.
 */
package ca.ualberta.cs.c301_teamproject;

/**
 * Class for elements of multi-element listview in ItemList.
 * Learned from: http://www.ezzylearning.com/tutorial.aspx?tid=1763429 
 * @author tullyj
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
