package Final;

import java.io.IOException;


//System.out.println("" + );
public class Tests {
	
	static FinalCrossValidator f; 

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		f = new FinalCrossValidator(10);
		testPermute2();
	}

	//PASSING - tests sizes throughout permutations
	public static void testPermute() throws Exception{
		System.out.println("TOTAL: " + f.allData.length );
		for(int i = 0; i < f.folds; i ++){
			f.permuteTestTrain(i);
			System.out.println("SIZE " + f.foldSize);
			System.out.println("IDX " + f.testIdx + "\n");
		}
		
	}
	
	//PASSING - test that the arrays are filled properly at the edges
	public static void testPermute2() throws Exception{
		f.permuteTestTrain(1);
		System.out.println("testI " + f.testIdx );
		System.out.println("train0 " + f.train[0][0]);
		System.out.println("trainN " + f.train[f.train.length -1][0]);
		System.out.println("test0 " + f.test[0][0]);
		System.out.println("testN " + f.test[f.test.length-1][0]);
	}

}
