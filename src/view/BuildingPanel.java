package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BuildingPanel extends JFrame {
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
		instatiateTheButtons();
		renameButtons();
		
		this.add(holder);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.setVisible(true);
	}

	private void renameButtons() {
		buildings[0].setText("House");
		buildings[1].setText("Storehouse");
		buildings[2].setText("Bridge");		
	}

	private void instatiateTheButtons() {
		for(int i = 0; i < buildings.length; i++) {
			buildings[i] = new JButton("Build"+(i+1));
			buildings[i].setSize(65, 75);
			holder.add(buildings[i]);
		}
	}
	private class BuildItListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource().equals(buildings[0])){
				caller.getMapPanel();
			}
			if(arg0.getSource().equals(buildings[1])) {
				
			}
			if(arg0.getSource().equals(buildings[2])) {
				
			}
				
			
		}
	}
}