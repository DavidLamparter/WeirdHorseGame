package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ThePackage implements Serializable {
	ListOfWorkers workmen = null;
	ArrayList<Buildable> buildings = new ArrayList<>();
	
	public ThePackage() {
	}
	public ThePackage(ArrayList<Buildable> buildings, ListOfWorkers workmen) {
		this.workmen = workmen;
		this.buildings = buildings;
	}
	public void updateAll(ListOfWorkers workmen, ArrayList<Buildable> buildings) {
		this.workmen = workmen;
		this.buildings = buildings;
	}
	public void updateListOfWorkers(ListOfWorkers workmen) {
		this.workmen = workmen;
	}
	public void updateBuildings(ArrayList<Buildable> buildings) {
		this.buildings = buildings;
	}
	public ArrayList<Buildable> getBuildings() {
		return this.buildings;
	}
	public ListOfWorkers getWorkers() {
		return this.workmen;
	}
}
