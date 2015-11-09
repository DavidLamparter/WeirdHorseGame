package model;

public class Brett extends Worker{
	
	public Brett() {
		super();
	}
	
	@Override
	public void addFatigue() {
		// If hunger rises above 10, this worker dies from starvation
		if(getFatigue() >= 10) {
			inDanger(getFatigue());
		}
		addFatigue(1.5);
	}
}
