import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

	public static void main(String[] args) {
		
		ImageProcessor ip = new ImageProcessor("im2.txt");
		ip.writeReduced(9, "ip-out.txt");
		
		//0 4 1 5 2 6 3 7 4 6 5 6 6 5 7 4 8 3 9 3
		
//		String test = " 39725  73042  18999  43954  34924  34069  28403  74012  33776 \r\n" + 
//				" 32523  42371  46523  62982  42236  16003  55290  37972  90287 \r\n" + 
//				" 18421  104448  46541  9887  33872  48018  32831  36938  86346 \r\n" + 
//				" 39931  54491  19885  79677  46014  67248  62814  31064  29204 \r\n" + 
//				" 43254  28515  60289  48718  31582  35518  36673  50159  56465 \r\n" + 
//				" 65241  13076  56167  30996  57288  25096  10358  59291  44722 \r\n" + 
//				" 57254  69764  63146  57102  40926  30412  25860  68633  56387 \r\n" + 
//				" 30420  39304  22738  42588  12995  32971  42154  25422  47107 \r\n" + 
//				" 58535  70661  30080  15791  39660  30453  34867  25966  29798 \r\n" + 
//				" 41070  63883  34727  21088  25495  24925  21403  45095  44485 ";
//		
//		String expected = " 39725  73042  18999  43954  34924  34069  28403  74012  33776 \r\n" + 
//				" 32523  42371  46523  62982  42236  16003  55290  37972  90287 \r\n" + 
//				" 18421 104448  46541   9887  33872  48018  32831  36938  86346 \r\n" + 
//				" 39931  54491  19885  79677  46014  67248  62814  31064  29204 \r\n" + 
//				" 43254  28515  60289  48718  31582  35518  36673  50159  56465 \r\n" + 
//				" 65241  13076  56167  30996  57288  25096  10358  59291  44722 \r\n" + 
//				" 57254  69764  63146  57102  40926  30412  25860  68633  56387 \r\n" + 
//				" 30420  39304  22738  42588  12995  32971  42154  25422  47107 \r\n" + 
//				" 58535  70661  30080  15791  39660  30453  34867  25966  29798 \r\n" + 
//				" 41070  63883  34727  21088  25495  24925  21403  45095  44485 ";
//		
//		Scanner scan1 = new Scanner(test);
//		Scanner scan2 = new Scanner(expected);
//		
//		boolean flag = true;
//		while(scan1.hasNext()) {
//			if (!scan1.next().equals(scan2.next())) {
//				flag = false;
//			}
//		}
//		System.out.println(flag);
		
		
//		WGraph WGraph = new WGraph("test2.txt");
		
//		WGraph.Node[][] graph = WGraph.getGraph();
//		
//		for (int i=0; i<graph.length; i++) {
//			for (int j=0; j<graph[0].length; j++) {
//				if (graph[i][j] != null)
//					System.out.println(graph[i][j]);
//			}
//		}
		
//		ArrayList<Integer> S1 = new ArrayList<Integer>();
//		S1.add(1);
//		S1.add(2);
//		S1.add(1);
//		S1.add(1);
//		S1.add(2);
//		S1.add(2);
//		
//		ArrayList<Integer> S2 = new ArrayList<Integer>();
//		S2.add(1);
//		S2.add(5);
//		S2.add(1);
//		S2.add(6);
//		S2.add(1);
//		S2.add(7);
//		
//		
//		ArrayList<Integer> result = WGraph.S2S(S1,S2);
//		for (int i=0; i<result.size(); i++) {
//			System.out.print(result.get(i) + " ");
//		}
		
//		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
//		pq.add(1);
//		pq.add(10);
//		pq.add(5);
//		pq.add(7);
//		pq.add(6);
//		
//		while(!pq.isEmpty()) {
//			System.out.print(pq.poll());
//		}

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
}
