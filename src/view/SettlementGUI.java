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
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Buildable;
import model.Game;
import model.Map;
import model.MapTile;
import model.ResourceType;
import model.Storage;
import model.Worker;

public class SettlementGUI extends JFrame {
	private int size;
	private MiniMap minimap = null;
	private OptionsGUI options = null;
	private MapPanel mapPanel = null;
	private BuildingPanel buildings;
	private QueueFrame theQueueFrame;
	private Map map = null;
	private MapTile[][] board = null;
	private Game game;
	
	public SettlementGUI(int sizeOfMap) {
		size = sizeOfMap;
		map = new Map(getMapSize());
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		//this.setSize(new Dimension(1080, 720));
		this.setLayout(null);
		mapPanel = new MapPanel(this);
		mapPanel.setSize(this.getSize());
		mapPanel.setLocation(0,0);
		mapPanel.setMaxScroll();
		mapPanel.addMouseListener(new ClickerListener());
		this.add(mapPanel);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		//this.requestFocus();
		this.addWindowListener(new WindogeListener());
		minimap = new MiniMap(this);
		
		options = new OptionsGUI(this);
		
		this.addMouseListener(new ClickerListener());
		minimap.relocateToBottomRight();
		this.setUndecorated(true);
		this.setVisible(true);
		mapPanel.addMouseMotionListener(new MapMotionListener());
		board = map.getMapTiles();
		
		buildings = new BuildingPanel(this, 3);
		
		theQueueFrame = new QueueFrame(this);
		
		game = new Game(map);	
		game.addObserver(mapPanel);
		game.addObserver(minimap.getGraphPanel());
		game.setChange();
		
		game.getWorkQueue().addObserver(theQueueFrame);

	}
	public void loadTheSave(Game game) {
		size = game.getMap().length;
		map = new Map(game.getMap());
		this.remove(mapPanel);
		mapPanel = new MapPanel(this);
		mapPanel.setSize(this.getSize());
		mapPanel.setLocation(0,0);
		mapPanel.setMaxScroll();
		mapPanel.addMouseListener(new ClickerListener());
		this.add(mapPanel);
		//this.requestFocus();
		this.addWindowListener(new WindogeListener());
		try {
		minimap.dispose();
		}
		catch(Exception e) {
			System.out.println("Oh!");
		}
		minimap = new MiniMap(this);
		
		minimap.relocateToBottomRight();
		board = map.getMapTiles();
				
		theQueueFrame = new QueueFrame(this);
		
		mapPanel.addMouseMotionListener(new MapMotionListener());
		
		try {
		game.deleteObservers();
		game.getWorkQueue().deleteObservers();
		}
		catch(Exception f) {
			System.out.println("Wow observers can't be removed");
		}
		
		this.game = game;	
		game.addObserver(mapPanel);
		game.addObserver(minimap.getGraphPanel());
		game.setChange();
		
		game.getWorkQueue().addObserver(theQueueFrame);
		game.startTimers();
	}
	public void CloseEverything() {
		minimap.dispose();
		options.dispose();
		buildings.dispose();
		game = null;
		
		this.dispose();
		System.exit(0);
	}
	public Game getGame() {
		return game;
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
				mapPanel.increaseY();
			if(goingSouth)
				mapPanel.decreaseY();
			if(goingWest)
				mapPanel.decreaseX();
			if(goingEast)
				mapPanel.increaseX();
			mapPanel.paintIt();
			minimap.getGraphPanel().paintIt();
		}
	}
	
	//  The following Variables are used for building
	private boolean mouseIsBuilding = false;
	private boolean canBuild = false;
	private int toBuild = 0;
	private Point topLeftBuildingPoint = null;
	
	private class MouseBuildingListener implements MouseMotionListener, MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if(mouseIsBuilding) {
				//  add the building to a array of buildings...
				mapPanel.getArrayLocationOfClicked(arg0.getX(), arg0.getY());
				
			}
			else {
				topLeftBuildingPoint = mapPanel.getArrayLocationOfClicked(arg0.getX(), arg0.getY());
				mouseIsBuilding = true;
			}
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

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	private class MapMotionListener implements MouseMotionListener {
		private boolean triggered = false;
		private int dividor = 14;
		
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
		private WorkerFrame workFrame;
		
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
			Point point = mapPanel.getArrayLocationOfClicked(arg0.getX(), arg0.getY());
			//  null needs to be our list of workers
			if(board[point.y][point.x].getResource().getResourceT().equals(ResourceType.NONE)) {
				worker(point, arg0);
				return;
			}
			if (frame != null)
				frame.dispose();
			frame = new ResourceFrame(arg0.getPoint(), point, board[point.y][point.x].getResource(), game);
			board[point.y][point.x].getResource().addObserver(frame);
			}

		private void worker(Point point, MouseEvent arg0) {
			Worker clicked = game.getList().getAt(point);
			if(clicked != null) {
				if(workFrame != null)
					workFrame.dispose();
				workFrame = new WorkerFrame(arg0.getPoint(), point, clicked);
				clicked.addObserver(workFrame);
			}
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
		FileInputStream fos = null;
		ObjectInputStream oos;
		try {
			fos = new FileInputStream("GameData");
		}
		catch(Exception e) {
			//  NOTHING TO LOAD DO NOT WORRY
		}
		//  Then we can load
		if(fos != null) {
			int reply = JOptionPane.showConfirmDialog(null, "Do you want to load your previous save?",
					"Load?", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION) {
				try {
					oos = new ObjectInputStream(fos);
					Game game = (Game)oos.readObject();
					//  Knees weak moms spagetti arms spaggetti rito spaghetti
					SettlementGUI guiLoad = new SettlementGUI(100);
					guiLoad.setAllVisible(false);
					guiLoad.loadTheSave(game);
					guiLoad.setAllVisible(true);
					fos.close();
					oos.close();
				}
				catch(Exception f) {
					f.printStackTrace();
				}
        	}
			else {
				SettlementGUI gui = new SettlementGUI(100);
			}
        }
		else {
			SettlementGUI gui = new SettlementGUI(100);
		}
	}
	private void setAllVisible(boolean b) {
		minimap.setVisible(b);
		options.setVisible(b);
		theQueueFrame.setVisible(b);
		buildings.setVisible(b);
		this.setVisible(b);
	}

	public Map getMap() {
		// TODO Auto-generated method stub
		return map;
	}
	public MapPanel getMapPanel() {
		// TODO Auto-generated method stub
		return mapPanel;
	}
}
