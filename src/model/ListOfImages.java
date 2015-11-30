package model;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ListOfImages {
	private class ImageAndName implements Comparable{
		
		private Image image;
		private String name;
		
		private ImageAndName(String name, Image image) {
			this.image = image;
			this.name = name;
		}
		
		public boolean equals(String arg0) {
			return name.compareTo(arg0) == 0;
		}
		
		@Override
		public int compareTo(Object arg0) {
			return name.compareTo((String)arg0);
		}
	}
	
	public ArrayList<ImageAndName> summerResources = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> summerBuildings = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> summerMapTiles = new ArrayList<ImageAndName>();
	
	public ArrayList<ImageAndName> winterResources = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> winterBuildings = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> winterMapTiles = new ArrayList<ImageAndName>();
		
	public ListOfImages() {
		//  LETS LOAD SOME FUCKING IMAGES!!!
		for(int i = 1; i <= 4; i++)
		try {
		Image temp = ImageIO.read(new File("./Graphics/Trees/Tree Redux_" + i + ".png"));
		summerResources.add(new ImageAndName("./Graphics/Trees/Tree Redux_" + i + ".png" , temp));
		winterResources.add(new ImageAndName("./Graphics/Trees/Tree Redux_" + i + ".png" , temp));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
