package mp1_prob1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Maze_pathFinding {
/* input - current maze
 * return 0 - if success, -1 if fail
 * solve the maze with greedy best first algorithm
 */
	public int SolveMazeGBFS(Maze cmaze){
		Stack st = new Stack();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int [] parent = new int [mazeSize];
		char []visited = new char [mazeSize];
		int [] Mdist = new int[mazeSize];
		int goalstate = cmaze.get_goal_state();
		int pathcost = 0;
		for(int i = 0;i < cmaze.get_width()* cmaze.get_height();i++)
		{
			if(cmaze.maze_index(i%cmaze.get_width(),i/cmaze.get_width()) != '%')
				Mdist[i] = Math.abs(goalstate%cmaze.get_width() - i % cmaze.get_width()) + Math.abs(goalstate / cmaze.get_width() - i / cmaze.get_width());
		}
		int current = cmaze.get_start_state();
		int next_pos = -1;
		//System.out.println("hello");
		while(!st.isEmpty())
		{
			//after we got to goal position
			if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())== '.'){
				current = parent[current];
				while(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())!= 'P')
				{	
					++pathcost;
					cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
					current = parent[current];
				}
				return 1;
			}
			else{
				visited[current] = 1;
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
			}
		}
		return 0;	
	}
/* input: cmaze - current maze, 
 * 		  startpos - an integer represent the startpos of the "player"
 * return: 1 - if success, 0 if fail
 * solve the maze with depth first search algorithm
 */
	public int SolveMazeDFS(Maze cmaze,int startpos){
		Stack st = new Stack();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int [] parent = new int [mazeSize];
		char []visited = new char [mazeSize];
		int start = startpos;
		int current;
		int nextpos;
		int n_node = 1;
		int pathcost = 0;
		//System.out.print(start);
		st.push(start);
		while(!st.isEmpty())
		{
			current = (Integer) st.peek();
			st.pop();
			//after we got to goal position
			if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())== '.'){
				current = parent[current];
				++pathcost;
				while(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())!= 'P')
				{	
					++pathcost;
					cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
					current = parent[current];
				}
				System.out.println();
				System.out.print("pathcost:");
				System.out.print(pathcost);
				System.out.println();
				System.out.print("number of nodes:");
				System.out.print(n_node);
				System.out.println();
				return 1;
			}
			else{
				visited[current] = 'V';
				if(current - 1 > 0){
					nextpos = current - 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						
						st.push(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
				
				if(current + 1 < mazeSize){
					nextpos = current + 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						st.push(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
				if(current + cmaze.get_width() < mazeSize){
					nextpos = current + cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						st.push(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
				if(current - cmaze.get_width() > 0){
					nextpos = current - cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						st.push(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
			}
		}
		return 0;	
	}
	
	/* input: cmaze - current maze, 
	 * 		  startpos - an integer represent the startpos of the "player"
	 * return: 1 - if success, 0 if fail
	 * solve the maze with depth first search algorithm
	 */
		public int penalize_SolveMazeDFS(Maze cmaze,int startpos,int turn_cost,int fm_cost){
			Stack st = new Stack();
			int mazeSize = cmaze.get_width() * cmaze.get_height();
			int [] parent = new int [mazeSize];
			char []visited = new char [mazeSize];
			int start = startpos;
			int current;
			int nextpos;
			int n_node = 1;
			int pathcost = 0;
			char lastdir;
			//System.out.print(start);
			st.push(start);
			while(!st.isEmpty())
			{
				current = (Integer) st.peek();
				st.pop();
				//after we got to goal position
				if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())== '.'){
					cmaze.find_dir(parent[current],current);
					lastdir = cmaze.get_dir();
					current = parent[current];
					pathcost += fm_cost;
					while(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())!= 'P')
					{	
						cmaze.find_dir(parent[current],current);
						if(lastdir != cmaze.get_dir())
							pathcost = pathcost + fm_cost + turn_cost;
						else 
							pathcost += fm_cost;
						lastdir = cmaze.get_dir();
						cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
						current = parent[current];
					}
					if(lastdir != 'E')
						pathcost += turn_cost;
					System.out.println();
					System.out.print("pathcost:");
					System.out.print(pathcost);
					System.out.println();
					System.out.print("number of nodes:");
					System.out.print(n_node);
					System.out.println();
					return 1;
				}
				else{
					visited[current] = 'V';
					if(current - 1 > 0){
						nextpos = current - 1;
						if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
							
							st.push(nextpos);
							n_node ++;
							parent[nextpos] = current;
						}
					}
					
					if(current + 1 < mazeSize){
						nextpos = current + 1;
						if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
							st.push(nextpos);
							n_node ++;
							parent[nextpos] = current;
						}
					}
					if(current + cmaze.get_width() < mazeSize){
						nextpos = current + cmaze.get_width();
						if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
							st.push(nextpos);
							n_node ++;
							parent[nextpos] = current;
						}
					}
					if(current - cmaze.get_width() > 0){
						nextpos = current - cmaze.get_width();
						if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
							st.push(nextpos);
							n_node ++;
							parent[nextpos] = current;
						}
					}
				}
			}
			return 0;	
		}
