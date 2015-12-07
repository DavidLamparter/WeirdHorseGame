package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Game;

public class BuildingPanel extends JFrame implements Observer {
	public static int BRIDGE_ID = 2;
	public static int STOREHOUSE_ID = 1;
	public static int HOUSE_ID = 0;
	private JButton[] buildings;
	private boolean[] buttonsState;
	private JPanel holder;
	private SettlementGUI caller;
	private int lastClicked;
	
	public BuildingPanel(SettlementGUI caller, int numberOfBuildings) {
		
		lastClicked = -1;
		this.caller = caller;
		this.setSize(numberOfBuildings*80+20,100);
		buildings = new JButton[numberOfBuildings];
		buttonsState = new boolean[numberOfBuildings];
		holder = new JPanel();
		holder.setSize(this.getSize());
		holder.setLocation(0, 0);
		holder.setLayout(new FlowLayout());
		instantiateTheButtons();
		renameButtons();
		holder.setBackground(new Color(127, 106, 69));
		this.add(holder);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.setVisible(true);
	}

	private void renameButtons() {
		buildings[HOUSE_ID].setText("<HTML><center>House<br>(160 Wood / 40 Stone)</center></HTML>");                // 0
		buildings[STOREHOUSE_ID].setText("<HTML><center>Storehouse<br>(100 Wood)</center></HTML>");      // 1
		buildings[BRIDGE_ID].setText("<HTML><center>Bridge<br>(100 Stone)</center></HTML>");				 // 2	
	}

	private void instantiateTheButtons() {
		for(int i = 0; i < buildings.length; i++) {
			buildings[i] = new JButton("Build"+(i+1));
			buildings[i].setSize(65, 75);
			holder.add(buildings[i]);
			buildings[i].addActionListener(new BuildItListener());
			buildings[i].setBackground(new Color(210,180,140));
		}
	}
	private class BuildItListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			resetButtons();
			for(int i = 0; i  < buildings.length; i++) {
				if(arg0.getSource().equals(buildings[i])) {
					caller.setLastClickedBuilding(i);
					buttonsState[i] = false;
					buildings[i].setEnabled(false);
					lastClicked = i;
				}
			}			
		}
	}
	public void resetButtons() {
		for(int i = 0; i < buildings.length; i++) {
			buildings[i].setEnabled(buttonsState[i]);
		}
		lastClicked = -1;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Game game = (Game)arg0;
		if(game.getTotalStone()>=100)
			buttonsState[BRIDGE_ID] = true;
		else
			buttonsState[BRIDGE_ID] = false;
		if(game.getTotalWood()>=100)
			buttonsState[STOREHOUSE_ID] = true;
		else 
			buttonsState[STOREHOUSE_ID] = false;
		if((game.getTotalStone()>=40)&&(game.getTotalWood()>=160))
			buttonsState[HOUSE_ID] = true;
		else
			buttonsState[HOUSE_ID] = false;
		if(lastClicked>=0)
			buttonsState[lastClicked] = false;
		
		for(int i = 0; i < buildings.length; i++) {
			buildings[i].setEnabled(buttonsState[i]);
		}
	}
}