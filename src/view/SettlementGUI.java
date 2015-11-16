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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Map;
import model.MapTile;
import model.ResourceType;

public class SettlementGUI extends JFrame {
	private int size;
	private MiniMap minimap = null;
	private OptionsGUI options = null;
	private MapPanel mapView = null;
	private Map map = null;
	private MapTile[][] board = null;
	public SettlementGUI(int sizeOfMap) {
		size = sizeOfMap;
		map = new Map(getMapSize() ,1234132513);
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		//this.setSize(new Dimension(1080, 720));
		this.setLayout(null);
		mapView = new MapPanel(this);
		mapView.setSize(this.getSize());
		mapView.setLocation(0,0);
		mapView.setMaxScroll();
		mapView.addMouseListener(new ClickerListener());
		this.add(mapView);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		//this.requestFocus();
		this.addWindowListener(new WindogeListener());
		minimap = new MiniMap(this);
		
		options = new OptionsGUI(this);
		this.addMouseListener(new ClickerListener());
		minimap.relocateToBottomRight();
		this.setUndecorated(true);
		this.setVisible(true);
		mapView.addMouseMotionListener(new MapMotionListener());
		board = map.getMapTiles();
	}
	public void CloseEverything() {
		minimap.dispose();
		options.dispose();
		this.dispose();
	}
	public int getMapSize() {
		return 100;
	}
	private boolean goingNorth = false;
	private boolean goingSouth = false;
	private boolean goingEast = false;
	private boolean goingWest = false;
	private Timer movementTimer = new Timer(69, new MapMovementAction());
	private class MapMovementAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(goingNorth)
				mapView.increaseY();
			if(goingSouth)
				mapView.decreaseY();
			if(goingWest)
				mapView.decreaseX();
			if(goingEast)
				mapView.increaseX();
			mapView.paintIt();
			minimap.getGraphPanel().paintIt();
		}
	}
	private class MapMotionListener implements MouseMotionListener {
		private boolean triggered = false;
		private int dividor = 12;
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			//  Going South?
			if(arg0.getY() < getHeight()/dividor) {
				goingSouth = true;
				triggered = true;
			}
			if(arg0.getY() >= getHeight()/dividor) {
				goingSouth = false;
			}
			//  Going North by any chance?
			if(arg0.getY() > getHeight() - getHeight()/dividor) {
				goingNorth = true;
				triggered = true;
			}
			if(arg0.getY() <= getHeight() - getHeight()/dividor) {
				goingNorth = false;
			}
			//  Going West or Nah?
			if(arg0.getX() < getWidth()/dividor) {
				goingWest = true;
				triggered = true;
			}
			if(arg0.getX() >= getWidth()/dividor) {
				goingWest = false;
			}
			//  Going East by any chance?
			if(arg0.getX() > getWidth() - getWidth()/dividor) {
				triggered = true;
				goingEast = true;
			}
			if(arg0.getX() <= getWidth() - getWidth()/dividor) {
				goingEast = false;
			}
			if(triggered) {
				movementTimer.start();
			}
			else {
				movementTimer.stop();
			}
			triggered = false;
			//  System.out.println("HODOR!");
		}
	}
	private class ClickerListener implements MouseListener{
		private ResourceFrame frame;
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
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
			//System.out.println("X,Y: " + point.x + ", " + point.y);
			Point point = mapView.getArrayLocationOfClicked(arg0.getX(), arg0.getY());
			//  null needs to be our list of workers
			if(board[point.y][point.x].getResource().getResourceT().equals(ResourceType.NONE))
				return;
			if (frame != null)
				frame.dispose();
			frame = new ResourceFrame(arg0.getPoint(), point, board[point.y][point.x].getResource(), null);
				//int[] oh = new int[1];
				//System.out.println(oh[2]);
				//  lel such funny joke
			}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	private class WindogeListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
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
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
		}
	}
	public static void main(String[] args) {
		SettlementGUI gui = new SettlementGUI(100);
	}
	public Map getMap() {
		// TODO Auto-generated method stub
		return map;
	}
	public MapPanel getMapPanel() {
		// TODO Auto-generated method stub
		return mapView;
	}
}
