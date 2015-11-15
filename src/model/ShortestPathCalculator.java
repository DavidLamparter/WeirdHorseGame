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
/*
 * This Program will take in a map!
 * If you run it as is there will be 3 things that appear on the S.O.P
 * Min, Max, and average for 6000 runs of random mazes going up down and diagonal for 1000 times each
 * There could be room for improvement but the technology isn't there yet
 */
import java.awt.Point;
import java.util.ArrayList;

public class ShortestPathCalculator{
	private boolean[][] map;
	//  map size must be equal aka 50 by 50 or 100 by 100 
	//  A NEW CALULATOR MUST BE MADE WHEN THE MAP CHANGES!!!
	public ShortestPathCalculator(MapTile[][] board) {
		this.map = new boolean[board.length][board.length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				this.map[i][j] = board[i][j].unPassable();
			}
		}
	}
	//  A NEW CALULATOR MUST BE MADE WHEN THE MAP CHANGES!!!
	public ShortestPathCalculator(boolean[][] board) {
		this.map = new boolean[board.length][board.length];
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				this.map[i][j] = board[i][j];
			}
		}
	}
	public void updateMap(boolean[][] newMap) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map.length; j++) {
				this.map[i][j] = newMap[i][j];
			}
		}
	}
	private boolean done = false;
	private ArrayList<Direction> thePath = new ArrayList<>();
	private void optimize() {
		for(int i = 0; i < thePath.size()-2;i++) {
			if(thePath.get(i).equals(thePath.get(i+2).invert(thePath.get(i+2)))) {//  My life... 
				thePath.remove(i+2);
				thePath.remove(i);
				i = -1;
				//System.out.println("HEYYY IM MR MEESEEKS LOOK AT ME!!!");
			}
		}
	}
	public ArrayList<Direction> getShortestPath(Point init, Point fin) {
		reset();
		shortestPath(init, fin, map, true);
		ArrayList<Direction> firstPath = new ArrayList<>();
		optimize();
		firstPath.addAll(thePath);
		reset();
		shortestPath(init, fin, map, false);
		optimize();
		
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
	private void reset() {
		thePath.clear();
		done = false;
	}
	private void shortestPath(Point init, Point fin, boolean[][] mapHelper, boolean larger) {
		if(init.equals(fin)) {
			done = true;
			return;
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
					shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
				try {
				if(fin.y > init.y) {
					//  PREFER GOING DOWN
					if(!mapHelper[curry+1][currx]) {
						mapHelper[curry][currx] = true;
						thePath.add(Direction.SOUTH);
						curry += 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger);
						//  RECURSION WILL RETURN HERE
						if(done)
							return;
						curry -= 1;
						thePath.remove(thePath.size()-1);
					}
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
					shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
				//  may add a method for less copy pasta
			}
			//  OK WELP LETS GO LEFT
			try{
			if(!mapHelper[curry][currx-1]) {
				mapHelper[curry][currx] = true;
				thePath.add(Direction.WEST);
				currx -= 1;
				shortestPath(new Point(currx,curry), fin, mapHelper, larger);
				if(done)
					return;
				currx += 1;
				thePath.remove(thePath.size()-1);
			}
			}
			catch(Exception e) {
				//return;
			}
			try {
				//  PREFER GOING DOWN
				if(!mapHelper[curry+1][currx]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.SOUTH);
					curry += 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
				shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
		/* * * * * * * * * * * * *
		 * Y direction prefered!
		 * * * * * * * * * * * * */
		else {
			if(fin.y >= init.y) {
				//  PREFER GOING DOWN
				try {
				if(!mapHelper[curry+1][currx]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.SOUTH);
					curry += 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
				//  GOING RIGHT
				try {
					if(!mapHelper[curry][currx+1]) {
						mapHelper[curry][currx] = true;
						thePath.add(Direction.EAST);
						currx += 1;
						shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
					shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
			try {
			if(!mapHelper[curry-1][currx]) {
				//  GOING UP... ON A TUESDAY
				mapHelper[curry][currx] = true;
				thePath.add(Direction.NORTH);
				curry -= 1;
				shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
			//  GOING RIGHT
			try {
				if(!mapHelper[curry][currx+1]) {
					mapHelper[curry][currx] = true;
					thePath.add(Direction.EAST);
					currx += 1;
					shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
				shortestPath(new Point(currx,curry), fin, mapHelper, larger);
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
	}
	public static void main(String[] args) {
	/*	System.out.print("{");
		boolean[][] mao = {{false, false, false, false, true, false, false, false, false, false, },
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
		int min = 100;
		for(int zod = 0; zod < 60000; zod++) {
		boolean[][] mao = new boolean[25][25];
		for(int i = 0; i < 25; i++) {
			for(int j = 0; j < 25; j++)
				if(Math.random()<.3)
					mao[i][j] = true;
		}
		ShortestPathCalculator theCalc = new ShortestPathCalculator(mao);
		ArrayList<Direction> theDor = null;
		if(zod<1000) {
			theDor = theCalc.getShortestPath(new Point(4,4), new Point(20, 4));
		}
		else if(zod<2000) {
			theDor = theCalc.getShortestPath(new Point(20,20), new Point(4, 4));
		}
		else if(zod<3000) {
			theDor = theCalc.getShortestPath(new Point(4,4), new Point(4, 20));
		}
		else if(zod<4000) {
			theDor = theCalc.getShortestPath(new Point(4,20), new Point(4, 4));
		}
		else if(zod < 5000)
			theDor = theCalc.getShortestPath(new Point(20,4), new Point(4, 4));
		else {
			theDor = theCalc.getShortestPath(new Point(4,4), new Point(4, 5));
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

