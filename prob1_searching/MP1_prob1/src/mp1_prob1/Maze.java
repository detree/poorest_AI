package mp1_prob1;

import java.io.*;

public class Maze{

	static String line = null; /* each line in the txt file */
	static String maze_read = null; /* the maze read from the txt file */
	static int maze_width = 0;
	static int maze_height = 0;
	static int[] maze_position = new int[2]; /* 0: start state; 1: goal state */
	
	/*
	 * 
	 */
	public Maze() {
		BufferedReader br = null;
		
		/*open maze.txt */
		try {
			br = new BufferedReader(new FileReader("mediumMaze.txt"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* read the maze from maze.txt and store it line by line */
		try {
			while( (line = br.readLine()) != null){
				maze_width = line.length();
				maze_height++;
				System.out.println(line);
				if(maze_read == null)
					maze_read = line;
				else
					maze_read = maze_read.concat(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("width = " + maze_width + " maze_height = "+maze_height);
		/* close bufferedRead */
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* store start state and goal state */
		maze_position[0] = maze_read.indexOf("P");
		maze_position[1] = maze_read.indexOf(".");
		
		//System.out.println(maze_read);
		System.out.println();
		System.out.println("Maze read in: ");
		for(int j=0; j < maze_height; j++){
			for(int i= 0; i < maze_width; i++){
				System.out.print(maze_index(i,j));
			}
			System.out.println();
		}
		
	}
	
	/*
	 * input: x - width, y - height
	 * return: the char at specified index (x, y).
	 */
	public static char maze_index(int x, int y) {
		if (x<0 || y <0 || x >= maze_width || y >= maze_height) {
			System.out.println("Out of bound");
			return 'a';
		}
		//System.out.println(maze_read.charAt(x + y * maze_width));
		return maze_read.charAt(x + y * maze_width);
	}
	
	/*
	 * input: none
	 * return: width of the maze
	 */
	public static int get_width(){
		return maze_width;
	}
	
	/*
	 * input: none
	 * return: height of the maze
	 */
	public static int get_height(){
		return maze_height;
	}
	
	/*
	 * input: none
	 * return: index of the start state
	 */
	public static int get_start_state(){
		return maze_position[0];
	}
	
	/*
	 * input: none
	 * return: index of the goal state
	 */
	public static int get_goal_state(){
		return maze_position[1];
	}
}