package part1_1;

import java.util.ArrayList;

public class GMatrix {
	//private int class_num;
	private int value_num;
	private ArrayList<ArrayList<Double>> g_mar = new ArrayList<ArrayList<Double>>();
	
		
	
	/*public GMatrix(int n, int m, LikelyMatrix curr, int type, int value, int digit){
		int size = curr.dim;
		value_num = value;
		class_num = digit;
		if(type == 1){
			for(int i = 0; i < size; i+=n){
				for(int j = 0; j < size; j+=m){
					g_mar.add( new ArrayList<Double>());
					ArrayList<Double> temp = g_mar.get(g_mar.size()-1);
					temp.add((double)i);
					temp.add((double)j);
					for(int p = 0; p < n; p++){
						for(int q = 0; q < m; q++){
							temp.add(curr.value[value_num][i+p][j+q][class_num]);
						}
					}
				}
			}
		}else{
			
		}
		
		for(int i = 0; i < g_mar.size();i++){
			
			System.out.println("At coordinate (x, y) = (" + g_mar.get(i).get(0) + ", " + g_mar.get(i).get(1) + ")");
			for(int p = 0; p < n; p++){
				for(int q = 0; q < m; q++){
					System.out.println("Original value: " + curr.value[value_num][(int) (g_mar.get(i).get(0)+p)][(int) (g_mar.get(i).get(1)+q)][class_num]);
				}
			}
			for(int j = 2; j < g_mar.get(i).size(); j++){
					
				System.out.println("Our value: " + g_mar.get(i).get(j));
			}
		}
	}*/
	
}
