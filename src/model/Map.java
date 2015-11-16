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
import java.util.Random;

public class Map {
	int size;
	long Seed;
	private Random gen;
	private MapTile[][] board;
	public Map(int size) {
		this.size = size;
		board = new MapTile[size][size];
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++)
				board[i][j] = new MapTile();
		gen = new Random();
		generate();
	}
	public Map(int size , long Seed) {
		this.size = size;
		board = new MapTile[size][size];
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++)
				board[i][j] = new MapTile();
		this.Seed = Seed;
		gen = new Random(Seed);
		generate();
	}
	//  For all of our testing purposes
	public Map(MapTile[][] board) {
		this.board = board;
		size = board.length;
		gen = new Random();
	}
	public Map(MapTile[][] board, long Seed) {
		this.board = board;
		size = board.length;
		this.Seed = Seed;
		gen = new Random(Seed);
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
		//IT IS THOUGH!!
		createOcean();
		createRiver();
		createTrees();
		spawnFood();
		spawnStone();
		spawnYoPeeps();
	}
	private void spawnYoPeeps() {
		//  if you thought stone had alot of if's
		int counter =0;
		//  it will try 1000 times before giving up
		int X = 0;
		int Y = 0;
		while(counter < 1000) {
			X = gen.nextInt(size-5)+1;
			Y = gen.nextInt(size-7)+1;
	
			//checks to see if the position on the land is not a river or ocean
			int placeCounter = 5*7;
			for(int i = X; i < X + 5; i++) {
				for(int j = Y; j < Y + 7; j++) {
					if(board[j][i].getLand().equals(Terrain.PLAIN)
							&& (board[j][i].getResource().getResourceT().equals(ResourceType.NONE))) {
						placeCounter--;
					}
				}
			}
			if(placeCounter == 0)
				break;
			counter++;
		}
		//  TownHall!!!!
		for(int i = X+1; i < X + 4; i++) {
			for(int j = Y + 1; j < Y + 6; j++) {
				board[j][i].setLand(Terrain.BEACH);
			}
		}
		
	}
	private void createOcean() {
		double num = gen.nextDouble();
		Direction initial = null;
		Point init = null;
		boolean goingToLine = true;
		boolean topLeft = true;
		if(num<.25) {
			initial = Direction.SOUTH;
			init = new Point(board.length/10, 0);
			goingToLine = true;
		}
		else if(num<.5) {
			initial = Direction.SOUTH;
			init = new Point(board.length-board.length/10, 0);
			topLeft = false;
			goingToLine = false;
		}
		else if(num<.75) {
			initial = Direction.EAST;
			init = new Point(0, board.length/10);
			goingToLine = true;
		}
		else {
			initial = Direction.EAST;
			topLeft = false;
			init = new Point(0, board.length-board.length/10);
			goingToLine = false;
		}
		OceanFilling(OceanMaking(init, initial) ,initial ,init, goingToLine, topLeft);		
	}
	private void OceanFilling(ArrayList<Direction> oceanTiles ,Direction initial , Point init, boolean fillTo, boolean topLeft) {
		Direction fillingDir = null;
		if(topLeft) {
			if(initial.equals(Direction.SOUTH)) {
				fillingDir = Direction.rotateRight(initial);
			}
			else
				fillingDir = Direction.rotateLeft(initial);
		}
		else {
			if(initial.equals(Direction.SOUTH)) {
				fillingDir = Direction.rotateLeft(initial);
			}
			else
				fillingDir = Direction.rotateRight(initial);
		}
		Direction first = null;
		while(!oceanTiles.isEmpty()) {
			first = oceanTiles.get(0);
			oceanTiles.remove(0);
			try {
			int i = 0;
			if(fillingDir.equals(Direction.EAST)) {
				board[init.y][init.x-1].setLand(Terrain.BEACH);
				board[init.y][init.x-2].setLand(Terrain.BEACH);
				while(true) {
					board[init.y][init.x+i].setLand(Terrain.OCEAN);
					i++;
					}
				}
			if(fillingDir.equals(Direction.NORTH)) {
				board[init.y+1][init.x].setLand(Terrain.BEACH);
				board[init.y+2][init.x].setLand(Terrain.BEACH);
				while(true) {
					board[init.y-i][init.x].setLand(Terrain.OCEAN);
					i++;
					}
				}
			if(fillingDir.equals(Direction.WEST)) {
				board[init.y][init.x+1].setLand(Terrain.BEACH);
				board[init.y][init.x+2].setLand(Terrain.BEACH);
				while(true) {
					board[init.y][init.x-i].setLand(Terrain.OCEAN);
					i++;
					}
				}
			if(fillingDir.equals(Direction.SOUTH)) {
				board[init.y-1][init.x].setLand(Terrain.BEACH);
				board[init.y-2][init.x].setLand(Terrain.BEACH);
				while(true) {
					board[init.y+i][init.x].setLand(Terrain.OCEAN);
					i++;
					}
				}
			else {
				break;
			}
			}
			catch(Exception breakingOut) {
				//  eyy
			}
			if(first.equals(Direction.NORTH)) {
				init.y--;
			}
			else if(first.equals(Direction.SOUTH)) {
				init.y++;
			}
			else if(first.equals(Direction.EAST)) {
				init.x++;
			}
			else if(first.equals(Direction.WEST)) {
				init.x--;
			}
		}
	}
	private ArrayList<Direction> OceanMaking(Point init, Direction initial) {
		ArrayList<Direction> theCoast = new ArrayList<>();
		double noLeft = .0;
		int i = 0;
		Direction last = Direction.invert(initial);
		while(i < board.length) {
			boolean changed = false;
			while(!changed) {
				changed = false;
				double num = gen.nextDouble();
				if(num>.5 +Math.abs(noLeft)) {
					theCoast.add(initial);
					last = initial;
					changed = true;
					i++;
				}
				else if(num >.25-noLeft) {
					if(!last.equals(Direction.rotateLeft(initial))) {
					theCoast.add(Direction.rotateLeft(initial));
					last = Direction.rotateLeft(initial);
					changed = true;
					noLeft -= .02;
					}
				}
				else {
					if(!last.equals(Direction.rotateRight(initial))) {
					theCoast.add(Direction.rotateRight(initial));
					last = Direction.rotateRight(initial);
					changed = true;
					noLeft += .02;
					}
				}
			}
		}
		return theCoast;

	}
	private void createTrees() {
		double initialChance = .09;
		double bonusChance = .6;
		int magicNumber37 = (board.length*board.length)/7; //  ? how much of the map should be trees?
		int noOfTrees = 0;
		while(noOfTrees<= magicNumber37) {
			//  FOREST SIZE MUST BE AN EVEN INT >=4 for the middle tree's to work properly :)
			int forestSize = 7;
			Point point = new Point(gen.nextInt(size), gen.nextInt(size));
			while(!board[point.y][point.x].toString().equals("[ ]")) { //  [ ] for plains
				point.x = gen.nextInt(size);
				point.y = gen.nextInt(size);
			}
			//  this is the center point of the trees!
			for(int i = point.x - forestSize; i < point.x + forestSize; i++) {
				for(int j = point.y - forestSize; j < point.y + forestSize; j++) {
					double chance = initialChance;
					if((((point.x-(forestSize/2)))<i)&&(((point.x+(forestSize)/2))>i)
							&&(((point.y-(forestSize/2)))<j)&&(((point.y+(forestSize)/2))>j)) {
						chance +=bonusChance;
					}
					if(chance >= gen.nextDouble()) {
						try{
							if(board[j][i].getLand().equals(Terrain.PLAIN) 
									&& board[j][i].getResource().getResourceT().equals(ResourceType.NONE)) {
								board[j][i].setResource(new Tree());
								noOfTrees++;
							}
						}
						catch(Exception e) {
						}
					}
				}
			}
		}
	}
	private void spawnFood() {
		// Spawns Fish and Berry bushes
		spawnFish();
		spawnBushes();
		spwanAnimals();
	}
	private void spwanAnimals() {
		//  Spawns some animal companions
		Point point = new Point(gen.nextInt(size), gen.nextInt(size));
		while(!board[point.x][point.y].getLand().equals(Terrain.PLAIN)
				&&(board[point.x][point.y].getResource().getResourceT().equals(ResourceType.NONE))) {
			point.x = gen.nextInt(size);
			point.y = gen.nextInt(size);
		}
		//board[point.x][point.y].setResource(new Meese());
	}
	private void spawnFish() {
		//  Spawns Fish in the river
		for(int i = 0; i < gen.nextInt(7) + 10; i++) {
			Point point = new Point(gen.nextInt(size), gen.nextInt(size));
			while(!board[point.x][point.y].getLand().equals(Terrain.RIVER)) {
				point.x = gen.nextInt(size);
				point.y = gen.nextInt(size);
			}
			board[point.x][point.y].setResource(new Fish());
		}
		//  Spawns Salty Fish in the Ocean
		for(int i = 0; i < gen.nextInt(30) + 20; i++) {
			Point point = new Point(gen.nextInt(size), gen.nextInt(size));
			while(!board[point.y][point.x].getLand().equals(Terrain.OCEAN)) {
				point.x = gen.nextInt(size);
				point.y = gen.nextInt(size);
			}
			board[point.y][point.x].setResource(new SaltyFish());
		} 
	}
	private boolean riverNotFinished;
	private void createRiver() {
		//  for lack of generality a random topleft point, to a random bottom right point
		int length = (int)((double)board.length*4); //  board.length*10 for the nile
		Point init = null;
		init = new Point(gen.nextInt(size/2)+ size/4,gen.nextInt(size/2)+ size/4);
		riverNotFinished = false;
		riverFilling(riverMaking(init, Direction.getRandom(Seed), length) ,init);
		if(riverNotFinished) {
			init = new Point(gen.nextInt(size/2)+ size/4,gen.nextInt(size/2)+ size/4);
			riverFilling(riverMaking(init, Direction.getRandom(Seed), length) ,init);
			riverNotFinished = false;
		}
		init = new Point(gen.nextInt(size/2)+ size/4,gen.nextInt(size/2)+ size/4);
		riverFilling(riverMaking(init, Direction.getRandom(Seed), length) ,init);
		if(riverNotFinished) {
			init = new Point();
			riverFilling(riverMaking(init, Direction.getRandom(Seed), length) ,init);
			riverNotFinished = false;
		}
	}
	private ArrayList<Direction> riverMaking(Point init, Direction initial, int size) {
		ArrayList<Direction> theRiver = new ArrayList<>();
		Direction last = Direction.invert(initial);
		for(int i = 0; i < size; i++) {
			boolean changed = false;
			while(!changed) {
				changed = false;
				double num = gen.nextDouble();
				if(num>.65) {
					if(!last.equals(initial)) {
						theRiver.add(initial);
						last = initial;
						changed = true;
					}
				}
				else if(num >.35) {
					if(!last.equals(Direction.rotateLeft(initial))) {
					theRiver.add(Direction.rotateLeft(initial));
					last = Direction.rotateLeft(initial);
					changed = true;
					}
				}
				else if(num >.05) {
						if(!last.equals(Direction.rotateRight(initial))) {
					theRiver.add(Direction.rotateRight(initial));
					last = Direction.rotateRight(initial);
					changed = true;
					}
				}
				else  {
					if(!last.equals(Direction.invert(initial))) {
					theRiver.add(Direction.invert(initial));
					last = Direction.invert(initial);
					changed = true;
					}
				}
			}
			if(gen.nextDouble()<.01) {
				if(gen.nextDouble()<.5) {
					initial = Direction.rotateLeft(initial);
				}
				else {
					initial = Direction.rotateRight(initial);
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
			try {
				if(board[init.y][init.x].getLand().equals(Terrain.OCEAN)) {
					riverNotFinished = true;
					return;
				}
			}
			catch(Exception oops) {
			}
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
	private void spawnStone(){
		int Outcroppings = gen.nextInt(10) + 15;

		while(Outcroppings > 0){
		int X = gen.nextInt(size-2)+1;
		int Y = gen.nextInt(size-2)+1;

		//checks to see if the position on the land is not a river
		if(board[X][Y].getLand() == Terrain.PLAIN &&
			board[X+1][Y].getLand() == Terrain.PLAIN &&
			board[X][Y+1].getLand() == Terrain.PLAIN &&
			board[X+1][Y+1].getLand() == Terrain.PLAIN &&
		   
			board[X][Y].getResource().getResourceT() == ResourceType.NONE &&
			board[X+1][Y].getResource().getResourceT() == ResourceType.NONE &&
			board[X][Y+1].getResource().getResourceT() == ResourceType.NONE &&
			board[X+1][Y+1].getResource().getResourceT() == ResourceType.NONE){
				//Sets our STONE outcropping
				Stone stoned = new Stone();
				board[X][Y].setResource(stoned);
				board[X+1][Y].setResource(stoned);
				board[X][Y+1].setResource(stoned);
				board[X+1][Y+1].setResource(stoned);
				
				Outcroppings--;
		}
		}
	}

		private void spawnBushes(){
		int dingDangBushes = gen.nextInt(10)+10;

		while(dingDangBushes > 0){
		int X = gen.nextInt(size-2)+1;
		int Y = gen.nextInt(size-2)+1;
		int treeCounter= 0;

		if(board[X][Y].getResource().getResourceT() == ResourceType.NONE){
			
			for(int i = -1; i < 1; i++){
				for(int j = -1; j < 1; j++){
				if(board[X+i][Y+j].getResource().getResourceT() == ResourceType.TREE)
				treeCounter++;
				}
			}
		}

			
			if(treeCounter > 0 && treeCounter < 6){
			board[X][Y].setResource(new BerryBush());
			dingDangBushes --;
			treeCounter = 0;
			}
		}
	}
	//public static void main(String[] args) {
	//	Map hodor = new Map(30);
	//}	
	public MapTile[][] getMapTiles(){
		return board;
	}
}