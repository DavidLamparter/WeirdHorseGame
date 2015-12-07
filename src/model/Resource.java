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

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class Resource extends Observable implements Serializable {
	
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
	
	//staggers things a little
	public int Offset;
	
	//keeps them form going negative
	private boolean harvestable;
	
	/**************************************
	 *        Resource Constructor        *
	 **************************************/
	
	// Resource is constructed using a resource type and a double which represents
	// the amount it will be created with for harvest
	public Resource(double q, ResourceType x) {
		quantity = q;
		max = q;
		type = x;
		Offset = num.nextInt(16)-8;
		harvestable = true;
	}
	
	/**************************************
	 *   Getters for Instance Variables   *
	 **************************************/
	// If a resource is empty it's gone
	public void setEmpty() {
		type = ResourceType.NONE;
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
	
	public boolean getHarvestable(){
		
		return harvestable;
	}
	
	public void setHarvestable(boolean b){
		harvestable = b;
	}
	/**************************************
	 *   Setters / Adders / Subtractors   *
	 **************************************/
	
	// Adds to the quantity of this resource (regneration)
	public void addResource(double n){
		quantity += n;
		setChanged();
		notifyObservers();
	}
	
	// Subtracts from the quantity of this resource (harvesting)
	public void subResource(double n){
		quantity -= n;
		setChanged();
		notifyObservers();
	}
	
	//sets the resource quantity to input value
	public void regen(){
		System.out.println(quantity + "     " + type);
		quantity = max;
		setChanged();
		notifyObservers();
	}
	
	/**************************************
	 *          Abstract Methods          *
	 **************************************/
	
	// Returns the name of this resource
	public abstract String getName();
	
	// Returns the file name of this resource
	public abstract String getFileName();
	// takes in the resource you want to change current one to.
	/*void changeResourceT(Resource newR){
		this.type = newR;
	} */
	
	public abstract String getWinterFileName();
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

	@Override
	public String getWinterFileName() {
		// TODO Auto-generated method stub
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
	private String summerFileName;
	private String winterFileName;
	public Tree() {
		super(num.nextInt(20)+45, ResourceType.TREE);
		
		int filenum = num.nextInt(3)+1;
		summerFileName = "./Graphics/Trees/Tree Redux_" + filenum + ".png";
		winterFileName = "./Graphics/Trees/Tree Redux_" + filenum + "_Winter.png";
	}
	
	// Returns the name of this resource, in this case "Tree"
	@Override
	public String getName() {
		return "Tree";
	}
	
	// Returns the file name of this tree. Can vary between different images
	@Override
	public String getFileName() {
		return summerFileName;
	}

	@Override
	public String getWinterFileName() {
		// TODO Auto-generated method stub
		return winterFileName;
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
		super(num.nextInt(30)+50, ResourceType.FISH);
	}
	
	// Returns the name of this resource, in this case "Fish"
	@Override
	public String getName() {
		return "Fish";
	}
	
	// Returns the file name of this fish. Can vary between different images
	@Override
	public String getFileName() {
		return "./Graphics/Water/fish.png";
	}

	@Override
	public String getWinterFileName() {
		// TODO Auto-generated method stub
		return "./Graphics/Water/fishWinter.png";
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
		super(num.nextInt(485)+ 15, ResourceType.SALTY_FISH);
	}

	// Returns the name of this resource, in this case "Salty Fish"
	@Override
	public String getName() {
		return "Salty Fish";
	}
	
	// Returns the file name of this SaltyFish. Can vary between different images
	@Override
	public String getFileName() {
		return "./Graphics/Water/fishSalty.png";
	}
	@Override
	public void regen() {
	}
	
	@Override
	public String getWinterFileName() {
		// TODO Auto-generated method stub
		return "./Graphics/Water/fishSalty.png";
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

	@Override
	public String getWinterFileName() {
		// TODO Auto-generated method stub
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
	
	// Returns the name of this resource, in this case "Berry Bush"
	@Override
	public String getName() {
		return "Berry Bush";
	}
	
	// Returns the file name of this BerryBush. Can vary between different images
	@Override
	public String getFileName() {
		return "./Graphics/BerryBushes/BerryBush_1.png";
	}

	@Override
	public String getWinterFileName() {
		// TODO Auto-generated method stub
		return "./Graphics/BerryBushes/BerryBush_1.png";
	}
	
}



