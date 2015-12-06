package part2_1;


public class Top {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TrainSet train1=null;
		train1 = new TrainSet("digitdata/testimages", "digitdata/testlabels");
		train1.scan_file();
		
		//System.out.println("size="+rnd.nextDouble());
	}

}
