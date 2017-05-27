package BinweiQi;
//this is the node class, it stores the attributes of the nodes
import java.util.ArrayList;

public class Node {
	
	private int nodeId;
	private int distanceFromSource = Integer.MAX_VALUE;
	private int previousNode = -1;
	private boolean visited = false;//check it the node is visited before
	private boolean removed = false;//check if the node is removed
	
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	//getters and setters
	public Node(int id) {
		this.nodeId = id;
	}

	public void init() {
		distanceFromSource = Integer.MAX_VALUE;
		previousNode = -1;
		visited = false;
	}
	
	public void clear() {
		init();
		removed = true;
		edges.clear();
	}

	public boolean IsRemoved() {
		return removed;
	}
	
	public int Id() {
		return nodeId;
	}
	
	public int getDistanceFromSource() {
		return distanceFromSource;
	}

	public void setDistanceFromSource(int distanceFromSource) {
		this.distanceFromSource = distanceFromSource;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public void setPreviousNode(int node) {
		this.previousNode = node;
	}
	
	public int getPreviousNode() {
		return this.previousNode;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	
	
	
}
