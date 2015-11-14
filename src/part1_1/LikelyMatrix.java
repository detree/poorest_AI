package part1_1;

public class LikelyMatrix {
	public int dim, class_num;
	public double [][][]value;
	public LikelyMatrix() {
		dim = 0;
		value = null;
	}
	public LikelyMatrix(int n) {
		dim = n;
		value = new double[n][n][class_num];
	}
}
