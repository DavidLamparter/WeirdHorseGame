package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTextField;

import model.*;

public class GameStatsView extends JFrame implements Observer{
	private int amountOfFood;
	private int amountOfWorkers;
	private int allowedAmountOfWorkers;
	private int amountOfWood;
	private int amountOfStone;
	
	private JTextField info = new JTextField();
	
	public GameStatsView(SettlementGUI caller) {
		this.setSize(caller.getWidthMinusExtras(),35);
		this.setLocation(caller.getBuildingsFrameWidth(), 0);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		
		info.setSize(this.getSize());
		this.add(info);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ThePackage myPackage = (ThePackage)arg1;
		ArrayList<Buildable> builds = myPackage.getBuildings();
		ListOfWorkers workmen = myPackage.getWorkers();
		int food = 0;
		int stone = 0;
		int wood = 0;
		for(int i = 0; i < builds.size(); i++) {
			if(builds.get(i)instanceof Storage) {
				stone += ((Storage)builds.get(i)).getStoneCount();
				wood += ((Storage)builds.get(i)).getWoodCount();
				food += ((Storage)builds.get(i)).getFoodCount();
			}
		}
		amountOfWood = wood;
		amountOfStone = stone;
		amountOfFood = food;
		String mT = "     ";
		amountOfWorkers = workmen.size();
		info.setText(mT + "Food: " + food + mT + "Stone: " + stone + mT + "Wood: " + wood
				+ mT + "Workers: " + workmen.size());
	}
}
