import java.io.BufferedReader;
import java.io.IOException;

public abstract class LyricProcessor {
	
	abstract String getCurrentPath();
	
	public String processWord(String s){
		
		if(s.charAt(s.length() - 1) == ','  ||
		   s.charAt(s.length() - 1) == '\'' ||
		   s.charAt(s.length() - 1) == '\n' ||
		   s.charAt(s.length() - 1) == '.'  ||
		   s.charAt(s.length() - 1) == ')'){
			s = s.substring(0, s.length() - 1);
			s = processWord(s);
		}
		return s.toLowerCase();
	}
	
	abstract boolean processLine();
	
	public void processLines(){
		// TODO Auto-generated method stub
		int lin = 0;
		while( processLine() ){
			lin++;
		}
		System.out.println(lin + " lines processed" );
	}
	
	

}
