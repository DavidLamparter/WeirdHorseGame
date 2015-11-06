
public class MapTile {
	private Resource resource;
	private Terrain terrain;
	public MapTile() {
		resource = null;
		terrain = terrain.PLAIN;
	}
	//  This will set rivers and mountains or whatever the hell we want
	public void setLand(Terrain toSet) {
		terrain = toSet;
	}
	public void setResource(Resource toSet) {
		System.out.println(toSet);
		resource = toSet;
	}
	public boolean unPassable() {
		if(terrain.equals(terrain.PLAIN)) {
			//  or it could be a method call from resource
			if(resource==null){
				return false;
			}
		}
		return true;
	}
	public String toString() {
		if(terrain.equals(terrain.RIVER))
			return "[R]";
		if(terrain.equals(terrain.PLAIN))
			return "[ ]";
		return "Hodor";
	}
}
