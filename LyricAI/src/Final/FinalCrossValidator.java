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
		allData = q.songsANDartists();
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
		String waste = reader.readLine(); //waste the first line, it has the column labels
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
		System.out.println("Testing fold: " + fold);
		permuteTestTrain(fold);
		if(fold == 0){
			writer.write("Song,Actual,TF-IDF,TF-IDF Error,Custom,Custom Err, ,1980"); writer.newLine();
		}
		for(int i = 0; i < test.length; i ++){
			int year = h.extractYear(q.bestWeek(test[i][0], test[i][1]));
			//if(year < 1990 ) continue;
			localWordStats =  new HashMap<String, Integer>();
			
			//find unique words and their frequencies
			String lyric = q.getLyrics(test[i][0], test[i][1]);
			
			
			String[] words = lyric.split(" ");
			for(String word : words){
				word = h.processWord(word);
				Integer localFreq = localWordStats.get(word);
				localWordStats.put(word, (localFreq == null ? 1 : localFreq++));
			}
			
			double[] customYearScore = new double[36];
			double[] tfidfYearScore  = new double[36];
			
			//compute
			Iterator<String> it = localWordStats.keySet().iterator();
			while(it.hasNext()){
				String s = it.next();
				WordStats current = globalWordStats.get(s);
				if(current == null) continue; //the word is in the test data only, so we can't reason about it.
				
				double bowScore = getTFIDF(s); //Bag of Words style word importance score
				double custScore = h.getWeight(current.stdDev, current.freq);
				
				customYearScore[current.avgYear - 1980] += custScore;
				tfidfYearScore[current.avgYear - 1980]  += bowScore;
				
				
			}
			String song = test[i][0].replaceAll(",", "");
			
			int bowPrediction = h.maxIdx(tfidfYearScore) + 1980;
			int custPrediction = h.maxIdx(customYearScore) + 1980;
			
			writer.write(song +","+ year  +","+ bowPrediction  +","+ Math.abs(year-bowPrediction)  +","+ custPrediction  +","+ Math.abs(year- custPrediction) + ", ,");
			for(int j =0; j < tfidfYearScore.length; j++){
				writer.write(tfidfYearScore[j] + ",");
			}
			writer.newLine();
		}
		
	}
	
	
	//Weight method for a BagOfWords classifier
	//Tells how important a word is in a document (song) in the context of a corpus (top50 data)
	//Increases proportionally to freq in song but offset by global usage in top50 data
	public double getTFIDF(String word) throws Exception{
		//from wikipedia
		double tf = 1 + Math.log(localWordStats.get(word) + 0.0);
		double N = train.length + 0.0; // the number of songs being considered
		double idf  = Math.log(1 + (N/globalWordStats.get(word).useage));
		return tf*idf;

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
