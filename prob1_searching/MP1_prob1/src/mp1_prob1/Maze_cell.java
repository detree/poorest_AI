package mp1_prob1;

import java.util.Comparator;

public class Maze_cell implements Comparator<Maze_cell>{
	
	/* privart variables*/
	private int index; // index of this maze_cell, used to transform this maze_cell back to the (x,y) coordinate
	private int parent;	// parent of this maze_cell
	private int heuristic; //heuristic value
	private int total_cost; // total cost from this maze_cell to the goal state
	private int level; // the current level of this maze_cell, path_cost
	private char dir; // dir when arriving this maze_cell
	
	/* default constructor */
	public Maze_cell(){
		index = -1;
		heuristic = -1;
		parent = -1;
	}
	
	/* input: i - index 
	 * 		  h - heuristic
	 */
	public Maze_cell(int i, int h){
		index = i;
		heuristic = h;
		parent = -1;
	}
	
	public int get_parent(){
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
	
	public void set_index(int i){
		this.index=i;
	}
	
	public int get_level(){
		return this.level;
	}
	
	public char get_dir(){
		return this.dir;
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

	public void set_dir(char dir){
		this.dir =  dir;
	}
	
	/* check whether making a turn between previos cell and curr cell 
	 * input: curr - curr maze position
	 * 		  prev - prev maze position
	 */
	public int turn_cost(Maze_cell prev){
		char north = 'N';
		char east = 'E';
		char west = 'W';
		char south = 'S';
		if(this.dir == north && prev.dir == south)
			return 4;
		else if(this.dir == south && prev.dir == north)
			return 4;
		else if(this.dir == west && prev.dir == east)
			return 4;
		else if(this.dir == east && prev.dir == west)
			return 4;
		else if(this.dir != prev.dir)
			return 2;
		else
			return 0;
	}
	
	public int forward_cost(Maze_cell prev){
		char north = 'N';
		char east = 'E';
		char west = 'W';
		char south = 'S';
		if(this.dir == north && prev.dir == south)
			return 2;
		else if(this.dir == south && prev.dir == north)
			return 2;
		else if(this.dir == west && prev.dir == east)
			return 2;
		else if(this.dir == east && prev.dir == west)
			return 2;
		else if(this.dir != prev.dir)
			return 1;
		else
			return 0;
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
