import java.util.LinkedList;
//TEST

public class Node implements Comparable<Node>{
	
	// member fields
	private int index;
	private String name;
	private int min_dist;
	private int state;
	private LinkedList<Node> adjacent;
	
	
	//initializing constructor
	public Node(String s)
	{
		name = s;
		index = 0;	
		min_dist = 0;
		state = 0;
		adjacent = new LinkedList<Node>();
	}
	
	public void setState(int x)
	{
		if(x==0){state = 0;} //unvisited
		if(x==1){state = 1;} //visiting
		if(x==2){state = 2;} //visited
	}
	
	public int getState()
	{
		return state;
	}
	
	public void setIndex(int i)
	{
		index = i;
	}
	
	public int getIndex()
	{
		return index;
	}
	public String getName()
	{
		return this.name;
	}
	
	public LinkedList<Node> getAdjacent()
	{
		return adjacent;
	}
	
	public int get_Min()
	{
		return this.min_dist;
	}
	
	public void set_Min(int x)
	{
		this.min_dist = x;
	}
	public boolean equals(Node n)
	{
	if(this.name == n.getName())
		{
			return true;
		}
		return false;
	}
	
	public String toString()
	{
		return "Name of Node:" + name + "Index:" + index;
	}

	@Override
	public int compareTo(Node a) {
		
		if(this.min_dist < a.get_Min())
		{
			return -1;
		}
		else if(this.min_dist > a.get_Min())
		{
			return 1;
		}
		else{
			return 0;
		}
	}
}
