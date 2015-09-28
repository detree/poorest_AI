package mp1_prob1;

import java.util.Comparator;

public class maze_cell_g implements Comparator<maze_cell_g>{
	private int index;
	private maze_cell_g parent;
	private int heuristic;
	private int total_cost;
	private int level;
	private int ghost_pos;
	private int ghost_dir;//0 for going to left and 1 for going to right

	public maze_cell_g(int i, int h){
		index = i;
		heuristic = h;
	}
	
	public maze_cell_g(int idx, int gpos, int gdir, int h){
		index = idx;
		ghost_pos = gpos;
		ghost_dir = gdir;
		heuristic = h;
	}
	
	public maze_cell_g(){
		index = -1;
		heuristic = -1;
		ghost_pos = -1;
		ghost_dir = -1;
		total_cost = -1;
	}
	
	public int get_ghost_pos(){
		return this.ghost_pos;
	}
	
	public int get_ghost_dir(){
		return this.ghost_dir;
	}
	
	public maze_cell_g get_parent(){
		return this.parent;
	}
	
	public int get_heuristic(){
		return this.heuristic;
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
	
	public void set_parent(maze_cell_g p){
		this.parent = p;
	}
	
	public void set_totalCost(int t){
		this.total_cost =  t;
	}
	
	public void set_ghost_pos(int pos){
		this.ghost_pos = pos;
	}
	
	public void set_ghost_dir(int dir){
		this.ghost_dir = dir;
	}
	
	
	public boolean equals(maze_cell_g other)
	{
		if( this.get_index() == other. get_index() && this.get_ghost_pos() == other.get_ghost_pos() &&
				this.get_ghost_dir() == other. get_ghost_dir() )
			return true;
		else
			return false;
			
	};
	
	@Override
	public int compare(maze_cell_g m1, maze_cell_g m2) {
		// TODO Auto-generated method stub
		if(m1.total_cost > m2.total_cost)
			return 1;
		else if(m1.total_cost < m2.total_cost)
			return -1;
		else 
			return 0;
	}
}