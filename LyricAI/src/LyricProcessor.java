
public abstract class LyricProcessor {
	
	abstract String getCurrentPath();
	
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
