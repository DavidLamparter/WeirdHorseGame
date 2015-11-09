package model;

public class David extends Worker {

	public David() {
		super();
	}
	
	@Override
	public void subtractHappiness() {
		subtractHappiness(1.5);
	}
}
