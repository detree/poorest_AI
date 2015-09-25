package mp1_prob1;

import java.util.Comparator;

public class multidot_goal_cell implements Comparator<multidot_goal_cell>{
	
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
		
		public void set_totalCost(int t){
			this.heuristic =  t;
		}

		public int compareTo(multidot_goal_cell other) {
			// TODO Auto-generated method stub
			if(other.heuristic > this.heuristic)
				return other.pos;
			else 
				return this.pos;
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
