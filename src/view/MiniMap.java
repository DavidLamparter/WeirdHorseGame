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
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import model.KJD;
import model.Map;
import model.Worker;

public class MiniMap extends JFrame{
	
	//public static void main(String[] args) {
		// TODO Auto-generated method stub
		//MiniMap main = new MiniMap(new SettlementGUI(100));
	//}
	private graphPanel panel = null;
	private JLabel maximize = new JLabel(" +");
	private SettlementGUI caller;
	public MiniMap(SettlementGUI caller){
	Map map = new Map(caller.getMapSize());
	this.caller = caller;
	this.setSize(200,200);
	this.setUndecorated(true);
	this.setAlwaysOnTop(true);
	this.setLocation(caller.getLocation().x+caller.getWidth()-this.getWidth(), 
			caller.getLocation().y + caller.getHeight()-this.getHeight());
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setLayout(null);
	panel = new graphPanel(map.getMapTiles());
	panel.setSize(this.getSize());
	panel.setLocation(0,0);
	maximize.setSize(this.getSize());
	maximize.setLocation(1,1);
	maximize.setFont(new Font("Comic Sans Ms", Font.PLAIN, 28));
	
	this.addMouseListener(new ClickerListener());
    this.add(panel);
	this.add(maximize);
	this.setVisible(true);
	}
	public void relocateToBottomRight() {
		setLocation(caller.getLocation().x+caller.getWidth()-getWidth(), 
				caller.getLocation().y + caller.getHeight()-getHeight());
	}
	public void toggleInvisible() {
		this.setVisible(!this.isVisible());
	}
	private class ClickerListener implements MouseListener {
		private boolean firstClick = false;
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			firstClick = !firstClick;
			if(firstClick) {
				setSize(new Dimension(25,25));
				maximize.setVisible(true);
				panel.setVisible(false);
			}
			else {
				setSize(200,200);
				maximize.setVisible(false);
				panel.setVisible(true);
			}
			relocateToBottomRight();
			maximize.setLocation(1,1);
			maximize.setSize(getSize());
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}
