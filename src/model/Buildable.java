package model;

import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public abstract class Buildable extends Observable implements Serializable {
//	private int buildCost;
//	private int buildCounter;
	private boolean passable;
	private boolean built = true;
	private ArrayList<Point> points = new ArrayList<>();
	
	//  the building takes 2 points and then makes a square
	public Buildable(Point topLeftPoint, Point bottomRightPoint, boolean passable) {
		for(int i = topLeftPoint.x; i <= bottomRightPoint.x; i++)
			for(int j = topLeftPoint.y; j <= bottomRightPoint.y; j++) {
				points.add(new Point(i,j));
			//	buildCost+=20;  //  4, 20
				//  every square cost 20 build
			}
		this.passable = passable;
	}
	
	//  gets all the points of the building to add it to the map
	public ArrayList<Point> getPoints() {
		return points;
	}
	//  gets the closest point relative to another point
	public Point getClosestPoint(Point p) {
		Point closest = null;
		double distance = Double.MAX_VALUE;
		for(int i = 0; i < points.size(); i++) {
			double potentialMin = p.distance(points.get(i));
			if(potentialMin< distance) {
				distance = potentialMin;
				closest = points.get(i);
			}
		}
		return closest;
	}
	public boolean isPassable() {
		return passable;
	}
	
	//  increments the counter by 1 and sees if it needs to be built
	/*public void build(){
		if(built)
			return;
		buildCounter++;
		if(buildCounter >= buildCost)
			built = true;
	}*/
	
	//  returns if the building has been built or not
	public boolean isBuilt() {
		return built;
	}
	
	public abstract String getImageFile();
	
	//  abstract whateverTheHellItReturns whateverTheHellItDoes(Object whateverTheHellItTakes);
	
	
}

// HOUSE
class House extends Buildable {

	public House(Point topPoint) {
		super(topPoint, new Point(topPoint.x+1, topPoint.y+1), false);
	}

	@Override
	public String getImageFile() {
		// TODO Auto-generated method stub
		return "./Graphics/Buildings/house.png";
	}
}

// VERTICAL BRIDGE
class VerticalBridge extends Buildable {

	public VerticalBridge(Point topPoint) {
		super(topPoint, new Point(topPoint.x, topPoint.y), true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getImageFile() {
		// TODO Auto-generated method stub
		return "./Graphics/Buildings/bridge.png";
	}
}
//  Could make bridges 1 by 1 and get rid of one of these classes
// HORIZONTAL BRIDGE
class HorizontalBridge extends Buildable {

	public HorizontalBridge(Point topPoint) {
		super(topPoint, new Point(topPoint.x, topPoint.y), true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getImageFile() {
		// TODO Auto-generated method stub
		return "./Graphics/Buildings/bridge.png";
	}
}

