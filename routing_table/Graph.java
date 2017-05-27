package BinweiQi;
//this is the Graph class, which includes all the methods to fulfill the requirements
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.standard.PrinterMessageFromOperator;

//create a graph and implement dijskra algorithm
public class Graph {
	private ArrayList<ArrayList<Integer>> matrix;
	private ArrayList<Node> nodes;
	private int noOfNodes;
	private ArrayList<Edge> edges;
	private int noOfEdges;
	

	
	public Graph(Scanner scfile) {
		matrix = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; scfile.hasNext(); i++){
			String line = scfile.nextLine();
			String[] nums = line.split(" ");
			matrix.add(new ArrayList<Integer>());//add a empty row into the matrix
			for (int j = 0; j < nums.length; j++) {
				int len = Integer.parseInt(nums[j]);
				matrix.get(i).add(len);//add an element to the row(i).
			}
		}
		
		buildGraph();
	}


	public void buildGraph(){
		this.noOfEdges = 0;
		this.edges = new ArrayList<Edge>();
		
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); j++) {
				int len = matrix.get(i).get(j);
				//System.out.print(A.get(i)[j] + " ");
				if (len > 0) {
					edges.add(new Edge(i, j, len));
					noOfEdges++;
				}
			}
			//System.out.println();
		}
		
		//create every node which is ready to be updated with the edges
		this.noOfNodes = calculateNoOfNodes();
		this.nodes = new ArrayList<Node>();
		
		for (int n = 0; n < this.noOfNodes; n++){
			this.nodes.add(new Node(n));
		}
			
		//add all edges to nodes and every edge has two nodes which are to and from
		
		for(int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++){
			Edge e = edges.get(edgeToAdd);
			Node node;
			
			node = this.nodes.get(e.getFromNodeIndex());
			node.getEdges().add(e);
			node = this.nodes.get(e.getToNodeIndex());
			node.getEdges().add(e);
		}
	}
	
	
	private int calculateNoOfNodes() {
		int noOfNodes = 0;
		
		for(int i =0; i< noOfEdges; i++){
			Edge e = edges.get(i);
			if(e.getToNodeIndex() > noOfNodes)
				noOfNodes = e.getToNodeIndex();
			if (e.getFromNodeIndex() > noOfNodes)
				noOfNodes = e.getFromNodeIndex();
		}
		noOfNodes++;
		return noOfNodes;
	}
