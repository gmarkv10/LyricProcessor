package Final;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
		allData = q.songsANDartists();
		
		foldSize = allData.length/folds;
		
		writer = new BufferedWriter(new FileWriter("Data/results.csv"));
	}
	
	public void permuteTestTrain(int perm){
		testIdx = foldSize * perm;
/*		for(int i = 0; i < allData.length; i++){
			if(i >= testIdxSTART && i < testIdxEND){
				test[i - testIdxSTART] = allData[i];
			}
			else{
				train[trainIdx] = allData[i];
				trainIdx++;
			}
		}*/
		
	}
	
	public void populateMap(){
		
	}
	
	public void crossValidate() throws Exception{
		for(int i  = 0; i < folds; i++){
			permuteTestTrain(i);
			train(i);
			test(i);
		}
		writer.close();
	}
	
	public void test(int i){
		
	}
	
	public void train(int i){
		
	}
	
	public double getWeight(double stdDev, int freq){
		if(stdDev < 3.29){ //average is 3.29
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
