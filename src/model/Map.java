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
|  Description:  This program creates the map for the game. It will randomly generate a new map
|                every time the program is run, "randomly" placing different objects across the map
|                such as water, trees, and stone. 
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Map implements Serializable, Observer {
	
	/**************************************
	 *          Instance Variables        *
	 **************************************/
	
	// Size of the map
	private int size;
	
	// Seed for generating the board
	private long Seed;
	
	// Random number
	private Random gen;
	
	// A 2D array of MapTiles from MapTile.java
	private MapTile[][] board;
	
	// The set of workers the player starts with when the game begins
	private ArrayList<Worker> initialWorkers = new ArrayList<>();
	
	// A boolean used for the generation of the rivers on the map
	private boolean riverNotFinished;
	
	//TH top left point
	private Point TH = new Point(0,0);
	
	//Arrays of the resources, used to determine closest Resource of working type
	private ArrayList<Job> TreeList = new ArrayList<Job>();
	private ArrayList<Job> StoneList = new ArrayList<Job>();
	private ArrayList<Job> BerryList = new ArrayList<Job>();
	private ArrayList<Job> FishList = new ArrayList<Job>();
	private ArrayList<Storage> storageList = new ArrayList<Storage>();
	
	/**************************************
	 *           Map Constructors         *
	 **************************************/
	
	// Creates the map given a size (perfect square)
	public Map(int size) {
		this.size = size;
		board = new MapTile[size][size];
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++)
				board[i][j] = new MapTile();
		gen = new Random();
		generate();
	}
	
	// Creates the map given a size and a seed for generation (perfect square)
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
	// Creates the map given a 2D board of MapTiles
	public Map(MapTile[][] board) {
		this.board = board;
		size = board.length;
		gen = new Random();
	}
	
	// Creates the map given a 2D board of MapTiles and a seed for generation
	public Map(MapTile[][] board, long Seed) {
		this.board = board;
		size = board.length;
		this.Seed = Seed;
		gen = new Random(Seed);
	}
	
	/**************************************
	 *   Getters for Instance Variables   *
	 **************************************/
	//returns the TH's position
	public Point getTH(){
		return TH;
	}
	
	// returns the generated map
	public MapTile[][] getMapTiles(){
		return board;
	}
	
	// Returns the list of workers that start the game
	public ArrayList<Worker> getInitialWorkers() {
		return initialWorkers;
	}

	public ArrayList<Job> getBerryList() {
		return BerryList;
	}

	public ArrayList<Job> getStoneList() {
		return StoneList;
	}

	public ArrayList<Job> getTreeList() {
		return TreeList;
	}
	
	public ArrayList<Job> getFishList() {
		return FishList;
	}
	
	public ArrayList getStorageList(){
		return storageList;
	}
	public boolean canBuildBuildingsAt(Point p) {
		boolean canBuild = false; 
		if((board[p.y][p.x].getLand()==Terrain.PLAIN))
			if(board[p.y][p.x].getResource().getResourceT()==ResourceType.NONE)
				canBuild = true;
		return canBuild;
	}
	public boolean canBuildBridgesAt(Point p) {
		boolean canBuild = false; 
		if((board[p.y][p.x].getLand()==Terrain.OCEAN)||(board[p.y][p.x].getLand()==Terrain.RIVER))
			if(board[p.y][p.x].getResource().getResourceT()==ResourceType.NONE)
				canBuild = true;
		return canBuild;
	}
	
	/**************************************
	 *              toString              *
	 **************************************/
	
	// toString method for text view of map
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
	
	/**************************************
	 *              Generate              *
	 **************************************/
	
	// Generates the map by calling each generate method
	public void generate() {
		createOcean();
		createRiver();
		cleanUpRiver();
		cleanUpBeach();
		createTrees();
		spawnFood();
		spawnStone();
		spawnWorkers();
	}
	
	/**************************************
	 *    Create Methods (and helpers)    *
	 **************************************/


	/**~~~~~~~~~~~~~~ OCEAN ~~~~~~~~~~~~~**/
	
	//removes solo blocks of sand
	
	private void cleanUpBeach() {
		int sandCount = 0;
		int oceanCount = 0;
		for(int x = 1; x < 98; x++){
			for(int y = 1; y < 98; y++){
				if(board[x][y].getLand().equals(Terrain.BEACH)){
					
					if(board[x][y+1].getLand().equals(Terrain.BEACH))
						sandCount+=1;
					if(board[x+1][y].getLand().equals(Terrain.BEACH))
						sandCount+=1;
					if(board[x][y-1].getLand().equals(Terrain.BEACH))
						sandCount+=1;
					if(board[x-1][y].getLand().equals(Terrain.BEACH))
						sandCount+=1;
					
					if(board[x][y+1].getLand().equals(Terrain.OCEAN))
						oceanCount+=1;
					if(board[x+1][y].getLand().equals(Terrain.OCEAN))
						oceanCount+=1;
					if(board[x][y-1].getLand().equals(Terrain.OCEAN))
						oceanCount+=1;
					if(board[x-1][y].getLand().equals(Terrain.OCEAN))
						oceanCount+=1;
					
					if(sandCount == 1)
						if(oceanCount == 0)
						board[x][y].setLand(Terrain.PLAIN);
						else
						board[x][y].setLand(Terrain.OCEAN);
					oceanCount = 0;
					sandCount = 0;
				}		
			}
		}
	}
	// Generates the ocean on one side of the map
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
	
	// Ensures that the ocean is generated together and doesnt turn out as a river
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
	
	// Fills the ocean once generated
	private void OceanFilling(ArrayList<Direction> oceanTiles, Direction initial, Point init, boolean fillTo, boolean topLeft) {
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
	
	
	/**~~~~~~~~~~~~~~ RIVERS ~~~~~~~~~~~~**/
	
	// Generates the rivers that flow across the map
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

	// Helper method in generating the river to ensure it looks like a river
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
	
	// Similar to ocean, this method fills up the river
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
	
	
	
	/**~~~~~~~~~~~~~~ River Cleanup ~~~~~~~~~~~~~**/
	private void cleanUpRiver() {
		int watCount = 0;
		for(int x = 1; x < 99; x++){
			for(int y = 1; y < 99; y++){
				if(board[x][y].getLand().equals(Terrain.RIVER)){
					
					if(board[x][y+1].getLand().equals(Terrain.RIVER)|| board[x][y+1].getLand().equals(Terrain.OCEAN))
						watCount+=1;
					if(board[x+1][y].getLand().equals(Terrain.RIVER)|| board[x][y+1].getLand().equals(Terrain.OCEAN))
						watCount+=1;
					if(board[x][y-1].getLand().equals(Terrain.RIVER)|| board[x][y+1].getLand().equals(Terrain.OCEAN))
						watCount+=1;
					if(board[x-1][y].getLand().equals(Terrain.RIVER)|| board[x][y+1].getLand().equals(Terrain.OCEAN))
						watCount+=1;
					
					if(watCount == 1)
						board[x][y].setLand(Terrain.PLAIN);
					watCount = 0;
				}
			}
		}
	}

	
	/**~~~~~~~~~~~~~~ TREES ~~~~~~~~~~~~**/
	
	// Generates the trees in spread "clusters" across the map
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
								TreeList.add(new Job(new Point(i,j),board[j][i].getResource()));
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
	/********************************************
	 *            Harvestable Methods           *
	 ********************************************/
	public void setHarvestable(){
		//for Stone
				int berrySize = BerryList.size();
				for(int i = 0; i < berrySize; i++){

				if(BerryList.get(i).resource.getQuantity() < 4.0){
					BerryList.get(i).resource.setHarvestable(false);
					}	
				else{
					if(BerryList.get(i).resource.getQuantity()>=20)
						BerryList.get(i).resource.setHarvestable(true);
				}
				}
				
		
		//for Stone
		int stoneSize = StoneList.size();
		for(int i = 0; i < stoneSize; i++){

		if(StoneList.get(i).resource.getQuantity() < 4.0){
			StoneList.get(i).resource.setHarvestable(false);
			}	
		else{
			if(StoneList.get(i).resource.getQuantity()>=20)
				StoneList.get(i).resource.setHarvestable(true);
		}
		}
			
			
		//for fish
		int fishSize = FishList.size();
		for(int i = 0; i < fishSize; i++){
		if(FishList.get(i).resource.getQuantity() < 4.0){
			FishList.get(i).resource.setHarvestable(false);
			}	
		else{
			if(FishList.get(i).resource.getQuantity()>=20)
				FishList.get(i).resource.setHarvestable(true);
		}	
		}
			
		//for Wood
		int treeSize = TreeList.size();
		for(int i = 0; i < treeSize; i++){
		if(TreeList.get(i).resource.getQuantity() < 4.0){
			TreeList.get(i).resource.setHarvestable(false);
			}	
		else{
			if(TreeList.get(i).resource.getQuantity()>=20)
				TreeList.get(i).resource.setHarvestable(true);
		}
		}
	}
	
	
	/**************************************
	 *            Spawn Methods           *
	 **************************************/
	
	/**~~~~~~~~~~~~~~ FOOD ~~~~~~~~~~~~~**/
	
	// Calls spawnFish, spawnBushes, and spawnAnimals to generate all food sources
	private void spawnFood() {
		spawnFish();
		spawnBushes();
		spawnAnimals();
	}
	
	// Spawns Salt water fish in the ocean, and fresh water fish in the rivers
	private void spawnFish() {
		//  Spawns Fish in the river
		for(int i = 0; i < gen.nextInt(7) + 10; i++) {
			Point point = new Point(gen.nextInt(size), gen.nextInt(size));
			while(!board[point.x][point.y].getLand().equals(Terrain.RIVER)) {
				point.x = gen.nextInt(size);
				point.y = gen.nextInt(size);
			}
			board[point.x][point.y].setResource(new Fish());
			FishList.add(new Job(new Point(point.x,point.y), board[point.x][point.y].getResource()));
		}
		//  Spawns Salty Fish in the Ocean
		for(int i = 0; i < gen.nextInt(30) + 20; i++) {
			Point point = new Point(gen.nextInt(size), gen.nextInt(size));
			while(!board[point.y][point.x].getLand().equals(Terrain.OCEAN)) {
				point.x = gen.nextInt(size);
				point.y = gen.nextInt(size);
			}
			board[point.y][point.x].setResource(new SaltyFish());
			FishList.add(new Job(new Point(point.y,point.x), board[point.y][point.x].getResource()));
		} 
	}
	
	// Spawns berry bushes across the map
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
			BerryList.add(new Job(new Point(Y,X),board[X][Y].getResource()));
			dingDangBushes --;
			treeCounter = 0;
			}
		}
	}
	
	// Spawns animals at start of game
	private void spawnAnimals() {
		//  Spawns some animal companions
		Point point = new Point(gen.nextInt(size), gen.nextInt(size));
		while(!board[point.x][point.y].getLand().equals(Terrain.PLAIN)
				&&(board[point.x][point.y].getResource().getResourceT().equals(ResourceType.NONE))) {
			point.x = gen.nextInt(size);
			point.y = gen.nextInt(size);
		}
		//board[point.x][point.y].setResource(new Meese());
	}
	
	

	public void regenFood() {
		int BerrySize = BerryList.size();
		int FishSize = FishList.size();
		
		for(int i = 0; i < BerrySize; i ++){
			Job temp = BerryList.get(i);
			
			board[temp.location.y][temp.location.x].regenResource();
		}
		
		for(int i = 0; i < FishSize; i ++){
			Job temp = FishList.get(i);
			
			board[temp.location.y][temp.location.x].regenResource();
		}
		
	}
	/**~~~~~~~~~~~~~~ STONE ~~~~~~~~~~~~**/
	
	// Spawns stone across the map in small "clusters"
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
				
				StoneList.add(new Job(new Point (Y,X), board[X][Y].getResource()));
				StoneList.add(new Job(new Point (Y,X+1), board[X][Y+1].getResource()));
				StoneList.add(new Job(new Point (Y+1,X), board[X+1][Y].getResource()));
				StoneList.add(new Job(new Point (Y+1,X+1), board[X+1][Y+1].getResource()));
				
				Outcroppings--;
		}
		}
	}

	/**~~~~~~~~~~~~~ WORKERS ~~~~~~~~~~~**/
	
	// Spawns the workers at the start of the game
	private void spawnWorkers() {
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
		TownHall highHrothgar = new TownHall(new Point(X + 1,Y + 1));
		storageList.add(highHrothgar);
		TH = new Point(X+1,Y+1);
	

		int initialNoWorker = 4;
		while(initialNoWorker!= 0) {
			for(int i = X; i < X + 5; i++) {
				for(int j = Y; j < Y + 7; j++) {
					boolean broke = false;
					while(board[j][i].getLand().equals(Terrain.BEACH)) {
						if(j==Y+7) {
							broke = true;
							break;
						}	
						j++;
					}
					if(broke) {
						System.out.println("Oh");
						continue;
					}
					if(gen.nextDouble()<.25) {
						int chance = gen.nextInt(100);
						
						if(initialNoWorker != 0) {
							if(chance <25){
								Worker James = new KJG(new Point(i,j));
								initialNoWorker --;
								initialWorkers.add(James);
							}
							
							else if(chance <50){
								Worker James = new KJD(new Point(i,j));
								initialNoWorker --;
								initialWorkers.add(James);
							}
							
							else if(chance <75){
								Worker SleepingBeauty = new Brett(new Point(i,j));
								initialNoWorker --;
								initialWorkers.add(SleepingBeauty);
							}
						
							else if(chance <100){
								Worker BeardedWonder = new David(new Point(i,j));
								initialNoWorker --;
								initialWorkers.add(BeardedWonder);
							}
							
						}
					}
				}
			}
		}		
	}
	private ArrayList<Buildable> buildings = new ArrayList<>();
	@Override
	public void update(Observable arg0, Object arg1) {
		ThePackage packages = (ThePackage)arg1;
		buildings = packages.getBuildings();
		ArrayList<Storage> store = new ArrayList<>();
		for(int i = 0; i < buildings.size(); i++) {
			if(buildings.get(i)instanceof Storage)
				store.add((Storage)buildings.get(i));
		}
		storageList = store;
	}
	public ArrayList<Buildable> getBuildings() {
		return buildings;
	}
}
