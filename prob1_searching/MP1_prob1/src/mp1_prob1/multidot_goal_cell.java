package mp1_prob1;

import java.util.Comparator;

public class multidot_goal_cell implements Comparator<multidot_goal_cell>{
		private int n_wall;
		private int pos;
		private int heuristic;
		
		public multidot_goal_cell(){
			pos = -1;
			heuristic = -1;
		}
		
		public multidot_goal_cell(int i, int h){
			pos = i;
			heuristic = h;
		}
				
		public int get_heuristic(){
			return this.heuristic;
		}
		

		public int get_pos(){
			return this.pos;
		}
		
		public void set_heurist(int t){
			this.heuristic =  t;
		}

		public int compareTo(multidot_goal_cell other) {
			// TODO Auto-generated method stub
			if(other.heuristic > this.heuristic)
				return other.pos;
			else 
				return this.pos;
		}
		public int set_n_wall(Maze cmaze)
		{
			//System.out.println(this.pos);
		
			if(this.pos + 1 < cmaze.get_width() * cmaze.get_height())
			 if(cmaze.maze_index( (this.pos+1) % cmaze.get_width(), (this.pos+1) / cmaze.get_width() ) == '%' )
				n_wall++;
			
			if(this.pos - 1 > 0)
			 if(cmaze.maze_index( (this.pos-1) % cmaze.get_width(), (this.pos-1) / cmaze.get_width() ) == '%' )
				n_wall++;
			
			if(this.pos + cmaze.get_width() < cmaze.get_width() * cmaze.get_height())
			 if(cmaze.maze_index( (this.pos+cmaze.get_width()) % cmaze.get_width(), (this.pos+cmaze.get_width()) / cmaze.get_width() ) == '%' )
				n_wall++;
			
			if(this.pos - cmaze.get_width() > cmaze.get_width() * cmaze.get_height())
			 if(cmaze.maze_index( (this.pos-cmaze.get_width()) % cmaze.get_width(), (this.pos-cmaze.get_width()) / cmaze.get_width() ) == '%' )
				n_wall++;
			
			return this.n_wall; 
		}
		@Override
		public int compare(multidot_goal_cell g1,  multidot_goal_cell g2) {
			// TODO Auto-generated method stub
			if(g1.heuristic > g2.heuristic)
				return 1;
			else if(g1.heuristic < g2.heuristic)
				return -1;
			else 
				return 0;
		}
}
