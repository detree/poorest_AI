package part2_1;

import java.util.Random;

public class SingleWeight {
	public double val[][];
	public int size=-1;
	public SingleWeight(int size_in) {
		size = size_in;
		val = new double[size][size];
	}
	public void init_to0(){
		if(size<0){
			System.out.println("the train set has not been initialized yet");
		}
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				val[i][j]=0;
	}
	public void init_rand(){
		Random rnd = new Random();
		if(size<0){
			System.out.println("the train set has not been initialized yet");
		}
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				val[i][j]=rnd.nextDouble();
	}
}
