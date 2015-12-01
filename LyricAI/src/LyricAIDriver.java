import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;


public class LyricAIDriver {
	
	static MyMap<String, WordPrecision> hMap = new MyMap<String, WordPrecision>(WordPrecision.comp); 

	public static void main(String[] args) {
		// TODO Get There
		MapSetList<WordPrecision> m = new MapSetList<WordPrecision>(WordPrecision.comp);
		System.out.println(m.insert(new WordPrecision(2000, 1, 10)));
		//System.out.println(m.size);
		System.out.println(m.insert(new WordPrecision(2001, 2, 3)));
		//System.out.println(m.isSorted());
		System.out.println(m.insert(new WordPrecision(2002, 3, 5)));
		//System.out.println(m.isSorted());
		/*		try {
			loadData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		

	}
	
	public static void loadData() throws IOException{
		System.out.println("Loading data...");
		File file = new File("."); 
		File dataFile = new File(file.getCanonicalPath()+"/Data/timeData.txt");
		BufferedReader br = new BufferedReader(new FileReader(dataFile));
		String sCurrentLine;
		String curLyric;
		int count = 0;
		int avgYear;
		double std;
		int freq;
		ArrayList<Integer> years;
		int o = 0;
		while ((sCurrentLine = br.readLine()) != null) {
			o++;
			if(0 > 7) break;
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
			System.out.println("AVG: " + avgYear + " SD: " + stdDev + " FRQ: " + freq);
			hMap.put(curLyric, new WordPrecision(avgYear, stdDev, freq));
		}
		System.out.println(hMap.toJSON());
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
	public static class WordPrecision{
		int avgYear;
		double stdDev;
		int freq;
		static Comparator comp;
		
		public WordPrecision(int aYear, double sd, int f){
			avgYear = aYear;
			stdDev = sd;
			freq = f;
			comp = new Comparator<WordPrecision>(){
				@Override
				public int compare(WordPrecision o1, WordPrecision o2) {
					// TODO Auto-generated method stub
					return o1.freq - o2.freq;
				}
			};
		}
		@Override
		public String toString(){
			return "AVG: " + avgYear + " SD: " + stdDev + " FRQ: " + freq;
		}
		
		
		
	}
	


}


