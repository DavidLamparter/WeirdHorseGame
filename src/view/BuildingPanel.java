package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BuildingPanel extends JFrame {
	public static int BRIDGE_V_ID = 3;
	public static int BRIDGE_H_ID = 2;
	public static int STOREHOUSE_ID = 1;
	public static int HOUSE_ID = 0;
	private JButton[] buildings;
	private JPanel holder;
	private SettlementGUI caller;
	
	public BuildingPanel(SettlementGUI caller, int numberOfBuildings) {
		
		this.caller = caller;
		this.setSize(numberOfBuildings*75+20,75);
		buildings = new JButton[numberOfBuildings];
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
		buildings[HOUSE_ID].setText("House");                // 0
		buildings[STOREHOUSE_ID].setText("Storehouse");      // 1
		buildings[BRIDGE_H_ID].setText("Horizontal Bridge"); // 2	
		buildings[BRIDGE_V_ID].setText("Vertical Bridge");   // 3
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
					buildings[i].setEnabled(false);
				}
			}			
		}
	}
	public void resetButtons() {
		for(int i = 0; i < buildings.length; i++) {
			buildings[i].setEnabled(true);
		}
		
	}
}