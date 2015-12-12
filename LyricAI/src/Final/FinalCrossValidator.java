package Final;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class FinalCrossValidator {
	
	private static FinalCrossValidator instance;
	static int folds = 0;
	Helpers h;
	String[][] allData;
	String[][] test;
	String[][] train;
	int testIdx = 0;
	int foldSize = 0;
	
	HashMap<String, WordStats> globalWordStats;
	HashMap<String, Integer> localWordStats;
	
	queries q;
	
	BufferedWriter writer;
	BufferedReader reader;
	
	private FinalCrossValidator(int folds) throws Exception{
		this.folds = folds;
		this.h     = new Helpers();
		
		q = new queries();
		allData = q.get100songsANDartists();
		foldSize = allData.length/folds;
		test = new String[foldSize][2];
		train =  new String[allData.length - foldSize][2];
		
		writer = new BufferedWriter(new FileWriter("Data/results.csv"));
	}
	
	public static FinalCrossValidator getInstance(int folds) throws Exception{
		if(instance == null || folds != FinalCrossValidator.folds){
			instance = new FinalCrossValidator(folds);
		}
		return instance;
	}
	
	
	
	public void permuteTestTrain(int fold) {
		testIdx = foldSize * fold;
		int trainIdx = 0;
		for(int i = 0; i < allData.length; i++){
			if(i >= testIdx && i < testIdx + foldSize) test[i-testIdx] = allData[i];
			else train[trainIdx++] = allData[i];
		}
		try{
			reader = new BufferedReader(new FileReader("Data/trainingData"+fold+".csv"));
		} catch(FileNotFoundException e){
			System.out.println("Please run makeFoldFiles and try again");
		}

	}
	
	public void populateMap(int fold){
		
		
	}
	
	public void crossValidate() throws Exception{
		for(int i  = 0; i < folds; i++){
			train(i);
			test(i);
		}
		writer.close();
	}
	
	public void train(int fold) throws Exception{ //reads the appropriate FILES* and populate the global statistics of each word
		String line;
		String[] data;
		int min, max, avg, freq, usage;
		double sd;
		
		globalWordStats = new HashMap<String, WordStats>();
		reader =  new BufferedReader(new FileReader("Data/trainingData"+fold+".csv"));
		System.out.println(reader.readLine()); //waste the first line, it has the column labels
		while((line = reader.readLine()) != null){
			data = line.split(",");
			min  = Integer.parseInt(data[1]);
			max  = Integer.parseInt(data[2]);
			avg  = Integer.parseInt(data[3]);
			freq = Integer.parseInt(data[4]);
			sd   = Double.parseDouble(data[5]);
			usage = Integer.parseInt(data[6]);
			
			globalWordStats.put(data[0], new WordStats(min, max, avg, freq, sd, usage));
			
		}
		reader.close();
	}


	
	public void test(int fold) throws Exception{
		System.out.println("");
		permuteTestTrain(fold);
		writer.write("Song,Actual,TF-IDF,Custom");
		//for(int i = 0; i < test.length; i ++){
			localWordStats =  new HashMap<String, Integer>();
			
			//find unique words and their frequencies
			//String lyric = q.getLyrics(test[i][0], test[i][1]);
			String lyric = q.getLyrics(test[0][0], test[0][1]);
			lyric = lyric.replaceAll("[^A-Za-z0-9 ]", "");
			String[] words = lyric.split(" ");
			for(String word : words){
				word = h.processWord(word);
				Integer localFreq = localWordStats.get(word);
				localWordStats.put(word, (localFreq == null ? 1 : localFreq++));
			}
			
			int[] customYearScore = new int[36];
			int[] tfidfYearScore  = new int[36];
			
			//compute
			Iterator<String> it = localWordStats.keySet().iterator();
			while(it.hasNext()){
				String s = it.next();
				writer.write(s + "," + getTFIDF(s));
				writer.newLine();
			}
		//}
		writer.close();
	}
	
	
	//Weight method for a BagOfWords classifier
	//Tells how important a word is in a document (song) in the context of a corpus (top50 data)
	//Increases proportionally to freq in song but offset by global usage in top50 data
	public double getTFIDF(String word) throws Exception{
		
		double globalUse = globalWordStats.get(word).freq + 0.0;
		double localUse  = localWordStats.get(word) + 0.0;
		return localUse/globalUse;
	}

	
	class WordStats{
		int minYear   = 0;
		int maxYear   = 0;
		int avgYear   = 0;
		int freq      = 0;
		double stdDev = 0.0;
		int useage = 0;
		
		public WordStats(int min, int max, int avg, int f, double sd, int use){
			minYear = min;
			maxYear = max;
			avgYear = avg;
			freq = f;
			stdDev = sd;
			useage = use;
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


}
