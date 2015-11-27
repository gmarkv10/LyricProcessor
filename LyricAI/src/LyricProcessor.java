import java.io.BufferedReader;
import java.io.IOException;

public abstract class LyricProcessor {
	

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
	
	abstract void resetLyric(String l);
	
	abstract int processLyric();
	
	//DEPRECATED AFTER Lyrics2005 Driver introduced with both our work.
	/*public void processLines(){
		// TODO Auto-generated method stub
		int lin = 0;
		while( processLine() ){
			lin++;
		}
		System.out.println(lin + " lines processed" );
	}*/
	
	

}
