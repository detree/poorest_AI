package part1_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.math.*;

public class Test {
	private String img_file, label_file;
	private int dim, class_num;//image size, and how many different classes.
	private int []class_count, correct_count;
	public Test(){
		label_file = img_file = null;
		dim = 0;
		class_num = 0;
	}
	public Test(String img, String label){
		img_file = img;
		label_file = label;
	}
	public void choose_file(String img, String label)
	{
		img_file = img;
		label_file = label;
	}
	public void set_para(int dimin, int classin)
	{
		dim = dimin;
		class_num = classin;
		class_count = new int[class_num];
		correct_count = new int[class_num];
	}
	
	public boolean scan_and_test(LikelyMatrix matrix, double[] class_psbl)
	{
		if(matrix.dim!=dim || matrix.class_num!=class_num){
			System.out.println("some error with init of Likely Matrix");
			return false;
		}
		String []line = new String[dim];
		try {
            // FileReader reads text files in the default encoding.
            FileReader init_fileimg = new FileReader(img_file);
            // Always wrap FileReader in BufferedReader.
            BufferedReader fileimg = new BufferedReader(init_fileimg);
            Scanner filelabel = new Scanner(new File(label_file));
            
            int linei=-1, number=0;
            
            while(  (line[0] = fileimg.readLine()) != null) {
            	number = filelabel.nextInt();
            	class_count[number]++;
            	for(int i=1; i<dim; i++)
            		line[i] = fileimg.readLine();
            	
            	// calculate the value of P(class | Fij)
            	int maxi=-1; double max_psbl=-999999999, curr_psbl=0;
            	for(int i=0; i<class_num; i++){
            		curr_psbl = Math.log10(class_psbl[i]);
            		for(int j=0; j<dim; j++){
            			for(int k=0; k<dim; k++){
            				if(line[j].charAt(k)==' ')
            					curr_psbl+=1.0*Math.log10(matrix.value[0][j][k][i]);
            				else if(line[j].charAt(k)=='+' || line[j].charAt(k)=='#')
            					curr_psbl+=1.0*Math.log10(matrix.value[1][j][k][i]);
            			}
            		}
            		if(curr_psbl>max_psbl){
            			max_psbl = curr_psbl;
            			maxi = i;
            		}
            	}
            	if(maxi==number)
            		correct_count[number]++;
            	else{
            		//System.out.println("wrong guess with:"+maxi+" real:"+number);
            	}
            }
            //close files.
            fileimg.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + img_file + "' and '" + label_file + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '"+ img_file + "' and '" + label_file + "'");
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
