/*================================================================================================
|   Assignment:  FINAL PROJECT - Settlement Management
|      Authors:  David Lamparter (Lamparter@email.arizona.edu)
|                Kyle Grady (kgrady1@email.arizona.edu)
|    			 Kyle DeTar (kdeTar@email.arizona.edu)
|	  			 Brett Cohen (brett7@email.arizona.edu)
|                       
|       Course:  331
|   Instructor:  R. Mercer
|           PM:  Sean Stephens
|     Due Date:  12/9/11
|
|  Description:  simple graphic panel based on colors
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
 *=================================================================================================*/

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Buildable;
import model.Game;
import model.ListOfImages;
import model.ListOfWorkers;
import model.MapTile;
import model.ResourceType;
import model.Terrain;
import model.ThePackage;

public class MapPanel extends JPanel implements Observer{

	ListOfImages images = new ListOfImages();
	ArrayList<Buildable> buildingList = new ArrayList<>();
	MapTile[][] graph;
	int width;
	int length;
	public static final int MAP_TILE_WIDTH = 50;
	public static final int MAP_TILE_HEIGHT = 50;
	private int initialx = 0;
	private int initialy = 0;
	private int maxX = 0;
	private int maxY = 0;
	private SettlementGUI caller;
	private ListOfWorkers workmen;
	private boolean isWinter;
	
	// Variables for the array of tiles for the background
	private Image[][] summerTiles = new Image[50][50];
	private String[][] sTileNames = new String[50][50];

	//Colors
	Color DarkGreen = new Color(20, 120, 20);
	Color LightGreen = new Color(130, 190, 60);

	MapPanel(SettlementGUI caller) {
		this.caller = caller;
		this.graph = caller.getMap().getMapTiles();
		this.width = graph.length;
		this.length = graph[0].length;
		isWinter = false;
		//this.setBackground(LightGreen);
		backgroundTiles(sTileNames, summerTiles);
		
		//this.graph = new MapTile[(width) + 1][(length) + 1];

	/*	for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				this.graph[i][j] = graph[i][j];
			}
		} */
		repaint();

		
	}
	
