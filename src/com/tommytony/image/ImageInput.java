package com.tommytony.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageInput {

	public static BufferedImage loadImage(int width, int height, File file) throws IOException {
		BufferedImage img = ImageIO.read(file);
		return img;
	}
	
	public static RGBModel getRGBAt(int pixelX, int pixelY, BufferedImage img) {
		int pixelD = img.getRGB(pixelX, pixelY);
		int red = (pixelD >> 16) & 0xff;
		int green = (pixelD >> 8) & 0xff;
		int blue = pixelD & 0xff;
		return new RGBModel(red, green, blue);
	}
	
	/**
	 *Returns an array of integers in the format of {red, green, blue}. These numbers represent the 
	 *Red Green Blue  value of a pixel on the screen
	 *
	 *@param pixelX  The X pixel in the image 
	 *@param pixelY  The Y pixel in the image
	 *@param img  The image to which the pixels refer to
	 *@return an array in the format 
	 */
	
	public static int[] getRGBAtInArray(int pixelX, int pixelY, BufferedImage img) {
	    int pixelD = img.getRGB(pixelX, pixelY);
		int[] rgb = new int[3];
		rgb[0] = (pixelD >> 16) & 0xff;
		rgb[1] = (pixelD >> 8) & 0xff;
		rgb[2] = pixelD & 0xff;
		return rgb;
	}
	
	public static void setRGBAt(int pixelX, int pixelY, BufferedImage img, int red, int blue, int green) {
		int pixelD = (red << 16) | (green << 8) | blue;
		img.setRGB(pixelX, pixelY, pixelD);
	}
	
}
