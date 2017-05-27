package BinweiQi;
//this is the edge class which stores the attributes of the edges
public class Edge {
	
	private int fromNodeIndex;
	private int length;
	private int toNodeIndex;
	//getters and setters
	public void setFromNodeIndex(int fromNodeIndex) {
		this.fromNodeIndex = fromNodeIndex;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setToNodeIndex(int toNodeIndex) {
		this.toNodeIndex = toNodeIndex;
	}

	public Edge(int fromNodeIndex, int toNodeIndex, int length){
		this.fromNodeIndex = fromNodeIndex;
		this.toNodeIndex = toNodeIndex;
		this.length = length;
	}

	public int getFromNodeIndex() {
		return fromNodeIndex;
	}

	public int getLength() {
		return length;
	}


	public int getToNodeIndex() {
		return toNodeIndex;
	}

	
	//determine the neighbour node of a supplied node, based on the two nodes connected by this edge
	public int getNeighbourIndex(int nodeIndex){
		if(this.fromNodeIndex == nodeIndex){
			return this.toNodeIndex;
		}
		else {
			return this.fromNodeIndex;
		}
	}
 
}
