//Gabe was here
//So too should Tony
public class CircList {
	
	int ptr = 0;
	String[] words;
	int size = 0;
	boolean isFull = false;
	
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
		if(ptr == 0){
			isFull = true;
		}
	}
	
	public void insert(String s){
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
