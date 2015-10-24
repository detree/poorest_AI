package part1_2;

public class Graph {
	private int node_n;
	private boolean[][] matrix;
	public Graph()
	{
		node_n = 0;
		matrix = null;
	}
	public Graph(int n)
	{
		node_n = n;
		matrix = new boolean[n][n];
		for(int i=0;i<node_n;i++)
			for(int j=0;j<node_n;j++)
				matrix[i][j]=false;
	}
	public void draw_graph(){
		
	}
}
