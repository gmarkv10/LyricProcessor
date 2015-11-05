
public class CircList {
	
	int ptr = 0;
	String[] words;
	int size = 0;
	
	public CircList(int size){
		if(size < 1){
			System.err.println("LIST TOO SMALL");
			System.exit(-1);
		}
		else{
			this.size = size;
			words = new String[size];
		}
	}
	
	public void advance(){
		ptr = (ptr + 1) % size;
	}
	
	public void insert(String s){
		FrequencyProcessor.processWord(s);
		words[ptr] = s;
		advance();
	}
	
	public String getPhrase(){		
		String s = "";
		for(int i = 0; i < size; i++){
			s += words[ptr] + " "; 
			advance();
		}
		return s;
	}

}
