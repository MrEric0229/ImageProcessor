import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

public class WGraph {

	ArrayList<Node> vertices;
	int numOfVertices;
	int numOfEdges;
	Node head;
	HashMap<Integer,HashMap<Integer, Node>> graph;
	
	public WGraph(String FName) {
		File file = new File(FName);
		graph = new HashMap<Integer,HashMap<Integer, Node>>();
		vertices = new ArrayList<Node>();
		head = null;
		
		try {
			Scanner scan = new Scanner(file);
			
			numOfVertices = scan.nextInt();
			numOfEdges = scan.nextInt();
			
			while(scan.hasNext()) {
				int srcx = scan.nextInt();
				int srcy = scan.nextInt();
				
				int desx = scan.nextInt();
				int desy = scan.nextInt();
				
				int weight = scan.nextInt();
				
				Node src = getNode(srcx, srcy);
				if (src == null) {
					src = new Node(srcx, srcy);
					setNode(srcx, srcy, src);
					vertices.add(src);
				}
				
				Node des = getNode(desx, desy);
				if (des == null) {
					des = new Node(desx, desy);
					setNode(desx, desy, des);
					vertices.add(des);
				}
				
				src.addNeighbor(des, weight);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public WGraph(ArrayList<ArrayList<Integer>> list, int numOfRow, int numOfColumn) {	
		graph = new HashMap<Integer,HashMap<Integer, Node>>();
		vertices = new ArrayList<Node>();
		
		for (int column=0; column<numOfColumn; column++) {
			for (int row=0; row<numOfRow; row++) {
				Node node = new Node(column, row);
				setNode(column, row, node);
				vertices.add(node);
			}
		}
		
		for (int column=0; column<numOfColumn; column++) {
			for (int row=0; row<numOfRow-1; row++) {
				Node node = getNode(column, row);
				
				if (column-1 >= 0) node.addNeighbor(getNode(column-1, row+1), list.get(row+1).get(column-1));
				node.addNeighbor(getNode(column, row+1), list.get(row+1).get(column));
				if (column+1 < numOfColumn) node.addNeighbor(getNode(column+1, row+1), list.get(row+1).get(column+1));
			}
		}
		
		head = new Node(numOfColumn, numOfRow);
		setNode(numOfColumn, numOfRow, head);
		vertices.add(head);
		
		for (int i=0; i<numOfColumn; i++) {
			head.addNeighbor(getNode(i, 0), list.get(0).get(i));
		}
	}
	
	// pre: ux, uy, vx, vy are valid coordinates of vertices u and v
	// in the graph
	// post: return arraylist contains even number of integers,
	// for any even i,
	// i-th and i+1-th integers in the array represent
	// the x-coordinate and y-coordinate of the i/2-th vertex
	// in the returned path (path is an ordered sequence of vertices)
	public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy){
		ArrayList<Integer> S1 = new ArrayList<Integer>();
		ArrayList<Integer> S2 = new ArrayList<Integer>();
		
		S1.add(ux);
		S1.add(uy);
		S2.add(vx);
		S2.add(vy);
		
		return S2S(S1, S2);
	}
	
	
	
	// pre: ux, uy are valid coordinates of vertex u from the graph
	// S represents a set of vertices.
	// The S arraylist contains even number of intergers
	// for any even i,
	// i-th and i+1-th integers in the array represent
	// the x-coordinate and y-coordinate of the i/2-th vertex
	// in the set S.
	public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S){
		ArrayList<Integer> S1 = new ArrayList<Integer>();
		
		S1.add(ux);
		S1.add(uy);
		
		return S2S(S1, S);
	}
	
	
	
	// pre: S1 and S2 represent sets of vertices (see above for
	// the representation of a set of vertices as arrayList)
	// post: same structure as the last method's post.
	public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2){
		
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		int finalCost = Integer.MAX_VALUE;
		
		for (int i=0; i<S1.size(); i++) {
			HashMap<Node,String> visited = new HashMap<Node,String>();
			HashMap<Node,Integer> dis  = new HashMap<Node,Integer>();
			PriorityQueue<NodeWithDistance> queue = new PriorityQueue<NodeWithDistance>();
			HashMap<Node, NodeWithDistance> elementMap = new HashMap<Node, NodeWithDistance>();
						
			for (Node v: vertices) {
				visited.put(v, "unvisited");
				dis.put(v, Integer.MAX_VALUE);
				v.previous = null;
			}
			
			int x = S1.get(i++);
			int y = S1.get(i);
			
			Node src = getNode(x, y);
			
			if (src != null) {
				dis.put(src, 0);
				NodeWithDistance element = new NodeWithDistance(src, 0);
				queue.add(element);
				elementMap.put(src, element);
//				while(!queue.isEmpty()) {
//					NodeWithDistance nodeWithDistance = queue.poll();
//					Node node = nodeWithDistance.getNode();
//					int distance = nodeWithDistance.getDistance();
//					visited.put(node, "visited");
//					
//					for (Node n: node.neighbors) {
//						if (visited.get(n).equals("unvisited")) {
//							int weightOfEdge = node.weights.get(n);
//							int newDis = distance + weightOfEdge;
//							if (dis.get(n) > newDis) {
//								dis.replace(n, newDis);
//								n.previous = node;
//							}
//							queue.add(new NodeWithDistance(n, dis.get(n)));
//							visited.put(n, "processing");
//						}
//					}
//				}
				
				while(!queue.isEmpty()) {
					
					NodeWithDistance nodeWithDistance = queue.poll();
					Node node = nodeWithDistance.getNode();
					int distance = nodeWithDistance.getDistance();
					visited.put(node, "visited");
					
					for (Node n: node.neighbors) {
						if (!visited.get(n).equals("visited")) {
							int weightOfEdge = node.weights.get(n);
							int newDis = distance + weightOfEdge;
							if (dis.get(n) > newDis) {
								dis.replace(n, newDis);
								n.previous = node;
								
								if (visited.get(n).equals("unvisited")) {
									NodeWithDistance tempElement = new NodeWithDistance(n, dis.get(n));
									queue.add(tempElement);
									visited.put(n, "processing");
									elementMap.put(n, tempElement);
								}
								else if(visited.get(n).equals("processing")){
									NodeWithDistance tempElement = elementMap.get(n);
									queue.remove(tempElement);
									tempElement.distance = dis.get(n);
									queue.add(tempElement);
								}
							}
							
						}
					}
				}
				
				Node targetNode = null;
				int cost = Integer.MAX_VALUE;
				for (int j=0; j<S2.size(); j++) {
					int vx = S2.get(j++);
					int vy = S2.get(j);
					
					Node node = getNode(vx, vy);
					int tempCost = dis.get(node);
					
					if (tempCost < cost) {
						cost = tempCost;
						targetNode = node;
					}
				}
				
				if (cost < finalCost && cost != Integer.MAX_VALUE){
					//System.out.println("Cost: " + cost + " Final Cost: " + finalCost);
					finalCost = cost;
					path = new ArrayList<Integer>();
					while (targetNode != head) {
							path.add(0, targetNode.getY());
							path.add(0, targetNode.getX());
							
							targetNode = targetNode.getPrevious();
					}
				}	
			}
		}
		
		return path;
	}
	
