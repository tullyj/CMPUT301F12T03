package ca.ualberta.cs.c301_crowdclient;

import android.graphics.Bitmap;

/**
 * Task Value Object (Mock)
 * @author Victor Guana - guana[at]ualberta.ca
 * University of Alberta, Department of Computing Science
 */
public class Content {

	private String A;
	private String B;
	private String C;
	private String D;
	private Bitmap image;
	
	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public String getB() {
		return B;
	}

	public void setB(String b) {
		B = b;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getD() {
		return D;
	}


	public void setD(String d) {
		D = d;
	}

	public String toString(){
		
		return A+", "+B+", "+C+", "+D;
	}

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
	
	
	
}
