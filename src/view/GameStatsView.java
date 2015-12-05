package view;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTextField;

import model.Buildable;
import model.Game;
import model.ListOfWorkers;
import model.ThePackage;

public class GameStatsView extends JFrame implements Observer{
	private int amountOfFood;
	private int amountOfWorkers;
	private int allowedAmountOfWorkers;
	private int amountOfWood;
	private int amountOfStone;
	private Game game;
	
	private JTextField info = new JTextField();
	
	public GameStatsView(SettlementGUI caller) {
		this.setSize(caller.getWidthMinusExtras(),35);
		this.setLocation(caller.getBuildingsFrameWidth(), 0);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		info.setSize(this.getSize());
		info.setBackground(new Color(127, 106, 69));
		info.setFont(info.getFont().deriveFont(Font.BOLD, 14f));
		info.setForeground(Color.LIGHT_GRAY);
		this.add(info);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		game = (Game) arg0;
		ThePackage myPackage = (ThePackage)arg1;
		ArrayList<Buildable> builds = myPackage.getBuildings();
		ListOfWorkers workmen = myPackage.getWorkers();
		amountOfWood = game.getTotalWood();
		amountOfStone = game.getTotalStone();
		amountOfFood = game.getTotalFood();
		String mT = "/" + game.getTotalMax() + "     ";
		amountOfWorkers = workmen.size();
		info.setText("      " + "Food: " + amountOfFood + mT + "Stone: " + amountOfStone + mT + "Wood: " + amountOfWood
				+ mT + "Workers: " + workmen.size());
	}
}
