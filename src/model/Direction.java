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
|  Description:  This program creates the Direction enum that represents which direction the workers
|				 will move across the map.
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.io.Serializable;
import java.util.Random;

// Defines directions for workers to move
public enum Direction implements Serializable {
	NORTH, SOUTH, EAST, WEST;
	
	// This method allows for a quick 180 in movement
	public static Direction invert(Direction dir) {
		if(dir == NORTH)
			return SOUTH;
		if(dir == SOUTH)
			return NORTH;
		if(dir == EAST)
			return WEST;
		return EAST;
	}
	
	// This method will rotate a worker 90 degrees left
	public static Direction rotateLeft(Direction dir) {
		if(dir == NORTH)
			return WEST;
		if(dir == WEST)
			return SOUTH;
		if(dir == SOUTH)
			return EAST;
		return NORTH;
	}
	
	// This method will rotate a worker 90 degrees right
	public static Direction rotateRight(Direction dir) {
		if(dir == NORTH)
			return EAST;
		if(dir == EAST)
			return SOUTH;
		if(dir == SOUTH)
			return WEST;
		return NORTH;
	}
	
	// This method selects a random direction for a spawned worker to face
	public static Direction getRandom(long Seed) {
		Random gen = new Random(Seed);
		double num = gen.nextDouble();
		if(num < .25)
			return NORTH;
		else if(num < .50)
			return WEST;
		else if(num < .75)
			return SOUTH;
		return EAST;
	}
}
