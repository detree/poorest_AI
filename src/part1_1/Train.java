package part1_1;

import java.io.*;
import java.math.*;
import java.util.*;

public class Train {
	private String img_file, label_file;
	private double K;//smoothing parameter.
	private int dim, class_num;//image size, and how many different classes.
	private int [][][]count; //count[i][j][class] of Fij for each class
	private int []class_count;//the # of instances for each class.
	public Train(){
		label_file = img_file = null;
		K=1;
		dim = 0;
		class_num = 0;
		class_count = null;
		count = null;
	}
	public Train(String img, String label){
		img_file = img;
		label_file = label;
	}
	public void choose_file(String img, String label)
	{
		img_file = img;
		label_file = label;
	}
	public void set_para(double kin, int dimin, int classin)
	{
		K = kin;
		dim = dimin;
		class_num = classin;
		count = new int[dim][dim][class_num];
		class_count = new int [class_num];
	}
	public boolean scan_file()
	{
		String line = null;
		try {
            // FileReader reads text files in the default encoding.
            FileReader init_fileimg = new FileReader(img_file);
            // Always wrap FileReader in BufferedReader.
            BufferedReader fileimg = new BufferedReader(init_fileimg);
            Scanner filelabel = new Scanner(new File(label_file));
            
            int linei=-1, number=0;
            
            while(  (line = fileimg.readLine()) != null) {
            	linei++;
            	if(linei==28) linei=0;
            	if(linei==0){
            		number = filelabel.nextInt();
            		class_count[number]++;
            	}
            	// (# of times pixel (i,j) has value f in training examples from this class)
            	for(int j=0; j<line.length(); j++){
            		if(line.charAt(j)=='+' || line.charAt(j)=='#'){
            			count[linei][j][number]++;
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
	
	public void calculate_likely(LikelyMatrix matrix){
		if(matrix.dim!=dim || matrix.class_num!=class_num){
			System.out.println("some error with init of Likely Matrix");
			return;
		}
		double raw=0;
		for(int i=0; i<dim; i++)
			for(int j=0; j<dim; j++)
				for(int k=0; k<class_num; k++){
					raw = (count[i][j][k] + K)/(class_count[k]+class_num*K);
					//matrix.value[i][j][k] = Math.log10(raw);
					matrix.value[i][j][k] = raw;
					//System.out.println(matrix.value[i][j][k]);
				}
	}
	
	public void get_class_likely(double [] likely)
	{
		double sum=0;
		for(int i=0; i<class_num; i++)
			sum+=class_count[i];
		for(int i=0; i<class_num; i++)
			likely[i] = (double)class_count[i]/sum;
		System.out.println(likely[4]);
	}
	
	public void print_count()
	{
		for(int i=0;i<class_num;i++){
			System.out.println("for class"+i);
			for(int j=0;j<dim;j++){
				for(int k=0;k<dim;k++)
					System.out.print(count[j][k][i]+" ");
				System.out.println();
			}
		}
	}
}
