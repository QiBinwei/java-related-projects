package BinweiQi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SeparatingPoints {
	Graph graph;

	public ArrayList<Line> separatingLines;
	
	public SeparatingPoints() {
		graph = new Graph();
		separatingLines = new ArrayList<>();
	}
	
	public void load(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner fs = new Scanner(file);
	 
		int n = fs.nextInt();
		int[][] points = new int[n][];
		
		for (int i = 0; i < n; i++) {
			int x = fs.nextInt();
			int y = fs.nextInt();
			points[i] = new int[]{x, y};
		}
		
		fs.close();
		
		createGraph(points);
	}
	
	public void createGraph(int[][] points) {
		graph.build(points);
	}
	
	public void solve() {
		while(graph.connected()) {
			Line line = graph.findMaxCrossingLine();
			//System.out.println("max crossing: " + line.crossedConnections.size() + " " + line);
			graph.separate(line);
			separatingLines.add(line);
		}
	}
	
	public void printSolution() {
		System.out.println(separatingLines.size());
		for (int i = 0; i < separatingLines.size(); i++) {
			Line line = separatingLines.get(i);
			System.out.println(line);
		}
	}
}
