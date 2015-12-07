package part2_2;

public class SingleTrain {
	public boolean val[][];
	public int label, size;
	public SingleTrain(int size_in, int label_in) {
		size = size_in;
		val = new boolean[size][size];
		label = label_in;
	}
}