//implement Dijkastra algorithm
	public void calculateShortestDistances(int fromNode){
		// init nodes
		for (int i = 0; i < this.nodes.size(); i++) {
			nodes.get(i).init();//for every implementation, we have to initialize the node[] 
		}
		
		//set input node as the source node
		this.nodes.get(fromNode).setDistanceFromSource(fromNode);
		int currentNode = fromNode;
		
		//visit each node
		for (int i = 0; i< this.nodes.size(); i++){
			//loop through the edges of this current node
			ArrayList<Edge> currentNodeEdges = this.nodes.get(currentNode).getEdges();
			
			for(int joinedEdge = 0; joinedEdge < currentNodeEdges.size(); joinedEdge++){
				Edge edge = currentNodeEdges.get(joinedEdge);	
				int neighbourIndex = edge.getNeighbourIndex(currentNode);
				//if the node is not visisted
				if(!this.nodes.get(neighbourIndex).isVisited()){
					int tentative = this.nodes.get(currentNode).getDistanceFromSource() + edge.getLength();
					//then compare the distances of source to the unvisited node and the distance of source to the visited nodes. 
					if(tentative < nodes.get(neighbourIndex).getDistanceFromSource()){
						nodes.get(neighbourIndex).setDistanceFromSource(tentative);
						nodes.get(neighbourIndex).setPreviousNode(currentNode);//get the previous node where the current node form.
					}//if the unvisited node's distance is shorter, then update the distance.
				}
			}
			//all neighbors are checked
			nodes.get(currentNode).setVisited(true);
			//next node must be with shortest distance
			currentNode = getNodeShortestDistanced();
		}
	}
	
	//implement this method
	private int getNodeShortestDistanced(){
		int storedNodeIndex = 0;
		int storedDist = Integer.MAX_VALUE;
		
		for(int i = 0; i < this.nodes.size(); i++){
			if (this.nodes.get(i).IsRemoved()) {
				continue;
			}
			int currentDist = this.nodes.get(i).getDistanceFromSource();
			
			if(! this.nodes.get(i).isVisited() && currentDist < storedDist){//if the node is not visited, and the current distance is shorter than the stored distance
				storedDist = currentDist;//then replace the current distance to the stored distance
				storedNodeIndex = i;
			}
		}
		return storedNodeIndex;//then, we'll get the shortest distance.
	}
	
	//show the result
	public void buildConnectionTable(int fromNode){
		String output = "";
		System.out.println("Router "+fromNode+" connection table\n"+ "Destination     Interface\n"+"=========================" );
		for(int i = 0; i < this.nodes.size(); i++){
			Node node = nodes.get(i);
			output += ("\n     " + node.Id() + "              " + getConnectionTableInterface(node.Id()));
		}
		System.out.println(output);
	}
	
	public String getConnectionTableInterface(int destination) {
		ArrayList<Integer> path = getPath(destination);
		if (path.size() <= 1) {
			return "-";//because we required this symbol, then we return string and the next return we get a "" + an int
		}
		return ""+path.get(path.size()-2);//the second last node which is the interface, because the last one is the source.we count from the destination to the source.
	}
	
	public void displayTopology(){
		
		for (int i = 0; i < matrix.size(); i++) {
			for (int j = 0; j < matrix.get(i).size(); j++) {
				int len = matrix.get(i).get(j);
				System.out.print(len + " ");
			}
			System.out.println();
		}
	}
	
	public ArrayList<Integer> getPath(int toNode) {
		ArrayList<Integer> path = new ArrayList<Integer>();

		int currentNode = toNode;
		//find all the passed previous nodes in the shortest path from the destination node.
		while (true) {
			Node node = nodes.get(currentNode);
			path.add(currentNode);
			currentNode = node.getPreviousNode();
			if (currentNode < 0) {//if the current node is -1, then it is the source node
				break;
			}
		}
		return path;
	}
	
	public String shortestPahtBetween(int fromNode, int toNode){
		calculateShortestDistances(fromNode);
		
		ArrayList<Integer> path = getPath(toNode);
		
		String pathRoute = "";
		for (int i =path.size()-1; i>=0;i--){
			if (i > 0) {
				pathRoute += path.get(i) + "->";
			} else {
				pathRoute += path.get(i);
			}
		}
		
		return pathRoute;//turn the path route for printing.
	}
	
	public void modifyTopology(int removedNode) {
		//remove from matrix
		for (int i = 0; i < matrix.size(); i++) {
			matrix.get(i).remove(removedNode);
		}
		matrix.remove(removedNode);
		
		// remove from edges
		for (int i = edges.size() - 1; i >= 0; i--) {
			Edge edge = edges.get(i);
			if (edge.getFromNodeIndex() == removedNode || edge.getToNodeIndex() == removedNode) {
				edges.remove(i);
				this.noOfEdges--;
			}
		}
		
		// remove from nodes
		for (int i = nodes.size() - 1; i >= 0 ; i--) {
			Node node = nodes.get(i);
			if (node.IsRemoved()) {
				continue;
			}
			if (node.Id() == removedNode) {
				node.clear(); //if the node was removed, then we clear the value of that node.
				//this.noOfNodes--;
			} else {
				for (int j = node.getEdges().size() - 1; j >= 0; j--) {//if the edge have a node which is the removed one, then remove that edge. By doing this, we can remove the edges from both side of the node.
					Edge edge = node.getEdges().get(j);
					if (edge.getFromNodeIndex() == removedNode || edge.getToNodeIndex() == removedNode) {
						node.getEdges().remove(j);
					}
				}
			}
		}
	}
	
	public String bestRouterForBroadcase() {
		int minValue = Integer.MAX_VALUE;
		int minNode = -1;
		for (int i = 0; i < nodes.size(); i++) {//loop through every node to find the shortest paths from this one to the rest.
			Node node = nodes.get(i);
			if (node.IsRemoved()) {
				continue;
			}
			calculateShortestDistances(node.Id());

			int sum = 0;
			for (int j = 0; j < noOfNodes; j++) {//sum the paths and find the minimum summation.
				if (nodes.get(j).IsRemoved()) {
					continue;
				}
				sum += nodes.get(j).getDistanceFromSource();
			}
			if (sum < minValue) {
				minValue = sum;
				minNode = node.Id();
			}
		}
		String best = "the best router for broadcast is Router"+minNode+"\n The minimum summation of the cust is"+minValue;
		return best;
	}
}
