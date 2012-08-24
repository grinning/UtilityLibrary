package com.tommytony.image;

public class RGBModel {

	private final int red;
	private final int green;
	private final int blue;
	
	public RGBModel(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public final int getRed() {
	    return this.red;	
	}
	
	public final int getGreen() {
		return this.green;
	}
	
	public final int getBlue() {
		return this.blue;
	}
}
