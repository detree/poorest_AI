/*
 * This is a maze class that support a few basic functions. See function below for detail
 * 
 * @author Lunan Li
 * @Since 9/13/2015
 */
package mp1_prob1;

// libraries
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

//import java.util.Arrays;
public class Maze {

	static String line = null; /* each line in the txt file */
	static char[] maze_read = null; /* the maze read from the txt file */
	static int maze_width = 0;
	static int maze_height = 0;
	static int[] maze_position = new int[2]; /* 0: start state; 1: goal state */
	static char dir;
	static int[][] n_walls_between_blocks = null;

	/*
	 * Constructor of maze. It will read the default file and create an array
	 * that store the information of the maze
	 */
	public Maze() {
		BufferedReader br = null;

		/* open maze.txt */
		try {
			br = new BufferedReader(new FileReader("bigTurn.txt"));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* read the maze from maze.txt and store it line by line */
		try {
			String temp_maze = null;
			while ((line = br.readLine()) != null) {
				maze_width = line.length();
				maze_height++;
				// System.out.println(line);
				if (temp_maze == null)
					temp_maze = line;
				else
					temp_maze = temp_maze.concat(line);
			}
			maze_read = temp_maze.toCharArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("width = " + maze_width +
		// " maze_height = "+maze_height);
		/* close bufferedRead */
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* store start state and goal state */
		maze_position[0] = search('P');
		maze_position[1] = search('.');
		// fill_in_maze(0,0,'a');
		// System.out.println(maze_read[maze_position[0]] + " " +
		// maze_read[maze_position[1]]);
		// System.out.println(maze_read.length);
		System.out.println();
		/*
		 * System.out.println("Maze read in: "); for (int j = 0; j <
		 * maze_height; j++) { for (int i = 0; i < maze_width; i++) {
		 * System.out.print(maze_index(i, j)); } System.out.println(); }
		 */

	}

	/*
	 * input: x - width, y - height return: the char at specified index (x, y).
	 */
	public char maze_index(int x, int y) {
		if (x < 0 || y < 0 || x >= maze_width || y >= maze_height) {
			System.out.println("Out of bound");
			System.out.println("x" + x + " " + "y" + y);
			return 'a';
		}
		// System.out.println(maze_read.charAt(x + y * maze_width));
		return maze_read[x + y * maze_width];
	}

	/*
	 * input: none return: width of the maze
	 */
	public int get_width() {
		return maze_width;
	}

	/*
	 * input: none return: height of the maze
	 */
	public int get_height() {
		return maze_height;
	}

	/*
	 * input: none return: index of the start state
	 */
	public int get_start_state() {
		return maze_position[0];
	}

	/*
	 * input: none return: index of the goal state
	 */
	public int get_goal_state() {
		return maze_position[1];
	}

	/*
	 * input: x - width, y - height, content - the char to be filled in return:
	 * none
	 */
	public void fill_in_maze(int x, int y, char content) {
		maze_read[x + y * maze_width] = content;
	}

	/*
	 * input: the char to be searches return: the first occurrence of that char
	 */
	public int search(char c) {
		for (int i = 0; i < maze_read.length; i++) {
			if (maze_read[i] == c)
				return i;
		}
		return -1;
	}

	public int manhattan_distance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	// input: c - the direction
	public void set_dir(char c) {
		this.dir = c;
	}

	// return the dir
	public char get_dir() {
		return this.dir;
	}

	/*
	 * input: cur - current position next - the position of the pacman after the
	 * pacman move on more stepreturn the direction the pacman should take if he
	 * wants to go from cur to next
	 */
	public void find_dir(int cur, int next) {
		if (next - cur == 1)
			this.dir = 'E';
		else if (next - cur == -1)
			this.dir = 'W';
		else if (next - cur == maze_width)
			this.dir = 'S';
		else if (next - cur == -maze_width)
			this.dir = 'N';
	}

	public void set_n_walls_between_blocks() {
		n_walls_between_blocks = new int[this.get_width() * this.get_height()][this
				.get_width() * this.get_height()];
		for (int i = 0; i < this.get_width() * this.get_height(); i++)
			for (int j = i; j < this.get_width() * this.get_height(); j++) {
				int count = 0;
				int field_height = j / this.get_width() - i / this.get_width();
				int field_width = j % this.get_width() - i % this.get_width();
				for (int z = 0; z < field_height; z++)
					for (int k = 0; k < field_width; k++) {
						if (this.maze_index(i % maze_width + k, i / maze_width
								+ z) == '%')
							count++;
					}
				n_walls_between_blocks[i][j] = count;
			}
	}

	/*
	 * input: cur_pos - current position next - the position of the pacman after
	 * the pacman move on more step
	 */
	public int find_goals(int cur_pos) {
		if (cur_pos >= (this.get_width() * this.get_height()))
			return -1;
		// System.out.println("maze: " + this.get_height() * this.get_width());
		cur_pos++;
		while (maze_read[cur_pos] != '.') {
			cur_pos++;
			// System.out.println(cur_pos);
			if (cur_pos >= (this.get_width() * this.get_height()))
				return -1;
		}
		return cur_pos;
	}

	public int min(int a, int b) {
		if (a < b)
			return a;
		else
			return b;
	}

	private final int LARGE_INT = 0xfffffff;
	private static int heuristic_wall_count[];

	int hval_wall(int x, int y)

	{

		return heuristic_wall_count[x + y * this.get_width()];

	}

	/*
	 * 
	 * input - none
	 * 
	 * purpose - return a heuristic function that represent the smallest number
	 * of vertical wall and horizontal wall from a point to the goal.
	 * 
	 * return - int[# of points in maze][2].
	 */

	public void heuristic_wall_count()

	{

		int y_max = get_height(), x_max = get_width();

		int number_of_blocks = y_max * x_max;

		heuristic_wall_count = new int[number_of_blocks];

		/*
		 * 
		 * [#][0] represents for a horizontal line, # of wall block it will
		 * encounter to the column of goal
		 * 
		 * [#][1] represents for a vertical line, # of wall block it will
		 * encounter to the row of goal
		 */

		int[][] val = new int[number_of_blocks][2];

		int[][] mid = new int[number_of_blocks][2];

		// first we need to initialize the value of the blocks have same
		// column/row with the GOAL point.

		int goalx = this.get_goal_state() % x_max, goaly = this
				.get_goal_state() / x_max;

		val[this.get_goal_state()][0] = 0;

		val[this.get_goal_state()][1] = 0;

		for (int x = goalx - 1; x >= 0; x--)

		{

			if (this.maze_index(x, goaly) == '%')

				val[x + goaly * x_max][0] = val[x + 1 + goaly * x_max][0] + 1;

			else

				val[x + goaly * x_max][0] = val[x + 1 + goaly * x_max][0];

			val[x + goaly * x_max][1] = 0;

			mid[x + goaly * x_max][0] = val[x + goaly * x_max][0];

			mid[x + goaly * x_max][1] = LARGE_INT;

		}

		for (int x = goalx + 1; x < x_max; x++)

		{

			if (this.maze_index(x, goaly) == '%')

				val[x + goaly * x_max][0] = val[x - 1 + goaly * x_max][0] + 1;

			else

				val[x + goaly * x_max][0] = val[x - 1 + goaly * x_max][0];

			val[x + goaly * x_max][1] = 0;

			mid[x + goaly * x_max][0] = val[x + goaly * x_max][0];

			mid[x + goaly * x_max][1] = LARGE_INT;

		}

		for (int y = goaly - 1; y >= 0; y--)

		{

			if (this.maze_index(goalx, y) == '%')

				val[goalx + y * x_max][1] = val[goalx + (y + 1) * x_max][1] + 1;

			else

				val[goalx + y * x_max][1] = val[goalx + (y + 1) * x_max][1];

			val[goalx + y * x_max][0] = 0;

			mid[goalx + y * x_max][0] = LARGE_INT;

			mid[goalx + y * x_max][1] = val[goalx + y * x_max][1];

		}

		for (int y = goaly + 1; y < y_max; y++)

		{

			if (this.maze_index(goalx, y) == '%')

				val[goalx + y * x_max][1] = val[goalx + (y - 1) * x_max][1] + 1;

			else

				val[goalx + y * x_max][1] = val[goalx + (y - 1) * x_max][1];

			val[goalx + y * x_max][0] = 0;

			mid[goalx + y * x_max][0] = LARGE_INT;

			mid[goalx + y * x_max][1] = val[goalx + y * x_max][1];

		}

		// =================================================================================

		// values for helper function.

		for (int x = goalx - 1; x >= 0; x--)

			for (int y = goaly - 1; y >= 0; y--)

			{

				if (this.maze_index(x, y) == '%')

				{

					val[x + y * x_max][0] = val[x + 1 + y * x_max][0] + 1;

					val[x + y * x_max][1] = val[x + (y + 1) * x_max][1] + 1;

				}

				else

				{

					val[x + y * x_max][0] = val[x + 1 + y * x_max][0];

					val[x + y * x_max][1] = val[x + (y + 1) * x_max][1];

				}

			}

		for (int x = goalx + 1; x < x_max; x++)

			for (int y = goaly - 1; y >= 0; y--)

			{

				if (this.maze_index(x, y) == '%')

				{

					val[x + y * x_max][0] = val[x - 1 + y * x_max][0] + 1;

					val[x + y * x_max][1] = val[x + (y + 1) * x_max][1] + 1;

				}

				else

				{

					val[x + y * x_max][0] = val[x - 1 + y * x_max][0];

					val[x + y * x_max][1] = val[x + (y + 1) * x_max][1];

				}

			}

		for (int x = goalx - 1; x >= 0; x--)

			for (int y = goaly + 1; y < y_max; y++)

			{

				if (this.maze_index(x, y) == '%')

				{

					val[x + y * x_max][0] = val[x + 1 + y * x_max][0] + 1;

					val[x + y * x_max][1] = val[x + (y - 1) * x_max][1] + 1;

				}

				else

				{

					val[x + y * x_max][0] = val[x + 1 + y * x_max][0];

					val[x + y * x_max][1] = val[x + (y - 1) * x_max][1];

				}

			}

		for (int x = goalx + 1; x < x_max; x++)

			for (int y = goaly + 1; y < y_max; y++)

			{

				if (this.maze_index(x, y) == '%')

				{

					val[x + y * x_max][0] = val[x - 1 + y * x_max][0] + 1;

					val[x + y * x_max][1] = val[x + (y - 1) * x_max][1] + 1;

				}

				else

				{

					val[x + y * x_max][0] = val[x - 1 + y * x_max][0];

					val[x + y * x_max][1] = val[x + (y - 1) * x_max][1];

				}

			}

		// =================================================================================

		// values for non-combined final function.

		for (int x = goalx - 1; x >= 0; x--)

			for (int y = goaly - 1; y >= 0; y--)

			{

				mid[x + y * x_max][0] = min(mid[x + (y + 1) * x_max][0], val[x
						+ y * x_max][0]);

				mid[x + y * x_max][1] = min(mid[x + 1 + y * x_max][1], val[x
						+ y * x_max][1]);

			}

		for (int x = goalx + 1; x < x_max; x++)

			for (int y = goaly - 1; y >= 0; y--)

			{

				mid[x + y * x_max][0] = min(mid[x + (y + 1) * x_max][0], val[x
						+ y * x_max][0]);

				mid[x + y * x_max][1] = min(mid[x - 1 + y * x_max][1], val[x
						+ y * x_max][1]);

			}

		for (int x = goalx - 1; x >= 0; x--)

			for (int y = goaly + 1; y < y_max; y++)

			{

				mid[x + y * x_max][0] = min(mid[x + (y - 1) * x_max][0], val[x
						+ y * x_max][0]);

				mid[x + y * x_max][1] = min(mid[x + 1 + y * x_max][1], val[x
						+ y * x_max][1]);

			}

		for (int x = goalx + 1; x < x_max; x++)

			for (int y = goaly + 1; y < y_max; y++)

			{

				mid[x + y * x_max][0] = min(mid[x + (y - 1) * x_max][0], val[x
						+ y * x_max][0]);

				mid[x + y * x_max][1] = min(mid[x - 1 + y * x_max][1], val[x
						+ y * x_max][1]);

			}

		for (int i = 0; i < x_max * y_max; i++)

		{

			if (mid[i][0] != LARGE_INT)
				heuristic_wall_count[i] += (mid[i][0]);

			if (mid[i][1] != LARGE_INT)
				heuristic_wall_count[i] += (mid[i][1]);

		}

		
		  
		/*  int goal_state = this.get_goal_state();
		  
		  int width = this.get_width(), height = this.get_height();
		  
		  int goal_x = goal_state%width, goal_y = goal_state/width;
		  
		  for(int i=0; i<width*height; i++)
		  
		  heuristic_wall_count[i] = 0;
		  
		  
		  for(int x = goal_x-1; x>=0; x--)
		  
		  heuristic_wall_count[x+goal_y*width] += 2;
		  
		  for(int x = goal_x+1; x<width; x++)
		  
		  heuristic_wall_count[x+goal_y*width] += 2;
		  
		  for(int y = goal_y-1; y>=0; y--)
		  
		  heuristic_wall_count[goal_x+y*width] += 2;
		  
		  for(int y = goal_y+1; y<height; y++)
		  
		  heuristic_wall_count[goal_x+y*width] += 2;*/
		 

	}

	
}
