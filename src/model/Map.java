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
		createRiver();
	}
	private void createRiver() {
		//  for lack of generality a random topleft point, to a random bottom right point
		Point init = new Point((int)(Math.random()*board.length), (int)(Math.random()*board.length));
		Point fin = new Point((int)(Math.random()*board.length), (int)(Math.random()*board.length));
		double distance = 0;
		while((distance < ((double)(board.length))/1.5)||(Math.abs(init.x-fin.x)<4)||(Math.abs(init.y-fin.y)<4)) {
			init.x = (int)(Math.random()*board.length);
			init.y = (int)(Math.random()*board.length);
			fin.x = (int)(Math.random()*board.length);
			fin.y = (int)(Math.random()*board.length);
			distance = Math.sqrt(Math.pow((fin.x-init.x),2)+Math.pow((fin.y-init.y),2));
		}
		ShortestPathCalculator riverMaker = new ShortestPathCalculator(board);
		riverFilling(riverMaker.getShortestPath(init, fin), init);
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
					board[init.y][init.x].setLand(Terrain.RIVER);
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
					board[init.y][init.x].setLand(Terrain.RIVER);
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
					board[init.y][init.x].setLand(Terrain.RIVER);
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
					board[init.y][init.x].setLand(Terrain.RIVER);
				}
				init.x--;
			}
		}
	}
	public static void main(String[] args) {
		Map hodor = new Map(30);
		System.out.println(hodor.toString());
	}
}
