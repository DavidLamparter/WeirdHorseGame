package model;

public class KJG extends Worker{

	public KJG() {
		super();
	}
	
	@Override
	public void addColdness() {
		// If hunger rises above 10, this worker dies from starvation
		if(getColdness() >= 10) {
			inDanger(getColdness());
		}
		addColdness(1.5);
	}
}
