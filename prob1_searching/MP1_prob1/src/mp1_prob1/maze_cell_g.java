package mp1_prob1;

public class maze_cell_g extends Maze_cell{
	private int ghost_pos;
	private int ghost_dir;//0 for going to left and 1 for going to right

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