package part2_2;

public class Top {

	public static void main(String[] args) {
		TrainSet train1=null;
		train1 = new TrainSet("digitdata/testimages", "digitdata/testlabels");
		train1.scan_file();
		System.out.println("size="+train1.get_size());
		
		TestKNN test1=null;
		test1 = new TestKNN(train1, "digitdata/trainingimages", "digitdata/traininglabels", 10);
		test1.scan_and_test();
		test1.get_statistic();

	}

}
