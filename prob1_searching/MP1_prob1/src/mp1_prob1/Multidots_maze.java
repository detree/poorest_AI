package mp1_prob1;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Multidots_maze {
/*------------------------------------------------------ prob2 part1 -------------------------------------------------------*/
	public int multidots_normal_SolveMazeAStar(Maze cmaze) {
		if (cmaze == null) // if cmaze is null, fail
			return -1;
		// cur_pos is used to find all the goals
		int cur_pos = 0;
		int goal_found;
		int start_state = cmaze.get_start_state(); // start point in maze
		// initialize the priority queue for goals
		Comparator<multidot_goal_cell> gc_comp = new multidot_goal_cell();
		PriorityQueue<multidot_goal_cell> gc_que = new PriorityQueue<multidot_goal_cell>(
				gc_comp);
		// find the goal that has the smallest heuristic value, at the same time
		// count for number of goals.
		int n_goals = -1;
		//System.out.println("mazesize" + cmaze.get_width() * cmaze.get_height());
		for (int i = 0; (goal_found = cmaze.find_goals(cur_pos)) != -1; i++) {
			int cur_Mdist = cmaze.manhattan_distance(
					start_state % cmaze.get_width(),
					start_state / cmaze.get_width(),
					goal_found % cmaze.get_width(),
					goal_found / cmaze.get_width());
			
			multidot_goal_cell goal_cell = new multidot_goal_cell(goal_found,
					cur_Mdist);
			gc_que.add(goal_cell);
			n_goals = i;
			cur_pos = goal_found;
		}
		n_goals++;
		// set the goal with minimum heuristic value as the chosen goal, it's
		// index is the goal_state
		multidot_goal_cell chosengoal = gc_que.poll();
		//System.out.println("firstgoal" + chosengoal.get_pos());
		// System.out.println("chosen" + chosengoal.get_pos());
		int goal_state = chosengoal.get_pos();

		int nextpos = -100;
		int pathcost = 0;
		char[] visited = new char[cmaze.get_height() * cmaze.get_width()];

		// data structure that holds all maze cells in maze
		Maze_cell[] cmaze_cell = new Maze_cell[cmaze.get_height()
				* cmaze.get_width()];

		// initialize cmaze_cell
		for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
			if (cmaze.maze_index(i % cmaze.get_width(), i / cmaze.get_width()) == '%')
				cmaze_cell[i] = new Maze_cell(i, -1);
			else
				cmaze_cell[i] = new Maze_cell(i, cmaze.manhattan_distance(i
						% cmaze.get_width(), i / cmaze.get_width(), goal_state
						% cmaze.get_width(), goal_state / cmaze.get_width()));
		}

		// set up priority queue for cmaze
		Comparator<Maze_cell> mc_comp = new Maze_cell();
		PriorityQueue<Maze_cell> mc_que = new PriorityQueue<Maze_cell>(
				cmaze.get_height() * cmaze.get_width(), mc_comp);

		// start to do cmaze expansion
		mc_que.add(cmaze_cell[start_state]);
		cmaze_cell[start_state].set_level(0);
		Maze_cell temp = null;
		int n_node = 0;
		int n_goal_found = 0;	
		// similar to BFS expansion but need to update the parent information
		// when find a smaller total cost
		// for the same maze cell in queue
		while (n_goals != n_goal_found) {
			while (mc_que.size() != 0) {

				temp = mc_que.poll(); // pop the smallest item in queue
				//System.out.println(temp.get_index());
				n_node++;
				visited[temp.get_index()] = 'V';
				if(temp.get_index() == goal_state)
					break;
				// check the four neighbors
				// left point
				if (temp.get_index() - 1 > 0) {
					nextpos = temp.get_index() - 1;
					// Check the point is already in queue or not
					// If the point is already in queue, compare the total cost
					// for
					// the current path and the previous path
					// Select the smaller cost path and store the result in
					// cmaze_cell
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {

						// if exist, compare path cost and restore if necessary
						if (mc_que.contains(cmaze_cell[nextpos])) {
							int new_tc = cmaze_cell[nextpos].get_heuristic()
									+ temp.get_level() + 1;

							if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
								mc_que.remove(cmaze_cell[nextpos]);
								cmaze_cell[nextpos]
										.set_parent(temp.get_index());
								cmaze_cell[nextpos]
										.set_level(temp.get_level() + 1);
								cmaze_cell[nextpos].set_totalCost(temp
										.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else { // if not exist, store information required
							n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}

				// the next 3 steps are the same except for neighbors from
				// right, bottom and top
				// right neighbor
				if (temp.get_index() + 1 < cmaze.get_height()
						* cmaze.get_width()) {
					nextpos = temp.get_index() + 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						if (mc_que.contains(cmaze_cell[nextpos])) {
							int new_tc = cmaze_cell[nextpos].get_heuristic()
									+ temp.get_level() + 1;
							
							if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
								mc_que.remove(cmaze_cell[nextpos]);
								cmaze_cell[nextpos]
										.set_parent(temp.get_index());
								cmaze_cell[nextpos]
										.set_level(temp.get_level() + 1);
								cmaze_cell[nextpos].set_totalCost(temp
										.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else {
							// n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}
				// bottom neighbor
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
								cmaze_cell[nextpos]
										.set_parent(temp.get_index());
								cmaze_cell[nextpos]
										.set_level(temp.get_level() + 1);
								cmaze_cell[nextpos].set_totalCost(temp
										.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else {
							// n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}
				// top neighbor
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
								cmaze_cell[nextpos]
										.set_parent(temp.get_index());
								cmaze_cell[nextpos]
										.set_level(temp.get_level() + 1);
								cmaze_cell[nextpos].set_totalCost(temp
										.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else {
							// n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}

			}
			temp = cmaze_cell[goal_state];
			// get the index of the goal and mark it
			int curr = temp.get_index();
			char char_on_goal;
			
			int perserve_curr = curr;
			
			while (curr != start_state) {
				pathcost++;
				if(cmaze.maze_index(curr%cmaze.get_width(), curr/cmaze.get_width()) == '.')
				{
					if(n_goal_found < 10)
						char_on_goal = (char) (n_goal_found + 48);
					else if(n_goal_found < 34)
						char_on_goal  =  (char)(n_goal_found + 55);
					else 
						char_on_goal = (char)(n_goal_found + 63);
					cmaze.fill_in_maze(curr % cmaze.get_width(),
							curr / cmaze.get_width(), char_on_goal);
					n_goal_found ++; 
					//System.out.println("cur" + curr);
				}
				else if(cmaze.maze_index(curr%cmaze.get_width(), curr/cmaze.get_width()) == ' ')
					cmaze.fill_in_maze(curr % cmaze.get_width(),
							curr / cmaze.get_width(),'*');
				//System.out.println("cur" + curr);
				curr = temp.get_parent();
				temp = cmaze_cell[curr];
			}

			// now the pacman start from this position to get to one of the
			// other remaining goals
			start_state = perserve_curr;
			if(n_goals == n_goal_found){
				System.out.println("pathcost:" + pathcost);
				System.out.println("number of nodes:" + n_node);
				return 0;
			}
			
			gc_que.clear();
			// find all the goals
			cur_pos = 0;
			

			for (int i = 0; (goal_found = cmaze.find_goals(cur_pos)) != -1; i++) {
				int cur_Mdist = cmaze.manhattan_distance(
						goal_found % cmaze.get_width(),
						goal_found / cmaze.get_width(),
						start_state % cmaze.get_width(),
						start_state / cmaze.get_width());
				multidot_goal_cell goal_cell = new multidot_goal_cell(
						goal_found, cur_Mdist);
			
				gc_que.add(goal_cell);
				cur_pos = goal_found;
			}
			chosengoal = gc_que.poll();
				// System.out.println("chosen goal" + chosengoal.get_pos());
			goal_state = chosengoal.get_pos();
			//System.out.println("goal" + goal_state);
			nextpos = -100;
			// clean the visited array,because we may need to go to a cell
			// visited before again
			for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
				visited[i] = 0;
			}
			// clean the previous table first.
			for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
				cmaze_cell[i] = null;
			}
			// based on the new goal and start, make a new heuristic table.
			for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
				if (cmaze.maze_index(i % cmaze.get_width(),
						i / cmaze.get_width()) == '%')
					cmaze_cell[i] = new Maze_cell(i, -1);
				else
					cmaze_cell[i] = new Maze_cell(i, cmaze.manhattan_distance(i
							% cmaze.get_width(), i / cmaze.get_width(),
							goal_state % cmaze.get_width(),
							goal_state / cmaze.get_width()));

			}
			mc_que.clear();
			mc_que.add(cmaze_cell[start_state]);
			//System.out.println(start_state);
			//System.out.println("gotbottom");
		}
		
		System.out.println("pathcost" + pathcost);
		return 0;
	}
	/*------------------------------------------------------ prob2 part2 -------------------------------------------------------*/
	public int multidots_SolveMazeAStar(Maze cmaze) {
		if (cmaze == null) // if cmaze is null, fail
			return -1;
		// cur_pos is used to find all the goals
		int cur_pos = 0;
		int goal_found;
		int start_state = cmaze.get_start_state(); // start point in maze
		// initialize the priority queue fo goals
		Comparator<multidot_goal_cell> gc_comp = new multidot_goal_cell();
		PriorityQueue<multidot_goal_cell> gc_que = new PriorityQueue<multidot_goal_cell>(
				gc_comp);
		// find the goal that has the smallest heuristic vaule, at the same time
		// count for number of goals.
		int n_goals = -1;
		//System.out.println("mazesize" + cmaze.get_width() * cmaze.get_height());
		for (int i = 0; (goal_found = cmaze.find_goals(cur_pos)) != -1; i++) {
			int cur_Mdist = cmaze.manhattan_distance(
					start_state % cmaze.get_width(),
					start_state / cmaze.get_width(),
					goal_found % cmaze.get_width(),
					goal_found / cmaze.get_width());
			
			multidot_goal_cell goal_cell = new multidot_goal_cell(goal_found,
					cur_Mdist);
			
			int n_wall = goal_cell.set_n_wall(cmaze);
			//System.out.println("index" + goal_found + "n_wall" + n_wall);
			if(n_wall == 2)
				goal_cell.set_heurist(cur_Mdist * 1000);
			
			else if(n_wall== 1)
				goal_cell.set_heurist(cur_Mdist * 1000);
			
			if(n_wall== 0)
				goal_cell.set_heurist(cur_Mdist * 1000);
			
			gc_que.add(goal_cell);
			n_goals = i;
			cur_pos = goal_found;
		}
		n_goals++;
		// set the goal with minimum heuristic value as the chosen goal, it's
		// index is the goal_state
		multidot_goal_cell chosengoal = gc_que.poll();
		//System.out.println("firstgoal" + chosengoal.get_pos());
		// System.out.println("chosen" + chosengoal.get_pos());
		int goal_state = chosengoal.get_pos();

		int nextpos = -100;
		int pathcost = 0;
		char[] visited = new char[cmaze.get_height() * cmaze.get_width()];

		// data structure that holds all maze cells in maze
		Maze_cell[] cmaze_cell = new Maze_cell[cmaze.get_height()
				* cmaze.get_width()];

		// initialize cmaze_cell
		for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
			if (cmaze.maze_index(i % cmaze.get_width(), i / cmaze.get_width()) == '%')
				cmaze_cell[i] = new Maze_cell(i, -1);
			else
				cmaze_cell[i] = new Maze_cell(i, cmaze.manhattan_distance(i
						% cmaze.get_width(), i / cmaze.get_width(), goal_state
						% cmaze.get_width(), goal_state / cmaze.get_width()));
		}

		// set up priority queue for cmaze
		Comparator<Maze_cell> mc_comp = new Maze_cell();
		PriorityQueue<Maze_cell> mc_que = new PriorityQueue<Maze_cell>(
				cmaze.get_height() * cmaze.get_width(), mc_comp);

		// start to do cmaze expansion
		mc_que.add(cmaze_cell[start_state]);
		cmaze_cell[start_state].set_level(0);
		Maze_cell temp = null;
		int n_node = 0;
		int n_goal_found = 0;	
		int rotate_flag = 0;
		int retval = 0;
		int rotate_count = 0;
		int center = 0;
		// similar to BFS expansion but need to update the parent information
		// when find a smaller total cost
		// for the same maze cell in queue
		while (n_goals != n_goal_found) {
			while (mc_que.size() != 0) {
				
				temp = mc_que.poll(); // pop the smallest item in queue
				n_node++;
//				if(goal_state == 43)
//					System.out.println("temp" + temp.get_index());
				visited[temp.get_index()] = 'V'; // mark as visited
				int testret;
				testret = cmaze.square_nearby(temp.get_index());
				if((testret != 0 && rotate_flag == 0) || (testret != 0 && rotate_flag == 1 && start_state != temp.get_index()))
				{
						
						retval = testret;
						center = temp.get_index();
						//System.out.println("center" + center + " retval" + retval);
						rotate_count = 0;
						goal_state = center;
						
						if(rotate_flag == 0)
							rotate_flag = 1;
						else 
							rotate_flag = 2;
						
						System.out.println("center" + center);
						break;	
				}
					
				if(temp.get_index() == goal_state)
					break;
				// check the four neighbors
				// left point
				if (temp.get_index() - 1 > 0) {
					nextpos = temp.get_index() - 1;
					// Check the point is already in queue or not
					// If the point is already in queue, compare the total cost
					// for
					// the current path and the previous path
					// Select the smaller cost path and store the result in
					// cmaze_cell
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {

						// if exist, compare path cost and restore if necessary
						if (mc_que.contains(cmaze_cell[nextpos])) {
							int new_tc = cmaze_cell[nextpos].get_heuristic()
									+ temp.get_level() + 1;

							if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
								mc_que.remove(cmaze_cell[nextpos]);
								cmaze_cell[nextpos]
										.set_parent(temp.get_index());
								cmaze_cell[nextpos]
										.set_level(temp.get_level() + 1);
								cmaze_cell[nextpos].set_totalCost(temp
										.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else { // if not exist, store information required
							n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}

				// the next 3 steps are the same except for neighbors from
				// right, bottom and top
				// right neighbor
				if (temp.get_index() + 1 < cmaze.get_height()
						* cmaze.get_width()) {
					nextpos = temp.get_index() + 1;
					if (visited[nextpos] != 'V'
							&& cmaze.maze_index(nextpos % cmaze.get_width(),
									nextpos / cmaze.get_width()) != '%') {
						if (mc_que.contains(cmaze_cell[nextpos])) {
							int new_tc = cmaze_cell[nextpos].get_heuristic()
									+ temp.get_level() + 1;
							
							if (new_tc < cmaze_cell[nextpos].get_totalCost()) {
								mc_que.remove(cmaze_cell[nextpos]);
								cmaze_cell[nextpos]
										.set_parent(temp.get_index());
								cmaze_cell[nextpos]
										.set_level(temp.get_level() + 1);
								cmaze_cell[nextpos].set_totalCost(temp
										.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else {
							// n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}
				// bottom neighbor
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
								cmaze_cell[nextpos]
										.set_parent(temp.get_index());
								cmaze_cell[nextpos]
										.set_level(temp.get_level() + 1);
								cmaze_cell[nextpos].set_totalCost(temp
										.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else {
							// n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}
				// top neighbor
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
								cmaze_cell[nextpos]
										.set_parent(temp.get_index());
								cmaze_cell[nextpos]
										.set_level(temp.get_level() + 1);
								cmaze_cell[nextpos].set_totalCost(temp
										.get_level() + 1);
								mc_que.add(cmaze_cell[nextpos]);
							}
						} else {
							// n_node++;
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					}
				}

			}
			temp = cmaze_cell[goal_state];
			// get the index of the goal and mark it
			int curr = temp.get_index();
			char char_on_goal;
			
			int perserve_curr = curr;
			// count the path length
//			System.out.println();
//			System.out.println("goal_state" + goal_state);
//			System.out.println("start_state" + start_state);
			while (curr != start_state) {
				pathcost++;
//				if(pathcost < 21 && pathcost > 9)
//				{
//					System.out.println(curr);
//				}
				if(cmaze.maze_index(curr%cmaze.get_width(), curr/cmaze.get_width()) == '.')
				{
//					if(n_goal_found < 10)
//						char_on_goal = (char) (n_goal_found + 48);
//					else if(n_goal_found < 34)
//						char_on_goal  =  (char)(n_goal_found + 55);
//					else 
//						char_on_goal = (char)(n_goal_found + 63);
					//cmaze.fill_in_maze(curr % cmaze.get_width(),
						//	curr / cmaze.get_width(), '1');
					if(pathcost < 9)
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'A');
					else if(pathcost < 21){
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'B');
					//System.out.println("B" + curr);
					}
					else if(pathcost < 35)
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'C');
					else if (pathcost < 75) {
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'D');
					}
					else if(pathcost < 99)
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'E');
					else if (pathcost < 182) {
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'F');
					}
					else if (pathcost < 195) {
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'G');
					}
					else if (pathcost < 212) {
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'H');
					}
					else if(pathcost < 238)
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'I');
					else {
						cmaze.fill_in_maze(curr % cmaze.get_width(),
								curr / cmaze.get_width(), 'J');
					}
					n_goal_found ++; 
				}
				curr = temp.get_parent();
				temp = cmaze_cell[curr];
			}
			//System.out.println("pathcost:" + pathcost);
			// now the pacman start from this position to get to one of the
			// other remaining goals
			start_state = perserve_curr;
			// System.out.println("n_goals" + n_goals);
			// System.out.println("goals_found" + n_goal_found);
			// show that one more goal is found
			//n_goal_found++;
			if(n_goals == n_goal_found){
				System.out.println("pathcost:" + pathcost);
				System.out.println("number of nodes:" + n_node);
				return 0;
			}
			
			gc_que.clear();
			// find all the goals
			cur_pos = 0;
			if(rotate_flag != 0 && rotate_count != 4)
			{
				goal_state = cmaze.find_rotate_pos(retval,rotate_count,center);
				rotate_count ++;
				if(rotate_flag == 2){
					System.out.println();
					System.out.println("start" + start_state);
					System.out.println("center" + center);
					System.out.println("goal" + goal_state);
					System.out.println("pathcost" + pathcost);
					System.out.println("count" + rotate_count);		
				}
//				System.out.println();
//				System.out.println("start" + start_state);
//				System.out.println("center" + center);
//				System.out.println("goal" + goal_state);
//				System.out.println("pathcost" + pathcost);
//				System.out.println("count" + rotate_count);
				if(rotate_count == 4)
				{
					rotate_count = 0;
					rotate_flag = 0;
				}
			}
			// find the nearest goal again
			else{
				for (int i = 0; (goal_found = cmaze.find_goals(cur_pos)) != -1; i++) {
					int cur_Mdist = cmaze.manhattan_distance(
							goal_found % cmaze.get_width(),
							goal_found / cmaze.get_width(),
							start_state % cmaze.get_width(),
							start_state / cmaze.get_width());
					multidot_goal_cell goal_cell = new multidot_goal_cell(
							goal_found, cur_Mdist);
				
					int n_wall = goal_cell.set_n_wall(cmaze);
					if(n_wall == 2)
						goal_cell.set_heurist(cur_Mdist *5);
				
					else if(n_wall== 1)
						goal_cell.set_heurist(cur_Mdist *10);
				
					if(n_wall== 0)
						goal_cell.set_heurist(cur_Mdist * 16);
				
					gc_que.add(goal_cell);
					cur_pos = goal_found;
				}
				chosengoal = gc_que.poll();
				if(cmaze.may_have_wall_between(start_state,chosengoal.get_pos()))
				{
					int mid = (start_state + chosengoal.get_pos()) / 2;
					if(cmaze.maze_index(mid%cmaze.get_width(),mid / cmaze.get_width()) == '%'){
						if(gc_que.size() != 0)
							chosengoal = gc_que.poll();
					}
				}
				// System.out.println("chosen goal" + chosengoal.get_pos());
				goal_state = chosengoal.get_pos();
			}
			nextpos = -100;
			// clean the visited array,because we may need to go to a cell
			// visited before again
			for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
				visited[i] = 0;
			}
			// clean the previous table first.
			for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
				cmaze_cell[i] = null;
			}
			// based on the new goal and start, make a new heuristic table.
			for (int i = 0; i < cmaze.get_width() * cmaze.get_height(); i++) {
				if (cmaze.maze_index(i % cmaze.get_width(),
						i / cmaze.get_width()) == '%')
					cmaze_cell[i] = new Maze_cell(i, -1);
				else
					cmaze_cell[i] = new Maze_cell(i, cmaze.manhattan_distance(i
							% cmaze.get_width(), i / cmaze.get_width(),
							goal_state % cmaze.get_width(),
							goal_state / cmaze.get_width()));
				
//				if(goal_state == 43)
//				{
//					System.out.println("index" + i + " heu " +cmaze_cell[i].get_heuristic());
//				}
			}
			mc_que.clear();
			mc_que.add(cmaze_cell[start_state]);
		}

		System.out.println("pathcost" + pathcost);
		return 0;
	}
}
