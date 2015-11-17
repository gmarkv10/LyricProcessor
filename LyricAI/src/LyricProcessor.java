import java.io.BufferedReader;
import java.io.IOException;

public abstract class LyricProcessor {
	
	BufferedReader reader;
	
	abstract String getCurrentPath();
	
	public String processWord(String s){
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
