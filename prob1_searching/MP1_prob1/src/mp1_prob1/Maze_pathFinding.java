package mp1_prob1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Maze_pathFinding {
<<<<<<< HEAD
	
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
	
=======
/* input - current maze
 * return 0 - if success, -1 if fail
 * solve the maze with greedy best first search algorithm
 */
>>>>>>> origin/master
	public int SolveMazeGBFS(Maze cmaze){
		//initialize
		int [] Mdist = new int[cmaze.get_width() * cmaze.get_height()];
		int goalstate = cmaze.get_goal_state();
		int pathcost = 0;
		//mark the goal so it is not '.'
		cmaze.fill_in_maze(goalstate%cmaze.get_width(),goalstate/cmaze.get_width(),'g');
		//use heuristic funtion to make a table
		for(int i = 0;i < cmaze.get_width()* cmaze.get_height();i++)
		{
			if(cmaze.maze_index(i%cmaze.get_width(),i/cmaze.get_width()) != '%')
				Mdist[i] = cmaze.manhattan_distance(goalstate%cmaze.get_width(), goalstate / cmaze.get_width(), i % cmaze.get_width(), i / cmaze.get_width());
			else 
				Mdist[i] = -1;
		}
		int current = cmaze.get_start_state();
		int next_pos = -1;
		
		while(cmaze.maze_index(current % cmaze.get_width(),current / cmaze.get_width()) != 'g')
		{
				//find next position
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
				current = next_pos;
				//if the closest place is somewhere we already visited, GFBS is failed.
				if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width()) == '.')
				{
					System.out.println();
					System.out.print("pathcost:");
					System.out.print(pathcost);
					System.out.println();
					System.out.print("number of nodes:");
					System.out.print(pathcost);
					System.out.println();
					return -1;
				}
				cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
				pathcost ++;
		}
		//successfully find the goal
		return 0;
	}

	
/* input - current maze
 * return 0 - if success, -1 if fail
 * solve the maze with greedy best first search algorithm
 */
	public int penalize_SolveMazeGBFS(Maze cmaze,int turn_cost,int fm_cost){
		//initialize
		int [] Mdist = new int[cmaze.get_width() * cmaze.get_height()];
		int goalstate = cmaze.get_goal_state();
		int pathcost = 0;
		int n_node = 0;
		char last_dir;
		cmaze.fill_in_maze(goalstate%cmaze.get_width(),goalstate/cmaze.get_width(),'g');
		for(int i = 0;i < cmaze.get_width()* cmaze.get_height();i++)
		{
			if(cmaze.maze_index(i%cmaze.get_width(),i/cmaze.get_width()) != '%')
				Mdist[i] = cmaze.manhattan_distance(goalstate%cmaze.get_width(), goalstate / cmaze.get_width(), i % cmaze.get_width(), i / cmaze.get_width());
			else 
				Mdist[i] = -1;
		}
		int start_pos = cmaze.get_start_state();
		int current = start_pos;
		int next_pos = -1;
		//System.out.println("hello");
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
				last_dir = 'E';
				cmaze.find_dir(current,next_pos);
				if(last_dir != cmaze.get_dir())
					pathcost = pathcost + fm_cost + turn_cost;
				//check for go back(only possible at the beginning)
				if(current == start_pos && cmaze.get_dir() == 'W')
					pathcost += turn_cost;
				
				last_dir = cmaze.get_dir();
				current = next_pos;
				n_node += 1;
				//System.out.println(cmaze.maze_index(next_pos % cmaze.get_width(),next_pos / cmaze.get_height()));
				if(cmaze.maze_index(current%cmaze.get_width(),current/cmaze.get_width()) == '.')
				{
					if(current != goalstate)
						
					System.out.println();
					System.out.print("pathcost:");
					System.out.print(pathcost);
					System.out.println();
					System.out.print("number of nodes:");
					System.out.print(n_node);
					System.out.println();
					return -1;
				}
				cmaze.fill_in_maze(current%cmaze.get_width(),current/cmaze.get_width(),'.');
				
		}
<<<<<<< HEAD
		return -1;
=======
		
		return 0;
>>>>>>> origin/master
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
		st.push(start);
		while(!st.isEmpty())
		{
			//pop one "node" from stack
			current = (Integer) st.peek();
			st.pop();
			//after we got to goal position,print out the route and count pathcost
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
			//traverse the maze
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
	 * 		  turn_cost - how many does turn cost
	 * 		  forward - how many does go forward cost
	 * return: 1 - if success, 0 if fail
	 * solve a maze penalizeing turns with depth first search algorithm
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
				//after we got to goal position,count the path cost and draw the route
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
					//because the pacman head to right at the begin,
					//check if it has to turn before start moving(turn back is also covered because 
					//pacman has to turn one time first.
					if(lastdir != 'E'){
						pathcost += turn_cost;
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
				//traverse the maze
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
			//after we got to goal position,count the pathcost and draw the maze
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
			//traverse the maze 
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
	
/* input: cmaze - current maze, 
 * 		  startpos - an integer represent the startpos of the "player"
 * 		  turn_cost - how many does turn cost
 * 		  fm_cost - how many does go forward cost
 * return: 1 - if success, 0 if fail
 * solve a maze penalizing turns with breadth first search algorithm
 */	
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
			//after we got to goal position,count the pathcost and draw the path
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
				if(last_dir != 'E'){
					pathcost += turn_cost;
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
			//traverse the maze
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
