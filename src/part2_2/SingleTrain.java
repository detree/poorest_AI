package part2_2;

public class SingleTrain {
	public boolean val[][];
	public int label;
	public SingleTrain(int size, int label_in) {
		val = new boolean[size][size];
		label = label_in;
	}
}