package part2_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TestKNN {
	private int class_cnt[], correct_cnt[], class_num;
	TrainSet train_data = null;
	String img_file, label_file;
	public TestKNN(TrainSet train_data_in, String img, String label, int class_num_in){
		train_data = train_data_in;
		img_file = img;
		label_file = label;
		class_num = class_num_in;
		class_cnt = new int[class_num];
		correct_cnt = new int[class_num];
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
	
	private int predict(SingleTrain in){
		
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
            SingleTrain curr_digit = null;//for temporary store the current train matrix.
            
            while(  (line = fileimg.readLine()) != null) {
            	linei++;
            	//the start of a single test image.
            	if(linei==0){
            		number = filelabel.nextInt();
            		curr_digit = new SingleTrain(28, number);
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
}
