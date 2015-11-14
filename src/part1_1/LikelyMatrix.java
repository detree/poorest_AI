package part1_1;

public class LikelyMatrix {
	//the dimension, how many classes, and how many value it can be for one point.
	public int dim, class_num, value_num;
	public double [][][][]value;
	public LikelyMatrix() {
		dim = 0;
		value = null;
	}
	public LikelyMatrix(int n, int class_n, int vnum) {
		dim = n;
		class_num = class_n;
		value_num = vnum;
		value = new double[value_num][n][n][class_num];
	}
}
