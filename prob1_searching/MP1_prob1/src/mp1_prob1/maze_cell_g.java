package mp1_prob1;

import java.util.Comparator;

public class maze_cell_g extends Maze_cell implements Comparator<Maze_cell>{
	private int ghost_pos;
	private int ghost_dir;//0 for going to left and 1 for going to right

	public maze_cell_g(int i, int h){
		super(i,h);
	}
	public maze_cell_g(){
		super();
		ghost_pos = -1;
		ghost_dir = -1;
	}
	
	public int get_ghost_pos(){
		return this.ghost_pos;
	}
	
	public int get_ghost_dir(){
		return this.ghost_dir;
	}
	public void set_ghost_pos(int pos){
		this.ghost_pos = pos;
	}
	
	public void set_ghost_dir(int dir){
		this.ghost_dir = dir;
	}
}