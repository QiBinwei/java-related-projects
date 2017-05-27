package BinweiQi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Graph {
	public int n;
	public Point[] points;
	public ArrayList<Connection> conns;
	public ArrayList<Line> lines;
	
	public static int X = 0;
	public static int Y = 1;

	public boolean connected() {
		//System.out.println("conns size: " + conns.size());
		return conns.size() > 0;
	}//check if there is still any connections exist
	
	public void build(int[][] croods) {//build everything with the coordinates(typo, too lazy to change)
		n = croods.length;
		points = new Point[n];
		for (int i = 0; i < n; i++) {
			points[i] = new Point(croods[i][0], croods[i][1]);
		}
		
		buildLines();
		buildConnections();
		crossLinesAndConnections();
	}
	
	public void buildLines() {
		lines = new ArrayList<>();
		
		ArrayList<Line> xLines = new ArrayList<>();
		ArrayList<Line> yLines = new ArrayList<>();
		
		Arrays.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point p, Point q) {
				return p.x - q.x;
			}
		});
		for (int i = 0; i < n-1; i++) {
			Point p = points[i];
			Point q = points[i+1];
			if (p.x != q.x) {
				Line l = new Line(X, (float)(p.x + q.x) / 2);
				xLines.add(l);
				lines.add(l);
			}
		}
		
		Arrays.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point p, Point q) {
				return p.y - q.y;
			}
		});
		for (int i = 0; i < n-1; i++) {
			Point p = points[i];
			Point q = points[i+1];
			if (p.y != q.y) {
				Line l = new Line(Y, (float)(p.y + q.y) / 2);
				yLines.add(l);
				lines.add(l);
			}
		}
	}
	
	public void buildConnections() {
		conns = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				Connection c = new Connection(points[i], points[j]);
				conns.add(c);
			}
		}
	}
	
	public void crossLinesAndConnections() {
		for (int i = 0; i < conns.size(); i++) {
			Connection conn = conns.get(i);
			for (int j = 0; j < lines.size(); j++) {
				Line line = lines.get(j);
				
				if (line.sameSide(conn.a, conn.b)) {
					continue;
				}
				
				cross(line, conn);
			}
		}
	}

	public void cross(Line line, Connection conn) {
		line.crossedConnections.add(conn);
		conn.crossedLines.add(line);
	}
	
	public Line findMaxCrossingLine() {
		Line max = null;
		for (int i = 0; i < lines.size(); i++) {
			Line line = lines.get(i);
			if (max == null || max.crossedConnections.size() < line.crossedConnections.size()) {
				max = line;
			}
		}
		return max;
	}
	
	public void separate(Line line) {//delete the lines with the max cross connections. And also delete the connections who have stored the information that has been crossed by that deleted line.
		lines.remove(line);
		
		ArrayList<Connection> disconns = new ArrayList<>();
		
		for (int i = 0; i < line.crossedConnections.size(); i++) {
			Connection c = line.crossedConnections.get(i);
			disconns.add(c);
		}
		
//		System.out.println(" >>> conns:" + conns.size());
		
		for (int i = 0; i < disconns.size(); i++) {
			disconnect(disconns.get(i));
		}
		
//		System.out.println(" >>> disconns " + disconns.size() + " conns " + conns.size());
	}
	
	public void disconnect(Connection c) {//delete the connections who had been crossed by the max crossing line, And also delete the lines who store the information that has crossed such deleted connections.
		conns.remove(c);
		
		for (int j = 0; j < c.crossedLines.size(); j++) {
			Line l = c.crossedLines.get(j);
			l.crossedConnections.remove(c);
		}
	}
}
