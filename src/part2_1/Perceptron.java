package part2_1;

import java.util.Random;

public class Perceptron {
	TrainSet train_data = null;
	int class_num, max_epoch;
	double bias;
	SingleWeight weight[];
	public Perceptron(TrainSet set_in, int class_num_in, int single_class_size) {
		train_data = set_in;
		class_num = class_num_in;
		weight = new SingleWeight[class_num];
		for(int i=0;i<class_num_in;i++)
			weight[i] = new SingleWeight(single_class_size);
	}
	//init_method choose from "init0" and "rand"
	//the initialize should be done using this func and constructor above
	public void set_para(int epoch_in, double bias_in, String init_method){
		max_epoch = epoch_in;
		bias = bias_in;
		if(init_method=="init0")
			for(int i=0;i<class_num;i++)
				weight[i].init_to0();
		if(init_method=="rand")
			for(int i=0;i<class_num;i++)
				weight[i].init_rand();
	}
	
	private double learn_rate(int curr_epoch){
		double f;
		f=Math.exp(-(curr_epoch/10.0));
		//f = 1000.0/(1000.0+(double)curr_epoch);
		return f;
	}
	
	private void update_weight(int curr_epoch, SingleTrain train_data, int real, int guess){
		double alpha = learn_rate(curr_epoch);
		for(int i=0;i<weight[real].size;i++)
			for(int j=0;j<weight[real].size;j++){
				if(train_data.val[i][j])
					weight[real].val[i][j] += alpha;
			}
		
		for(int i=0;i<weight[guess].size;i++)
			for(int j=0;j<weight[guess].size;j++){
				if(train_data.val[i][j])
					weight[guess].val[i][j] -= alpha;
			}
	}
	
	private double evaluate(SingleTrain train_data, int curr_class){
		double ret = 0;
		for(int i=0;i<weight[curr_class].size;i++)
			for(int j=0;j<weight[curr_class].size;j++){
				if(train_data.val[i][j])
					ret += weight[curr_class].val[i][j];
			}
		return ret;
	}
	
	public void train_in_order(){
		for(int curr_epoch = 0; curr_epoch<max_epoch; curr_epoch++){
			//iterate through every training data to train
			for(int i=0;i<train_data.get_size();i++){
				SingleTrain curr_train = train_data.get_single_train(i);
				double max_sum = Double.NEGATIVE_INFINITY, curr_sum;
				int max_sum_class = 0;
				//iterate through every class to find the guess
				for(int j=0;j<class_num;j++){
					curr_sum = evaluate(curr_train, j);
					if(curr_sum>max_sum){
						max_sum = curr_sum;
						max_sum_class = j;
					}
				}
				if(max_sum_class != curr_train.label){
					update_weight(curr_epoch, curr_train, curr_train.label, max_sum_class);
				}
			}
		}
	}
	
	public void train_rand_order(){
		for(int curr_epoch = 0; curr_epoch<max_epoch; curr_epoch++){
			boolean rand_hash[] = new boolean[train_data.size];
			Random rnd = new Random();
			int tried_count = 0, i;
			while(tried_count<train_data.size){
				i = rnd.nextInt(train_data.size);
				if(rand_hash[i]==false){
					rand_hash[i] = true;
					tried_count++;
				}
				else{
					continue;
				}
				SingleTrain curr_train = train_data.get_single_train(i);
				double max_sum = Double.NEGATIVE_INFINITY, curr_sum;
				int max_sum_class = 0;
				//iterate through every class to find the guess
				for(int j=0;j<class_num;j++){
					curr_sum = evaluate(curr_train, j);
					if(curr_sum>max_sum){
						max_sum = curr_sum;
						max_sum_class = j;
					}
				}
				if(max_sum_class != curr_train.label){
					update_weight(curr_epoch, curr_train, curr_train.label, max_sum_class);
				}
			}
		}
	}
	
	public SingleWeight get_weight_class(int class_num){
		return weight[class_num];
	}
}
