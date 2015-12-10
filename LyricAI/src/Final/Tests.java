package Final;

import java.io.IOException;


//System.out.println("" + );
public class Tests {
	
	static FinalCrossValidator f; 

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		f = new FinalCrossValidator(10);
		testPermute();
	}

	public static void testPermute(){
		System.out.println("TOTAL: " + f.allData.length );
		for(int i = 0; i < f.folds; i ++){
			f.permuteTestTrain(i);
			System.out.println("SIZE " + f.foldSize);
			System.out.println("IDX " + f.testIdx + "\n");
		}
		
	}

}
