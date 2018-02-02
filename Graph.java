import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.*;

/**
 * The Graph class illustrates the network
 * of railway routes between towns in KiwiLand
 */
public class Graph {

//TEST
	private int vertices;	//number of nodes
	private int indices;	//index of every node
	private int edges;		//# of edges
	private int[][] list;	//Matrix of distance between two nodes
	private ArrayList<Node> nodes;	//List of all nodes

	/**
	 * The Graph constructor 
	 * initializes all fields and 
	 * and takes in a parameter to initialize
	 * number of nodes in the graph. 
	 * @param	v	The number or vertices/nodes in the graph
	 */
	public Graph(int v)
	{
		this.vertices = v;
		this.indices = 0;
		this.edges = 0;
		this.list = new int[v][v];
		this.nodes = new ArrayList<Node>();		
	}
	
	/**
	 * Adds a node/vertex to the graph.
	 * An index is set within the node to 
	 * specify which index the node is at
	 * within the list matrix. The node is 
	 * also added to the nodes list
	 * @param	n	Node/Vertex to be added to the graph
	 * @return	void
	 */
	public void addVertex(Node n)
	{
			n.setIndex(indices);
			nodes.add(n);
			indices++;			
	}
	public ArrayList<Node> getNodes()
	{
		return this.nodes;
	}
	/**
	 * Adds an edge between vertices/nodes.
	 * This indicates that there is a route
	 * between two nodes. If the list matrix
	 * does not have a value above 0 at the 
	 * index of vertex a and b, the weight/distance
	 * is set to that index. 
	 * @param	a	Starting Node/Vertex of edge
	 * @param	b	Ending Node/Vertex of edge
	 * @param	w	Weight/Distance between Nodes
	 * @return	boolean indicating whether a edge was added
	 */
	public boolean addEdge(Node a, Node b,int w)
	{
		//check that edge is not added to same vertex
		if(a.equals(b))
		{
			System.out.println("Error: Trying to add self edge!");
			return false;
		}
		Edge e = new Edge(a,b,w);
		int n = a.getIndex();
		int m = b.getIndex();
		
		if(list[n][m] == 0){
			this.edges++;
			list[n][m]= w;
			a.getAdjacent().add(b);
			return true;
		}
		System.out.println("Error:Route already exists");
		return false;
		
	}
	/**
	 * Takes in an array of Nodes/Vertices
	 * and calculates the overall distance 
	 * from the first Node to the last Node,including 
	 * the distance from every Node in between.
	 * 
	 * @param	n     An array of nodes which specifies route where distance is 
	 * 		      to be calculated
	 * @Return	String representation of overall distance
	 */
	public String route(Node[] n)
	{
		int x = 0;
		int i = 0;
		int dist = 0;
		for(i =0;i<n.length - 1;i++)
		{
			Node curr = n[i];
			Node next = n[i+1];
			x = list[curr.getIndex()][next.getIndex()];
			if(x == 0)
			{
				return "NO SUCH ROUTE";
			}
			dist = dist + x;
		}
		return "" + dist;
	}
	/**
	 * Finds every route between two nodes in the Graph
	 * that has no more than a specified number of stops.
	 * An assumption is made that the starting node/Vertex is not
	 * included in the amount of stops within the route. This is
	 * a depth-first recursive search. 
	 * @param	start	Starting node/vertex of the route
	 * @param	end	Ending node/vertex of the route
	 * @param	depth	Indicates how many stops have been made, depth of search
	 * @param	count	Indicates current number of routes found that meet the 
	 * 			specifications.
	 * @param	max	Max number of stops allowed within the route
	 * @return	Gives the amount of routes found that have less than or equal to
	 * 		max stops between two Nodes. 
	 */	
	public int maxStops(Node start,Node end,int depth,int count,int max)
	{
		depth++;
		for(int i = 0; i < start.getAdjacent().size();i++)
		{
			Node n = start.getAdjacent().get(i);
			if(n.equals(end) && depth <= max)
			{
				count++;
			}
			if(depth > max)
			{
				return count;
			}
			count = maxStops(n,end,depth,count,max);
		}
		return count;
	}
	/**
	 * Finds every route between two nodes in the Graph
	 * that has a specified number of stops.
	 * An assumption is made that the starting node/Vertex is not
	 * included in the amount of stops within the route. This is
	 * a depth-first recursive search. 
	 * @param	start	Starting node/vertex of the route
	 * @param	end	Ending node/vertex of the route
	 * @param	depth	Indicates how many stops have been made, depth of search
	 * @param	count	Indicates current number of routes found that meet the 
	 * 			specifications.
	 * @param	exact	Number of stops allowed within the route
	 * @return	Gives the amount of routes found that have the specified amount of
	 * 		stops between two Nodes.
	 */	
	public int exactStops(Node start,Node end,int depth,int count,int exact)
	{
		//Number of Stops
		depth++;
		
		//Iterate through all adjacent nodes of starting node
		for(int i = 0; i < start.getAdjacent().size();i++)
		{
			Node n = start.getAdjacent().get(i);
			
			//Specifications met and number of routes found is incremented
			if(n.equals(end) && depth == exact)
			{
				count++;
			}
			
			//Number of stops has exceeded stops specified
			if(depth > exact)
			{
				return count;
			}
			
			//Find all routes within adjacent Nodes with specified stops
			count = exactStops(n,end,depth,count,exact);
		}
		return count;
	}
	/**
	 * Finds every route between two nodes in the Graph below
	 * a certain distance. This is
	 * a depth-first recursive search. 
	 * @param	start	Starting node/vertex of the route
	 * @param	end	Ending node/vertex of the route
	 * @param	curr_dist	the distance between starting Node and the current node
	 * @param	count	Indicates current number of routes found that meet the 
	 * 			specifications.
	 * @param	max	Max distance that a route cannot go over
	 * @return	Gives the amount of routes found that have less than or equal to
	 * 			max distance. 
	 */	
	public int maxDist(Node start,Node end,int curr_dist,int count,int max)
	{
		//Get adjacent Nodes of starting Node
		for(int i = 0; i < start.getAdjacent().size();i++)
		{
			Node n = start.getAdjacent().get(i);
			
			//Add distance from adjacent node to current node to current distance
			curr_dist = curr_dist + list[start.getIndex()][n.getIndex()];
			
			//If end Node found and current distance less than max
			if(n.equals(end) && curr_dist < max)
			{
				//Route Found
				count++;
			}
			
			//current distance has exceeded max distance
			if(curr_dist >= max)
			{
				return count;
			}
			
			//Find routes within adjacent Node that are less than max distance
			count = maxDist(n,end,curr_dist,count,max);
			
			//Decrement current distance after adjacent is visited
			curr_dist = curr_dist - list[start.getIndex()][n.getIndex()];
		}
		return count;
	}
	/**
	 * Finds the shortest route between two nodes within the Graph.
	 * A PriorityQueue is included to compare 
	 * the min. distance variable of every Node within the Queue.
	 * Adjacent Nodes are added to the queue and sorted according the 
	 * min. values. This is a Breadth-first search where adjacent nodes
	 * are added to a Queue and taken out of the queue according to min.
	 * distance priority.
	 * @param	s	Starting Node/Vertex of the route
	 * @param	e	Ending Node/Vertex of the route
	 * @return	Indicates the overall distance of the shortest route found
	 */	
	public int shortest(Node s, Node e)
	{
		PriorityQueue<Node> q = new PriorityQueue<Node>();
		int distance = 0;
		int min = 0;

		//Reset all min. distance values of every Node in Graph
		for(int i = 0; i < indices; i++)
		{
			nodes.get(i).set_Min(0);
		}
		
		//Add starting Node to the Queue
		q.add(s);
		Node u = null;
			
		while(!q.isEmpty())
		{
			u = q.poll();
			if(u != null)
			{	
				//Get adjacent nodes of current node
				for(int x = 0; x < u.getAdjacent().size();x++)
				{
					Node v = u.getAdjacent().get(x);
					
					//Get the distance between current node and adjacent node
					distance = list[u.getIndex()][v.getIndex()];
					
					//Add distance from current to adjacent to  current min. distance
					min = u.get_Min() + distance;
					
					//Check if the adjacent node min. distance is less than current
					if(v.get_Min() < min)
					{
						q.remove(v);		//Reset min. distance of adjacent node
						v.set_Min(min);
						q.add(v);			//Add adjacent node to queue
					}
					
					//if ending Node/Vertex found 
					if(v.equals(e))
					{
						return min;
					}
				}
			}	
		}
		return -1;
	}

