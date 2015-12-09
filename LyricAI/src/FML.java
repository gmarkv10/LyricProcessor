import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FML {
	
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
	//double threshhold = 0.0;
	String[][] allData;
	String[][] test;
	String[][] train;
	queries  q;
	BufferedReader reader;
	BufferedWriter writer;
	HashMap hMap = new HashMap<String, YearWeight>();
	
	public FML(int folds) throws ClassNotFoundException{
		this.folds = folds;
		//this.threshhold = thresh;
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
			reader = new BufferedReader(new FileReader(dataFile));
			dataFile = new File(file.getCanonicalPath()+"/Data/predictionSpread.csv");
			writer = new BufferedWriter(new FileWriter(dataFile));
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
		if(stdDev < 3.29){ //average is 3.29
			System.out.println(Math.ceil(3.29-stdDev) + ","+Math.log10(freq)/Math.log10(2));
			return Math.ceil(3.29 - stdDev) * Math.log10(freq)/Math.log10(2);
			
		}
		else return 0;
	}
	
	public void train() throws Exception{
		String line;
		String[] data;
		while((line = reader.readLine()) != null){
			data = line.split(",");
			String word = data[0]; 
			int year =      Integer.parseInt(data[1]);
			double stdDev = Double.parseDouble(data[2]);
			int freq =      Integer.parseInt(data[3]);
			
			Double weight = getWeight(stdDev, freq );
			
			if(weight > 0.0){
				hMap.put(word, new YearWeight(year, weight));
			}			
		}
		System.out.println("\nTrained.");
		
	}
	
	public void test() throws ClassNotFoundException, IOException{
		int prediction = -1;
		int actual = -1;
		double[] yearScore = null;
		writer.write("prediction,actual,diff,predSCore,actScore"); writer.newLine();
		for (int i=0;i<allData.length;i++){
			
			String[] words = q.getLyrics(allData[i][0], allData[i][1]).split(" ");
			
			yearScore =  new double[36];  //array representing years
			YearWeight info;
			for(String word : words){
				info = (YearWeight) hMap.get(word);
				if(info != null) yearScore[info.year - 1980] += info.weight;	
			}
			
			prediction = maxIdx(yearScore) + 1980;
			actual = Integer.parseInt(allData[i][2].substring(0, 4));
			writer.write(prediction + "," + actual +"," + Math.abs(prediction - actual)
			                +"," + yearScore[prediction - 1980] + "," +yearScore[actual - 1980]);
			writer.newLine();
		}
		System.out.println("Tested.");
		writer.close();
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
	
	public int maxIdx(double[]  years){
		int max = 0;
		double maxVal = -1.0;
		for(int i = 0; i < years.length; i++){
			if(years[i] > maxVal){
				max = i;
				maxVal = years[i];
			}
		}
		return max;
	}
	
	
	
	
	

}