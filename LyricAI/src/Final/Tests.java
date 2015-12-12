package Final;

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
		
		f = FinalCrossValidator.getInstance(FOLDS);
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
		
		//testFindUniques("hey hey wow tony wow hey pie");
		//testSongAndArtistInitialized();
		//testConvertWeeklyList(testListOfWeeks);
		//testDataPopulation();
			
		lm.makeFoldFile(1);
		
		
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

}
