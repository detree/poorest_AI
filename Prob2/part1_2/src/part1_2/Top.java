package part1_2;

public class Top {

	public static void main(String[] args) {
		int node_num = 40;
		Graph g1 = new Graph(node_num,60);
		g1.draw_graph();
		
		boolean [][]result = new boolean[node_num][4];
		long start_time = System.nanoTime();
		result = g1.sol_forward_enter();
		long ass1 = g1.get_node_checked();
		long start_time2 = System.nanoTime();
		result = g1.sol_backtracing_enter();
		long ass2 = g1.get_node_checked();
		System.out.println(ass1+" time:"+(start_time2-start_time)/1000000000.00000);
		System.out.println(ass2+" time:"+(System.nanoTime()-start_time2)/1000000000.00000);
		//System.out.println("the result is:"+g1.check_result(result));
		//System.out.println("checked node number:"+g1.get_node_checked());
	}

}
