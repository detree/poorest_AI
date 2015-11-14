package part1_1;

public class LikelyMatrix {
	public int dim;
	public double [][]value;
	public LikelyMatrix() {
		dim = 0;
		value = null;
	}
	public LikelyMatrix(int n) {
		dim = n;
		value = new double[n][n];
	}
}
