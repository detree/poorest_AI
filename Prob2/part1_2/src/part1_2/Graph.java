package part1_2;

//libraries
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class Graph {
	private int node_n;
	private double square_size;
	private List<Graph_node> nodes;
	private List< List<Integer> > adj_list;
	private boolean[][] matrix;
	private Random random_generator;
	//init a empty graph
	public Graph()
	{
		node_n = 0;
		matrix = null;
		square_size = 0;
		nodes = null;
		adj_list = null;
		random_generator = new Random();
	}
	//init a graph with n points.
	public Graph(int n, double size)
	{
		node_n = n;
		matrix = new boolean[n][n];
		for(int i=0;i<node_n;i++)
			for(int j=0;j<node_n;j++)
				if(i==j){
					matrix[i][j]=true;
				}
				else {
					matrix[i][j]=false;
				}
		square_size = size;
		nodes = new ArrayList<Graph_node>();
		adj_list = new ArrayList<List<Integer>>();
		for(int i=0;i<node_n;i++)
			adj_list.add( new ArrayList<Integer>() );
		random_generator = new Random();
	}
	private double distance(Graph_node n1, Graph_node n2)
	{
		return Math.sqrt( (n1.posx-n2.posx)*(n1.posx-n2.posx) + (n1.posy-n2.posy)*(n1.posy-n2.posy) );
	}

	private boolean LineIntersect(Graph_node node1,Graph_node node2,Graph_node node3,Graph_node node4)
	{
		double x1=node1.posx, y1=node1.posy,
			x2=node2.posx, y2=node2.posy,
			x3=node3.posx, y3=node3.posy,
			x4=node4.posx, y4=node4.posy;
		double mua,mub;
		double denom,numera,numerb;
		double x,y;
		final double EPS = 0.00001;
		denom  = (y4-y3) * (x2-x1) - (x4-x3) * (y2-y1);
		numera = (x4-x3) * (y1-y3) - (y4-y3) * (x1-x3);
		numerb = (x2-x1) * (y1-y3) - (y2-y1) * (x1-x3);

		/* Are the line coincident? */
		if (Math.abs(numera) < EPS && Math.abs(numerb) < EPS && Math.abs(denom) < EPS) {
			x = (x1 + x2) / 2;
			y = (y1 + y2) / 2;
			return(false);
		}

		/* Are the line parallel */
		if (Math.abs(denom) < EPS) {
		   x = 0;
		   y = 0;
		   return(false);
		}

		/* Is the intersection along the the segments */
		mua = numera / denom;
		mub = numerb / denom;
		if (mua < 0 || mua > 1 || mub < 0 || mub > 1) {
			x = 0;
			y = 0;
			return(false);
		}
		x = x1 + mua * (x2 - x1);
		y = y1 + mua * (y2 - y1);
		
		int boundry_count=0;
		if(Math.abs(x-x1)<EPS && Math.abs(y-y1)<EPS)
			boundry_count++;
		if(Math.abs(x-x2)<EPS && Math.abs(y-y2)<EPS)
			boundry_count++;
		if(Math.abs(x-x3)<EPS && Math.abs(y-y3)<EPS)
			boundry_count++;
		if(Math.abs(x-x4)<EPS && Math.abs(y-y4)<EPS)
			boundry_count++;
		if(boundry_count>=2)
			return false;
		return(true);
	}
	
	private boolean have_intersect(int connect_from, int connect_to)
	{
		for(int j=0;j<node_n;j++)//go through every point has already assigned edges
		{
			for(int k=0; k < adj_list. get(j). size(); k++)
			{
				if( (j==connect_from && k==connect_to) || (j==connect_to && k==connect_from) )
					continue;
				if(j!=k && adj_list.get(j).size()>0 &&
						LineIntersect(nodes.get(connect_from), nodes.get(connect_to), nodes.get(j), nodes.get(adj_list.get(j).get(k))) )
				{
System.out.println(connect_from+","+connect_to+","+j+","+adj_list.get(j).get(k));
					return true;
				}
			}
		}
		return false;
	}

	public boolean draw_graph(){
		if(node_n ==0) return false;
		Graph_node temp = new Graph_node();
		int i=0;
		while(i<node_n)
		{
			temp = new Graph_node();
			temp.rand_pos(square_size);
			if(!nodes.contains(temp))
			{
				nodes.add(temp);
				i++;
			}
		}
for( i=0;i<nodes.size();i++)
	System.out.println(i+" at("+nodes.get(i).posx+","+nodes.get(i).posy+")");
		//---------now every nodes has the positions, then we need to connect them-----------
		//summary: i is the node we currently working on
		//nodes in the queue, or curr_node is the node we want to connect to
		boolean []hash=new boolean[node_n+1];
		int can_connect = node_n;
		while(can_connect>0)
		{
			i = random_generator.nextInt(node_n);
			//adding all nodes with their distance to i to a priority queue.
			PriorityQueue<Checking_pair> queue = new PriorityQueue<Checking_pair>();
			for(int j=0;j<node_n;j++)
			{
				if(matrix[i][j]==false && !have_intersect(i, j))
				{
					Checking_pair temppair = new Checking_pair( j, distance(nodes.get(i), nodes.get(j) ) );
System.out.println(i+" in compare:"+j+" dist:"+temppair.dist);
					queue.add( temppair );
				}
			}
			if(queue.isEmpty())
			{
				if(hash[i]==false)
				{
					can_connect--;
					hash[i]=true;
				}
				continue;
			}
			Checking_pair curr = queue.poll();
			int curr_node = curr.node_num;
			adj_list.get(i).add(curr_node);
			adj_list.get(curr_node).add(i);
			matrix[curr_node][i]=true;
			matrix[i][curr_node]=true;
		}
for(i=0;i<adj_list.size();i++){
	System.out.println("for node "+i+" :");
	for(int j=0;j<adj_list.get(i).size();j++)
		System.out.print(adj_list.get(i).get(j)+",");
	System.out.println();
}
		return true;
	}
}
