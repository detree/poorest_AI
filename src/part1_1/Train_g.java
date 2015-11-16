package part1_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Train_g {
	private String img_file = null, label_file = null;
	private double K = 0;// smoothing parameter.
	private int dim_x = 0, dim_y = 0, class_num = 0;// image size, and how many
													// different classes.
	private int[][][][] count = null;
	private int[] class_count = null;
	private int size = 0;

	public Train_g(String imgin, String labelin, double kin, int dimin, int classin, int n, int m) {
		dim_x = dimin - n;
		dim_y = dimin - m;
		class_num = classin;
		size = (int) Math.pow(2, (n + m));
		count = new int[size][dim_x][dim_y][class_num];
		class_count = new int[class_num];
		img_file = imgin;
		label_file = labelin;
	}

	public void scan_file(int dim, int n, int m) throws IOException {
		String[] line = new String[dim];
		// FileReader reads text files in the default encoding.
		FileReader init_fileimg = new FileReader(img_file);
		// Always wrap FileReader in BufferedReader.
		BufferedReader fileimg = new BufferedReader(init_fileimg);
		// Read int directly from file
		Scanner filelabel = new Scanner(new File(label_file));

		int counter = -1;

		while ((line[0] = fileimg.readLine()) != null) {
			int number = filelabel.nextInt();
			class_count[number]++;
			for (int i = 1; i < dim; i++)
				line[i] = fileimg.readLine();

			for (int j = 0; j < dim_y; j++) {
				for (int i = 0; i < dim_x; i++) {
					int result = 0;
					for (int q = 0; q < m; q++) {
						for (int p = 0; p < n; p++) {
							if (line[j + q].charAt(i + p) == '+' || line[j + q].charAt(i + p) == '#')
								result += (int) Math.pow(2, (q * n + p));

							count[result][i][j][number]++;
						}
					}
				}
			}

		}
		fileimg.close();
	}

	public boolean calculate_probability(LargeMatrix matrix) {
		if (matrix.dim_x != dim_x || matrix.dim_y != dim_y || matrix.class_num != class_num) {
			System.out.println("some error with init of Likely Matrix");
			return false;
		}
		double raw0 = 0, raw1 = 0;
		;
		for (int i = 0; i < dim_x; i++)
			for (int j = 0; j < dim_y; j++)
				for (int k = 0; k < class_num; k++)
					for (int l = 0; l < size; l++)
						matrix.value[l][i][j][k] = (count[l][i][j][k] + K) / (class_count[k] + K * class_num);

		return true;
	}

	public void get_class_likely(double[] likely) {
		double sum = 0;
		for (int i = 0; i < class_num; i++)
			sum += class_count[i];
		for (int i = 0; i < class_num; i++)
			likely[i] = (double) class_count[i] / sum;
		// System.out.println(likely[4]);
	}
}
