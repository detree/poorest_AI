package part1_1;

public class Test {
	private String img_file, label_file;
	private int dim, class_num;//image size, and how many different classes.
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
	}
}
