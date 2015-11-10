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

package view;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import model.MapTile;

public class SettlementGUI extends JFrame {
	private int size;
	private MiniMap minimap = null;
	public SettlementGUI(int sizeOfMap) {
		size = sizeOfMap;
		this.setSize(new Dimension(1000,600));
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.requestFocus();
		this.addWindowListener(new WindogeListener());
		minimap = new MiniMap(this);
		this.addMouseListener(new MouseExitListener());
	}
	public int getMapSize() {
		return 100;
	}
	private class MouseExitListener implements MouseListener, MouseMotionListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			minimap.relocateToBottomRight();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			minimap.relocateToBottomRight();
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	private class WindogeListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			minimap.relocateToBottomRight();
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			requestFocus();
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			minimap.toggleInvisible();
			minimap.relocateToBottomRight();
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			minimap.toggleInvisible();
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	public static void main(String[] args) {
		SettlementGUI gui = new SettlementGUI(100);
	}
}
