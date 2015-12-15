package Final;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Helpers {
	
	public static final int FOLDS = 10;
	
	public String processWord(String s){
		s = s.replaceAll("[^A-Za-z0-9 ]", "");
		return s.toLowerCase();
	}

	
	public double getWeight(double stdDev, int freq){
		if(stdDev < 3.29 && freq > 1){ //average is 3.29, if a word only appears in one songs, its useless to test on.
			//System.out.println(Math.ceil(3.29-stdDev) + ","+Math.log10(freq)/Math.log10(2));
			return Math.ceil(3.29 - stdDev) * Math.log10(freq)/Math.log10(2);
			
		}
		else return 0;
	}
	
	
	public double getStdDev(int avg, ArrayList<Integer> pop){
		int variance = 0;
		Iterator yr = pop.iterator();
		while(yr.hasNext()){
			Integer v = (Integer) yr.next();
			variance += ((int)v - (int) avg)*((int)v - (int) avg);
		}
		return Math.sqrt(variance/(double) pop.size());
	}
	
	public Object[] getStatsFromWeekCount(ArrayList<weekCount> list){
		int minYear   = 2017;
		int maxYear   = 1979;
		int avgYear   = 0;
		int freq      = 0;
		double stdDev = -1.0; 
		ArrayList<Integer> years =  new ArrayList<Integer>();
		//return order:
		/**
		 * [0]:minYear
		 * [1]:maxYear
		 * [2]:avgYear
		 * [3]:frequency
		 * [4]:standard deviation
		 * [5]:top year
		 * [6]:2nd top year
		 * [7]:3rd top year
		 * [8]:4th top year
		 * [9]:5th top year
		 */
		for(weekCount data: list){
			for(int i = 0; i < data.count; i ++){
				int year = extractYear(data.week);
				if(year > maxYear) maxYear = year;
				if(year < minYear) minYear = year;
				avgYear += year;
				years.add(year); //for stdDev
				freq++;
			}
		}
		avgYear = avgYear/freq;
		stdDev = getStdDev(avgYear, years);
		int[] topYears = top5Years(years);
		return new Object[]{minYear, maxYear, avgYear, freq, stdDev, topYears[0], topYears[1], topYears[2], topYears[3], topYears[4]};
	}
	
	public int[] top5Years(ArrayList<Integer> yrs){
		int[] yrfrq = new int[5];
		Integer mode = 0;
		Integer max = 0;
		for(int i  = 0; i < 5; i++){
			if(mode(yrs) < 0){
				yrfrq[i] = max;//give any remaining weights to the max, it deserves it
				continue;
			}
			mode = mode(yrs);
			if(i == 0) max = mode;
			yrfrq[i] = mode;
			while(yrs.remove(mode));
		}
		
		return yrfrq;
	}
	
	public Integer mode(ArrayList<Integer> yrs){
		if(yrs.isEmpty()) return -1;
		int[]  spread = new int[36];
		Iterator<Integer> it = yrs.iterator();
		while(it.hasNext()){
			spread[it.next() - 1980]++;
		}
		
		return maxIdx(spread) + 1980;
		
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
	
	public int maxIdx(int[]  years){
		int max = 0;
		double maxVal = -1;
		for(int i = 0; i < years.length; i++){
			if(years[i] > maxVal){
				max = i;
				maxVal = years[i];
			}
		}
		return max;
	}
	
	
	public static int extractYear(String s){
		String y = s.substring(0,4);
		return Integer.parseInt(y);
	}
	
	public Set<String> findUniqueWords(String lyrics){
		Set<String> uniques = new HashSet<String>();
		//removes non-alphanumeric characters, judgement call
		
		String[] words = lyrics.split(" ");
		for (String word : words){
			word = processWord(word);
			uniques.add(word);
		}
		return uniques;
	}


	public static void trimUnpredictedYears(int coYear, double[] score) {
		// TODO Auto-generated method stub
		double HALF = 0.5;
		double DELTA = HALF/(coYear - 1980);
		for(int i = 0; i < score.length; i ++){
			if(i + 1980 < coYear){
				score[i] = score[i]*(HALF + i*DELTA);
				
			}
		}
		
	}

}