	public HashMap<Integer,HashMap<Integer, Node>> getGraph(){
		return graph;
	}
	
	private Node getNode(int x, int y) {
		HashMap<Integer, Node> Ys = graph.get(x);
		return Ys == null ? null : Ys.get(y);
	}
	
	private void setNode(int x, int y, Node node) {
		HashMap<Integer, Node> Ys = graph.get(x);
		
		if (Ys == null) {
			Ys = new HashMap<Integer, Node>();
			Ys.put(y, node);
			graph.put(x, Ys);
		}
		else {
			Ys.put(y, node);
		}
	}
	
	final public class Node{
		
		int x;
		int y;
		int size;
		Node previous;
		ArrayList<Node> neighbors;
		HashSet<Node> neighborsSet;
		HashMap<Node,Integer> weights;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
			
			size = 0;
			previous = null;
			neighbors = new ArrayList<Node>();
			weights = new HashMap<Node,Integer>();
			neighborsSet = new HashSet<Node>();
		}
		
		public void addNeighbor(Node node, int weight) {
			if (!containNode(node)) {
				neighbors.add(node);
				neighborsSet.add(node);
				weights.put(node, weight);
				size++;
			}
		}
		
		public void setPrevious(Node node) {
			previous = node;
		}
		
		public Node getPrevious() {
			return previous;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY(){
			return y;
		}
		
		public boolean containNode(Node node){
			return neighborsSet.contains(node);
		}
		
		public int size() {
			return size;
		}
		
		public String getCoordinates() {
			return "(" + x + ", " + y + ")";
		}
		
		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
		
//		@Override
//		public String toString() {
//			String str = "(" + x + ", " + y + ") - neighbors: ";
//			for (Node node: neighbors) {
//				int weight = weights.get(node);
//				str += node.getCoordinates() + "=>" + weight +" ";
//			}
//			return str;
//		}
	}
	
	final class NodeWithDistance implements Comparable<NodeWithDistance>{
		Node node;
		int distance;
		
		public NodeWithDistance(Node node, int distance)
		{
			this.node = node;
			this.distance = distance;
		}
		
		public Node getNode(){
			return node;
		}
		
		public int getDistance() {
			return distance;
		}
		
		@Override
		public int compareTo(NodeWithDistance node2) {
			return this.distance - node2.distance;
		}
		
	}
}
