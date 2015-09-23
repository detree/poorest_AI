/*
 * This is a maze class that support a few basic functions. See function below for detail
 * 
 * @author Lunan Li
 * @Since 9/13/2015
 */
package mp1_prob1;

// libraries
import java.io.*;
import java.util.*;

//import java.util.Arrays;
public class Maze{
	
	static String line = null; /* each line in the txt file */
	static char[] maze_read = null; /* the maze read from the txt file */
	static int maze_width = 0;
	static int maze_height = 0;
	static int[] maze_position = new int[2]; /* 0: start state; 1: goal state */
<<<<<<< HEAD

=======
	static char dir;
	
>>>>>>> origin/master
	/*
	 * Constructor of maze. It will read the default file and create an array
	 * that store the information of the maze
	 */
	public Maze() {
		BufferedReader br = null;

		/* open maze.txt */
		try {
			br = new BufferedReader(new FileReader("mediumMaze.txt"));

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
				System.out.println(line);
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
		System.out.println("Maze read in: ");
		for (int j = 0; j < maze_height; j++) {
			for (int i = 0; i < maze_width; i++) {
				System.out.print(maze_index(i, j));
			}
			System.out.println();
		}

	}

	/*
	 * input: x - width, y - height return: the char at specified index (x, y).
	 */
	public char maze_index(int x, int y) {
		if (x < 0 || y < 0 || x >= maze_width || y >= maze_height) {
			System.out.println("Out of bound");
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
<<<<<<< HEAD

	public int manhattan_distance(int x1, int y1, int x2, int y2) {
=======
	
	public int manhattan_distance(int x1, int y1, int x2, int y2){
>>>>>>> origin/master
		int ret_distance = 0;

		if (x1 - x2 < 0)
			ret_distance += (x2 - x1);
		else
			ret_distance += (x1 - x2);

		if (y1 - y2 < 0)
			ret_distance += (y2 - y1);
		else
			ret_distance += (y1 - y2);

		return ret_distance;
	}
	
<<<<<<< HEAD
=======
	public void set_dir(char c)
	{
		this.dir = c;
	}
	
	public char get_dir()
	{
		return this.dir;
	}
	
	public void find_dir(int cur,int next)
	{
		if(next - cur == 1)
			this.dir = 'E';
		else if(next - cur == -1)
			this.dir = 'W';
		else if(next - cur == maze_width)
			this.dir = 'S';
		else if(next - cur == -maze_width)
			this.dir = 'N';
	}
>>>>>>> origin/master
}
