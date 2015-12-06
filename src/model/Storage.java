package model;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Storage extends Buildable {
	private int capacity;
	ArrayList<ResourceType> theGoods = new ArrayList<>();
	
	public Storage(Point topLeftPoint, Point bottomRightPoint, int multiplier) {
		super(topLeftPoint, bottomRightPoint, false);
		capacity = this.getPoints().size()*25*multiplier;
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
	public int getCapacity() {
		return capacity;
	}
	public void removeResource(ResourceType toRemove) {
		int goodsSize = theGoods.size();
		for(int i = 0; i < goodsSize; i++) {
			if(toRemove == ResourceType.STONE)
			if(theGoods.get(i).equals(ResourceType.STONE)) {
				theGoods.remove(i);
				return;
			}
			if(toRemove == ResourceType.TREE)
			if(theGoods.get(i) == ResourceType.TREE) {
				theGoods.remove(i);
				return;
			}
			if(toRemove == ResourceType.BERRY_BUSH)
			if(theGoods.get(i).equals(ResourceType.BERRY_BUSH)) {
				theGoods.remove(i);
				return;
			}
			if(toRemove == ResourceType.SALTY_FISH)
			if(theGoods.get(i).equals(ResourceType.SALTY_FISH)) {
				theGoods.remove(i);
				return;
			}
			if(toRemove == ResourceType.FISH)
			if(theGoods.get(i).equals(ResourceType.FISH)) {
				theGoods.remove(i);
				return;
			}
			if(toRemove == ResourceType.POISION_BUSH)
			if(theGoods.get(i).equals(ResourceType.POISION_BUSH)) {
				theGoods.remove(i);
				return;
			}
		}
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
				continue;
			}
			if(theGoods.get(i).equals(ResourceType.TREE)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
				continue;
			}
			if(theGoods.get(i).equals(ResourceType.BERRY_BUSH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
				continue;
			}
			if(theGoods.get(i).equals(ResourceType.SALTY_FISH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
				continue;
			}
			if(theGoods.get(i).equals(ResourceType.FISH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
				continue;
			}
			if(theGoods.get(i).equals(ResourceType.POISION_BUSH)) {
				toGit.add(theGoods.get(i));
				toGit.remove(i);
				i--;
				continue;
			}
		}
		return toGit;
	}
	
	public int getQuantity(){
		return theGoods.size();
	}
	
	public boolean isEmpty() {
		return theGoods.isEmpty();
	}
	
	public boolean isFull() {
		return capacity >= theGoods.size();
	}
	public void addResource(ResourceType toAdd) {
		theGoods.add(toAdd);
	}
	
	public int getFoodCount(){
		int fCount=0;
		for(int i=0; i < theGoods.size(); i++){
			//  a wild null pointer has appeared
			if(theGoods.get(i)!=null) {
				if( (theGoods.get(i).equals(ResourceType.BERRY_BUSH)) ||
					(theGoods.get(i).equals(ResourceType.SALTY_FISH)) ||
					(theGoods.get(i).equals(ResourceType.FISH)) )
						fCount++;
				if(theGoods.get(i).equals(ResourceType.POISION_BUSH))
					fCount-=5;
			}
		}
		return fCount;
	}
	
	public int getStoneCount(){
		int sCount=0;
		for(int i=0; i < theGoods.size(); i++){
			//  a wild null pointer has appeared
			if(theGoods.get(i)!=null)
				if(theGoods.get(i).equals(ResourceType.STONE))
					sCount++;
		}
		return sCount;
	}
	
	public int getWoodCount(){
		int wCount=0;
		for(int i=0; i < theGoods.size(); i++){
			//  a wild null pointer has appeared
			if(theGoods.get(i)!=null)
				if(theGoods.get(i).equals(ResourceType.TREE))
					wCount++;
		}
		return wCount;
	}
	
	
	
	public abstract String getName();
	public abstract String getImageFile();
	public boolean hasResource(ResourceType type) {	
		for(int i = 0; i < theGoods.size(); i++) {
				if(type == ResourceType.STONE)
				if(theGoods.get(i) == ResourceType.STONE) {
					return true;
				}
				if(type == ResourceType.TREE)
				if(theGoods.get(i) == ResourceType.TREE) {
					return true;
				}
				if(type == ResourceType.BERRY_BUSH)
				if(theGoods.get(i) == ResourceType.BERRY_BUSH) {
					return true;
				}
				if(type == ResourceType.SALTY_FISH)
				if(theGoods.get(i) == ResourceType.SALTY_FISH) {
					return true;
				}
				if(type == ResourceType.FISH)
				if(theGoods.get(i) == ResourceType.FISH) {
					return true;
				}
				if(type == ResourceType.POISION_BUSH)
				if(theGoods.get(i).equals(ResourceType.POISION_BUSH)) {
					return true;
				}
			}
		return false;
	}
}

//TOWNHALL
class TownHall extends Storage {
	public TownHall(Point topPoint) {
		super(topPoint, new Point(topPoint.x+1, topPoint.y+2), 2);
	}

	@Override
	public String getImageFile() {
		return "./Graphics/Buildings/townHall.png";
	}
	
	public int getQuantity(){
		return theGoods.size();
	}

	@Override
	public String getName() {
		return "TownHall";
	}
}

// STOREHOUSE
class Storehouse extends Storage {
	
	public Storehouse(Point topPoint) {
		super(topPoint, new Point(topPoint.x+1, topPoint.y+2), 1);
	}

	@Override
	public String getImageFile() {
		return "./Graphics/Buildings/storage.png";
	}

	@Override
	public String getName() {
		return "StoreHouse";
	}
}
