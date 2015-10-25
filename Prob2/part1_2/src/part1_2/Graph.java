package part1_2;

//libraries
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.IntPredicate;

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
//System.out.println(connect_from+","+connect_to+","+j+","+adj_list.get(j).get(k));
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
//System.out.println(i+" in compare:"+j+" dist:"+temppair.dist);
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
	
	//=============================search functions===================================
	private boolean [][]plate;//plate[node_n][4], the second dimension is for 4 different colors.
	
	private boolean sol_backtracing(boolean [][]color, int curr_node, boolean visited[], int visited_num)
	{
//System.out.println("visiting:"+curr_node+" all:"+visited_num);
		if(visited_num>=node_n) return true;
		boolean []color_hash = new boolean[4];
		short color_tried = 0, color_next;
		while(color_tried<4)
		{
			color_next = (short) random_generator.nextInt(4);//the next color for trial.
			if(color_hash[color_next]) continue;//this color has been tried before.
			color_hash[color_next] = true;
			color_tried++;
			//now need to try if the constrain is met.
			boolean flag_constrain=false;
			for(int j=0; j<adj_list.get(curr_node).size(); j++)
			{
				int temp_checking = adj_list.get(curr_node).get(j);
				if(visited[temp_checking] && color[temp_checking][color_next] == true)
				{
					flag_constrain = true;
					break;
				}
			}
			if(!flag_constrain)
			{
				if(visited_num == node_n-1)
				{
					for(int j=0; j<node_n; j++)
						for(int k=0;k<4;k++)
							plate[j][k] = color[j][k];
					plate[curr_node][color_next] = true;
					return true;
				}
				
				//normally, we need to try next node.
				boolean [][]new_color = new boolean[node_n][4];
				boolean []new_visited = new boolean[node_n];
				for(int j=0; j<node_n; j++)
				{
					new_visited[j] = visited[j];
					for(int k=0;k<4;k++)
						new_color[j][k] = color[j][k];
				}
				new_visited[curr_node] = true;
				new_color[curr_node][color_next] = true;
				int next_node = random_generator.nextInt(node_n);
				while( new_visited[next_node] )
					next_node = random_generator.nextInt(node_n);
				if(sol_backtracing(new_color, next_node, new_visited, visited_num+1))
					return true;
			}
		}
		return false;
	}
	//naive search with randomized next node and randomized value assignment. seem that upper bound is 45
	public boolean[][] sol_backtracing_enter()
	{
		plate = new boolean[node_n][4];
		boolean [][]color = new boolean[node_n][4];
		boolean []visited = new boolean[node_n];
		if( sol_backtracing(color, random_generator.nextInt(node_n), visited, 0) )
		{
			//print out the solution
			for(int j=0;j<node_n;j++)
				for(int k=0;k<4;k++)
					if(plate[j][k])
						System.out.println("for node"+j+" color is"+k);
			return plate;
		}
		else
		{
System.out.println("hmm, serious error in backtracing.");
			return plate;
		}
	}

	private boolean foward_check(int node_num, short color_next, boolean[][] color,
			short[] plate_remain, int[] use_by_other)
	{
		for(int i=0; i<adj_list.get(node_num).size(); i++)
		{
			int next_node = adj_list.get(node_num).get(i);
			if(color[next_node][color_next])
			{
				plate_remain[next_node]--;
				if(plate_remain[next_node]<=0)
					return false;
			}
			color[next_node][color_next] = false;
			for(int j=0;j<4;j++)
				if(color[next_node][j])
					use_by_other[j]++;
		}
		return true;
	}
	private boolean sol_forward(int curr_node, boolean [][]color, boolean visited[], int visited_num,
			short[] plate_remain, int[] use_by_other)
	{
//System.out.println("visiting:"+curr_node+" all:"+visited_num);
		if(visited_num==node_n)
		{
			for(int j=0; j<node_n; j++)
				for(int k=0;k<4;k++)
					plate[j][k] = color[j][k];
			return true;
		}
		boolean []color_hash = new boolean[4];
		short color_tried=0, color_next=0;
		for(short i=0;i<4;i++)
		{
			if(color[curr_node][i]==false)
			{
				color_hash[i]=true;
				color_tried++;
			}
		}
		while(color_tried<4)
		{
			for(short i=0;i<4;i++)
				if(color_hash[i]==false)
				{
					color_next = i;
					break;
				}
			for(short i=0;i<4;i++)
				if(color_hash[i]==false && use_by_other[i]<use_by_other[color_next])
					color_next = i;
			//already found the next color to try.
			color_tried++;
			color_hash[color_next]=true;
//System.out.println("trying color:"+color_next);
			boolean [][]new_color = new boolean[node_n][4];
			boolean []new_visited = new boolean[node_n];
			short []new_plate_remain = new short[node_n];
			int[] new_use_by_other = new int[4];
			for(short i=0;i<4;i++) new_use_by_other[i]=0;
			for(int j=0; j<node_n; j++)
			{
				new_visited[j] = visited[j];
				new_plate_remain[j] = plate_remain[j];
				for(int k=0;k<4;k++)
					if(j!=curr_node) new_color[j][k] = color[j][k];
			}
			new_color[curr_node][color_next] = true;
			new_visited[curr_node] = true;
			new_plate_remain[curr_node] = 1;
			if(foward_check(curr_node, color_next, new_color, new_plate_remain, new_use_by_other))
			{
				//choose the next node to explore, using plate_remain.
				int next_node=-1;
				for(int i=0;i<node_n;i++)
				{
					if(next_node==-1 && new_visited[i]==false)
						next_node = i;
					if(next_node!=-1 && new_plate_remain[i]>new_plate_remain[next_node])
						next_node = i;
				}
				if(next_node<0 && visited_num+1<node_n)
					System.out.println("hmm, serious error in forward_checking main.");
				if(sol_forward(next_node, new_color, new_visited, visited_num+1, new_plate_remain, new_use_by_other))
					return true;
			}
		}
		return false;
	}
	public boolean[][] sol_forward_enter()
	{
		plate = new boolean[node_n][4];
		boolean [][]color = new boolean[node_n][4];
		boolean []visited = new boolean[node_n];
		short[] plate_remain = new short[node_n];
		int[] use_by_other = new int[4];
		int temp_first_node = 0;
		for(int i=0;i<node_n;i++)
		{
			if(adj_list.get(temp_first_node).size()<adj_list.get(i).size())
				temp_first_node = i;
			plate_remain[i]=4;
			for(int j=0;j<4;j++)
				color[i][j]=true;
		}
		for(short i=0;i<4;i++) use_by_other[i]=0;
		if( sol_forward(temp_first_node, color, visited, 0, plate_remain, use_by_other) )
		{
			//print out the solution
			for(int j=0;j<node_n;j++)
				for(int k=0;k<4;k++)
					if(plate[j][k])
						System.out.println("for node"+j+" color is"+k);
			return plate;
		}
		else
		{
System.out.println("hmm, serious error in sol_foward_enter.");
			return plate;
		}
	}
	
}
