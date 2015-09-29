package mp1_prob1;

public class Mp1_execute {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Maze new_maze = new Maze();
		Maze_pathFinding solvemaze =  new Maze_pathFinding(); 
		//solvemaze.multidots_SolveMazeAStar(new_maze);
		//solvemaze.SolveMazeBFS(new_maze,new_maze.get_start_state());
		//solvemaze.Ghost_Approx_SolveMazeAStar(new_maze);
		solvemaze.Ghost_SolveMazeAStar(new_maze);
		//solvemaze.SolveMazeAStar(new_maze);
		for (int j = 0; j < new_maze.get_height(); j++) {
			for (int i = 0; i < new_maze.get_width(); i++) {
				System.out.print(new_maze.maze_index(i, j));
			}
			System.out.println();
		}
//		solvemaze.SolveMazeBFS(new_maze,new_maze.get_start_state());
//		System.out.println("bfs");
//		for(int j = 0; j < new_maze.get_height();j++){
//			for(int i = 0;i < new_maze.get_width();i++)
//				System.out.print(new_maze.maze_index(i,j));
//			System.out.println();
//		}
		
//		solvemaze.SolveMazeDFS(new_maze,new_maze.get_start_state());
//		System.out.println("dfs");
//		for(int j = 0; j < new_maze.get_height();j++){
//			for(int i = 0;i < new_maze.get_width();i++)
//				System.out.print(new_maze.maze_index(i,j));
//			System.out.println();
//		}
		
//		solvemaze.SolveMazeGBFS(new_maze);
//		System.out.println("greedy");
//		for(int j = 0; j < new_maze.get_height();j++){
//			for(int i = 0;i < new_maze.get_width();i++)
//				System.out.print(new_maze.maze_index(i,j));
//			System.out.println();
//		}
//		
		
//		solvemaze.penalize_SolveMazeGBFS(new_maze,2,1);
//		System.out.println("penalize_greedy");
//		for(int j = 0; j < new_maze.get_height();j++){
//			for(int i = 0;i < new_maze.get_width();i++)
//				System.out.print(new_maze.maze_index(i,j));
//			System.out.println();
//		}
		
		
//		solvemaze.penalize_SolveMazeBFS(new_maze,new_maze.get_start_state(),2,1);
//		System.out.println("BFS");
//		for(int j = 0; j < new_maze.get_height();j++){
//			for(int i = 0;i < new_maze.get_width();i++)
//				System.out.print(new_maze.maze_index(i,j));
//			System.out.println();
//		}
		
//		solvemaze.penalize_SolveMazeGBFS(new_maze,2,1);
//		System.out.println("greedy");
//		for(int j = 0; j < new_maze.get_height();j++){
//			for(int i = 0;i < new_maze.get_width();i++)
//				System.out.print(new_maze.maze_index(i,j));
//			System.out.println();
//		}
	}
}
