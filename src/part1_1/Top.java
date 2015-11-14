package part1_1;

public class Top {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Train trainer = new Train("digitdata/trainingimages", "digitdata/traininglabels");
		trainer.set_para(1, 28, 10);
		if(!trainer.scan_file())
			System.out.println("some error during scan file.");
		trainer.print_count();
	}

}
