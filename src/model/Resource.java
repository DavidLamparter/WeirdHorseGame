/*================================================================================================
|   Assignment:  FINAL PROJECT - Settlement Management
|      Authors:  David Lamparter (Lamparter@email.arizona.edu)
|                Kyle Grady (kgrady1@email.arizona.edu)
|    			 Kyle DeTar (kdeTar@email.arizona.edu)
|	  			 Brett Cohen (brett7@email.arizona.edu)
|                       
|       Course:  335
|   Instructor:  R. Mercer
|           PM:  Sean Stephens
|     Due Date:  12/9/15
|
|  Description:  This program creates the Resource abstract object that defines how each resource
|                will be named, how much of the resource is available, it's regeneration rate,
|                and what image it will be using on the map.
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.util.Random;

public abstract class Resource {
	
	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// Random number generator for use with constructor
	static Random num = new Random();
	
	// How much does this resource have available for the workers
	// to gather
	private double quantity;
	
	// Which resource is this
	private ResourceType type;
	
	// The maximum amount this resource can hold
	private double max;
	
	//The Graphic ID;
	private int ID;
	
	//staggers things a little
	public int Offset;
	
	/**************************************
	 *        Resource Constructor        *
	 **************************************/
	
	// Resource is constructed using a resource type and a double which represents
	// the amount it will be created with for harvest
	public Resource(double q, ResourceType x) {
		quantity = q;
		type = x;
		ID = num.nextInt(4);
		Offset = num.nextInt(16)-8;
	}
	
	/**************************************
	 *   Getters for Instance Variables   *
	 **************************************/
	//Returns ID
	public int getID() {
		return ID;
	}
	// Returns max possible value of this resource
	public double getMax(){
		return this.max;
	}
	
	// Returns current quantity of resource
	public double getQuantity() {
		return quantity;
	}
	
	// Returns the type of this resource (ex: Stone vs Wood)
	public ResourceType getResourceT(){
		return type;
	}
	
	/**************************************
	 *   Setters / Adders / Subtractors   *
	 **************************************/
	
	// Sets the max of this resource
	public void setMax(double number){}	
	
	// Adds to the quantity of this resource (regneration)
	public void addResource(double n){
		quantity += n;
	}
	
	// Subtracts from the quantity of this resource (harvesting)
	public void subResource(double n){
		quantity -= n;
	}
	
	/**************************************
	 *          Abstract Methods          *
	 **************************************/
	
	// Allows resources to regenerate so that the player
	// (if good enough) could play forever
	public abstract void regen();
	
	// Returns the name of this resource
	public abstract String getName();
	
	// Returns the file name of this resource
	public abstract String getFileName();
	// takes in the resource you want to change current one to.
	/*void changeResourceT(Resource newR){
		this.type = newR;
	} */
}


/******************************************************
*~~~~~~~~~~~~~~~~~~~ NOTHING CLASS ~~~~~~~~~~~~~~~~~~~*
******************************************************/


class Nothing extends Resource {
	
	/**************************************
	 *          Nothing Constructor       *
	 **************************************/
	
	// Resource is constructed with 0 quantity, and nothing resourcetype enum
	public Nothing() {
		super(0, ResourceType.NONE);
	}
	
	// This type of resource can't regenerate as it is nothing
	@Override
	public void regen() {}
	
	// Returns the name of this resource, in this case "Nothing is here"
	@Override
	public String getName() {
		return "Nothing is here";
	}
	
	// Returns the file name of this resource, in this case, none
	@Override
	public String getFileName() {
		return "";
	}
}


/******************************************************
*~~~~~~~~~~~~~~~~~~~~ TREE CLASS ~~~~~~~~~~~~~~~~~~~~~*
******************************************************/


class Tree extends Resource {
	
	/**************************************
	 *            Tree Constructor        *
	 **************************************/
	// Tree constructed with a randomly generated quantity
	public Tree() {
		super(num.nextInt(50)+75, ResourceType.TREE);
	}
	
