package part2_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TrainSet {
	private ArrayList<SingleTrain> train_data=null;
	private int size=-1;
	private String img_file, label_file;
	public TrainSet(String img, String label) {
		img_file = img;
		label_file = label;
		train_data = new ArrayList<SingleTrain>();
	}
	
	private SingleTrain img_shift(SingleTrain in, int x_shift, int y_shift){
		int size_here = in.size;
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
	
	//scan the file and get the training matrixes into the array list
	public void scan_file(){
		String line = null;
		size = 0;
		try {
            // FileReader reads text files in the default encoding.
            FileReader init_fileimg = new FileReader(img_file);
            // Always wrap FileReader in BufferedReader.
            BufferedReader fileimg = new BufferedReader(init_fileimg);
            Scanner filelabel = new Scanner(new File(label_file));
            
            int linei=-1, number=0;
            SingleTrain curr_train_data = null;//for temporary store the current train matrix.
            
            while(  (line = fileimg.readLine()) != null) {
            	linei++;
            	//the start of a single test image.
            	if(linei==0){
            		number = filelabel.nextInt();
            		curr_train_data = new SingleTrain(28, number);
            	}
            	//get every line in the single test image.
            	for(int j=0; j<line.length(); j++){
            		if(line.charAt(j)=='+' || line.charAt(j)=='#'){
            			curr_train_data.val[linei][j] = true;
            		}
            		else{
            			curr_train_data.val[linei][j] = false;
            		}
            	}
            	//a single test image should end in this condition.
            	if(linei==27){
            		size++;
            		linei=-1;
            		train_data.add(curr_train_data);
//            		for(int i=-1;i<=1;i++)
//            			for(int j=-1;j<=1;j++)
//            				train_data.add(img_shift(curr_train_data,i,j));
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
	
	public SingleTrain get_single_train(int index){
		if(size<0){
			System.out.println("the train set has not been initialized yet");
			return null;
		}
		if(index>=size){
			System.out.println("overflow error in getting single train. size="+size+",trying get="+index);
			return null;
		}
		return train_data.get(index);
	}
	
	public int get_size(){
		if(size<0){
			System.out.println("the train set has not been initialized yet");
			return 0;
		}
		return size;
	}
	
}
