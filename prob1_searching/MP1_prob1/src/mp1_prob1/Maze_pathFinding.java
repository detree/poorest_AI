package mp1_prob1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Maze_pathFinding {
	
	public int SolveMazeAStar(Maze cmaze){
		int [] Mdist = new int[cmaze.get_width() * cmaze.get_height()];
		int goal_state = cmaze.get_goal_state();
		int start_state = cmaze.get_start_state();
		int path_cost = 0;
		int [] total_cost = new int[cmaze.get_width() * cmaze.get_height()];
		for(int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++){
			if(cmaze.maze_index(i%cmaze.get_width(), i/cmaze.get_height()) == '%')
				Mdist[i] = -1;
			else
				Mdist[i] = cmaze.manhattan_distance(i%cmaze.get_width(), i/cmaze.get_height(), goal_state%cmaze.get_width(), goal_state/cmaze.get_height());
		}
		
		
		return 1;
	}
	
	public int SolveMazeGBFS(Maze cmaze){
		int [] Mdist = new int[cmaze.get_width() * cmaze.get_height()];
		int goalstate = cmaze.get_start_state();
		cmaze.fill_in_maze(goalstate%cmaze.get_width(),goalstate/cmaze.get_width(),'g');
		for(int i = 0;i < cmaze.get_width()* cmaze.get_height();i++)
		{
			if(cmaze.maze_index(i%cmaze.get_width(),i/cmaze.get_width()) != '%')
				Mdist[i] = Math.abs(goalstate%cmaze.get_width() - i % cmaze.get_width()) + Math.abs(goalstate / cmaze.get_width() - i / cmaze.get_width());
		}
		int current = cmaze.get_goal_state();
		int next_pos = -1;
		System.out.println("hello");
		while(cmaze.maze_index(current % cmaze.get_width(),current / cmaze.get_width()) != 'g')
		{
				if (cmaze.maze_index((current - 1) % cmaze.get_width(),(current - 1) / cmaze.get_width()) != '%')
					next_pos = current - 1;
				if (cmaze.maze_index((current - cmaze.get_width()) % cmaze.get_width(),(current - cmaze.get_width()) / cmaze.get_width()) != '%'){
					if(next_pos == -1)
						next_pos = current - cmaze.get_width();
					else if(Mdist[current - cmaze.get_width()] < Mdist[next_pos])
						next_pos = current - cmaze.get_width();
				}
				if (cmaze.maze_index((current + 1) % cmaze.get_width(),(current + 1) / cmaze.get_width()) != '%'){
					if(next_pos == -1)
						next_pos = current + 1;
					else if(Mdist[current + 1] < Mdist[next_pos])
						next_pos = current + 1;
				}
				if (cmaze.maze_index((current + cmaze.get_width()) % cmaze.get_width(),(current + cmaze.get_width()) / cmaze.get_width()) != '%'){
					if(next_pos == -1)
						next_pos = current + cmaze.get_width();
					else if(Mdist[current + cmaze.get_width()] < Mdist[next_pos])
						next_pos = current + cmaze.get_width();
				}
				if(next_pos == -1)
					return -1;
				current = next_pos;
				System.out.println(cmaze.maze_index(next_pos % cmaze.get_width(),next_pos / cmaze.get_height()));
				if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width()) == '.')
					return 0;
				cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
		}
		return -1;
	}
	/* input cmaze - current maze, 
	 * 		 startpos - start position 
	 * 
	 */
	public int SolveMazeDFS(Maze cmaze,int startpos){
		Stack st = new Stack();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int [] parent = new int [mazeSize];
		char []visited = new char [mazeSize];
		int start = startpos;
		int current;
		int nextpos;
		//System.out.print(start);
		st.push(start);
		while(!st.isEmpty())
		{
			current = (Integer) st.peek();
			st.pop();
			//after we got to goal position
			if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())== '.'){
				current = parent[current];
				while(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())!= 'P')
				{	
					cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
					current = parent[current];
				}
				return 1;
			}
			else{
				visited[current] = 'V';
				if(current - 1 > 0){
					nextpos = current - 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						st.push(nextpos);
						parent[nextpos] = current;
					}
				}
				
				if(current + 1 < mazeSize){
					nextpos = current + 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						st.push(nextpos);
						parent[nextpos] = current;
					}
				}
				if(current + cmaze.get_width() < mazeSize){
					nextpos = current + cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						st.push(nextpos);
						parent[nextpos] = current;
					}
				}
				if(current - cmaze.get_width() > 0){
					nextpos = current - cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						st.push(nextpos);
						parent[nextpos] = current;
					}
				}
			}
		}
		return 0;	
	}
	public int SolveMazeBFS(Maze cmaze,int startpos){
		Queue<Integer> queue = new LinkedList<Integer>();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int [] parent = new int [mazeSize];
		char []visited = new char [mazeSize];
		int start = startpos;
		int current;
		int nextpos;
		//System.out.print(start);
		queue.add(start);
		while(!queue.isEmpty())
		{
			current = queue.remove();
			//after we got to goal position
			if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())== '.'){
				current = parent[current];
				while(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())!= 'P')
				{	
					cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
					current = parent[current];
				}
				return 1;
			}
			else{
				visited[current] = 'V';
				if(current - 1 > 0){
					nextpos = current - 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						parent[nextpos] = current;
					}
				}
				
				if(current + 1 < mazeSize){
					nextpos = current + 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						parent[nextpos] = current;
					}
				}
				if(current + cmaze.get_width() < mazeSize){
					nextpos = current + cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						parent[nextpos] = current;
					}
				}
				if(current - cmaze.get_width() > 0){
					nextpos = current - cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						parent[nextpos] = current;
					}
				}
			}
		}
		return 0;
	}

}