	// Tree regenerates over time to allow more playtime
	@Override
	public void regen() {
		if (this.getQuantity() == 0)
			this.addResource(10);		
		else if (this.getQuantity() > this.getMax())
			this.subResource(1);
		else
			this.addResource(.5);
	}
	
	// Returns the name of this resource, in this case "Tree"
	@Override
	public String getName() {
		return "Tree";
	}
	
	// Returns the file name of this tree. Can vary between different images
	@Override
	public String getFileName() {
		return "./Graphics/Trees/Tree Redux_1.png";
	}
	
}


/******************************************************
*~~~~~~~~~~~~~~~~~~~~ FISH CLASS ~~~~~~~~~~~~~~~~~~~~~*
******************************************************/


class Fish extends Resource {
	
	/**************************************
	 *            Fish Constructor        *
	 **************************************/
	
	// Fish constructed with a randomly generated quantity
	public Fish() {
		super(num.nextInt(20)+10, ResourceType.FISH);
	}
	
	// Fish regenerates over time to allow more playtime
	@Override
	public void regen() {
		if (this.getQuantity() == 0)
			this.addResource(5);		
		else if (this.getQuantity() > this.getMax())
			this.subResource(3);
		else
			this.addResource(2.3);
	}
	
	// Returns the name of this resource, in this case "Fish"
	@Override
	public String getName() {
		return "Fish";
	}
	
	// Returns the file name of this fish. Can vary between different images
	@Override
	public String getFileName() {
		return "";
	}
	
}


/******************************************************
*~~~~~~~~~~~~~~~~~~~ SALTYFISH CLASS ~~~~~~~~~~~~~~~~~*
******************************************************/


class SaltyFish extends Resource {
	
	/**************************************
	 *         SaltyFish Constructor      *
	 **************************************/
	
	// SaltyFish constructed with a randomly generated quantity
	public SaltyFish() {
		super(num.nextInt(40)+10, ResourceType.SALTY_FISH);
	}
	
	// SaltyFish regenerates over time to allow more playtime
	@Override
	public void regen() {
		if (this.getQuantity() == 0)
			this.addResource(5);		
		else if (this.getQuantity() > this.getMax())
			this.subResource(1);
		else
			this.addResource(.1);
	}
	
	// Returns the name of this resource, in this case "Salty Fish"
	@Override
	public String getName() {
		return "Salty Fish";
	}
	
	// Returns the file name of this SaltyFish. Can vary between different images
	@Override
	public String getFileName() {
		return "";
	}
	
}


/******************************************************
*~~~~~~~~~~~~~~~~~~~~ STONE CLASS ~~~~~~~~~~~~~~~~~~~~*
******************************************************/


class Stone extends Resource {
	
	/**************************************
	 *           Stone Constructor        *
	 **************************************/
	
	// Stone constructed with a randomly generated quantity
	public Stone() {
		super(num.nextInt(200)+200, ResourceType.STONE);
	}
	
	// Stone does NOT regenerate over time (limited resource)
	@Override
	public void regen() {
		this.addResource(0);
	}
	
	// Returns the name of this resource, in this case "Stone"
	@Override
	public String getName() {
		return "Stone";
	}
	
	// Returns the file name of this Stone. Can vary between different images
	@Override
	public String getFileName() {
		return "./Graphics/Stone/Stone_1.png";
	}
	
}


/******************************************************
*~~~~~~~~~~~~~~~~~~~ BERRYBUSH CLASS ~~~~~~~~~~~~~~~~~*
******************************************************/


class BerryBush extends Resource {
	
	/**************************************
	 *        BerryBush Constructor       *
	 **************************************/
	
	// BerryBush constructed with a randomly generated quantity
	public BerryBush() {
		super(num.nextInt(15)+50, ResourceType.BERRY_BUSH);
	}
	
	// BerryBush regenerates over time to allow more playtime
	@Override
	public void regen() {
		this.addResource(this.getMax());
	}
	
	// Returns the name of this resource, in this case "Berry Bush"
	@Override
	public String getName() {
		return "Berry Bush";
	}
	
	// Returns the file name of this BerryBush. Can vary between different images
	@Override
	public String getFileName() {
		return "";
	}
	
}



