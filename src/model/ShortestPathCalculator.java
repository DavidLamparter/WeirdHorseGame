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
|  Description:  This program will calculate the shortest path for a unit to travel to a target
|                location. This algorithm uses recursion to calculate a path which will dodge
|                obstacles and navigate to the target location. The map must be a perfect square
|                for this algorithm to function correctly. 
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;
/*
 * If you run it as is there will be 3 things that appear on the S.O.P
 * Min, Max, and average for 6000 runs of random mazes going up down and diagonal for 1000 times each
 * There could be room for improvement but the technology isn't there yet
 */
import java.awt.Point;
import java.util.ArrayList;

public class ShortestPathCalculator{
	
	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// A boolean map that will be passed in place of the real map to lighten memory load
	private boolean[][] map;
	private boolean[][] savedMap;
	
	// boolean that when set to true signifies that algorithm has completed
	private boolean done = false;
	
	// ArrayList that holds the directions from the beginning to the end (the path)
	private ArrayList<Direction> thePath = new ArrayList<>();
	
	/**************************************
	 *             Constructors           *
	 **************************************/
	
	//  A NEW CALULATOR MUST BE MADE WHEN THE MAP CHANGES!!!
	
	// Finds the shortest path using the actual map
	public ShortestPathCalculator(MapTile[][] board) {
		this.map = new boolean[board.length][board.length];
		this.savedMap = new boolean[board.length][board.length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				this.map[i][j] = board[i][j].unPassable();
				this.savedMap[i][j] = board[i][j].unPassable();
			}
		}
	}
	
	// Finds the shortest path using a boolean 2D array
	public ShortestPathCalculator(boolean[][] board) {
		this.savedMap = new boolean[board.length][board.length];
		this.map = new boolean[board.length][board.length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				this.map[i][j] = board[i][j];
				this.savedMap[i][j] = board[i][j];
			}
		}
	}
	
	/**************************************
	 *              updateMap             *
	 **************************************/
	
	// Updates the map with the newest information
	public void updateMap(boolean[][] newMap) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				this.map[i][j] = newMap[i][j];
				this.savedMap[i][j] = newMap[i][j];
			}
		}
	}
	
	/**************************************
	 *             optimize           *
	 **************************************/
	
	// Optimizes the current path to decrease travel time for units
	private void optimize() {

		for(int i = 0; i < thePath.size()-2;i++) {
			if(thePath.get(i).equals(thePath.get(i+2).invert(thePath.get(i+2)))) {//  My life... 
				thePath.remove(i+2);
				thePath.remove(i);
				i = -1;
			}
			else if(thePath.get(i).equals(Direction.invert(thePath.get(i+1)))) {
				thePath.remove(i+1);
				thePath.remove(i);
				i = -1;
			}
		}
	}
	//  For some reason unbeknown to me, these numbers will cause you to sometimes walk over rivers if they l/t is > 5
	private int lowerBound = 10;
	private int tightness = 3;
	private boolean supaOptimize(Point init, Point fin, boolean b) {
		//  Now with Better Variable Names! <33
		boolean onlyOnce = false;
		b=!b;
		ArrayList<Direction> theShortestPath = new ArrayList<>();
		theShortestPath.addAll(thePath);
		int curry = init.y;
		int currx = init.x;
		int successCounter = 0;
		for(int i = 0; i < theShortestPath.size()-1;i++) {
			if(onlyOnce)
				break;
			Direction toGo1 = theShortestPath.get(i);
			if(toGo1.equals(Direction.NORTH))
				curry--;
			if(toGo1.equals(Direction.SOUTH))
				curry++;
			if(toGo1.equals(Direction.EAST))
				currx++;
			if(toGo1.equals(Direction.WEST))
				currx--;
			Point location = new Point(currx, curry);
			int dankCounter = 0;
			for(int j = i+1; j < theShortestPath.size()-2; j++) {
				if(onlyOnce)
					break;
				Direction toGo = theShortestPath.get(j);
				if(toGo.equals(Direction.NORTH))
					location.y--;
				if(toGo.equals(Direction.SOUTH))
					location.y++;
				if(toGo.equals(Direction.EAST))
					location.x++;
				if(toGo.equals(Direction.WEST))
					location.x--;
				if(dankCounter>=lowerBound)
				if((Math.abs(curry-location.y)+Math.abs(currx-location.x))<=lowerBound/tightness) {
						ArrayList<Direction> eyy = new ArrayList<>();
						ArrayList<Direction> temp = new ArrayList<>();
						ArrayList<Direction> endPart = new ArrayList<>();
						//System.out.println("SIZE BEFORE " + theShortestPathIsAlwaysTheDankest.size());
						for(int k = 0; k <= i; k++) {
							eyy.add(theShortestPath.get(k));
							//System.out.println(eyy);
						}
						for(int k = i+1; k <= j; k++) {
							temp.add(theShortestPath.get(k));
						}
						for(int k = j+1; k < theShortestPath.size(); k++) {
							endPart.add(theShortestPath.get(k));
						}
						reset();
						shortestPath(new Point(currx,curry), new Point(location.x,location.y), map, b, false);
						optimize();

						if(thePath.isEmpty()) {
							reset();
							shortestPath(new Point(currx,curry), new Point(location.x,location.y), map, !b, false);
							optimize();
						}
						if((thePath.size()<temp.size())&&(thePath.size()!=0)) {
							eyy.addAll(thePath);
						//	System.out.printf("Better : curr x,y = %d,%d location = %d,%d\n",currx,curry,location.x,location.y);
						//	dankCounter = 0;
						//  System.out.println("ThePath " + thePath.size());
						//  System.out.println("Replaces: " + temp.size());
							//j = i;
							//location = new Point(currx, curry);
							successCounter++;
							onlyOnce = true;
						}
						else {
							eyy.addAll(temp);
						//	System.out.printf("Worse : curr x,y = %d,%d location = %d,%d\n",currx,curry,location.x,location.y);
						//	System.out.println("ThePath " + thePath);
						//	System.out.println("Worse Than: " + temp);
						} 
						
						//eyy.addAll(temp);
						
						//int tempVal = endPart.size();
						eyy.addAll(endPart);
						theShortestPath.clear();
						theShortestPath.addAll(eyy);
						//System.out.printf("i = %d, j = %d, dankCounter = %d, Size = %d\n\n", i,j,dankCounter, theShortestPathIsAlwaysTheDankest.size()+tempVal);
						//System.out.println(theShortestPathIsAlwaysTheDankest);
						//System.out.println(eyy.size()+"\n");
				}
				dankCounter++;
			}
		}
		//System.out.println(successCounter);
		thePath.clear();
		thePath.addAll(theShortestPath);
		return onlyOnce;
			//  MUCH PATH
	}
	/**************************************
	 *           getShortestPath          *
	 **************************************/
	
	// Returns the shortest path. Calls on the shortestPath method
	public ArrayList<Direction> getShortestPath(Point init, Point fin) {
		reset();
		shortestPath(init, fin, map, true, true);
		ArrayList<Direction> firstPath = new ArrayList<>();
		optimize();
	//	while(supaOptimize(init, fin, true));
		firstPath.addAll(thePath);
		reset();
		shortestPath(init, fin, map, false, true);
		optimize();
	//	while(supaOptimize(fin, fin, false));
		
		//  Because even the worst path is better than no path
		if(thePath.size() == 0)
			return firstPath;
		if(firstPath.size() == 0)
			return thePath;
		
		//  the better of the two
		if(thePath.size() >= firstPath.size()) 
			return firstPath;
		return thePath;
	}
	
	/**************************************
	 *               reset                *
	 **************************************/
	
	// Resets the path
	private void reset() {
		thePath.clear();
		done = false;
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				map[i][j] = savedMap[i][j];
			}
		}
	}

	/**************************************
	 *             shortestPath           *
	 **************************************/
	// Recursively calculates the shortest path between two points on the map
	private void shortestPath(Point init, Point fin, boolean[][] mapHelper, boolean larger, boolean endCondition) {
		if(endCondition) {
			if((Math.abs(init.x-fin.x)<=1)&&(Math.abs(init.y-fin.y)<=1)) {
				if(!((Math.abs(init.x-fin.x)==1)&&(Math.abs(init.y-fin.y)==1))) {
					done = true;
					return;
				}
			}
		}
		else {
			if((Math.abs(init.x-fin.x)<=0)&&(Math.abs(init.y-fin.y)<=0)) {
				done = true;
				return;
			}
		}
		//  will either take the longer path and make it shorter or the shorter path and make it 0
		boolean longerX = true;
		if(larger)
			longerX = false;
		int currx = init.x;
		int curry = init.y;
		//  which distance is further
		if(Math.abs(fin.x-init.x)>=Math.abs(fin.y-init.y))
			longerX = !longerX;
		
		/* * * * * * * * * *
		 * X direction!
		 * * * * * * * * * */
		if(longerX) {//  x goes first
			if(fin.x >= init.x) {
				//  PREFER GOING RIGHT
				try {
				if(!mapHelper[curry][currx+1]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.EAST);
					currx += 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
					if(done)
						return;
					currx -= 1;
					thePath.remove(thePath.size()-1);
					//  RECURSION WILL RETURN HERE IF IT RETURNS THEN SHIT LETS GO ANOTHER WAY!
				}
				}
				catch(Exception e) {
					//  return;
				}
				if(fin.y >= init.y) {
				try {
					//  PREFER GOING DOWN
					if(!mapHelper[curry+1][currx]) {
						mapHelper[curry][currx] = true;
						thePath.add(Direction.SOUTH);
						curry += 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
						//  RECURSION WILL RETURN HERE
						if(done)
							return;
						curry -= 1;
						thePath.remove(thePath.size()-1);
					}
				}
				catch(Exception e) {
					//  return;
				}
				try {
				if(!mapHelper[curry-1][currx]) {
					//  GOING UP... ON A TUESDAY
					mapHelper[curry][currx] = true;
					thePath.add(Direction.NORTH);
					curry -= 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
					//  RECURSION WILL RETURN HERE
					if(done)
						return;
					curry += 1;
					thePath.remove(thePath.size()-1);
				}
				}
				catch(Exception e) {
					//  return;
				}
				}
				else {
					try {
						if(!mapHelper[curry-1][currx]) {
							//  GOING UP... ON A TUESDAY
							mapHelper[curry][currx] = true;
							thePath.add(Direction.NORTH);
							curry -= 1;
							shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
							//  RECURSION WILL RETURN HERE
							if(done)
								return;
							curry += 1;
							thePath.remove(thePath.size()-1);
						}
						}
						catch(Exception e) {
							//  return;
						}
					try {
						//  PREFER GOING DOWN
						if(!mapHelper[curry+1][currx]) {
							mapHelper[curry][currx] = true;
							thePath.add(Direction.SOUTH);
							curry += 1;
							shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
							//  RECURSION WILL RETURN HERE
							if(done)
								return;
							curry -= 1;
							thePath.remove(thePath.size()-1);
						}
					}
					catch(Exception e) {
						//  return;
					}
				}
				//  OK WELP LETS GO LEFT
				try{
				if(!mapHelper[curry][currx-1]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.WEST);
					currx -= 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
					if(done)
						return;
					currx += 1;
					thePath.remove(thePath.size()-1);
				}
				}
				catch(Exception e) {
					//return;
				}
				//  may add a method for less copy pasta
			}
			else {
				//  OK WELP LETS GO LEFT
				try{
				if(!mapHelper[curry][currx-1]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.WEST);
					currx -= 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
					if(done)
						return;
					currx += 1;
					thePath.remove(thePath.size()-1);
				}
				}
				catch(Exception e) {
					//return;
				}
			if(fin.y >= init.y) {
			try {
				//  PREFER GOING DOWN
				if(!mapHelper[curry+1][currx]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.SOUTH);
					curry += 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
					//  RECURSION WILL RETURN HERE
					if(done)
						return;
					curry -= 1;
					thePath.remove(thePath.size()-1);
				}
			}
			catch(Exception e) {
				//  return;
			}
			try {
			if(!mapHelper[curry-1][currx]) {
				//  GOING UP... ON A TUESDAY
				mapHelper[curry][currx] = true;
				thePath.add(Direction.NORTH);
				curry -= 1;
				shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
				//  RECURSION WILL RETURN HERE
				if(done)
					return;
				curry += 1;
				thePath.remove(thePath.size()-1);
			}
			}
			catch(Exception e) {
				//  return;
			}
			}
			else {
				try {
					if(!mapHelper[curry-1][currx]) {
						//  GOING UP... ON A TUESDAY
						mapHelper[curry][currx] = true;
						thePath.add(Direction.NORTH);
						curry -= 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
						//  RECURSION WILL RETURN HERE
						if(done)
							return;
						curry += 1;
						thePath.remove(thePath.size()-1);
					}
					}
					catch(Exception e) {
						//  return;
					}
				try {
					//  PREFER GOING DOWN
					if(!mapHelper[curry+1][currx]) {
						mapHelper[curry][currx] = true;
						thePath.add(Direction.SOUTH);
						curry += 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
						//  RECURSION WILL RETURN HERE
						if(done)
							return;
						curry -= 1;
						thePath.remove(thePath.size()-1);
					}
				}
				catch(Exception e) {
					//  return;
				}
			}
			try {
				if(!mapHelper[curry][currx+1]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.EAST);
					currx += 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
					if(done)
						return;
					currx -= 1;
					thePath.remove(thePath.size()-1);
				}
				}
			catch(Exception e) {
				//  return;
			}
			}
			
			//  may add a method for less copy pasta
		}
		/* * * * * * * * * * * * *
		 * Y direction preferred!
		 * * * * * * * * * * * * */
		else {
			if(fin.y >= init.y) {
				//  PREFER GOING DOWN
				try {
				if(!mapHelper[curry+1][currx]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.SOUTH);
					curry += 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
					if(done)
						return;
					//  RECURSION WILL RETURN HERE
					curry -= 1;
					thePath.remove(thePath.size()-1);
				}
				}
				catch(Exception e) {
					//  return;
				}
				if(fin.x >= init.x) {
				//  GOING RIGHT
				try {
					if(!mapHelper[curry][currx+1]) {
						mapHelper[curry][currx] = true;
						thePath.add(Direction.EAST);
						currx += 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
						if(done)
							return;
						currx -= 1;
						thePath.remove(thePath.size()-1);
					}
					}
				catch(Exception e) {
					//  return;
				}
				//  GOING LEFT
				try {
				if(!mapHelper[curry][currx-1]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.WEST);
					currx -= 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
					if(done)
						return;
					currx += 1;
					thePath.remove(thePath.size()-1);
				}
				}
				catch(Exception e) {
					//  return;
				}
			}
				else {
					//  GOING LEFT
					try {
					if(!mapHelper[curry][currx-1]) {
						mapHelper[curry][currx] = true;
						thePath.add(Direction.WEST);
						currx -= 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
						if(done)
							return;
						currx += 1;
						thePath.remove(thePath.size()-1);
					}
					}
					catch(Exception e) {
						//  return;
					}
					//  GOING RIGHT
					try {
						if(!mapHelper[curry][currx+1]) {
							mapHelper[curry][currx] = true;
							thePath.add(Direction.EAST);
							currx += 1;
							shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
							if(done)
								return;
							currx -= 1;
							thePath.remove(thePath.size()-1);
						}
						}
					catch(Exception e) {
						//  return;
					}
				}
				try {
					if(!mapHelper[curry-1][currx]) {
						//  GOING UP... ON A TUESDAY
						mapHelper[curry][currx] = true;
						thePath.add(Direction.NORTH);
						curry -= 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
						if(done)
							return;
						//  RECURSION WILL RETURN HERE
						curry += 1;
						thePath.remove(thePath.size()-1);
					}
					}
					catch(Exception e) {
						//  return;
					}
			}
			else {
			try {
			if(!mapHelper[curry-1][currx]) {
				//  GOING UP... ON A TUESDAY
				mapHelper[curry][currx] = true;
				thePath.add(Direction.NORTH);
				curry -= 1;
				shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
				if(done)
					return;
				//  RECURSION WILL RETURN HERE
				curry += 1;
				thePath.remove(thePath.size()-1);
			}
			}
			catch(Exception e) {
				//  return;
			}
				if(fin.x >= init.x) {
				//  GOING RIGHT
				try {
					if(!mapHelper[curry][currx+1]) {
						mapHelper[curry][currx] = true;
						thePath.add(Direction.EAST);
						currx += 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
						if(done)
							return;
						currx -= 1;
						thePath.remove(thePath.size()-1);
					}
					}
				catch(Exception e) {
					//  return;
				}
				//  GOING LEFT
				try {
				if(!mapHelper[curry][currx-1]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.WEST);
					currx -= 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
					if(done)
						return;
					currx += 1;
					thePath.remove(thePath.size()-1);
				}
				}
				catch(Exception e) {
					//  return;
				}
			}
				else {
					//  GOING LEFT
					try {
					if(!mapHelper[curry][currx-1]) {
						mapHelper[curry][currx] = true;
						thePath.add(Direction.WEST);
						currx -= 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
						if(done)
							return;
						currx += 1;
						thePath.remove(thePath.size()-1);
					}
					}
					catch(Exception e) {
						//  return;
					}
					//  GOING RIGHT
					try {
						if(!mapHelper[curry][currx+1]) {
							mapHelper[curry][currx] = true;
							thePath.add(Direction.EAST);
							currx += 1;
							shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
							if(done)
								return;
							currx -= 1;
							thePath.remove(thePath.size()-1);
						}
						}
					catch(Exception e) {
						//  return;
					}
				}
				//  PREFER GOING DOWN
				 try {
					if(!mapHelper[curry+1][currx]) {
						mapHelper[curry][currx] = true;
						thePath.add(Direction.SOUTH);
						curry += 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger, endCondition);
						if(done)
							return;
						//  RECURSION WILL RETURN HERE
						curry -= 1;
						thePath.remove(thePath.size()-1);
					}
					}
					catch(Exception e) {
						//  return;
					} 
			}
		}
	}


	public static void main(String[] args) {
	/*	System.out.print("{");
		boolean[][] map = {{false, false, false, false, true, false, false, false, false, false, },
{false, true, true, false, true, false, true, true, false, false, },
{false, true, false, true, false, false, false, false, false, false, },
{false, false, false, false, false, true, false, false, false, false, },
{false, false, false, true, false, false, false, false, false, false, },
{false, false, false, false, false, true, false, false, false, true, },
{false, true, false, false, false, false, true, true, false, false, },
{false, false, false, false, true, false, false, false, false, false, },
{false, true, true, false, false, false, true, false, false, false, },
{false, false, false, true, true, false, false, false, false, false}};
		*/
		double average = 0;
		int counter = 0;
		int max = 0;
		int min = 1000;
		for(int zod = 0; zod < 60000; zod++) {
		boolean[][] mao = new boolean[100][100];
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j <100; j++)
				if(Math.random()<.3)
					mao[i][j] = true;
		}
		ShortestPathCalculator theCalc = new ShortestPathCalculator(mao);
		ArrayList<Direction> theDor = null;
		if(zod<1000) {
			theDor = theCalc.getShortestPath(new Point(4,4), new Point(75, 4));
		}
		else if(zod<2000) {
			theDor = theCalc.getShortestPath(new Point(75,75), new Point(4, 4));
		}
		else if(zod<3000) {
			theDor = theCalc.getShortestPath(new Point(4,4), new Point(4, 75));
		}
		else if(zod<4000) {
			theDor = theCalc.getShortestPath(new Point(4,75), new Point(4, 4));
		}
		else if(zod < 5000)
			theDor = theCalc.getShortestPath(new Point(75,75), new Point(4, 4));
		else {
			theDor = theCalc.getShortestPath(new Point(4,4), new Point(75, 75));
		}
		if(theDor.size()!= 0) {
			if(theDor.size() < min)
				min = theDor.size();
			if(theDor.size() > max)
				max = theDor.size();
			average+= theDor.size();
			counter ++;
		}
		//for(int i = 0; i < theDor.size(); i++) {
		//	System.out.print(theDor.get(i) + ",");
		//}
		//System.out.println();
		/*for(int i = 0; i < 500; i++) {
			System.out.print("{");
			for(int j = 0; j< 500 ; j++) {
				if(mao[i][j])
					System.out.print("true, ");
				else
					System.out.print("false, ");
				b4[i][j] = mao[i][j];
			}
			System.out.println("},");
		} */
		//System.out.println("---------------------------");
	/*	for(int i = 0; i < 25; i++) {
			System.out.print("");
			for(int j = 0; j< 25 ; j++) {
				if(mao[i][j]) {
					if(i == 4 && j == 4)
						System.out.print("[S] ");
					else if(i==20 && j ==4)
						System.out.print("[D] ");
					else
						System.out.print("[X] ");
				}
				else {
					if(i == 4 && j == 4)
						System.out.print("[S] ");
					else if(i==20 && j ==4)
						System.out.print("[D] ");
					else
						System.out.print("[ ] ");
				}
				//b4[i][j] = mao[i][j];
			}
			System.out.println();
		} */
		//System.out.println("---------------------------");
		//times = new Timer(10, new TimerListener());
		//tic = 0;
		//times.start();
		//ShortestPathCalculator hodor = new ShortestPathCalculator(mao);
		//ArrayList<Direction> path1 = new ArrayList<>();
		//hodor.shortestPath(new Point(4,4), new Point(450,480), mao, true);
		//times.stop();
		//System.out.println("It took " + tic + " seconds");
		//path1.addAll(hodor.thePath);
		//hodor.thePath.clear();
		//hodor.done=false;
		//hodor.shortestPath(new Point(4,4), new Point(450,480), b4, false);
		//for(int i = 0; i < path1.size();i++) {
		//	System.out.print(path1.get(i) + ",");
		//}
		//System.out.println();
		//for(int i = 0; i < hodor.thePath.size();i++) {
			//System.out.print(hodor.thePath.get(i) + ",");
		//}
		//System.out.println();
		//System.out.println("The shortest path is!!!");
		//if(hodor.thePath.size() > path1.size())
		//	System.out.println("PATH 1");
		//else
		//	System.out.println("PATH 2!");
		/* for(int i = 0; i < 10; i++) {
			for(int j = 0; j< 10 ; j++) {
				if(!b4[i][j]&&mao[i][j]) {
					if(i == 4 && j == 4)
						System.out.print("[S] ");
					else if(i==8 && j ==5)
						System.out.print("[D] ");
				else
					System.out.print("[X] ");
				}
				else {
					if(i == 4 && j == 4)
						System.out.print("[S] ");
					else if(i==8 && j ==5)
						System.out.print("[D] ");
					else System.out.print("[ ] ");
				}
			}
			System.out.println();
		} 
		for(int i = 0 ; i < hodor.thePath.size(); i++) {
			System.out.print(hodor.thePath.get(i) + " ");
		}*/
	}
		System.out.println(min);
		System.out.println(max);
		System.out.println(average/counter);
		System.out.println(counter);
	}
	
}

