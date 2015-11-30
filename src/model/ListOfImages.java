package model;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ListOfImages {
	private class ImageAndName implements Comparable {
		
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
		public Image getImage() {
			return image;
		}
	}
	
	//  SUMMER
	public ArrayList<ImageAndName> summerResources = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> summerBuildings = new ArrayList<ImageAndName>();
	
	//  WINTER
	public ArrayList<ImageAndName> winterResources = new ArrayList<ImageAndName>();
	public ArrayList<ImageAndName> winterBuildings = new ArrayList<ImageAndName>();
		
	public ListOfImages() {
		//  LETS LOAD SOME FUCKING IMAGES!!!
		for(int i = 1; i <= 4; i++) {
			//  TREES!
			try {
				Image temp = ImageIO.read(new File("./Graphics/Trees/Tree Redux_" + i + ".png"));
				summerResources.add(new ImageAndName("./Graphics/Trees/Tree Redux_" + i + ".png" , temp));
				temp = ImageIO.read(new File("./Graphics/Trees/Tree Redux_" + i + "_Winter.png"));
				winterResources.add(new ImageAndName("./Graphics/Trees/Tree Redux_" + i + "_Winter.png" , temp));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		//  FISH LETS GO!!!
		try {
			Image temp = ImageIO.read(new File("./Graphics/Fish/Fish.png"));
			summerResources.add(new ImageAndName("./Graphics/Fish/Fish.png", temp));
			temp = ImageIO.read(new File("./Graphics/Fish/Fish_Winter_Fish.png"));
			winterResources.add(new ImageAndName("./Graphics/Fish/Winter_Fish" , temp));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//  BERRY BUSHES are just OK
		try {
			Image temp = ImageIO.read(new File("./Graphics/BerryBushes/BerryBush_1.png"));
			summerResources.add(new ImageAndName("./Graphics/BerryBushes/BerryBush_1.png", temp));
			temp = ImageIO.read(new File("./Graphics/BerryBushes/BerryBush_1_Winter.png"));
			winterResources.add(new ImageAndName("./Graphics/BerryBushes/BerryBush_1_Winter.png" , temp));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		//  Stoned are stones
		try {
			Image temp = ImageIO.read(new File("./Graphics/Stone/Stone_1.png"));
			summerResources.add(new ImageAndName("./Graphics/Stone/Stone_1.png", temp));
			temp = ImageIO.read(new File("./Graphics/Stone/Stone_1_Winter.png"));
			winterResources.add(new ImageAndName("./Graphics/Stone/Stone_1_Winter.png" , temp));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//  LETS GET SUM BUILDINGS
		try {
			Image temp = ImageIO.read(new File("./Graphics/Buildings/bridge.png"));
			summerBuildings.add(new ImageAndName("./Graphics/Buildings/bridge.png", temp));
			temp = ImageIO.read(new File("./Graphics/Buildings/house.png"));
			summerBuildings.add(new ImageAndName("./Graphics/Buildings/house.png", temp));
			temp = ImageIO.read(new File("./Graphics/Buildings/storage.png"));
			summerBuildings.add(new ImageAndName("./Graphics/Buildings/storage.png", temp));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Image getResource(String fileName, boolean isWinter) {
		for(int i = 0; i < summerResources.size(); i++) {
			if(summerResources.get(i).equals(fileName))
				return summerResources.get(i).getImage();
		}
		for(int i = 0; i < winterResources.size(); i++) {
			if(winterResources.get(i).equals(fileName))
				return winterResources.get(i).getImage();
		}
		
		try {
			throw new FileNotFoundException();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Image getBuilding(String fileName, boolean isWinter) {
		for(int i = 0; i < summerBuildings.size(); i++) {
			if(summerBuildings.get(i).equals(fileName))
				return summerBuildings.get(i).getImage();
		}
		for(int i = 0; i < winterBuildings.size(); i++) {
			if(winterBuildings.get(i).equals(fileName))
				return winterBuildings.get(i).getImage();
		}
		
		try {
			throw new FileNotFoundException();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
