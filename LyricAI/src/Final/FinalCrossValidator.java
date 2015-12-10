package Final;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FinalCrossValidator {
	
	int folds = 0;
	Helpers h;
	
	BufferedWriter writer;
	BufferedReader reader;
	
	public FinalCrossValidator(int folds) throws IOException{
		this.folds = folds;
		this.h = new Helpers();
		
		writer = new BufferedWriter(new FileWriter("Data/results.csv"));
	}
	
	public void permuteTestTrain(int perm){
		
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
