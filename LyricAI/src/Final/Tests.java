package Final;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;


//System.out.println("" + );
public class Tests {
	
	private static final int FOLDS = 10;
	
	static FinalCrossValidator f; 
	static LyricManager lm;
	static Helpers h;
	public static void main(String[] args) throws Exception {
		
		//f = FinalCrossValidator.getInstance(FOLDS);
		h = new Helpers();
		lm = new LyricManager(FOLDS);
		
			//dummy test data
			String week1 = "1111-22-22";
			String week2 = "2222-22-22";
			String week3 = "2222-22-22";
			String week4 = "3333-22-22";
			String week5 = "1111-22-22";
			String week6 = "1111-22-22";
			String[] testArrayOfWeeks = {week1,week2,week3,week4,week5,week6};
			ArrayList <String> testListOfWeeks = new ArrayList();
			for (String week : testArrayOfWeeks){testListOfWeeks.add(week);}
		
		
		//testPermute();
		//testPermute2();
		
		//testFindUniques("hey hey wow tony wow hey pie");
		//testSongAndArtistInitialized();
		//testConvertWeeklyList(testListOfWeeks);
		//testDataPopulation();
			
		//testTrainingFileCreation();
		//testTrainMethod();
		//testTestMethod();
		
		//System.out.println("Start");
		//testTheWholeThing();
		//System.out.println("End");
		
		testYearTrimming();
		
	}
	
	public static void testYearTrimming(){
		double[] years = { 14.95909917	,19.94588077,	4.987719424,	10.26376022,	18.31762648,	26.45523182	,12.57649239	,12.76869294	,11.30506516	,19.15339982	,23.34284155	,62.41673665	,34.59338455	,66.66129308	,30.65214471	,37.09059752	,30.52744331	,57.27208979	,112.0896694	,34.66926514	,26.87538941	,24.34436358	,88.23147506	,131.4526746	,108.1960826	,117.4083488	,173.0807941	,93.96149518	,48.01145424	,50.95916363	,62.29558492	,63.90592797	,36.02213118,	49.04397273,	55.18882531,	22.75228472 };
		double[] tyears = { 14.95909917	,19.94588077,	4.987719424,	10.26376022,	18.31762648,	26.45523182	,12.57649239	,12.76869294	,11.30506516	,19.15339982	,23.34284155	,62.41673665	,34.59338455	,66.66129308	,30.65214471	,37.09059752	,30.52744331	,57.27208979	,112.0896694	,34.66926514	,26.87538941	,24.34436358	,88.23147506	,131.4526746	,108.1960826	,117.4083488	,173.0807941	,93.96149518	,48.01145424	,50.95916363	,62.29558492	,63.90592797	,36.02213118,	49.04397273,	55.18882531,	22.75228472 };
		Helpers.trimUnpredictedYears(1990, years);
		for(int i = 0; i < years.length;i++){
			System.out.println(years[i] + "," + tyears[i]);
		}		
	}
	//tests populateLyricWeeks() and populateProcessedWeeklyCounts();
	public static void testDataPopulation() throws Exception{
		LyricManager testLM = new LyricManager(10); 
		//step 1 gets all the raw lyric/week data
		testLM.populateLyricWeeks();
		int lwAfterPop = testLM.lyricWeeks.size();
		//step 2 converts the "raw" data to lyric/weekCounts 
		testLM.populateProcecessedWeeklyCount();
		
		System.out.println("lyricWeeks size should be over 30,000 before processing, actual size: "+lwAfterPop);
		System.out.println("lyricWeeks size should be 0 after processing, actual size: "+testLM.lyricWeeks.size());
		System.out.println("ProcessedWeekCount size should be over 30,0000 after processing, actual size: "+testLM.processedLyricWeeks.size());
	}
	
	//PASSING
	public static void testConvertWeeklyList(ArrayList<String> listOfWeeks){
		System.out.println("INPUT LIST:");
		for (String week : listOfWeeks){
			System.out.print(week+", ");
		}
		System.out.println("\nOUTPUT weekCount LIST:");
		ArrayList<weekCount> convertedListOfWeeks = lm.convertWeekList(listOfWeeks);
		for (weekCount wc : convertedListOfWeeks){
			System.out.print("("+wc.week+", "+wc.count+"), ");
		}
	}
	//PASSING findUniques working properly
	public static void testFindUniques(String s){
		Set<String> uniques = h.findUniqueWords(s);
		System.out.println("found "+uniques.size()+" unique words in string:");
		for (String unique : uniques){
			System.out.print(unique+" ");
		}
		System.out.println("\n");
	}
	//PASSING - songsAndArtists[][] properly initialized 
	public static void testSongAndArtistInitialized(){
		if (lm.songsAndArtists == null){
			System.out.println("songsAndArtists == null");
			return;
		}
		else if (lm.songsAndArtists.length == 7801){
			System.out.println("songsAndArtists[][] initialized with "+lm.songsAndArtists.length + " songs");
			return;
		}
		else{
			System.out.println("songsAndArtists of unexpected size: " + lm.songsAndArtists.length + " songs");
		}
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
	
	//PASSING
	public static void testTrainingFileCreation() throws Exception{
		lm.makeFoldFile(1);
		System.out.println("Training file in Data/trainingData1.csv exists: " + new File("Data/trainingData1.csv").exists());
	}
	
	public static void testTrainMethod() throws Exception{
		f.train(4);
		int highfrq = f.globalWordStats.get("the").freq;
		int lowfrq = f.globalWordStats.get("champion").freq;
		System.out.println("Word 'the' occurs: " + highfrq + " times, should be: " + 1033);
		System.out.println("Word 'champion' occurs:" + lowfrq+ " times, should be: " + 1 );
	}
	
	public static void testTestMethod() throws Exception {
		f.test(4,1990);
	}
	
	public static void testTheWholeThing() throws Exception {
		f.crossValidate(1990);
	}
	
	public static void testYearCountClass(){
		ArrayList<Integer> yrs = new ArrayList<Integer>();
		yrs.add(1982); yrs.add(1982); yrs.add(1982); yrs.add(1981); yrs.add(1981); yrs.add(1980);
		int[] res = h.top5Years(yrs);
		for(int j =0;j<5;j++){
			System.out.println(res[j]);
		}
		
	}
	

}