	// create the array of images for the ground in summer
	public void backgroundTiles(String[][] sNames, Image[][] sPic){
		int counter = 1;
		System.out.println("STARTED READING FILES");
		// create the array file names first so that it's easier to read in to 2Darray
		for (int i=0; i < 50; i++){
			for (int j=0; j < 50; j++){
				sTileNames[i][j] = "./Graphics/Ground/summerTiles/sTile_" + counter + ".png";
				counter++;
			}
		}
		// creates the image 2Darray from file names
		for (int i=0; i < 50; i++){
			for (int j=0; j < 50; j++){
				try {
					summerTiles[i][j] = ImageIO.read(new File(sTileNames[i][j]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("FINISHED READING FILES");
		sNames = sTileNames;
	    sPic = summerTiles;
		
	}

	private void doDrawing(Graphics g, MapTile[][] graph) {
		Random gen = new Random();
		Graphics2D g2d = (Graphics2D) g;

		int width = this.graph.length;
		int length = this.graph[0].length;
	// iterates through the array and blocks the colors
		int width2 = getMapWidth();
		int length2 = getMapHeight();
		
		//draw the ground first
		for (int ilol = 0; ilol < width2/2; ilol++) {
			for (int jlol = 0; jlol < length2/2; jlol++) {
				int i = ilol + initialx/2;
				int j = jlol + initialy/2;
				g2d.drawImage(summerTiles[j][i], ilol*(MAP_TILE_WIDTH*2), jlol*MAP_TILE_HEIGHT*2, null);
			}
		}
		
		
		
		for (int ilol = 0; ilol < width2; ilol++) {
			for (int jlol = 0; jlol < length2; jlol++) {
				int i = ilol + initialx;
				int j = jlol + initialy;
				
				// Fish
				/*if (graph[j][i].getResource().getResourceT().equals(ResourceType.FISH)) {
					g2d.setColor(Color.CYAN);
					g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
				} */
				// River
				if (graph[j][i].getLand().equals(Terrain.RIVER)) {
					g2d.setColor(Color.BLUE);
					g2d.drawImage(images.getWater(isWinter), ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, null);
					//g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
				}
				// SaltyFish
				/* else if (graph[j][i].getResource().getResourceT().equals(ResourceType.SALTY_FISH)) {
					g2d.setColor(Color.magenta);
					g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
				} */
				// Ocean
				else if (graph[j][i].getLand().equals(Terrain.OCEAN)) {
					g2d.setColor(new Color(20, 20, 200));
					g2d.drawImage(images.getWater(isWinter), ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, null);
				}
				// Sand
				else if (graph[j][i].getLand().equals(Terrain.BEACH)) {
					g2d.setColor(Color.ORANGE);
					g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
				}
				//  i think this will draw all the resources
				if (graph[j][i].getResource().getResourceT()!=ResourceType.NONE) {
					if(!isWinter)
						g2d.drawImage(images.getResource(graph[j][i].getResource().getFileName(),isWinter), ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, null);
					else
						g2d.drawImage(images.getResource(graph[j][i].getResource().getWinterFileName(),isWinter), ilol*MAP_TILE_WIDTH+ (graph[j][i].getResource().Offset), jlol*MAP_TILE_HEIGHT-50, null);
				}
				// Berry Bush
				/*else if (graph[j][i].getResource().getResourceT().equals(ResourceType.BERRY_BUSH)) {
					try {
						g2d.drawImage(ImageIO.read(new File("./Graphics/BerryBushes/BerryBush_1.png")), ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
				// Stone
				else if (graph[j][i].getResource().getResourceT().equals(ResourceType.STONE)){
						//&& graph[j+1][i+1].getResource().getResourceT().equals(ResourceType.STONE)){
					try {
						g2d.drawImage(ImageIO.read(new File("./Graphics/Stone/Stone_1.png")), ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} */
				
				/*
				 * if (i % 2 == 0 || j % 2 == 0) {
				 * g2d.setColor(Color.BLACK); g2d.fillRect(i*25, j*25, 25, 25); }
				 */
			}
		}
		drawThemBuildings(g2d);
		drawThemWorkers(g2d);
		for (int ilol = 0; ilol < width2; ilol++) {
			for (int jlol = 0; jlol < length2; jlol++) {
				int i = ilol + initialx;
				int j = jlol + initialy;
				// Trees Go on top of workers so uhh yesah we need a nother loop
				if (graph[j][i].getResource().getResourceT().equals(ResourceType.TREE)) {
					if(!isWinter)
						g2d.drawImage(images.getResource(graph[j][i].getResource().getFileName(),isWinter), ilol*MAP_TILE_WIDTH+ (graph[j][i].getResource().Offset), jlol*MAP_TILE_HEIGHT-50, null);
					else
						g2d.drawImage(images.getResource(graph[j][i].getResource().getWinterFileName(),isWinter), ilol*MAP_TILE_WIDTH+ (graph[j][i].getResource().Offset), jlol*MAP_TILE_HEIGHT-50, null);
				}
			}
		}
	}
	
	private void drawThemBuildings(Graphics2D g2d) {
		for(int i = 0; i < buildingList.size(); i++) {
			int x = (int) buildingList.get(i).getPoints().get(0).getX() - initialx;
			int y = (int) buildingList.get(i).getPoints().get(0).getY() - initialy;
			if(!isWinter)
				g2d.drawImage(images.getBuilding(buildingList.get(i).getImageFile(), isWinter), x * MAP_TILE_WIDTH, y * MAP_TILE_HEIGHT, null); 
			else
				g2d.drawImage(images.getBuilding(buildingList.get(i).getImageFile(), !isWinter), x * MAP_TILE_WIDTH, y * MAP_TILE_HEIGHT, null); 
		}
	}

	private void drawThemWorkers(Graphics2D g) {
		//  No animation yet... Sorry bois
		if(workmen!=null) {
			int workmenSize = workmen.size();
			for(int i = 0; i < workmenSize; i++) {
				Point l = workmen.get(i).getPoint();
				g.drawImage(images.getWorker(workmen.get(i).animationFrameFileName(), isWinter), (l.x-initialx)*50, (l.y-initialy)*50, null);
				//System.out.printf("Worker %d X Location: %d, Y Location: %d\n",i, l.x-initialx, l.y-initialx);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g, graph);
	}
	public void increaseX() {
		if(initialx < maxX)
			initialx ++;
	}
	public void increaseY() {
		if(initialy < maxY)
			initialy ++;
	}
	public void decreaseX() {
		if(initialx > 0)
			initialx --;
	}
	public void decreaseY() {
		if(initialy > 0)
			initialy --;
	}
	public void setMaxScroll() {
		maxY = length - this.getHeight()/MAP_TILE_HEIGHT -1;
		maxX = width - this.getWidth()/MAP_TILE_WIDTH -1;
	}
	public void paintIt() {
		repaint();
	}
	public Point getInitialPoint() {
		return new Point(initialx, initialy);
	}

	public int getMapWidth() {
		// TODO Auto-generated method stub
		return this.getWidth()/MAP_TILE_HEIGHT +1;
	}
	public int getMapHeight() {
		return this.getHeight()/MAP_TILE_WIDTH +1;
	}
	public Point getArrayLocationOfClicked(int xCord, int yCord) {
		xCord /= MAP_TILE_WIDTH;
		yCord /= MAP_TILE_HEIGHT;
		xCord += initialx;
		yCord += initialy;
		return new Point(xCord, yCord);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ThePackage ITS_THE_FUCKING_PACKAGE = (ThePackage)arg1;
		workmen = ITS_THE_FUCKING_PACKAGE.getWorkers();
		buildingList = ITS_THE_FUCKING_PACKAGE.getBuildings();
		//  thanks c9 Sneaky
		Game noPackageNeeded = (Game)arg0;
		this.isWinter = noPackageNeeded.isWinter();
		//  forgot about that... 
		//  Well at least I had fun making the package
		repaint();
	}
	
	public void setInitialPoint(Point point) {
		initialx = point.x / 2 - caller.getWidth()/MAP_TILE_WIDTH/2; //  it just takes some time
		initialy = point.y / 2 - caller.getHeight()/MAP_TILE_HEIGHT/2; //  the middle 
		if(initialx <= 0)
			initialx = 0;
		if(initialy <= 0)
			initialy = 0;
		if(initialx >= (MAP_TILE_WIDTH*caller.getMapSize()-caller.getWidth())/MAP_TILE_WIDTH)
			initialx = (MAP_TILE_WIDTH*caller.getMapSize()-caller.getWidth())/MAP_TILE_WIDTH;
		if(initialy >= (MAP_TILE_HEIGHT*caller.getMapSize()-caller.getHeight())/MAP_TILE_HEIGHT) 
			initialy = (MAP_TILE_HEIGHT*caller.getMapSize()-caller.getHeight())/MAP_TILE_HEIGHT;
	}
	
}