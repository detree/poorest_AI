package part2_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.chrono.MinguoChronology;
import java.util.Scanner;


public class TestKNN {
	private int class_cnt[], correct_cnt[], class_num;
	TrainSet train = null;
	String img_file, label_file;
	private int confusion[][];
	public TestKNN(TrainSet train_data_in, String img, String label, int class_num_in){
		train = train_data_in;
		img_file = img;
		label_file = label;
		class_num = class_num_in;
		class_cnt = new int[class_num];
		correct_cnt = new int[class_num];
		confusion = new int[class_num_in][class_num_in];
	}
	
	private int check_overlap(SingleTrain in, SingleTrain target){
		int check=0;
		for(int i=0;i<in.size;i++)
			for(int j=0;j<in.size;j++)
				if(in.val[i][j]==target.val[i][j])
					check++;
		return check;
	}
	
	private SingleTrain img_shift(SingleTrain in, int x_shift, int y_shift){
		int size_here = 28;
		SingleTrain ret = new SingleTrain(in.size, in.label);
		for(int i=0;i<in.size;i++)
			for(int j=0;j<in.size;j++){
				if(i-x_shift<0 || i-x_shift>=size_here ||
					j-y_shift<0 || j-y_shift>=size_here){
					ret.val[i][j] = false;
				}
				else{
					ret.val[i][j] = in.val[i-x_shift][j-y_shift];
				}
			}
		return ret;
	}
	
	private SingleTrain shift_to_overlap(SingleTrain in, SingleTrain target){
		int xrange = 0, yrange = 0;
		int max_overlap = Integer.MIN_VALUE, max_shiftx=0, max_shifty=0;
		for(int i=-xrange; i<=xrange; i++)
			for(int j=-yrange; j<=yrange; j++){
				if(check_overlap(img_shift(in, i, j), target)>max_overlap){
					max_shiftx = i;
					max_shifty = j;
					
				}
			}
		return img_shift(in, max_shiftx, max_shifty);
	}
	
	private double naive_distance(SingleTrain in, SingleTrain target){
		double dist=0.0;
		for(int i=0;i<in.size;i++)
			for(int j=0;j<in.size;j++){
				if(in.val[i][j] != target.val[i][j]){
					dist+=1;
				}
			}
		return dist;
	}
	
	private int min(int a, int b){
		if(a<b) return a; else return b;
	}
	private double naive_distance2(SingleTrain in, SingleTrain target){
		double dist=0.0;
		for(int i=0;i<in.size;i++)
			for(int j=0;j<in.size;j++){
				if(in.val[i][j] != target.val[i][j]){
					int ipj= i+j+2;//i plus j
					boolean flag = true;
					for(int ii=i; ii<min(in.size,ipj-j); ii++){
						for(int jj=j;jj<min(in.size, ipj-ii);jj++){
							if(in.val[ii][jj] == target.val[i][j]){
								dist+=((ii-i)*(ii-i)+(jj-j)*(jj-j));
								flag = false;
								break;
							}
						}
						if(!flag) break;
					}
					if(flag){
						dist+=2;
					}
				}
			}
		return dist;
	}
	
	private int predict(SingleTrain target){
		int k=5;
		ItemInOrder k_neighbour = new ItemInOrder(k);
		for(int i=0;i<train.get_size();i++){
			//System.out.println(i);
			double curr_dist;
			//SingleTrain cmpr_to = shift_to_overlap(train.get_single_train(i), target);
			SingleTrain cmpr_to = train.get_single_train(i);
			//SingleTrain cmpr_to = img_shift(train.get_single_train(i), -1, 0);
			
			curr_dist = naive_distance2(cmpr_to, target);
			
			k_neighbour.check_and_add(train.get_single_train(i).label, curr_dist);
			
		}
		int count[]={0,0,0,0,0,0,0,0,0,0};
		int max_count = Integer.MIN_VALUE, max_num = -1;
		for(int i=1;i<=k;i++)
		{
			count[k_neighbour.get_item_num(i)]++;
			if(count[k_neighbour.get_item_num(i)]>max_count){
				max_count = count[k_neighbour.get_item_num(i)];
				max_num = k_neighbour.get_item_num(i);
			}
		}
		return max_num;
	}
	
	public void scan_and_test(){
		String line = null;
		try {
            // FileReader reads text files in the default encoding.
            FileReader init_fileimg = new FileReader(img_file);
            // Always wrap FileReader in BufferedReader.
            BufferedReader fileimg = new BufferedReader(init_fileimg);
            Scanner filelabel = new Scanner(new File(label_file));
            
            int linei=-1, number=0, count = 0;
            SingleTrain curr_digit = null;//for temporary store the current train matrix.
            
            while(  (line = fileimg.readLine()) != null) {
            	linei++;
            	//the start of a single test image.
            	if(linei==0){
            		number = filelabel.nextInt();
            		curr_digit = new SingleTrain(28, number);
            		//count++;
            		//System.out.println(count);
            	}
            	//get every line in the single test image.
            	for(int j=0; j<line.length(); j++){
            		if(line.charAt(j)=='+' || line.charAt(j)=='#'){
            			curr_digit.val[linei][j] = true;
            		}
            		else{
            			curr_digit.val[linei][j] = false;
            		}
            	}
            	//a single test image should end in this condition.
            	if(linei==27){
            		linei=-1;
            		class_cnt[curr_digit.label]++;
            		int guess = predict(curr_digit);
            		if(guess == curr_digit.label)
            			correct_cnt[guess]++;
            		else
            			confusion[curr_digit.label][guess]++;
            		
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
		System.out.println("confusion:");
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++)
				System.out.print((double)confusion[i][j]/class_cnt[i] + "\t");
			System.out.println();
		}
	}
}
