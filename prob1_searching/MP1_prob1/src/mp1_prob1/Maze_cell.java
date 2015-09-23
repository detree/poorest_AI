package mp1_prob1;

import java.util.Comparator;

public class Maze_cell implements Comparator<Maze_cell>{
	private int index;
	private int parent;
	private int heuristic;
	private int total_cost;
	private int level;
	
	public Maze_cell(){
		index = -1;
		heuristic = -1;
	}
	
	public Maze_cell(int i, int h){
		index = i;
		heuristic = h;
	}
	
	public int get_parent(){
		return this.parent;
	}
	
	public int get_heuristic(){
		return 0;//this.heuristic;
	}
	
	public int get_totalCost(){
		return this.total_cost;
	}
	
	public int get_index(){
		return this.index;
	}
	
	public int get_level(){
		return this.level;
	}
	
	public void set_level(int l){
		this.level = l;
	}
	
	public void set_parent(int p){
		this.parent = p;
	}
	
	public void set_totalCost(int t){
		this.total_cost =  t;
	}

	public int compareTo(Maze_cell m) {
		// TODO Auto-generated method stub
		if(m.total_cost > this.total_cost)
			return m.index;
		else 
			return this.index;
	}

	@Override
	public int compare(Maze_cell m1, Maze_cell m2) {
		// TODO Auto-generated method stub
		if(m1.total_cost > m2.total_cost)
			return 1;
		else if(m1.total_cost < m2.total_cost)
			return -1;
		else 
			return 0;
	}
}