	public static void main(String[] args) throws IOException{
		
		Graph g = new Graph(5);
    
		//Create Nodes for the Graph
		Node a = new Node("A");
		Node b = new Node("B");
		Node c = new Node("C");
		Node d = new Node("D");
		Node e = new Node("E");
		
		//Add all Nodes/Vertices to the Graph
		g.addVertex(a);
		g.addVertex(b);
		g.addVertex(c);
		g.addVertex(d);
		g.addVertex(e);
		
		
		//Add Edges between Nodes to the Graph
		g.addEdge(a, b, 5);
		g.addEdge(b, c, 4);
		g.addEdge(c, d, 8);
		g.addEdge(d, c, 8);
		g.addEdge(d, e, 6);
		g.addEdge(a, d, 5);
		g.addEdge(c, e, 2);
		g.addEdge(e, b, 3);
		g.addEdge(a, e, 7);
		
		
		//Create routes within the Graph
		Node[] n1 = {a,b,c};
		Node[] n2 = {a,d};
		Node[] n3 = {a,d,c};
		Node[] n4 = {a,e,b,c,d};
		Node[] n5 = {a,e,d};
		
		
		// 1. The distance of the route A-B-C.
		System.out.println("Output #1: "+g.route(n1));
		
		//2. The distance of the route A-D.
		System.out.println("Output #2: "+g.route(n2));
		
		//3. The distance of the route A-D-C.
		System.out.println("Output #3: "+g.route(n3));
		
		//4. The distance of the route A-E-B-C-D.
		System.out.println("Output #4: "+g.route(n4));
		
		//5. The distance of the route A-E-D
		System.out.println("Output #5: "+g.route(n5));
		
		/*6. The number of trips starting at C and ending at C 
		with a maximum of 3 stops.*/
		int x = g.maxStops(c, c,0,0, 3);
		System.out.println("Output #6: " +x);
		
		/*7. The number of trips starting at A and ending at C 
		 with exactly 4 stops.*/
		x = g.exactStops(a, c, 0, 0, 4);
		System.out.println("Output #7: " +x);
		
		/*8. The length of the shortest route 
		 (in terms of distance to travel) from A to C.*/
		System.out.println("Output #8: " + g.shortest(a, c));
		
		/*9. The length of the shortest route 
		(in terms of distance to travel) from B to B.*/
		System.out.println("Output #9: " + g.shortest(b, b));
		
		/*10. The number of different routes from C to C 
		with a distance of less than 30.*/ 
		x = g.maxDist(c, c, 0, 0, 30);
		System.out.println("Output #10: " +x);
		
	}
}
