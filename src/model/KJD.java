package model;

public class KJD extends Worker {
	
	public KJD() {
		super();
	}
	
	@Override
	public void addHunger() {
		// If hunger rises above 10, this worker dies from starvation
		if(getHunger() >= 10) {
			inDanger(getHunger());
		}
		addHunger(1.5);
	}
}
