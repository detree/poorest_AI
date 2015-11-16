package part1_1;

import java.io.IOException;

public class Top {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Train trainer = new Train("digitdata/trainingimages", "digitdata/traininglabels");
		//LikelyMatrix Fij = new LikelyMatrix(28,10,2);
		double []class_psbl = new double[10];
		LargeMatrix Fij = new LargeMatrix(28, 10, 2, 2);
		Train_g trainer = new Train_g("digitdata/trainingimages", "digitdata/traininglabels", 2, 28, 10, 2, 2);
		//trainer.set_para(4.0, 28, 10);
		trainer.scan_file(28,2,2);
			//System.out.println("some error during scan file for train.");
		trainer.calculate_probability(Fij);
		trainer.get_class_likely(class_psbl);
		
		
////		for(int i=0;i<10;i++)
////			System.out.println(class_psbl[i]);
//		for(int i=0;i<1/*Fij.class_num*/;i++){
//			System.out.println("for class"+i);
//			for(int j=0;j<Fij.dim;j++){
//				for(int k=0;k<Fij.dim;k++)
//					System.out.print(Fij.value[1][j][k][i]+" ");
////					if(Fij.value[1][j][k][i]<0.00001){
////						System.out.print(" ");
////					}
////					else {
////						System.out.print("+");
////					}
//				System.out.println();
//			}
//		}
		
		
		//Test tester = new Test("digitdata/testimages", "digitdata/testlabels");
		Test_g tester = new Test_g("digitdata/testimages", "digitdata/testlabels", 28, 10, 2, 2);
		tester.scan_and_test(Fij, class_psbl, 28, 2, 2);
		//if(!tester.scan_and_test(Fij, class_psbl))
			//System.out.println("some error during scan file for test.");
		tester.statistic();
		
	}

}
