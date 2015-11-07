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
|  Description:  This program . . .
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

public class MapTile {
	private Resource resource;
	private Terrain terrain;
	public MapTile() {
		resource = resource.NONE;
		terrain = terrain.PLAIN;
	}
	//  This will set rivers and mountains or whatever the hell we want
	public void setLand(Terrain toSet) {
		terrain = toSet;
	}
	public void setResource(Resource toSet) {
		//System.out.println(toSet);
		resource = toSet;
	}
	public boolean unPassable() {
		if(terrain.equals(terrain.PLAIN)) {
			//  or it could be a method call from resource
			if(resource==null){
				return false;
			}
		}
		return true;
	}
	public String toString() {
		//  RESOURCES
		if(resource.equals(resource.TREE))
			return "[T]";
		if(resource.equals(resource.FISH))
			return "[F]";
		if(resource.equals(resource.SALTY_FISH))
			return "[F]";
		
		//  TERRAIN
		if(terrain.equals(terrain.RIVER))
			return "[R]";
		if(terrain.equals(terrain.PLAIN))
			return "[ ]";
		return "Hodor";
	}
}
