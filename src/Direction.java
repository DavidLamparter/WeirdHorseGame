//  ENUMS
public enum Direction {
	NORTH, SOUTH, EAST, WEST;
	public Direction invert(Direction dir) {
		if(dir == NORTH)
			return SOUTH;
		if(dir == SOUTH)
			return NORTH;
		if(dir == EAST)
			return WEST;
		return EAST;
	}
}
