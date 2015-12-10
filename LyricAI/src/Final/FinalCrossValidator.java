package Final;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FinalCrossValidator {
	
	int folds = 0;
	Helpers h;
	String[][] allData;
	String[][] test;
	String[][] train;
	int testIdx = 0;
	int foldSize = 0;
	
	queries q;
	
	BufferedWriter writer;
	BufferedReader reader;
	
	public FinalCrossValidator(int folds) throws Exception{
		this.folds = folds;
		this.h = new Helpers();
		
		q = new queries();
		allData = q.get100songsANDartists();
		foldSize = allData.length/folds;
		test = new String[foldSize][2];
		//System.out.println(allData.length - foldSize);
		train =  new String[allData.length - foldSize][2];
//		System.out.println("ALL: " + allData.length);
//		System.out.println("TRAIN: " + train.length);
//		System.out.println("TEST: " + test.length);
		
		
		
		writer = new BufferedWriter(new FileWriter("Data/results.csv"));
	}
	
	public void permuteTestTrain(int fold) throws Exception{
		testIdx = foldSize * fold;
		int trainIdx = 0;
		for(int i = 0; i < allData.length; i++){
			if(i >= testIdx && i < testIdx + foldSize) test[i-testIdx] = allData[i];
			else train[trainIdx++] = allData[i];
		}
		
		reader = new BufferedReader(new FileReader("Data/trainingData"+fold+".csv"));
		
	}
	
	public void populateMap(int fold){
		
	}
	
	public void crossValidate() throws Exception{
		for(int i  = 0; i < folds; i++){
			permuteTestTrain(i);
			train();
			test();
		}
		writer.close();
	}
	
	public void train(){
		
	}
	
	public void test(){
		
	}
	

	
	public double getWeight(double stdDev, int freq){
		if(stdDev < 3.29 && freq > 1){ //average is 3.29, if a word only appears in one songs, its useless to test on.
			//System.out.println(Math.ceil(3.29-stdDev) + ","+Math.log10(freq)/Math.log10(2));
			return Math.ceil(3.29 - stdDev) * Math.log10(freq)/Math.log10(2);
			
		}
		else return 0;
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
