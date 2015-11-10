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
|  Description:  Visuals for map(colors no graphics)
|                
| Deficiencies:  We know of no unsatisfied requirements and no logic errors.
*=================================================================================================*/

package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.KJD;
import model.Map;
import model.Worker;

public class Main extends JFrame{
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main main = new Main();
	}
	Main(){
	Map map = new Map(100);
	this.setSize(1000,1000);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	graphPanel panel = new graphPanel(map.getMapTiles());

	panel.setPreferredSize(new Dimension(1000,1000));
	
	JScrollPane scroll = new JScrollPane(panel);
	this.add(scroll);
	this.setVisible(true);
	}
}
