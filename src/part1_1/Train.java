package part1_1;

import java.io.*;
import java.math.*;
import java.util.*;

public class Train {
	private String img_file, label_file;
	private double K;//smoothing parameter.
	private int dim, class_num;//image size, and how many different classes.
	private int [][][]count1, count0; //count[i][j][class] of Fij for each class
	private int []class_count;//the # of instances for each class.
	public Train(){
		label_file = img_file = null;
		K=1;
		dim = 0;
		class_num = 0;
		class_count = null;
		count0 = null;
		count1 = null;
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
		count0 = new int[dim][dim][class_num];
		count1 = new int[dim][dim][class_num];
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
            			count1[linei][j][number]++;
            		}
            		else{
            			count0[linei][j][number]++;
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
		double raw0 = 0, raw1 = 0;;
		for(int i=0; i<dim; i++)
			for(int j=0; j<dim; j++)
				for(int k=0; k<class_num; k++){
					raw0 = (count0[i][j][k] + K)/(class_count[k]+class_num*K);
					raw1 = (count1[i][j][k] + K)/(class_count[k]+class_num*K);
					//matrix.value[i][j][k] = Math.log10(raw);
					matrix.value[0][i][j][k] = raw0;
					matrix.value[1][i][j][k] = raw1;
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
		//System.out.println(likely[4]);
	}
	
	public void print_count(int which)
	{
		for(int i=0;i<class_num;i++){
			System.out.println("for class"+i);
			for(int j=0;j<dim;j++){
				for(int k=0;k<dim;k++)
					if(which==1)
						System.out.print(count1[j][k][i]+" ");
					else {
						System.out.print(count0[j][k][i]+" ");
					}
				System.out.println();
			}
		}
	}
}
