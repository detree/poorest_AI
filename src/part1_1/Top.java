package part1_1;

public class Top {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Train trainer = new Train("digitdata/trainingimages", "digitdata/traininglabels");
		LikelyMatrix Fij = new LikelyMatrix(28,10,2);
		double []class_psbl = new double[10];
		
		
		trainer.set_para(4.0, 28, 10);
		if(!trainer.scan_file())
			System.out.println("some error during scan file for train.");
		trainer.calculate_likely(Fij);
		trainer.get_class_likely(class_psbl);
//		for(int i=0;i<10;i++)
//			System.out.println(class_psbl[i]);
//		for(int i=0;i<Fij.class_num;i++){
//			System.out.println("for class"+i);
//			for(int j=0;j<Fij.dim;j++){
//				for(int k=0;k<Fij.dim;k++)
//					System.out.print(Fij.value[1][j][k][i]+" ");
//					if(Fij.value[1][j][k][i]<0.00001){
//						System.out.print(" ");
//					}
//					else {
//						System.out.print("+");
//					}
//				System.out.println();
//			}
//		}
		
		Test tester = new Test("digitdata/testimages", "digitdata/testlabels");
		tester.set_para(28, 10);
		if(!tester.scan_and_test(Fij, class_psbl))
			System.out.println("some error during scan file for test.");
		tester.statistic();
		
	}

}
