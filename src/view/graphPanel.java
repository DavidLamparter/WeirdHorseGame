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
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.ListOfImages;
import model.ListOfWorkers;
import model.Map;
import model.MapTile;
import model.Resource;
import model.ResourceType;
import model.Terrain;
import model.ThePackage;

public class graphPanel extends JPanel implements Observer {
	
	MapTile[][] graph;
	int width;
	int length;
	SettlementGUI caller = null;
	Map map = null;
	MapPanel mapPanel;
	ListOfWorkers workmen;
	
	graphPanel(MapTile[][] graph, SettlementGUI caller) {
		this.caller = caller;
		mapPanel = caller.getMapPanel();
		this.map = caller.getMap();
		this.width = graph.length;
		this.length = graph[0].length;
		this.graph = new MapTile[(width) * 2 + 1][(length) * 2 + 1];

		for (int i = 0; i < width * 2; i++) {
			for (int j = 0; j < length * 2; j++) {
				this.graph[i][j] = graph[i / 2][j / 2];
			}
		}
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
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {

				try {
					// Trees
					if (graph[j][i].getResource().getResourceT().equals(ResourceType.TREE)) {
						g2d.setColor(DarkGreen);
						g2d.fillRect(i, j, 2, 2);
					}
					// Fish
					else if (graph[j][i].getResource().getResourceT().equals(ResourceType.FISH)) {
						g2d.setColor(Color.CYAN);
						g2d.fillRect(i, j, 2, 2);					
						}
					// River
					else if (graph[j][i].getLand().equals(Terrain.RIVER)) {
						g2d.setColor(Color.BLUE);
						g2d.fillRect(i, j, 2, 2);					
						}
					// SaltyFish
					else if (graph[j][i].getResource().getResourceT().equals(
							ResourceType.SALTY_FISH)) {
						g2d.setColor(Color.magenta);
						g2d.fillRect(i, j, 2, 2);
					}
					// Ocean
					else if (graph[j][i].getLand().equals(Terrain.OCEAN)) {
						g2d.setColor(new Color(20, 20, 200));
						g2d.fillRect(i, j, 2, 2);
						}
					// Sand
					else if (graph[j][i].getLand().equals(Terrain.BEACH)) {
						g2d.setColor(Color.ORANGE);
						g2d.fillRect(i, j, 2, 2);
						}
					// Berry Bush
					else if (graph[j][i].getResource().getResourceT().equals(
							ResourceType.BERRY_BUSH)) {
						g2d.setColor(Color.RED);
						g2d.fillRect(i, j, 2, 2);
						}
					// Stone
					else if (graph[j][i].getResource().getResourceT().equals(ResourceType.STONE)) {
						g2d.setColor(Color.GRAY);
						g2d.fillRect(i, j, 2, 2);
						}
					// Plain
					else {
						g2d.setColor(LightGreen);
						g2d.fillRect(i, j, 2, 2);
					}

					/*
					 * if (i % 2 == 0 || j % 2 == 0) {
					 * g2d.setColor(Color.BLACK); g2d.drawLine(i, j, i, j); }
					 */
				}

				catch (NullPointerException E) {
					//System.out.println("Why");
				}

			}
		}
		if(workmen!=null) {
			int workSize = workmen.size();
			for(int i = 0; i < workSize; i++) {
				Point l = workmen.get(i).getPoint();
				g2d.setColor(Color.BLACK);
				g2d.fillRect(l.x*2, l.y*2, 2, 2);
			}
		}
		//  DRAW RECTANGE AROUND LOCATION
		g2d.setColor(new Color(255, 255, 135));
		g2d.drawRect(mapPanel.getInitialPoint().x*2, mapPanel.getInitialPoint().y*2,
				mapPanel.getMapWidth()*2, mapPanel.getMapHeight()*2);
	}
	public void paintIt() {
		repaint();
	}
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g, graph);
	}

	@Override
	public void update(Observable o, Object arg) {
		workmen = ((ThePackage)arg).getWorkers();
		repaint();		
	}
}