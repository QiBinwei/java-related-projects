package BinweiQi;

import java.util.ArrayList;

public class Line {
	public int axis;
	public float position;
	
	public ArrayList<Connection> crossedConnections;
	
	public Line(int axis, float position) {
		this.axis = axis;
		this.position = position;
		this.crossedConnections = new ArrayList<>();
	}
	
	public int side(Point p) {
		if (axis == Graph.X) {
			return position < p.x ? -1 : 1;
		} else {
			return position < p.y ? -1 : 1;
		}
	}
	
	public boolean sameSide(Point p, Point q) {
		return side(p) == side(q);
	}
	
	public String toString() {
		if (axis == Graph.X) {
			return "v " + position;
		} else {
			return "h " + position;
		}
	}
}
