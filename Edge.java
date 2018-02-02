
public class Edge {
//TEST BRANCH	
	private Node start;
	private Node end;
	private int weight;
	
	public Edge()
	{
		start = null;
		end = null;
		weight = 0;
	}
	public Edge(Node s, Node e, int w)
	{
		start = s;
		end = e;
		weight = w;
	}
	
	public Node getStart()
	{
		return this.start;
	}
	
	public Node getEnd()
	{
		return this.end;
	}
	
	public int getWeight()
	{
		return this.weight;
	}
	
	public boolean equals(Edge e)
	{
		if(this.start.equals(e.start) && this.end.equals(e.end))
		{
			if(this.weight == e.weight)
			{
				return true;
			}
		}
		return false;
	}
}
