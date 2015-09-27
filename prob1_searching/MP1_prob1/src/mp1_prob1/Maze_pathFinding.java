package mp1_prob1;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Maze_pathFinding {

	/*----------------------------------------------------problem 1 part 1 ---------------------------------------------*/
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
	 * input: cmaze - current maze return: 0 - if success, -1 if fail solve a
	 * maze with greedy best first algorithm
	 */
	public int SolveMazeGBFS(Maze cmaze) {
		if (cmaze == null)
			return -1; // if the maze doesn't exist, return 0
		int mazeSize = cmaze.get_width() * cmaze.get_height();
		// define arrays necessary for traversing and drawing the result
		int[] parent = new int[mazeSize];
		int[] visited = new int[mazeSize];
		int[] Mdist = new int[mazeSize];
		// make a heuristic table
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
		/*
		 * I used a stack here but the stack is only used when all four
		 * directions of current node is wall or visited. The most important
		 * thing is to find the node with minimum heuristic value.
		 */
		while (!st.isEmpty()) {
			// -1 means next_pos doesn't exist
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
				// search all four directions to get the neighbor node with
				// minimum
				// heuristic value.
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
				// if we can't go to any neighbor node, go back one step
				if (next_pos == -1) {
					current = (Integer) st.peek();
					st.pop();
				}
				// go to the neighbor node with minimum heuristic value
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
	 * input: cmaze - current maze return 0 when success, -1 if fail solve a
	 * maze with A* search
	 */
	public int SolveMazeAStar(Maze cmaze) {

		if (cmaze == null) // if cmaze is null, fail
			return -1;

		int goal_state = cmaze.get_goal_state(); // end point in maze
		int start_state = cmaze.get_start_state(); // start point in maze
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

		// similar to BFS expansion but need to update the parent information
		// when find a smaller total cost
		// for the same maze cell in queue
		while (mc_que.size() != 0) {

			temp = mc_que.poll(); // pop the smallest item in queue

			visited[temp.get_index()] = 'V'; // mark as visited

			// check the four neighbors
			// left point
			if (temp.get_index() - 1 > 0) {
				nextpos = temp.get_index() - 1;
				// Check the point is already in queue or not
				// If the point is already in queue, compare the total cost for
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
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else { // if not exist, store information required
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						mc_que.add(cmaze_cell[nextpos]);
					}
				}
			}

			// the next 3 steps are the same except for neighbors from right,
			// bottom and top
			// right neighbor
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

		// print out the path and calculate the total path cost
		temp = cmaze_cell[goal_state];
		int curr = temp.get_index();
		while (curr != start_state) {
			pathcost++;
			cmaze.fill_in_maze(curr % cmaze.get_width(),
					curr / cmaze.get_width(), '.');

			curr = temp.get_parent();
			temp = cmaze_cell[curr];
		}

		System.out.println("Path cost is: " + pathcost);

		for (int i = 0; i < cmaze.get_height() * cmaze.get_width(); i++)
			System.out.println(cmaze_cell[i].get_index() % cmaze.get_width()
					+ "," + cmaze_cell[i].get_index() / cmaze.get_width() + " "
					+ cmaze_cell[i].get_totalCost());
		return 0;
	}

	/*----------------------------------------------------problem 1 part 2 ---------------------------------------------*/

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

	/*
	 * input: cmaze - current maze return 0 when success, -1 if fail solve a
	 * maze with A* search, turn cost 2, forward cost 1
	 */
	public int penalize_SolveMazeAStar_turn(Maze cmaze) {

		if (cmaze == null) // if cmaze is null, fail
			return -1;

		int goal_state = cmaze.get_goal_state(); // end point in maze
		int start_state = cmaze.get_start_state(); // start point in maze
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
		cmaze_cell[start_state].set_dir('E');
		Maze_cell temp = null;

		// similar to BFS expansion but need to update the parent information
		// when find a smaller total cost
		// for the same maze cell in queue
		while (mc_que.size() != 0) {

			temp = mc_que.poll(); // pop the smallest item in queue

			visited[temp.get_index()] = 'V'; // mark as visited

			// check the four neighbors
			// left point
			if (temp.get_index() - 1 > 0) {
				nextpos = temp.get_index() - 1;
				// Check the point is already in queue or not
				// If the point is already in queue, compare the total cost for
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
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							cmaze_cell[nextpos].set_dir('W');
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else { // if not exist, store information required
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						mc_que.add(cmaze_cell[nextpos]);
						cmaze_cell[nextpos].set_dir('W');
					}
				}
			}

			// the next 3 steps are the same except for neighbors from right,
			// bottom and top
			// right neighbor
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
							cmaze_cell[nextpos].set_dir('E');
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else {
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						cmaze_cell[nextpos].set_dir('E');
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
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							cmaze_cell[nextpos].set_dir('S');
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else {
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						cmaze_cell[nextpos].set_dir('S');
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
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							cmaze_cell[nextpos].set_dir('N');
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else {
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						cmaze_cell[nextpos].set_dir('N');
						mc_que.add(cmaze_cell[nextpos]);
					}
				}
			}
		}

		// print out the path and calculate the total path cost
		temp = cmaze_cell[goal_state];
		int curr = temp.get_index();
		while (curr != start_state) {
			if(temp.turn_cost(cmaze_cell[temp.get_parent()]) != 0 )
				pathcost = ( pathcost + temp.turn_cost(cmaze_cell[temp.get_parent()]) + 1);
			else
				pathcost++;
			cmaze.fill_in_maze(curr % cmaze.get_width(),
					curr / cmaze.get_width(), '.');

			curr = temp.get_parent();
			temp = cmaze_cell[curr];
		}

		System.out.println("Path cost is: " + pathcost);

		return 0;
	}
	
	/*
	 * input: cmaze - current maze return 0 when success, -1 if fail solve a
	 * maze with A* search, turn cost 1, forward cost 2
	 */
	public int penalize_SolveMazeAStar_forward(Maze cmaze) {

		if (cmaze == null) // if cmaze is null, fail
			return -1;

		int goal_state = cmaze.get_goal_state(); // end point in maze
		int start_state = cmaze.get_start_state(); // start point in maze
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
		cmaze_cell[start_state].set_dir('E');
		Maze_cell temp = null;

		// similar to BFS expansion but need to update the parent information
		// when find a smaller total cost
		// for the same maze cell in queue
		while (mc_que.size() != 0) {

			temp = mc_que.poll(); // pop the smallest item in queue

			visited[temp.get_index()] = 'V'; // mark as visited

			// check the four neighbors
			// left point
			if (temp.get_index() - 1 > 0) {
				nextpos = temp.get_index() - 1;
				// Check the point is already in queue or not
				// If the point is already in queue, compare the total cost for
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
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							cmaze_cell[nextpos].set_dir('W');
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else { // if not exist, store information required
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						mc_que.add(cmaze_cell[nextpos]);
						cmaze_cell[nextpos].set_dir('W');
					}
				}
			}

			// the next 3 steps are the same except for neighbors from right,
			// bottom and top
			// right neighbor
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
							cmaze_cell[nextpos].set_dir('E');
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else {
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						cmaze_cell[nextpos].set_dir('E');
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
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							cmaze_cell[nextpos].set_dir('S');
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else {
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						cmaze_cell[nextpos].set_dir('S');
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
							cmaze_cell[nextpos].set_parent(temp.get_index());
							cmaze_cell[nextpos].set_level(temp.get_level() + 1);
							cmaze_cell[nextpos]
									.set_totalCost(temp.get_level() + 1);
							cmaze_cell[nextpos].set_dir('N');
							mc_que.add(cmaze_cell[nextpos]);
						}
					} else {
						// n_node++;
						cmaze_cell[nextpos].set_parent(temp.get_index());
						cmaze_cell[nextpos].set_level(temp.get_level() + 1);
						cmaze_cell[nextpos].set_totalCost(temp.get_level() + 1);
						cmaze_cell[nextpos].set_dir('N');
						mc_que.add(cmaze_cell[nextpos]);
					}
				}
			}
		}

		// print out the path and calculate the total path cost
		temp = cmaze_cell[goal_state];
		int curr = temp.get_index();
		while (curr != start_state) {
			if(temp.forward_cost(cmaze_cell[temp.get_parent()]) != 0 )
				pathcost = ( pathcost + temp.forward_cost(cmaze_cell[temp.get_parent()]) + 2);
			else
				pathcost+=2;
			cmaze.fill_in_maze(curr % cmaze.get_width(),
					curr / cmaze.get_width(), '.');

			curr = temp.get_parent();
			temp = cmaze_cell[curr];
		}

		System.out.println("Path cost is: " + pathcost);

		return 0;
	}
}
