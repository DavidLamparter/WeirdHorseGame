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
|  Description:  This program creates the individual map tile to be used by the map during game
|                generation. It is set to a specific terrain enum and resource.
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.Image;

public class MapTile {

	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// Assigns this tile as a resource (could be none)
	private Resource resource;
	
	// Assigns this tile as a terrain
	private Terrain terrain;
	
	/**************************************
	 *         MapTile Constructor        *
	 **************************************/
	
	public MapTile() {
		resource = new Nothing();
		terrain = terrain.PLAIN;
	}
	
	/**************************************
	 *   Getters for Instance Variables   *
	 **************************************/
	
	// Returns the terrain enum this tile is set to
	public Terrain getLand(){
		return terrain;
	}
	
	// Returns the resource this tile is set to
	public Resource getResource(){
		return resource;
	}
	
	/**************************************
	 *   Setters for Instance Variables   *
	 **************************************/
	
	// sets the tile to a terrain enum passed as a parameter
	public void setLand(Terrain toSet) {
		terrain = toSet;
	}
	
	// sets the tile to a resource passed as a parameter
	public void setResource(Resource toSet) {
		//System.out.println(toSet);
		resource = toSet;
	}
	
	/**************************************
	 *             unPassable             *
	 **************************************/
	
	// Determines if this tile counts as an obstacle for units trying to travel
	public boolean unPassable() {
		if(terrain.equals(terrain.PLAIN)||terrain.equals(terrain.BEACH)) {
			//  or it could be a method call from resource
			if(resource.getResourceT().equals(ResourceType.NONE)){
				return false;
			}
		}
		return true;
	}
	
	/**************************************
	 *              toString              *
	 **************************************/
	
	// ToString method for printing
	public String toString() {
		//  RESOURCES
		if(resource.getResourceT().equals(ResourceType.TREE))
			return "[T]";
		if(resource.getResourceT().equals(ResourceType.FISH))
			return "[F]";
		if(resource.getResourceT().equals(ResourceType.SALTY_FISH))
			return "[F]";
		
		//  TERRAIN
		if(terrain.equals(terrain.RIVER))
			return "[R]";
		if(terrain.equals(terrain.PLAIN))
			return "[ ]";
		return "Hodor";
	}

	//returns the ID
	public Image getSummerID() {
		// TODO Auto-generated method stub
		return resource.getID();
	}
	public Image getWinterID() {
		// TODO Auto-generated method stub
		return resource.getID();
	}
}
