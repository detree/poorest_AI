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
		String []line = new String[class_num];
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
            		for(int j=0; j<dim; j++)
            			for(int k=0; k<dim; k++){
            				
            				curr_psbl+=Math.log10(matrix.value[j][k][i]);
            			}
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
}
