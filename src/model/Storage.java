package model;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Storage extends Buildable {
	private int capacity;
	ArrayList<ResourceType> theGoods = new ArrayList<>();
	
	public Storage(Point topLeftPoint, Point bottomRightPoint) {
		super(topLeftPoint, bottomRightPoint, false);
		capacity = this.getPoints().size()*42;
	}
	//  this will return only food objects as the other will return anything you tell it
	public ArrayList<ResourceType> getFood(int quanity) {
		//  I made a funny joke
		ArrayList<ResourceType> toGit = new ArrayList<>();
		int goodsSize = theGoods.size();
		for(int i = 0; i < goodsSize; i++) {
			if(toGit.size() >= quanity)
				break;
			if(theGoods.get(i).equals(ResourceType.BERRY_BUSH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
			if(theGoods.get(i).equals(ResourceType.SALTY_FISH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
			if(theGoods.get(i).equals(ResourceType.FISH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
			if(theGoods.get(i).equals(ResourceType.POISION_BUSH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
		}
		return toGit;
	}
	public ArrayList<ResourceType> getResource(ResourceType type, int quanity) {
		//  I made a funny joke
		ArrayList<ResourceType> toGit = new ArrayList<>();
		int goodsSize = theGoods.size();
		for(int i = 0; i < goodsSize; i++) {
			if(toGit.size() >= quanity)
				break;
			if(theGoods.get(i).equals(ResourceType.STONE)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
			if(theGoods.get(i).equals(ResourceType.TREE)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
			if(theGoods.get(i).equals(ResourceType.BERRY_BUSH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
			if(theGoods.get(i).equals(ResourceType.SALTY_FISH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
			if(theGoods.get(i).equals(ResourceType.FISH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
			if(theGoods.get(i).equals(ResourceType.POISION_BUSH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
			}
		}
		return toGit;
	}
	public boolean isFull() {
		return capacity >= theGoods.size();
	}
	public void addResource(ResourceType toAdd) {
		theGoods.add(toAdd);
	}
	public abstract Image getImage();
}
class TownHall extends Storage {

	public TownHall(Point topLeftPoint, Point bottomRightPoint) {
		super(topLeftPoint, bottomRightPoint);
	}

	@Override
	public Image getImage() {
		return null;
	}
}
class Storehouse extends Storage {

	public Storehouse(Point topLeftPoint, Point bottomRightPoint) {
		super(topLeftPoint, bottomRightPoint);
	}

	@Override
	public Image getImage() {
		return null;
	}
}
