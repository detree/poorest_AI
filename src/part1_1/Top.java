package part1_1;

public class Top {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Train trainer = new Train("digitdata/trainingimages", "digitdata/traininglabels");
		trainer.set_para(1, 28, 10);
		if(!trainer.scan_file())
			System.out.println("some error during scan file.");
		LikelyMatrix Fij = new LikelyMatrix(28,10);
		trainer.calculate_likely(Fij);
		
		for(int i=0;i<Fij.class_num;i++){
			System.out.println("for class"+i);
			for(int j=0;j<Fij.dim;j++){
				for(int k=0;k<Fij.dim;k++)
					System.out.print(Fij.value[j][k][i]+" ");
				System.out.println();
			}
		}
	}

}