/* input: cmaze - current maze, 
 * 		  startpos - an integer represent the startpos of the "player"
 * return: 1 - if success, 0 if fail
 * solve the maze with breadth first search algorithm
 */	
	public int SolveMazeBFS(Maze cmaze,int startpos){
		Queue<Integer> queue = new LinkedList<Integer>();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int [] parent = new int [mazeSize];
		char []visited = new char [mazeSize];
		int start = startpos;
		int current;
		int nextpos;
		int pathcost = 0;
		//System.out.print(start);
		queue.add(start);
		int n_node = 0;
		while(!queue.isEmpty())
		{
			current = queue.remove();
			//after we got to goal position
			if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())== '.'){
				current = parent[current];
				pathcost ++;
				while(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())!= 'P')
				{	
					pathcost ++;
					cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
					current = parent[current];
				}
				System.out.println();
				System.out.print("pathcost:");
				System.out.print(pathcost);
				System.out.println();
				System.out.print("number of nodes:");
				System.out.print(n_node);
				System.out.println();
				return 1;
			}
			else{
				visited[current] = 'V';
				if(current - 1 > 0){
					nextpos = current - 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
				
				if(current + 1 < mazeSize){
					nextpos = current + 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
				if(current + cmaze.get_width() < mazeSize){
					nextpos = current + cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
				if(current - cmaze.get_width() > 0){
					nextpos = current - cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
			}
		}
		return 0;
	}
	
	public int penalize_SolveMazeBFS(Maze cmaze,int startpos,int turn_cost, int fm_cost){
		Queue<Integer> queue = new LinkedList<Integer>();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int [] parent = new int [mazeSize];
		char []visited = new char [mazeSize];
		int start = startpos;
		int current;
		int nextpos;
		int pathcost = 0;
		//System.out.print(start);
		queue.add(start);
		int n_node = 0;
		char last_dir;
		while(!queue.isEmpty())
		{
			current = queue.remove();
			//after we got to goal position
			if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())== '.'){
				cmaze.find_dir(parent[current],current);
				last_dir = cmaze.get_dir();
				current = parent[current];
				pathcost += fm_cost;
				while(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width())!= 'P')
				{	
					cmaze.find_dir(parent[current],current);
					if(last_dir != cmaze.get_dir())
						pathcost = pathcost + fm_cost + turn_cost;
					else 
						pathcost += fm_cost;
					last_dir = cmaze.get_dir();
					cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
					current = parent[current];
				}
				System.out.println();
				System.out.print("pathcost:");
				System.out.print(pathcost);
				System.out.println();
				System.out.print("number of nodes:");
				System.out.print(n_node);
				System.out.println();
				return 1;
			}
			else{
				visited[current] = 'V';
				if(current - 1 > 0){
					nextpos = current - 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
				
				if(current + 1 < mazeSize){
					nextpos = current + 1;
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
				if(current + cmaze.get_width() < mazeSize){
					nextpos = current + cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
				if(current - cmaze.get_width() > 0){
					nextpos = current - cmaze.get_width();
					if(visited[nextpos] != 'V' && cmaze.maze_index(nextpos%cmaze.get_width(),nextpos/cmaze.get_width()) != '%'){
						queue.add(nextpos);
						n_node ++;
						parent[nextpos] = current;
					}
				}
			}
		}
		return 0;
	}

}
