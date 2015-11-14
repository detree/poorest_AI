package part1_1;

public class Top {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Train trainer = new Train("digitdata/trainingimages", "digitdata/traininglabels");
		LikelyMatrix Fij = new LikelyMatrix(28,10,2);
		double []class_psbl = new double[10];
		
		
		trainer.set_para(1.0, 28, 10);
		if(!trainer.scan_file())
			System.out.println("some error during scan file.");
		trainer.calculate_likely(Fij);
		trainer.get_class_likely(class_psbl);
//		for(int i=0;i<10;i++)
//			System.out.println(class_psbl[i]);
//		for(int i=0;i<Fij.class_num;i++){
//			System.out.println("for class"+i);
//			for(int j=0;j<Fij.dim;j++){
//				for(int k=0;k<Fij.dim;k++)
//					System.out.print(Fij.value[j][k][i]+" ");
//				System.out.println();
//			}
//		}
		
		Test tester = new Test("digitdata/testimages", "digitdata/testlabels");
		tester.set_para(28, 10);
		
	}

}
