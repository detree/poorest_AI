package part1_1;

public class LargeMatrix {
	// the dimension, how many classes, and how many value it can be for one
	// point.
	public int dim_x, dim_y, class_num, value_num;
	public double[][][][] value;

	public LargeMatrix() {
			dim_x = 0;
			dim_y = 0;
			value = null;
		}

	public LargeMatrix(int dim, int class_n, int n, int m) {
			dim_x = dim - n;
			dim_y = dim - m;
			class_num = class_n;
			value_num = (int)Math.pow(2, (n + m));;
			value = new double[value_num][dim_x][dim_y][class_num];
		}
}
