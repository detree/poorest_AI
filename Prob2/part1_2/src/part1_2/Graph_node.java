package part1_2;
import java.util.Random;

public class Graph_node
{
	public double posx, posy;
	private Random random_generator;
	public Graph_node(){
		posx=0;
		posy=0;
		random_generator = new Random();
	}
	public Graph_node(double x, double y){
		posx=x;
		posy=y;
		random_generator = new Random();
	}
	public void rand_pos(double range){
		posx = random_generator.nextDouble()*range;
		posy = random_generator.nextDouble()*range;
	}
}
