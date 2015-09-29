package mp1_prob1;

public class Mp1_execute {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Maze new_maze = new Maze();
		Maze_pathFinding solvemaze = new Maze_pathFinding();
		//solvemaze.SolveMazeBFS(new_maze);
		//solvemaze.SolveMazeDFS(new_maze);
		//solvemaze.SolveMazeGBFS(new_maze, 0, 1);
		//solvemaze.SolveMazeAStar(new_maze);
		//solvemaze.penalize_SolveMazeAStar_forward(new_maze);
		solvemaze.Ghost_Approx_SolveMazeAStar(new_maze);
		//Multidots_maze solvemaze = new Multidots_maze();
		//solvemaze.multidots_normal_SolveMazeAStar(new_maze);
		// solvemaze.multidots_normal_SolveMazeAStar(new_maze);
		// solvemaze.SolveMazeBFS(new_maze,new_maze.get_start_state());
		for (int j = 0; j < new_maze.get_height(); j++) {
			for (int i = 0; i < new_maze.get_width(); i++) {
				System.out.print(new_maze.maze_index(i, j));
			}
			System.out.println();
		}

	}
}
