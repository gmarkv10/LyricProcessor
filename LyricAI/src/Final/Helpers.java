package Final;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Helpers {
	

	
	public String processWord(String s){
		
		try{

			if(s.charAt(0) == '\"' || s.charAt(0) == '('){
				s = s.substring(1);
				s = processWord(s);
			}

			if(s.charAt(s.length() - 1) == ','  ||
					s.charAt(s.length() - 1) == '\'' ||
					s.charAt(s.length() - 1) == '\n' ||
					s.charAt(s.length() - 1) == '.'  ||
					s.charAt(s.length() - 1) == '\"'  ||
					s.charAt(s.length() - 1) == ')')
			{
				s = s.substring(0, s.length() - 1);
				s = processWord(s);
			}
			return s.toLowerCase();
		}catch(StringIndexOutOfBoundsException e){
			return s;
		}
	}
	
	//Weight method for a BagOfWords classifier
	//Tells how important a word is in a document (song) in the context of a corpus (top50 data)
	//Increases proportionally to freq in song but offset by global usage in top50 data
	public int getTFIDF(String word){
		
		return 0;
	}
	
	public double getWeight(double stdDev, int freq){
		if(stdDev < 3.29 && freq > 1){ //average is 3.29, if a word only appears in one songs, its useless to test on.
			//System.out.println(Math.ceil(3.29-stdDev) + ","+Math.log10(freq)/Math.log10(2));
			return Math.ceil(3.29 - stdDev) * Math.log10(freq)/Math.log10(2);
			
		}
		else return 0;
	}
	
	
	public double getStdDev(double avg, ArrayList<Integer> pop){
		int variance = 0;
		Iterator yr = pop.iterator();
		while(yr.hasNext()){
			Integer v = (Integer) yr.next();
			variance += ((int)v - (int) avg)*((int)v - (int) avg);
		}
		return Math.sqrt(variance/(double) pop.size());
	}
	
	public int maxIdx(double[]  years){
		int max = 0;
		double maxVal = -1.0;
		for(int i = 0; i < years.length; i++){
			if(years[i] > maxVal){
				max = i;
				maxVal = years[i];
			}
		}
		return max;
	}
	
	
	int extractYear(String s){
		String y = s.substring(0,4);
		return Integer.parseInt(y);
	}
	
	public Set<String> findUniqueWords(String lyrics){
		Set<String> uniques = new HashSet<String>();
		//removes non-alphanumeric characters, judgement call
		lyrics = lyrics.replaceAll("[^A-Za-z0-9 ]", "");
		String[] words = lyrics.split(" ");
		for (String word : words){
			word = word.toLowerCase();
			uniques.add(word);
		}
		return uniques;
	}

}