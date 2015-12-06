package part2_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test {
	private int class_cnt[], correct_cnt[], class_num;
	Perceptron percep = null;
	String img_file, label_file;
	public Test(Perceptron percep_in, String img, String label, int class_num_in){
		percep = percep_in;
		img_file = img;
		label_file = label;
		class_num = class_num_in;
		class_cnt = new int[class_num];
		correct_cnt = new int[class_num];
	}
	private double evaluate(SingleTrain train_data, int curr_class){
		double ret = 0;
		SingleWeight eva_weight = percep.get_weight_class(curr_class);
		for(int i=0;i<eva_weight.size;i++)
			for(int j=0;j<eva_weight.size;j++){
				if(train_data.val[i][j])
					ret += eva_weight.val[i][j];
			}
		return ret;
	}
	
	public void scan_and_test(){
		String line = null;
		try {
            // FileReader reads text files in the default encoding.
            FileReader init_fileimg = new FileReader(img_file);
            // Always wrap FileReader in BufferedReader.
            BufferedReader fileimg = new BufferedReader(init_fileimg);
            Scanner filelabel = new Scanner(new File(label_file));
            
            int linei=-1, number=0;
            SingleTrain curr_train = null;//for temporary store the current train matrix.
            
            while(  (line = fileimg.readLine()) != null) {
            	linei++;
            	//the start of a single test image.
            	if(linei==0){
            		number = filelabel.nextInt();
            		curr_train = new SingleTrain(28, number);
            	}
            	//get every line in the single test image.
            	for(int j=0; j<line.length(); j++){
            		if(line.charAt(j)=='+' || line.charAt(j)=='#'){
            			curr_train.val[linei][j] = true;
            		}
            		else{
            			curr_train.val[linei][j] = false;
            		}
            	}
            	//a single test image should end in this condition.
            	if(linei==27){
            		linei=-1;
            		class_cnt[curr_train.label]++;
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
    				if(max_sum_class == curr_train.label)
    					correct_cnt[curr_train.label]++;
            	}
            }
            //close files.
            fileimg.close();
            filelabel.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + img_file + "' and '" + label_file + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '"+ img_file + "' and '" + label_file + "'");
        }
	}
	
	public void get_statistic(){
		int total_correct=0, total=0;
		for(int i=0;i<class_num;i++){
			total_correct+=correct_cnt[i];
			total+=class_cnt[i];
			System.out.println("for "+i+",correct:"+(double)correct_cnt[i]/class_cnt[i]);
		}
		System.out.println("overall:"+(double)total_correct/total);
	}
	
}
