package model;

public abstract class Resource {
	
	private double quantity;
	private ResourceType type;
	
	public Resource(double q, ResourceType x) {
		quantity = q;
		type = x;
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public void addResource(double n){
		quantity += n;
	}
	
	public void subResource(double n){
		quantity -= n;
	}
	
	public ResourceType getResourceT(){
		return type;
	}
	public abstract void regen();
	public abstract String getName();
	
	// takes in the resource you want to change current one to.
	/*void changeResourceT(Resource newR){
		this.type = newR;
	} */
}
class Nothing extends Resource {
	public Nothing() {
		super(0, ResourceType.NONE);
	}
	@Override
	public void regen() {
		//  NOTHING DOESNT REGEN?
	}
	@Override
	public String getName() {
		return "Nothing is here";
	}
}
