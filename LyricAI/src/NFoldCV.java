import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class NFoldCV {
	
	//For each fold:
	//  +make filter words from the TRAINing songs
	//    -assign a weight to the year that is a function of stdDec and freq
	//    -if that weight is above a certain threshold:
	//      put lyric in a map <word, year with weight>
	//  +TEST the rest of the data
	//    -assign a year by adding the weights of any words that made
	//    -didn't get pruned to an array with an entry for every year
	//    -and finding the max
	//    -compare that year to the actual year.  

	int folds = 0;
	int foldSize = 0;
	int permutation = 0;
	String[][] allData;
	String[][] test;
	String[][] train;
	queries  q;
	BufferedReader reader;
	HashMap hMap = new HashMap<String, YearWeight>();
	
	public NFoldCV(int folds) throws ClassNotFoundException{
		this.folds = folds;
		q = new queries();
		allData = q.songANDartistANDbestweekANDbestrank();
		System.out.print("DB Query Completed Successfully.");
		foldSize = allData.length/folds;
		test = new String[foldSize][4];
		train =  new String[allData.length - foldSize][4];
		
		File file = new File("."); 
		File dataFile;
		try {
			dataFile = new File(file.getCanonicalPath()+"/Data/globalFreq_StdDev.csv");
			BufferedReader reader = new BufferedReader(new FileReader(dataFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void arrangeData(){
		
	}
	
	public void permuteTestTrain(int perm){
		System.out.println("Starting permution " + perm);
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
		}
	}
	
	public double getWeight(double stdDev, int freq){
		return freq/stdDev;
	}
	
	public void train() throws ClassNotFoundException{
		
		for (int i=0;i<train.length;i++){ 
		}
		
	}
	
	public void test() throws ClassNotFoundException{
		for (int i=0;i<test.length;i++){
			String[] words = q.getLyrics(train[i][0], train[i][1]).split(" ");
		}
		
	}
	
	class YearWeight{
		int year;
		double weight;
		
		public YearWeight(int y, double w){
			year = y;
			weight = w;
		}
	}
	
	public String processWord(String s){
		
		try{

			if(s.charAt(0) == '\"' || s.charAt(0) == '('){
				s = s.substring(1);
				s = processWord(s);
			}

			if(s.charAt(s.length() - 1) == ','  ||
					s.charAt(s.length() - 1) == '\'' ||
					s.charAt(s.length() - 1) == '\n' ||
					s.charAt(s.length() - 1) == '.'  ||
					s.charAt(s.length() - 1) == '\"'  ||
					s.charAt(s.length() - 1) == ')')
			{
				s = s.substring(0, s.length() - 1);
				s = processWord(s);
			}
			return s.toLowerCase();
		}catch(StringIndexOutOfBoundsException e){
			return s;
		}
	}
	
	
	
	
	

}
