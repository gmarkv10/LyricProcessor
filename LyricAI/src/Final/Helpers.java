package Final;

import java.util.ArrayList;
import java.util.Iterator;

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

}
