package part1_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test_g {
	private String img_file = null, label_file = null;
	private int dim_x = 0, dim_y = 0, class_num = 0;// image size, and how many
													// different classes.
	private int[] class_count, correct_count;

	public Test_g(String imgin, String labelin, int dimin, int classin, int n, int m) {
		dim_x = dimin - n;
		dim_y = dimin - m;
		class_num = classin;
		class_count = new int[class_num];
		correct_count = new int[class_num];
		img_file = imgin;
		label_file = labelin;
	}

	public boolean scan_and_test(LargeMatrix matrix, double[] class_psbl, int dim, int n, int m) {
		if (matrix.dim_x != dim_x || matrix.dim_y != dim_y || matrix.class_num != class_num) {
			System.out.println("some error with init of Likely Matrix");
			return false;
		}
		String[] line = new String[dim];
		try {
			// FileReader reads text files in the default encoding.
			FileReader init_fileimg = new FileReader(img_file);
			// Always wrap FileReader in BufferedReader.
			BufferedReader fileimg = new BufferedReader(init_fileimg);
			Scanner filelabel = new Scanner(new File(label_file));

			int linei = -1, number = 0;

			while ((line[0] = fileimg.readLine()) != null) {
				number = filelabel.nextInt();
				class_count[number]++;
				for (int i = 1; i < dim; i++)
					line[i] = fileimg.readLine();

				// calculate the value of P(class | Fij)
				int maxi = -1;
				double max_psbl = -999999999, curr_psbl = 0;
				for (int i = 0; i < class_num; i++) {
					curr_psbl = Math.log10(class_psbl[i]);
					for (int j = 0; j < dim_y; j++) {
						for (int k = 0; k < dim_x; k++) {
							int result = 0;
							for (int q = 0; q < m; q++) {
								for (int p = 0; p < n; p++) {
									if (line[j + q].charAt(k + p) == '+' || line[j + q].charAt(k + p) == '#')
										result += (int) Math.pow(2, (q * n + p));

									curr_psbl += Math.log10(matrix.value[result][k][j][i]);
								}
							}
						}
					}
					if (curr_psbl > max_psbl) {
						max_psbl = curr_psbl;
						maxi = i;
					}

				}
				if (maxi == number)
					correct_count[number]++;
				else {
					// System.out.println("wrong guess with:"+maxi+"
					// real:"+number);
				}
			}
			// close files.
			fileimg.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + img_file + "' and '" + label_file + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + img_file + "' and '" + label_file + "'");
		}
		return true;
	}
	
	public void statistic(){
		int correct=0,all=0;
		for(int i=0;i<class_num;i++)
		{
			correct+=correct_count[i];
			all+=class_count[i];
			System.out.println("for number"+i+" correct rate="+(double)correct_count[i]/class_count[i]);
		}
		System.out.println("overall correctness:"+(double)correct/all);
	}
}
