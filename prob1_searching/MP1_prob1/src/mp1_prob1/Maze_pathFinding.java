package mp1_prob1;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import javafx.geometry.Pos;

public class Maze_pathFinding {

	/*
	 * input: cmaze - current maze 
	 * return: 0 - if success, -1 if fail 
	 * solve a maze with greedy best first algorithm
	 */
	public int SolveMazeGBFS(Maze cmaze) {
		if (cmaze == null)
			return -1;		//if the maze doesn't exist, return 0
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		//define arrays necessary for traversing and drawing the result
		int[] parent = new int[mazeSize];
		int[] visited = new int[mazeSize];
		int[] Mdist = new int[mazeSize];
		//make a heuristic table
		int goalstate = cmaze.get_goal_state();
		int pathcost = 0;
		int n_node = 1;
		for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
			if (cmaze.maze_index(i % cmaze.get_width(), i / cmaze.get_width()) != '%')
				Mdist[i] = Math.abs(goalstate % cmaze.get_width() - i
						% cmaze.get_width())
						+ Math.abs(goalstate / cmaze.get_width() - i
								/ cmaze.get_width());
		}
		// set up the stack for traversing
		Stack st = new Stack();
		int current = cmaze.get_start_state();
		int next_pos = -1;
		int possible_next_pos;
		st.push(current);
		/* I used a stack here but the stack is only used when all four directions 
		 * of current node is wall or visited. The most important thing is to find the node
		 * with minimum heuristic value. 
		 */
		while (!st.isEmpty()) {
			//-1 means next_pos doesn't exist
			next_pos = -1;
			// after we got to goal position
			if (cmaze.maze_index(current % cmaze.get_width(),
					current / cmaze.get_width()) == '.') {
				current = parent[current];
				++pathcost;
				while (cmaze.maze_index(current % cmaze.get_width(), current
						/ cmaze.get_width()) != 'P') {
					++pathcost;
					cmaze.fill_in_maze(current % cmaze.get_width(), current
							/ cmaze.get_width(), '.');
					current = parent[current];
				}

				System.out.println();
				System.out.println("pathcost:" + pathcost);
				System.out.println("number of nodes:" + n_node);
				return 0;
			}

			else {
				//search all four directions to get the neighbor node with minimum
				//heuristic value.
				visited[current] = 1;
				if (current - 1 > 0) {
					possible_next_pos = current - 1;
					if (cmaze.maze_index(possible_next_pos % cmaze.get_width(),
							possible_next_pos / cmaze.get_width()) != '%'
							&& visited[possible_next_pos] != 1) {
						next_pos = possible_next_pos;
						++n_node;
					}
				}
				if (current - cmaze.get_width() > 0) {
					possible_next_pos = current - cmaze.get_width();
					if (cmaze.maze_index(possible_next_pos % cmaze.get_width(),
							possible_next_pos / cmaze.get_width()) != '%')
						next_pos = current - 1;
					if (cmaze.maze_index(possible_next_pos % cmaze.get_width(),
							possible_next_pos / cmaze.get_width()) != '%'
							&& visited[possible_next_pos] != 1) {
						if (next_pos == -1)
							next_pos = possible_next_pos;
						else if (Mdist[possible_next_pos] < Mdist[next_pos])
							next_pos = possible_next_pos;
						++n_node;
					}
				}
				if (current + 1 < mazeSize) {
					possible_next_pos = current + 1;
					if (cmaze.maze_index(possible_next_pos % cmaze.get_width(),
							possible_next_pos / cmaze.get_width()) != '%'
							&& visited[possible_next_pos] != 1) {
						if (next_pos == -1)
							next_pos = possible_next_pos;
						else if (Mdist[possible_next_pos] < Mdist[next_pos])
							next_pos = possible_next_pos;
						++n_node;
					}
				}
				if (current + cmaze.get_width() < mazeSize) {
					possible_next_pos = current + cmaze.get_width();
					if (cmaze.maze_index(possible_next_pos % cmaze.get_width(),
							possible_next_pos / cmaze.get_width()) != '%'
							&& visited[possible_next_pos] != 1) {
						if (next_pos == -1)
							next_pos = possible_next_pos;
						else if (Mdist[possible_next_pos] < Mdist[next_pos])
							next_pos = possible_next_pos;
						++n_node;
					}
				}
				//if we can't go to any neighbor node, go back one step
				if (next_pos == -1) {
					current = (Integer) st.peek();
					st.pop();
				}
				//go to the neighbor node with minimum heuristic value
				else {
					parent[next_pos] = current;
					st.push(current);
					current = next_pos;
				}
			}
		}
		return -1;
	}
	
	/*
	 * input: cmaze - current maze
	 * return 0 when success, -1 if fail
	 * solve a maze with A* search
	 */
	public int SolveMazeAStar(Maze cmaze) {
		
		if (cmaze == null) // if cmaze is null, fail
			return -1;

		int goal_state = cmaze.get_goal_state(); //end point in maze
		int start_state = cmaze.get_start_state(); //start point in maze
		int nextpos = -100;
		int pathcost = 0;
		char[] visited = new char[cmaze.get_height() * cmaze.get_width()];
		
		//data structure that holds all maze cells in maze
		Maze_cell[] cmaze_cell = new Maze_cell[cmaze.get_height()
				* cmaze.get_width()];
		
		//initialize cmaze_cell
		for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
			if (cmaze.maze_index(i % cmaze.get_width(), i / cmaze.get_height()) == '%')
				cmaze_cell[i] = new Maze_cell(i, -1);
			else
				cmaze_cell[i] = new Maze_cell(i, cmaze.manhattan_distance(i
						% cmaze.get_width(), i / cmaze.get_height(), goal_state
						% cmaze.get_width(), goal_state / cmaze.get_height()));
		}
		
		//set up priority queue for cmaze
		Comparator<Maze_cell> mc_comp = new Maze_cell();
		PriorityQueue<Maze_cell> mc_que = new PriorityQueue<Maze_cell>(
				cmaze.get_height() * cmaze.get_width(), mc_comp);
		
		//start to do cmaze expansion
		mc_que.add(cmaze_cell[start_state]);
		cmaze_cell[start_state].set_level(0);
		Maze_cell temp = null;
		
		//similar to BFS expansion but need to update the parent information when find a smaller total cost
		// for the same maze cell in queue
		while (mc_que.size() != 0) {

			temp = mc_que.poll(); //pop the smallest item in queue

			visited[temp.get_index()] = 'V'; //mark as visited
			
			//check the four neighbors
			//left point
			if (temp.get_index() - 1 > 0) {
				nextpos = temp.get_index() - 1;
				//Check the point is already in queue or not
				//If the point is already in queue, compare the total cost for 
				//the current path and the previous path
				//Select the smaller cost path and store the result in cmaze_cell
				if (visited[nextpos] != 'V'
						&& cmaze.maze_index(nextpos % cmaze.get_width(),
								nextpos / cmaze.get_width()) != '%') {
					
					//if exist, compare path cost and restore if necessary
					if (mc_que.contains(cmaze_cell[nextpos])) {
						int new_tc = cmaze_cell[nextpos].get_heuristic()
								+ temp.get_level() + 1;

						if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
							mc_que.remove(cmaze_cell[nextpos]);
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else { //if not exist, store information required
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						mc_que.add(cmaze_cell[nextpos]);
					}
				}
			}
			
			// the next 3 steps are the same except for neighbors from right, bottom and top
			//right neighbor
			if (temp.get_index() + 1 < cmaze.get_height() * cmaze.get_width()) {
				nextpos = temp.get_index() + 1;
				if (visited[nextpos] != 'V'
						&& cmaze.maze_index(nextpos % cmaze.get_width(),
								nextpos / cmaze.get_width()) != '%') {
					if (mc_que.contains(cmaze_cell[nextpos])) {
						int new_tc = cmaze_cell[nextpos].get_heuristic()
								+ temp.get_level() + 1;

						if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
							mc_que.remove(cmaze_cell[nextpos]);
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else {
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						mc_que.add(cmaze_cell[nextpos]);
					}
				}
			}
			//bottom neighbor
			if (temp.get_index() + cmaze.get_width() < cmaze.get_height()
					* cmaze.get_width()) {
				nextpos = temp.get_index() + cmaze.get_width();
				if (visited[nextpos] != 'V'
						&& cmaze.maze_index(nextpos % cmaze.get_width(),
								nextpos / cmaze.get_width()) != '%') {
					if (mc_que.contains(cmaze_cell[nextpos])) {
						int new_tc = cmaze_cell[nextpos].get_heuristic()
								+ temp.get_level() + 1;

						if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
							mc_que.remove(cmaze_cell[nextpos]);
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else {
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						mc_que.add(cmaze_cell[nextpos]);
					}
				}
			}
			//top neighbor
			if (temp.get_index() - cmaze.get_width() > 0) {
				nextpos = temp.get_index() - cmaze.get_width();
				if (visited[nextpos] != 'V'
						&& cmaze.maze_index(nextpos % cmaze.get_width(),
								nextpos / cmaze.get_width()) != '%') {
					if (mc_que.contains(cmaze_cell[nextpos])) {
						int new_tc = cmaze_cell[nextpos].get_heuristic()
								+ temp.get_level() + 1;
						if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
							mc_que.remove(cmaze_cell[nextpos]);
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else {
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						mc_que.add(cmaze_cell[nextpos]);
					}
				}
			}
		}
		
		//print out the path and calculate the total path cost
		temp = cmaze_cell[goal_state];
		int curr = temp.get_index();
		while (curr != start_state) {
			pathcost++;
			cmaze.fill_in_maze(curr % cmaze.get_width(),
					curr / cmaze.get_width(), '.');

			curr = temp.get_parent();
			temp = cmaze_cell[curr];
		}

		/*System.out.println("Path cost is: " + pathcost);

		for (int i = 0; i < cmaze.get_height() * cmaze.get_width(); i++)
			System.out.println(cmaze_cell[i].get_index() % cmaze.get_width()
					+ "," + cmaze_cell[i].get_index() / cmaze.get_width() + " "
					+ cmaze_cell[i].get_totalCost());*/
		return 0;
	}


	public int Ghost_SolveMazeAStar(Maze cmaze)
	{
		if (cmaze == null) // if cmaze is null, fail
			return -1;
		
/*///////////////////////////////////////
maze_cell_g cell1 = new maze_cell_g();
maze_cell_g cell2 = new maze_cell_g();
cell1.set_level(0);
cell1.set_totalCost(0);
cell1.set_ghost_pos(1);
cell1.set_ghost_dir(0);
cell1.set_parent(cell1);
cell2.set_level(0);
cell2.set_parent(cell1);
cell2.set_totalCost(0);
cell2.set_ghost_pos(0);
cell2.set_ghost_dir(0);
System.out.println("Test if equal:"+(cell1==cell2));
///////////////////////////////////////*/
		
		int goal_state = cmaze.get_goal_state(); //end point in maze
		int start_state = cmaze.get_start_state(); //start point in maze
		int ghost_start = cmaze.get_ghost_start();
		int width = cmaze.get_width(), height = cmaze.get_height(), totaln = width*height, ghostn=1, ghost_left=ghost_start;
		for(int tempi=ghost_start-1; cmaze.maze_index(tempi%width, tempi/width)=='g'; tempi--)
		{
			ghostn++;
			ghost_left = tempi;
		}
		for(int tempi=ghost_start+1; cmaze.maze_index(tempi%width, tempi/width)=='g'; tempi++)
			ghostn++;
		char [][][]visited = new char[totaln][ghostn][2];
	
		//data structure for all of the cells in the maze
		maze_cell_g [][][]cells = new maze_cell_g[totaln][ghostn][2];
	
		Comparator<maze_cell_g> mc_comp = new maze_cell_g();
		PriorityQueue<maze_cell_g> mc_que = 
							new PriorityQueue<maze_cell_g>(totaln*ghostn*2, mc_comp);
	
		//initialize maze cells
		for (int i = 0; i < totaln; i++)
			for(int j=0; j<ghostn; j++)
				for(int k=0; k<2; k++){
					if (cmaze.maze_index( i%width, i/width ) == '%')
						cells[i][j][k] = new maze_cell_g(i, j, k, -1);
					else
						cells[i][j][k] = new maze_cell_g(i, j, k, cmaze.manhattan_distance
								(i%width, i/width, goal_state%width, goal_state/width));
			}
	
		//initialize the starting point and add it into the queue
		cells[start_state][ghost_start-ghost_left][1].set_level(0);
		cells[start_state][ghost_start-ghost_left][1].set_parent(null);
		cells[start_state][ghost_start-ghost_left][1].set_totalCost(0);
		cells[start_state][ghost_start-ghost_left][1].set_ghost_pos(ghost_start);
		cells[start_state][ghost_start-ghost_left][1].set_ghost_dir(1);
		mc_que.add( cells[start_state][ghost_start-ghost_left][1] );
		
		//the next while loop is for searching the path.
		maze_cell_g curr = null;
		int nextpos = -100;
		int gnextpos = -100, gcurrpos = -100, gnextdir=-1;
		int pathcost = 0;
		while( mc_que.size() > 0 )
		{
			//get the current point need to be considered
			curr = mc_que.poll();
			visited[curr.get_index()][curr.get_ghost_pos()-ghost_left][curr.get_ghost_dir()] = 'V';
			gcurrpos = curr.get_ghost_pos();
			//calculate the ghost's next position.
			if( curr.get_ghost_dir() == 0 )
			{
				gnextpos = gcurrpos - 1;
				gnextdir = 0;
				if(cmaze.maze_index(gnextpos%width, gnextpos/width) == '%')
				{
					gnextpos = gcurrpos + 1;
					gnextdir = 1;
				}
			}
			else if( curr.get_ghost_dir() == 1 )
			{
				gnextpos = gcurrpos + 1;
				gnextdir = 1;
				if(cmaze.maze_index(gnextpos%width, gnextpos/width) == '%')
				{
					gnextpos = gcurrpos - 1;
					gnextdir = 0;
				}
			}
			else
			{
				//TODO: throw exception.
			}
			int dir_vector[] = {-1, +1, width, -width};
//System.out.println("ghost at("+gcurrpos%width+","+gcurrpos/width+"), next at("
//		+gnextpos%width+","+gnextpos/width+")");
//System.out.println("P curr at("+curr.get_index()%width+","+curr.get_index()/width+")");
			if(curr.get_index() == gcurrpos)
				System.out.println("Seems BIG ERROR in finding path");
			//check the four neighbors
			//left, right, bottom, top point============================================================
			for(int dir_num=0; dir_num<4; dir_num++)
			{
				nextpos = curr.get_index() + dir_vector[dir_num];
				if( nextpos>0 && visited[nextpos][gnextpos-ghost_left][gnextdir] != 'V' && cmaze.maze_index(nextpos%width, nextpos/width) != '%' )
				{
					//now without the ghost, this is a valid position. MAY NOT in the queue YET
					if( !( (nextpos == gcurrpos && curr.get_index() == gnextpos) || nextpos == gnextpos ) )
					{
						//now we know that this next position has no conflict with the ghost.
						int new_totalcost= cells[nextpos][gnextpos-ghost_left][gnextdir]. get_heuristic() + curr.get_level() + 1;
						if( mc_que.contains( cells[nextpos][gnextpos-ghost_left][gnextdir] ) && new_totalcost < cells[nextpos][gnextpos-ghost_left][gnextdir].get_totalCost() )
						{
							//in this situation the queue has the next position, we need to check whether
							//there is a shorter path to it. If there is, delete the previous one in the queue.
							mc_que.remove( cells[nextpos][gnextpos-ghost_left][gnextdir] );
						}
						if( !mc_que.contains( cells[nextpos][gnextpos-ghost_left][gnextdir]) )
						{
							cells[nextpos][gnextpos-ghost_left][gnextdir]. set_parent(curr);
							cells[nextpos][gnextpos-ghost_left][gnextdir]. set_level(curr.get_level() + 1);
							cells[nextpos][gnextpos-ghost_left][gnextdir]. set_totalCost(new_totalcost);
							cells[nextpos][gnextpos-ghost_left][gnextdir]. set_ghost_dir(gnextdir);
							cells[nextpos][gnextpos-ghost_left][gnextdir]. set_ghost_pos(gnextpos);
							mc_que.add(cells[nextpos][gnextpos-ghost_left][gnextdir]);
//System.out.println("P has next at("+nextpos%width+","+nextpos/width+")");
//if(nextpos%width == 1 && nextpos/width ==1 )
//{
//	System.out.println("special P parent:"+cells[nextpos][gnextpos-ghost_left][gnextdir]. get_parent().get_index());
//	System.out.println("special P gnextpos:"+gnextpos+"g next dir:"+gnextdir);
//	System.out.println("special P curr at("+curr.get_index()%width+","+curr.get_index()/width+")");
//}
						}
					}
				}
			}
		}
		System.out.println("finished searching");
		
		curr = cells[goal_state][0][0];
		if(curr.get_totalCost()==-1) curr.set_totalCost(9999999);
		for(int i=0; i<ghostn; i++)
			for(int j=0; j<2; j++)
			{
				if(cells[goal_state][i][j].get_totalCost() != -1 &&
					cells[goal_state][i][j].get_parent() != null &&
					curr.get_totalCost()>cells[goal_state][i][j].get_totalCost())
				{
//System.out.println("dest parent at:"+cells[goal_state][i][j].get_parent().get_index()%width+","+cells[goal_state][i][j].get_parent().get_index()/width);
					curr = cells[goal_state][i][j];
				}
			}
		
		while( curr != null )
		{
			System.out.println("point at:"+curr.get_index()%width+","+curr.get_index()/width);
			pathcost++;
			cmaze.fill_in_maze(curr.get_index() % width, curr.get_index() / width, '.');
			curr = curr.get_parent();
			//System.out.println("next point at:"+curr.get_index());
		}
		System.out.println("total cost="+pathcost);
		return 0;
	}

/*	
public int multidots_SolveMazeAStar(Maze cmaze) {
		if (cmaze == null) // if cmaze is null, fail
			return -1;
		//cur_pos is used to find all the goals
		int cur_pos = 0;
		int goal_found;
		int start_state = cmaze.get_start_state(); //start point in maze
		//initialize the priority queue fo goals
		Comparator<multidot_goal_cell> gc_comp = new multidot_goal_cell();
		PriorityQueue<multidot_goal_cell> gc_que = new PriorityQueue<multidot_goal_cell>(gc_comp);
		//find the goal that has the smallest heuristic vaule, at the same time count for number fo goals.
		int n_goals = -1;
		for(int i = 0; (goal_found = cmaze.find_goals(cur_pos)) != -1; i++){
			int cur_Mdist = cmaze.manhattan_distance(start_state%cmaze.get_width(), start_state/cmaze.get_width(), goal_found%cmaze.get_width(),goal_found/cmaze.get_width());
			multidot_goal_cell goal_cell = new multidot_goal_cell(goal_found,cur_Mdist);
			gc_que.add(goal_cell);
			n_goals = i;
			cur_pos = goal_found;
		}
		goal_found ++;
		//set the goal with minimum heuristic value as the chosen goal, it's index is the goal_state
		multidot_goal_cell chosengoal = gc_que.poll();
		//System.out.println("chosen" + chosengoal.get_pos());
		int goal_state = chosengoal.get_pos();
		
		int nextpos = -100;
		int pathcost = 0;
		char[] visited = new char[cmaze.get_height() * cmaze.get_width()];
		
		//data structure that holds all maze cells in maze
		Maze_cell[] cmaze_cell = new Maze_cell[cmaze.get_height()
				* cmaze.get_width()];
		
		//initialize cmaze_cell
		for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
			if (cmaze.maze_index(i % cmaze.get_width(), i / cmaze.get_width()) == '%')
				cmaze_cell[i] = new Maze_cell(i, -1);
			else
				cmaze_cell[i] = new Maze_cell(i, cmaze.manhattan_distance(i
						% cmaze.get_width(), i / cmaze.get_width(), goal_state
						% cmaze.get_width(), goal_state / cmaze.get_width()));
		}
		
		//set up priority queue for cmaze
		Comparator<Maze_cell> mc_comp = new Maze_cell();
		PriorityQueue<Maze_cell> mc_que = new PriorityQueue<Maze_cell>(
				cmaze.get_height() * cmaze.get_width(), mc_comp);
		
		//start to do cmaze expansion
		mc_que.add(cmaze_cell[start_state]);
		cmaze_cell[start_state].set_level(0);
		Maze_cell temp = null;
		int n_node = 1;
		int n_goal_found = 1;
		//similar to BFS expansion but need to update the parent information when find a smaller total cost
		// for the same maze cell in queue
		while(n_goals != n_goal_found)
		{
			while (mc_que.size() != 0) {

				temp = mc_que.poll(); //pop the smallest item in queue

				visited[temp.get_index()] = 'V'; //mark as visited
			
				//check the four neighbors
				//left point
				if (temp.get_index() - 1 > 0) {
					nextpos = temp.get_index() - 1;
					//Check the point is already in queue or not
					//If the point is already in queue, compare the total cost for 
					//the current path and the previous path
					//Select the smaller cost path and store the result in cmaze_cell
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
					
						//if exist, compare path cost and restore if necessary
						if (mc_que.contains(cmaze_cell[nextpos])) {
							int new_tc = cmaze_cell[nextpos].get_heuristic()
									+ temp.get_level() + 1;
	
							if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
								mc_que.remove(cmaze_cell[nextpos]);
								cmaze_cell[nextpos].set_parent(temp.get_index());
								cmaze_cell[nextpos].set_level(temp.get_level() + 1);
								cmaze_cell[nextpos]
										.set_totalCost(temp.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else { //if not exist, store information required
							n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}
				
				// the next 3 steps are the same except for neighbors from right, bottom and top
				//right neighbor
				if (temp.get_index() + 1 < cmaze.get_height() * cmaze.get_width()) {
					nextpos = temp.get_index() + 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						if (mc_que.contains(cmaze_cell[nextpos])) {
							int new_tc = cmaze_cell[nextpos].get_heuristic()
									+ temp.get_level() + 1;
	
							if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
								mc_que.remove(cmaze_cell[nextpos]);
								cmaze_cell[nextpos].set_parent(temp.get_index());
								cmaze_cell[nextpos].set_level(temp.get_level() + 1);
								cmaze_cell[nextpos]
										.set_totalCost(temp.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else {
							// n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}
				//bottom neighbor
				if (temp.get_index() + cmaze.get_width() < cmaze.get_height()
						* cmaze.get_width()) {
					nextpos = temp.get_index() + cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						if (mc_que.contains(cmaze_cell[nextpos])) {
							int new_tc = cmaze_cell[nextpos].get_heuristic()
									+ temp.get_level() + 1;
	
							if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
								mc_que.remove(cmaze_cell[nextpos]);
								cmaze_cell[nextpos].set_parent(temp.get_index());
								cmaze_cell[nextpos].set_level(temp.get_level() + 1);
								cmaze_cell[nextpos]
										.set_totalCost(temp.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else {
							// n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}
				//top neighbor
				if (temp.get_index() - cmaze.get_width() > 0) {
					nextpos = temp.get_index() - cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						if (mc_que.contains(cmaze_cell[nextpos])) {
							int new_tc = cmaze_cell[nextpos].get_heuristic()
									+ temp.get_level() + 1;
							if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
								mc_que.remove(cmaze_cell[nextpos]);
								cmaze_cell[nextpos].set_parent(temp.get_index());
								cmaze_cell[nextpos].set_level(temp.get_level() + 1);
								cmaze_cell[nextpos]
										.set_totalCost(temp.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else {
							// n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}
			}	
			temp = cmaze_cell[goal_state];
			//get the index of the goal and mark it
			int curr = temp.get_index();
			char char_on_goal = (char)(n_goal_found + 48);
			cmaze.fill_in_maze(curr % cmaze.get_width(),
					curr / cmaze.get_width(), char_on_goal);
			int perserve_curr = curr;
			//count the path length
			while (curr != start_state) {
				pathcost++;
				curr = temp.get_parent();
				temp = cmaze_cell[curr];
			}
			//now the pacman start from this position to get to one of the other remaining goals
			start_state = perserve_curr;
			//System.out.println("n_goals" + n_goals);
			//System.out.println("goals_found" + n_goal_found);
			//show that one more goal is found
			n_goal_found ++;
			gc_que.clear();
			//find all the goals
			cur_pos = 0;
			//find the nearest goal again
			for(int i = 0; (goal_found = cmaze.find_goals(cur_pos)) != -1; i++){
				int cur_Mdist = cmaze.manhattan_distance(
				goal_found % cmaze.get_width(), goal_found/cmaze.get_width(), start_state%cmaze.get_width(),start_state/cmaze.get_width());
				multidot_goal_cell goal_cell = new multidot_goal_cell(goal_found,cur_Mdist);
				gc_que.add(goal_cell);
				cur_pos = goal_found;
			}
			chosengoal = gc_que.poll();
			//System.out.println("chsen goal" + chosengoal.get_pos());
			goal_state = chosengoal.get_pos();
			nextpos = -100;
			//clean the visited array,because we may need to go to a cell visited before again
			for(int i = 0; i < cmaze.get_width()*cmaze.get_height();i++)
			{
				visited[i]  = 0;
			}
			//clean the previous table first.
			for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
				cmaze_cell[i] = null;
			}
			//based on the new goal and start, make a new heuristic table.
			for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
				if (cmaze.maze_index(i % cmaze.get_width(), i / cmaze.get_width()) == '%')
					cmaze_cell[i] = new Maze_cell(i, -1);
				else
					cmaze_cell[i] = new Maze_cell(i, cmaze.manhattan_distance(i
						% cmaze.get_width(), i / cmaze.get_width(), goal_state
						% cmaze.get_width(), goal_state / cmaze.get_width()));
			}
			mc_que.add(cmaze_cell[start_state]);
		}
		
		System.out.println("pathcost" + pathcost);
return 0;
}*/

	/*
	 * input: cmaze - currentmaze turn_cost - how many does fm_cost - how many
	 * does go forward cost return: 0 - if success, -1 if fail solve a maze
	 * penalizeing turns with greedy best first algorithm
	 */
	public int penalize_SolveMazeGBFS(Maze cmaze, int turn_cost, int fm_cost) {
		Stack st = new Stack();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int[] parent = new int[mazeSize];
		int[] visited = new int[mazeSize];
		int[] Mdist = new int[mazeSize];
		int goalstate = cmaze.get_goal_state();
		int pathcost = 0;
		int n_node = 1;
		char last_dir;
		for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
			if (cmaze.maze_index(i % cmaze.get_width(), i / cmaze.get_width()) != '%')
				Mdist[i] = Math.abs(goalstate % cmaze.get_width() - i
						% cmaze.get_width())
						+ Math.abs(goalstate / cmaze.get_width() - i
								/ cmaze.get_width());
		}
		int current = cmaze.get_start_state();
		int next_pos = -1;
		int possible_next_pos;
		st.push(current);
		while (!st.isEmpty()) {
			next_pos = -1;
			// after we got to goal position
			if (cmaze.maze_index(current % cmaze.get_width(),
					current / cmaze.get_width()) == '.') {
				last_dir = cmaze.get_dir();
				current = parent[current];
				pathcost += fm_cost;
				while (cmaze.maze_index(current % cmaze.get_width(), current
						/ cmaze.get_width()) != 'P') {
					cmaze.find_dir(parent[current], current);
					if (last_dir != cmaze.get_dir())
						pathcost = pathcost + fm_cost + turn_cost;
					else
						pathcost += fm_cost;
					last_dir = cmaze.get_dir();
					cmaze.fill_in_maze(current % cmaze.get_width(), current
							/ cmaze.get_width(), '.');
					current = parent[current];
				}
				if (last_dir != 'E')
					pathcost += turn_cost;
				System.out.println();
				System.out.println("pathcost:" + pathcost);
				System.out.println("number of nodes:" + n_node);
				return 0;
			} else {
				visited[current] = 1;
				if (current - 1 > 0) {
					possible_next_pos = current - 1;
					if (cmaze.maze_index(possible_next_pos % cmaze.get_width(),
							possible_next_pos / cmaze.get_width()) != '%'
							&& visited[possible_next_pos] != 1) {
						next_pos = possible_next_pos;
						++n_node;
					}
				}
				if (current - cmaze.get_width() > 0) {
					possible_next_pos = current - cmaze.get_width();
					if (cmaze.maze_index(possible_next_pos % cmaze.get_width(),
							possible_next_pos / cmaze.get_width()) != '%'
							&& visited[possible_next_pos] != 1) {
						if (next_pos == -1)
							next_pos = possible_next_pos;
						else if (Mdist[possible_next_pos] < Mdist[next_pos])
							next_pos = possible_next_pos;
						++n_node;
					}
				}
				if (current + 1 < mazeSize) {
					possible_next_pos = current + 1;
					if (cmaze.maze_index(possible_next_pos % cmaze.get_width(),
							possible_next_pos / cmaze.get_width()) != '%'
							&& visited[possible_next_pos] != 1) {
						if (next_pos == -1)
							next_pos = possible_next_pos;
						else if (Mdist[possible_next_pos] < Mdist[next_pos])
							next_pos = possible_next_pos;
						++n_node;
					}
				}
				if (current + cmaze.get_width() < mazeSize) {
					possible_next_pos = current + cmaze.get_width();
					if (cmaze.maze_index(possible_next_pos % cmaze.get_width(),
							possible_next_pos / cmaze.get_width()) != '%'
							&& visited[possible_next_pos] != 1) {
						if (next_pos == -1)
							next_pos = possible_next_pos;
						else if (Mdist[possible_next_pos] < Mdist[next_pos])
							next_pos = possible_next_pos;
						++n_node;
					}
				}

				if (next_pos == -1) {
					current = (Integer) st.peek();
					st.pop();
				}

				else {
					parent[next_pos] = current;
					st.push(current);
					current = next_pos;
				}
			}
		}
		return -1;
	}

	/*
	 * input: cmaze - current maze, startpos - an integer represent the
	 * startposof the "player" return: 0 - if success, -1 if fail solve the maze
	 * with depth first search algorithm
	 */
	public int SolveMazeDFS(Maze cmaze, int startpos) {
		Stack st = new Stack();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int[] parent = new int[mazeSize];
		char[] visited = new char[mazeSize];
		int start = startpos;
		int current;
		int nextpos;
		int n_node = 1;
		int pathcost = 0;
		char lastdir;
		st.push(start);
		while (!st.isEmpty()) {
			// pop one "node" from stack
			current = (Integer) st.peek();
			st.pop();
			// after we got to goal position,print out the route and count
			// pathcost
			if (cmaze.maze_index(current % cmaze.get_width(),
					current / cmaze.get_width()) == '.') {
				current = parent[current];
				++pathcost;
				while (cmaze.maze_index(current % cmaze.get_width(), current
						/ cmaze.get_width()) != 'P') {
					++pathcost;
					cmaze.fill_in_maze(current % cmaze.get_width(), current
							/ cmaze.get_width(), '.');
					current = parent[current];
				}
				System.out.println();
				System.out.print("pathcost:");
				System.out.print(pathcost);
				System.out.println();
				System.out.print("number of nodes:");
				System.out.print(n_node);
				System.out.println();
				return 0;
			}
			// traverse the maze
			else {
				visited[current] = 'V';
				if (current - 1 > 0) {
					nextpos = current - 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {

						st.push(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}

				if (current + 1 < mazeSize) {
					nextpos = current + 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						st.push(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
				if (current + cmaze.get_width() < mazeSize) {
					nextpos = current + cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						st.push(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
				if (current - cmaze.get_width() > 0) {
					nextpos = current - cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						st.push(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
			}
		}
		return -1;
	}

	/*
	 * input: cmaze - current maze, startpos - an integer represent the startpos
	 * of the "player" turn_cost - how many does fm_cost - how many does go
	 * forward cost return: 0 - if success, -1 if fail solve a maze penalizeing
	 * turns with depth first search algorithm
	 */
	public int penalize_SolveMazeDFS(Maze cmaze, int startpos, int turn_cost,
			int fm_cost) {
		Stack st = new Stack();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int[] parent = new int[mazeSize];
		char[] visited = new char[mazeSize];
		int start = startpos;
		int current;
		int nextpos;
		int n_node = 1;
		int pathcost = 0;
		char lastdir;
		// System.out.print(start);
		st.push(start);
		while (!st.isEmpty()) {
			current = (Integer) st.peek();
			st.pop();
			// after we got to goal position,count the path cost and draw the
			// route
			if (cmaze.maze_index(current % cmaze.get_width(),
					current / cmaze.get_width()) == '.') {
				cmaze.find_dir(parent[current], current);
				lastdir = cmaze.get_dir();
				current = parent[current];
				pathcost += fm_cost;
				while (cmaze.maze_index(current % cmaze.get_width(), current
						/ cmaze.get_width()) != 'P') {
					cmaze.find_dir(parent[current], current);
					if (lastdir != cmaze.get_dir())
						pathcost = pathcost + fm_cost + turn_cost;
					else
						pathcost += fm_cost;
					lastdir = cmaze.get_dir();
					cmaze.fill_in_maze(current % cmaze.get_width(), current
							/ cmaze.get_width(), '.');
					current = parent[current];
				}
				// because the pacman head to right at the begin,
				// check if it has to turn before start moving(turn back is also
				// covered because
				// pacman has to turn one time first.
				if (lastdir != 'E') {
					pathcost += turn_cost;
				}
				System.out.println();
				System.out.print("pathcost:");
				System.out.print(pathcost);
				System.out.println();
				System.out.print("number of nodes:");
				System.out.print(n_node);
				System.out.println();
				return 0;
			}
			// traverse the maze
			else {
				visited[current] = 'V';
				if (current - 1 > 0) {
					nextpos = current - 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {

						st.push(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}

				if (current + 1 < mazeSize) {
					nextpos = current + 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						st.push(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
				if (current + cmaze.get_width() < mazeSize) {
					nextpos = current + cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						st.push(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
				if (current - cmaze.get_width() > 0) {
					nextpos = current - cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						st.push(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
			}
		}
		return -1;
	}

	/*
	 * input: cmaze - current maze, startpos - an integer represent the
	 * startposof the "player" return: 0 - if success, -1 if fail solve the maze
	 * with breadth first search algorithm
	 */
	public int SolveMazeBFS(Maze cmaze, int startpos) {
		Queue<Integer> queue = new LinkedList<Integer>();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int[] parent = new int[mazeSize];
		char[] visited = new char[mazeSize];
		int start = startpos;
		int current;
		int nextpos;
		int pathcost = 0;
		// System.out.print(start);
		queue.add(start);
		int n_node = 0;
		while (!queue.isEmpty()) {
			current = queue.remove();
			// after we got to goal position,count the pathcost and draw the
			// maze
			if (cmaze.maze_index(current % cmaze.get_width(),
					current / cmaze.get_width()) == '.') {
				current = parent[current];
				pathcost++;
				while (cmaze.maze_index(current % cmaze.get_width(), current
						/ cmaze.get_width()) != 'P') {
					pathcost++;
					cmaze.fill_in_maze(current % cmaze.get_width(), current
							/ cmaze.get_width(), '.');
					current = parent[current];
				}
				System.out.println();
				System.out.print("pathcost:");
				System.out.print(pathcost);
				System.out.println();
				System.out.print("number of nodes:");
				System.out.print(n_node);
				System.out.println();
				return 0;
			}
			// traverse the maze
			else {
				visited[current] = 'V';
				if (current - 1 > 0) {
					nextpos = current - 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						queue.add(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}

				if (current + 1 < mazeSize) {
					nextpos = current + 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						queue.add(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
				if (current + cmaze.get_width() < mazeSize) {
					nextpos = current + cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						queue.add(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
				if (current - cmaze.get_width() > 0) {
					nextpos = current - cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						queue.add(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
			}
		}
		return -1;
	}

	/*
	 * input: cmaze - current maze, startpos - an integer represent the startpos
	 * of the "player" turn_cost - how many does turn cost fm_cost - how many
	 * does go forward cost return: 0 - if success, -1 if fail solve a maze
	 * penalizing turns with breadth first search algorithm
	 */
	public int penalize_SolveMazeBFS(Maze cmaze, int startpos, int turn_cost,
			int fm_cost) {
		Queue<Integer> queue = new LinkedList<Integer>();
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		int[] parent = new int[mazeSize];
		char[] visited = new char[mazeSize];
		int start = startpos;
		int current;
		int nextpos;
		int pathcost = 0;
		// System.out.print(start);
		queue.add(start);
		int n_node = 0;
		char last_dir;
		while (!queue.isEmpty()) {
			current = queue.remove();
			// after we got to goal position,count the pathcost and draw the
			// path
			if (cmaze.maze_index(current % cmaze.get_width(),
					current / cmaze.get_width()) == '.') {
				cmaze.find_dir(parent[current], current);
				last_dir = cmaze.get_dir();
				current = parent[current];
				pathcost += fm_cost;
				while (cmaze.maze_index(current % cmaze.get_width(), current
						/ cmaze.get_width()) != 'P') {
					cmaze.find_dir(parent[current], current);
					if (last_dir != cmaze.get_dir())
						pathcost = pathcost + fm_cost + turn_cost;
					else
						pathcost += fm_cost;
					last_dir = cmaze.get_dir();
					cmaze.fill_in_maze(current % cmaze.get_width(), current
							/ cmaze.get_width(), '.');
					current = parent[current];
				}
				if (last_dir != 'E') {
					pathcost += turn_cost;
				}
				System.out.println();
				System.out.print("pathcost:");
				System.out.print(pathcost);
				System.out.println();
				System.out.print("number of nodes:");
				System.out.print(n_node);
				System.out.println();
				return 0;
			}
			// traverse the maze
			else {
				visited[current] = 'V';
				if (current - 1 > 0) {
					nextpos = current - 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						queue.add(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}

				if (current + 1 < mazeSize) {
					nextpos = current + 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						queue.add(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
				if (current + cmaze.get_width() < mazeSize) {
					nextpos = current + cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						queue.add(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
				if (current - cmaze.get_width() > 0) {
					nextpos = current - cmaze.get_width();
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						queue.add(nextpos);
						n_node++;
						parent[nextpos] = current;
					}
				}
			}
		}
		return -1;
	}

}
