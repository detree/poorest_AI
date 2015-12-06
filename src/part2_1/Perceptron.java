package part2_1;

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
	
	public void train_in_order(){
		for(int curr_epoch = 0; curr_epoch<max_epoch; curr_epoch++){
			
		}
	}
	
	public void train_in_rand(){
		for(int curr_epoch = 0; curr_epoch<max_epoch; curr_epoch++){
			
		}
	}
}
