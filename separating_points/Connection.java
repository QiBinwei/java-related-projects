package BinweiQi;

import java.util.ArrayList;

public class Connection {
	public Point a;
	public Point b;
	
	public ArrayList<Line> crossedLines;
	
	public Connection(Point a, Point b) {
		this.a = a;
		this.b = b;
		this.crossedLines = new ArrayList<>();
	}

}
