package part1_1;

import java.io.*;
import java.util.Scanner; 

public class Train {
	private String img_file, label_file;
	private int K;//smoothing parameter.
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
	public void set_para(int kin, int dimin, int classin)
	{
		K = kin;
		dim = dimin;
		class_num = classin;
		count = new int[dim][dim][class_num];
		class_count = new int [10];
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
            
            int linei=0, number=0;
            
            while(  (line = fileimg.readLine()) != null) {
            	linei++;
            	if(linei==29) linei=1;
            	if(linei==1){
            		number = filelabel.nextInt();
            		class_count[number]++;
            	}
//            	System.out.println(number);
//              System.out.println(line);
            	
            }   

            // Always close files.
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
