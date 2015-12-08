package part2_1;


public class Top {

	public static void main(String[] args) {
		TrainSet train1=null;
		train1 = new TrainSet("digitdata/trainingimages", "digitdata/traininglabels");
		train1.scan_file();
		
		Perceptron p1;
		p1 = new Perceptron(train1, 10, 28);
		p1.set_para(42, 0, "rand");
		//p1.train_in_order();
		p1.train_rand_order();
		
		Test tester;
		tester = new Test(p1, "digitdata/testimages", "digitdata/testlabels", 10);
		tester.scan_and_test();
		tester.get_statistic();
		System.out.println("size="+train1.get_size());
	}

}