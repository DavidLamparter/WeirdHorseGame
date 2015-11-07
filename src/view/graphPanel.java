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
|  Description:  simple graphic panel based on colors
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.MapTile;
import model.Resource;
import model.Terrain;

public class graphPanel extends JPanel {

	MapTile[][] graph;
	int width;
	int length;

	graphPanel(MapTile[][] graph) {

		this.width = graph.length;
		this.length = graph[0].length;
		this.graph = new MapTile[(width) * 10 + 1][(length) * 10 + 1];
		
		for (int i = 0; i < width * 10; i++) {
			for (int j = 0; j < length * 10; j++) {
				this.graph[i][j] = graph[i / 10][j / 10];
			}
		}
		repaint();
	}

	private void doDrawing(Graphics g, MapTile[][] graph) {
		Color DarkGreen = new Color(20,120,20);
		Color LightGreen = new Color(130,190,60);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLUE);

		int width = this.graph.length;
		int length = this.graph[0].length;

		// iterates through the array and blocks the colors
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				
				
				try{
				// Trees
				if (graph[j][i].getResource().equals(Resource.TREE)) {
					g2d.setColor(DarkGreen);
					g2d.drawLine(i, j, i, j);
				}
				//Fish
				else if (graph[j][i].getResource().equals(Resource.FISH)) {
					g2d.setColor(Color.CYAN);
					g2d.drawLine(i, j, i, j);
				}
			
				//River
				else if (graph[j][i].getLand().equals(Terrain.RIVER)) {
					g2d.setColor(Color.BLUE);
					g2d.drawLine(i, j, i, j);
				}
				//Berry Bush
				else if (graph[j][i].getResource().equals(Resource.BERRY_BUSH)) {
					g2d.setColor(Color.RED);
					g2d.drawLine(i, j, i, j);
				}
				//Stone
				else if (graph[j][i].getResource().equals(Resource.STONE)) {
					g2d.setColor(Color.GRAY);
					g2d.drawLine(i, j, i, j);
				}
				//Plain
				else{
					g2d.setColor(LightGreen);
					g2d.drawLine(i, j, i, j);
				}
				
				if (i % 10 == 0 || j % 10 == 0) {
					g2d.setColor(Color.BLACK);
					g2d.drawLine(i, j, i, j);
				}
				}
				catch(NullPointerException E){
					//System.out.println("Why");
				}

			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g, graph);
	}
}