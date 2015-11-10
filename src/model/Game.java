package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Timer;

public class Game extends Observable{
	private Map theMap;
	private ListOfWorkers list;
	private Timer hungerMeter = new Timer(5000, new HungerTimerListener());
	private Timer sleepMeter = new Timer(5000, new SleepTimerListener());
	private Timer coldMeter = new Timer(5000, new ColdTimerListener());
	private Timer gameTimer = new Timer(1000, new GameTimerListener());
	private Timer SpeedMeter = new Timer(50, new MovementTimerListener());
	private int gameLength = 0;
	private int lengthOfSeasons;
	private int seasonsCounter;
	private boolean isWinter;
	private short wintersSurvived;

	public Game(Map theMap) {
		this.theMap = theMap;
		list = new ListOfWorkers(20);
	}
	public ListOfWorkers getList() {
		return list;
	}
	private class MovementTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			list.moveYourAsses();
			setChanged();
			notifyObservers();
		}
	}
	private class HungerTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			list.incrementHunger();
		}
	}
	private class SleepTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			list.incrementSleep();
		}
	}
	private class ColdTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			list.incrementColdness();
		}
	}
	private class GameTimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			seasonsCounter++;
			if(seasonsCounter >= lengthOfSeasons) {
				isWinter = !isWinter;
				seasonsCounter = 0;
				if(!isWinter)
					wintersSurvived ++;
			}
			gameLength++;
		}
	}
}
