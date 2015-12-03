import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class LyricAIDriver {
	File file =  new File(".");
	static List<WordPrecision> hMap = new ArrayList<WordPrecision>(); 
	

	public static void main(String[] args) {

				try {
			loadData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	
	public static void loadData() throws IOException{
		System.out.println("Loading data...");
		File file = new File("."); 
		File dataFile = new File(file.getCanonicalPath()+"/Data/timeData.txt");
		BufferedReader br = new BufferedReader(new FileReader(dataFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter( new File(file.getCanonicalPath()+"/Data/globalFreq_StdDev_weights3.txt")));
		String sCurrentLine;
		String curLyric;
		int count = 0;
		int avgYear;
		double std;
		int freq;
		ArrayList<Integer> years;
		while ((sCurrentLine = br.readLine()) != null) {

			String[] words = sCurrentLine.split(",");
		
			curLyric = words[0];
			avgYear = 0;
			std = 0.0;
			freq  = 0;
			years = new ArrayList<Integer>();
			int avgDenom = 0;
			for (int i=1;i<words.length-1;i+=2){
				for(int j = 0; j < Integer.parseInt(words[i+1]); j++){
					int year = extractYear(words[i]);
					freq++;
					avgYear += year;
					years.add(year);
				}
				
			}
			avgYear = avgYear/freq;
			double stdDev = getStdDev(avgYear, years);
			hMap.add(new WordPrecision(curLyric, avgYear, stdDev, freq));
		}
		Collections.sort(hMap);
		Iterator<WordPrecision> i = hMap.listIterator();
		while(i.hasNext()){
			writer.write(i.next().toString());
			writer.newLine();
		}
		System.out.println("done");
		writer.close();
		br.close();
		
	}
	
	static int extractYear(String s){
		String y = s.substring(0,4);
		return Integer.parseInt(y);
	}
	
	public static double getStdDev(double avg, ArrayList<Integer> pop){
		int variance = 0;
		Iterator yr = pop.iterator();
		while(yr.hasNext()){
			Integer v = (Integer) yr.next();
			variance += ((int)v - (int) avg)*((int)v - (int) avg);
		}
		return Math.sqrt(variance/(double) pop.size());
	}
	public static class WordPrecision implements Comparable<WordPrecision>{
		String word;
		int avgYear;
		double stdDev;
		int freq;
		
		double freqScore;
		double devScore;
		
		public WordPrecision(String s, int aYear, double sd, int f){
			word = s;
			avgYear = aYear;
			stdDev = sd;
			freq = f;
			
			freqScore = f;
			devScore = sd;

		}
		@Override
		public String toString(){
			return word+"," + avgYear + "," + stdDev + "," + freq ;
		}

		@Override
		public int compareTo(WordPrecision o) {
			// TODO Auto-generated method stub
			this.weightScores();
			o.weightScores();
			
			if(this.freqScore/this.devScore == o.freqScore/o.devScore) return 0;
			if(this.freqScore/this.devScore > o.freqScore/o.devScore) return 1;
			else return -1;
		}
		
		private void weightScores(){
			if(devScore > 3.7 && devScore < 7.2 && freqScore > 650 && freq < 10000){
				devScore -= devScore*0.01; //shave off quite a bit of denominator
				freqScore *= 130.0; //90,000, the highest freq, divided by 650
			}
			
//			if(freqScore > 650 && freq < 10000){
//				freqScore *= 130.0; //90,000, the highest freq, divided by 650
//			}
		}
		
		
	}
	


}


