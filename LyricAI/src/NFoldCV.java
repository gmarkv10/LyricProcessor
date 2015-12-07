
public class NFoldCV {

	int folds = 0;
	int foldSize = 0;
	int permutation = 0;
	String[][] allData;
	String[][] test;
	String[][] train;
	queries  q;
	
	public NFoldCV(int folds) throws ClassNotFoundException{
		this.folds = folds;
		q = new queries();
		allData = q.songANDartistANDbestweekANDbestrank();
		System.out.print("DB Query Completed Successfully.");
		foldSize = allData.length/folds;
		test = new String[foldSize][4];
		train =  new String[allData.length - foldSize][4];
	}
	
	public void permuteTestTrain(int perm){
		int trainIdx = 0;
		int testIdxSTART = perm*foldSize;
		int testIdxEND   = testIdxSTART + foldSize;
		
		for(int i = 0; i < allData.length; i++){
			if(i >= testIdxSTART && i < testIdxEND) test[i - testIdxSTART] = allData[i];
			else train[trainIdx++ ] = allData[i];
		}
		
	}
	
	public void crossValidate(){
		for(int i  = 0; i < folds; i++){
			permuteTestTrain(i);
			System.out.println("First train:" + train[0][0]);
			System.out.println("Last train:" + train[train.length-1][0]);
			System.out.println("First test:" + test[0][0]);
			System.out.println("Last test:" + test[test.length -1][0] + "\n ------ \n");
		}
	}
	
	
	
	

}
