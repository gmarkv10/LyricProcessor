package Final;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FinalCrossValidator {
	
	private static FinalCrossValidator instance;
	static int folds = 0;
	Helpers h;
	String[][] allData;
	String[][] test;
	String[][] train;
	int testIdx = 0;
	int foldSize = 0;
	
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
	

	
	
	
	class YearWeight{
		int year;
		double weight;
		
		public YearWeight(int y, double w){
			year = y;
			weight = w;
		}
	}


}
