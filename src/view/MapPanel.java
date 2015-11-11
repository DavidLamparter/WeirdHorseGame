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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import model.MapTile;
import model.Resource;
import model.Terrain;

public class MapPanel extends JPanel {

	MapTile[][] graph;
	int width;
	int length;
	public static final int MAP_TILE_WIDTH = 25;
	public static final int MAP_TILE_HEIGHT = 25;
	private int initialx = 0;
	private int initialy = 0;
	private int maxX = 0;
	private int maxY = 0;
	private SettlementGUI caller;

	MapPanel(SettlementGUI caller) {
		this.caller = caller;
		this.graph = caller.getMap().getMapTiles();
		this.width = graph.length;
		this.length = graph[0].length;
		//this.graph = new MapTile[(width) + 1][(length) + 1];

	/*	for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				this.graph[i][j] = graph[i][j];
			}
		} */
		repaint();
	}

	private void doDrawing(Graphics g, MapTile[][] graph) {
		Color DarkGreen = new Color(20, 120, 20);
		Color LightGreen = new Color(130, 190, 60);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLUE);

		int width = this.graph.length;
		int length = this.graph[0].length;

		// iterates through the array and blocks the colors
		for (int ilol = 0; ilol < width; ilol++) {
			for (int jlol = 0; jlol < length; jlol++) {
				int i = ilol + initialx;
				int j = jlol + initialy;
				try {
					// Trees
					if (graph[j][i].getResource().equals(Resource.TREE)) {
						g2d.setColor(DarkGreen);
						g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
					}
					// Fish
					else if (graph[j][i].getResource().equals(Resource.FISH)) {
						g2d.setColor(Color.CYAN);
						g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
					}

					// River
					else if (graph[j][i].getLand().equals(Terrain.RIVER)) {
						g2d.setColor(Color.BLUE);
						g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
					}
					// SaltyFish
					else if (graph[j][i].getResource().equals(
							Resource.SALTY_FISH)) {
						g2d.setColor(Color.magenta);
						g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
					}
					// Ocean
					else if (graph[j][i].getLand().equals(Terrain.OCEAN)) {
						g2d.setColor(new Color(20, 20, 200));
						g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
					}
					// Sand
					else if (graph[j][i].getLand().equals(Terrain.BEACH)) {
						g2d.setColor(Color.ORANGE);
						g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
					}
					// Berry Bush
					else if (graph[j][i].getResource().equals(
							Resource.BERRY_BUSH)) {
						g2d.setColor(Color.RED);
						g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
					}
					// Stone
					else if (graph[j][i].getResource().equals(Resource.STONE)) {
						g2d.setColor(Color.GRAY);
						g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
					}
					// Plain
					else {
						g2d.setColor(LightGreen);
						g2d.fillRect(ilol*MAP_TILE_WIDTH, jlol*MAP_TILE_HEIGHT, MAP_TILE_WIDTH, MAP_TILE_HEIGHT);
					}

					/*
					 * if (i % 2 == 0 || j % 2 == 0) {
					 * g2d.setColor(Color.BLACK); g2d.fillRect(i*25, j*25, 25, 25); }
					 */
				}
				//NullPointer
				catch (Exception E) {
					// System.out.println("Why");
				}

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
	
}