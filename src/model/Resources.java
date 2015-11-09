package model;

abstract class Resources {
	
	private double quantity;
	private Resource type;
	
	Resources(double q, Resource x) {
		quantity = q;
		type = x;
	}
	
	double getQuantity() {
		return quantity;
	}
	
	void addResource(double n){
		quantity += n;
	}
	
	void subResource(double n){
		quantity -= n;
	}
	
	Resource getResourceT(){
		return type;
	}
	
	// takes in the resource you want to change current one to.
	void changeResourceT(Resource newR){
		this.type = newR;
	}



}
