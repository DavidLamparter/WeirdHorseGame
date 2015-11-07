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
import java.awt.Point;
import java.util.ArrayList;

public class Map {
	private MapTile[][] board;
	public Map(int size) {
		board = new MapTile[size][size];
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++)
				board[i][j] = new MapTile();
		generate();
	}
	//  For all of our testing purposes
	public Map(MapTile[][] board) {
		this.board = board;
	}
	public String toString() {
		String toReturn = "";
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				toReturn += board[i][j].toString();
			}
			toReturn += "\n";
		}
		return toReturn;
	}
	public void generate() {
		//  OMAN THIS IS NOT TESTABLE CODE!!!
		createRiver();
		//createOcean();
		createTrees();
		spawnFood();
		//  spawnStone();
		//  spwanAnimals();
		//  Ohwell...
	}
	private void createTrees() {
		double initialChance = .1;
		double bonusChance = .4;
		int magicNumber37 = (board.length*board.length)/5; //  ? how much of the map should be trees?
		int noOfTrees = 0;
		while(noOfTrees<= magicNumber37) {
			//  FOREST SIZE MUST BE AN EVEN INT >=4 for the middle tree's to work properly :)
			int forestSize = 6;
			Point point = new Point((int)(Math.random()*board.length), (int)(Math.random()*board.length));
			while(!board[point.y][point.x].toString().equals("[ ]")) { //  [ ] for plains
				point.x = (int)(Math.random()*board.length);
				point.y = (int)(Math.random()*board.length);
			}
			//  this is the center point of the trees!
			for(int i = point.x - forestSize; i < point.x + forestSize; i++) {
				for(int j = point.y - forestSize; j < point.y + forestSize; j++) {
					double chance = initialChance;
					if((((point.x-(forestSize/2)))<i)&&(((point.x+(forestSize)/2))>i)
							&&(((point.y-(forestSize/2)))<j)&&(((point.y+(forestSize)/2))>j)) {
						chance +=bonusChance;
						//System.out.println("Wow!");
					}
					if(chance >= Math.random()) {
						try{
							if(board[j][i].toString().equals("[ ]")) {
								board[j][i].setResource(Resource.TREE);
								noOfTrees++;
							}
						}
						catch(Exception e) {
							//System.out.print("Noes!");
						}
					}
				}
			}
		}
	}
	private void spawnFood() {
		// Spawns Fish and Berry bushes
		spawnFish();
		
	}
	private void spawnFish() {
		//  Spawns Fish in the river
		for(int i = 0; i < Math.random()*7+10; i++) {
			Point point = new Point((int)(Math.random()*board.length), (int)(Math.random()*board.length));
			while(!board[point.x][point.y].getLand().equals(Terrain.RIVER)) {
				point.x = (int)(Math.random()*board.length);
				point.y = (int)(Math.random()*board.length);
			}
			board[point.x][point.y].setResource(Resource.FISH);
		}
		//  Spawns Salty Fish in the Ocean
	/*	for(int i = 0; i < Math.random()*30+20; i++) {
			Point point = new Point((int)(Math.random()*board.length), (int)(Math.random()*board.length));
			while(!board[point.y][point.x].getLand().equals(Terrain.RIVER)) {
				point.x = (int)(Math.random()*board.length);
				point.y = (int)(Math.random()*board.length);
			}
			board[point.x][point.y].setResource(Resource.SALTY_FISH);
		} */
	}
	private void createRiver() {
		//  for lack of generality a random topleft point, to a random bottom right point
		int length = (int)((double)board.length*3); //  board.length*2 for the nile
		Point init = null;
		init = new Point((int)(Math.random()*board.length), (int)(Math.random()*board.length/10));
		riverFilling(riverMakingSouth(init, length) ,init);
		init = new Point((int)(Math.random()*board.length), (int)(Math.random()*board.length/10));
		riverFilling(riverMakingSouth(init, length) ,init);
	}
	private ArrayList<Direction> riverMakingSouth(Point init, int size) {
		ArrayList<Direction> theRiver = new ArrayList<>();
		Direction last = Direction.NORTH;
		for(int i = 0; i < size; i++) {
			boolean changed = false;
			while(!changed) {
				changed = false;
				double num = Math.random();
				if(num>.65) {
					if(!last.equals(Direction.SOUTH)) {
						theRiver.add(Direction.SOUTH);
						last = Direction.SOUTH;
						changed = true;
					}
				}
				else if(num >.35) {
					if(!last.equals(Direction.EAST)) {
					theRiver.add(Direction.EAST);
					last = Direction.EAST;
					changed = true;
					}
				}
				else if(num >.05) {
						if(!last.equals(Direction.WEST)) {
					theRiver.add(Direction.WEST);
					last = Direction.WEST;
					changed = true;
					}
				}
				else  {
					if(!last.equals(Direction.NORTH)) {
					theRiver.add(Direction.NORTH);
					last = Direction.NORTH;
					changed = true;
					}
				}
			}
		}
		return theRiver;
	}
	//  SIMILAR TO THE MOVEMENT OF WORKERS BUT WITHOUT THE WHILE LOOP
	private void riverFilling(ArrayList<Direction> showMeYourMoves, Point init) {
		Direction first = null;
		while(!showMeYourMoves.isEmpty()) {
			first = showMeYourMoves.get(0);
			showMeYourMoves.remove(0);
			if(first.equals(Direction.NORTH)) {
				try {
				board[init.y][init.x+1].setLand(Terrain.RIVER);
				board[init.y][init.x+0].setLand(Terrain.RIVER);
				board[init.y][init.x-1].setLand(Terrain.RIVER);
				}
				catch(Exception e) {
					//board[init.y][init.x].setLand(Terrain.RIVER);
				}
				init.y--;
			}
			if(first.equals(Direction.SOUTH)) {
				try {
				board[init.y][init.x+1].setLand(Terrain.RIVER);
				board[init.y][init.x+0].setLand(Terrain.RIVER);
				board[init.y][init.x-1].setLand(Terrain.RIVER);
				}
				catch(Exception e) {
					//board[init.y][init.x].setLand(Terrain.RIVER);
				}
				init.y++;
			}
			if(first.equals(Direction.EAST)) {
				try{
				board[init.y-1][init.x].setLand(Terrain.RIVER);
				board[init.y+0][init.x].setLand(Terrain.RIVER);
				board[init.y+1][init.x].setLand(Terrain.RIVER);
				}
				catch(Exception e) {
					//board[init.y][init.x].setLand(Terrain.RIVER);
				}
				init.x++;
			}
			if(first.equals(Direction.WEST)) {
				try{
				board[init.y-1][init.x].setLand(Terrain.RIVER);
				board[init.y+0][init.x].setLand(Terrain.RIVER);
				board[init.y+1][init.x].setLand(Terrain.RIVER);
				}
				catch(Exception e) {
					//board[init.y][init.x].setLand(Terrain.RIVER);
				}
				init.x--;
			}
		}
	}
	//public static void main(String[] args) {
	//	Map hodor = new Map(30);
	//	System.out.println(hodor.toString());
	//}	
	public MapTile[][] getMapTiles(){
		return board;
	}
}
