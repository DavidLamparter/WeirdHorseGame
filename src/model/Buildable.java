package model;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Buildable {
	private int buildCost;
	private int buildCounter;
	private boolean passable;
	private boolean built = false;
	private ArrayList<Point> points = new ArrayList<>();
	
	//  the building takes 2 points and then makes a square
	public Buildable(Point topLeftPoint, Point bottomRightPoint, boolean passable) {
		for(int i = topLeftPoint.x; i <= bottomRightPoint.x; i++)
			for(int j = topLeftPoint.y; j <= bottomRightPoint.y; j++) {
				points.add(new Point(i,j));
				buildCost+=20;  //  4, 20
				//  every square cost 20 build
			}
		this.passable = passable;
	}
	
	//  gets all the points of the building to add it to the map
	public ArrayList<Point> getPoints() {
		return points;
	}
	public boolean isPassable() {
		return passable;
	}
	
	//  increments the counter by 1 and sees if it needs to be built
	public void build(){
		if(built)
			return;
		buildCounter++;
		if(buildCounter >= buildCost)
			built = true;
	}
	
	//  returns if the building has been built or not
	public boolean isBuilt() {
		return built;
	}
	
	public abstract Image getImage();
	
	//  abstract whateverTheHellItReturns whateverTheHellItDoes(Object whateverTheHellItTakes);
}
class House extends Buildable {

	public House(Point topLeftPoint, Point bottomRightPoint) {
		super(topLeftPoint, bottomRightPoint, false);
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
class Bridge extends Buildable {

	public Bridge(Point topPoint, Point bottomPoint) {
		super(topPoint, bottomPoint, true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}

