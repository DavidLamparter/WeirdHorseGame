package model;

import java.util.Random;



public abstract class Resource {
	
	static Random num = new Random();
	
	private double quantity;
	private ResourceType type;
	private double max;
	
	public Resource(double q, ResourceType x) {
		quantity = q;
		type = x;
	}
	
	public void setMax(double number){}
	
	public double getMax(){
		return this.max;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public void addResource(double n){
		quantity += n;
	}
	
	public void subResource(double n){
		quantity -= n;
	}
	
	public ResourceType getResourceT(){
		return type;
	}
	public abstract void regen();
	public abstract String getName();
	public abstract String getFileName();
	// takes in the resource you want to change current one to.
	/*void changeResourceT(Resource newR){
		this.type = newR;
	} */
}
class Nothing extends Resource {
	public Nothing() {
		super(0, ResourceType.NONE);
	}
	@Override
	public void regen() {
		//  NOTHING DOESNT REGEN?
	}
	@Override
	public String getName() {
		return "Nothing is here";
	}
	@Override
	public String getFileName() {
		return "";
	}
}

class Tree extends Resource {
	
	public Tree() {
		super(num.nextInt(50)+75, ResourceType.TREE);
	}
	@Override
	public void regen() {
		if (this.getQuantity() == 0)
			this.addResource(10);		
		else if (this.getQuantity() > this.getMax())
			this.subResource(1);
		else
			this.addResource(.5);
	}
	@Override
	public String getName() {
		return "Tree";
	}
	@Override
	public String getFileName() {
		return "./Graphics/Trees/Tree_1.png";
	}
	
}

class Fish extends Resource {
	
	public Fish() {
		super(num.nextInt(20)+10, ResourceType.FISH);
	}
	@Override
	public void regen() {
		if (this.getQuantity() == 0)
			this.addResource(5);		
		else if (this.getQuantity() > this.getMax())
			this.subResource(3);
		else
			this.addResource(2.3);
	}
	@Override
	public String getName() {
		return "Fish";
	}
	@Override
	public String getFileName() {
		return "";
	}
	
}

class SaltyFish extends Resource {
	
	public SaltyFish() {
		super(num.nextInt(40)+10, ResourceType.SALTY_FISH);
	}
	@Override
	public void regen() {
		if (this.getQuantity() == 0)
			this.addResource(5);		
		else if (this.getQuantity() > this.getMax())
			this.subResource(1);
		else
			this.addResource(.1);
	}
	@Override
	public String getName() {
		return "Salty Fish";
	}
	@Override
	public String getFileName() {
		return "";
	}
	
}

class Stone extends Resource {
	
	public Stone() {
		super(num.nextInt(200)+200, ResourceType.STONE);
	}
	@Override
	public void regen() {
		this.addResource(0);
	}
	@Override
	public String getName() {
		return "Stone";
	}
	@Override
	public String getFileName() {
		return "";
	}
	
}

class BerryBush extends Resource {
	
	public BerryBush() {
		super(num.nextInt(15)+50, ResourceType.BERRY_BUSH);
	}
	@Override
	public void regen() {
		this.addResource(this.getMax());
	}
	@Override
	public String getName() {
		return "Berry Bush";
	}
	@Override
	public String getFileName() {
		return "";
	}
	
}



