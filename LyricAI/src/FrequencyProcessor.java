import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FrequencyProcessor implements LyricProcessor {

	private String filename;
	private File   file;
	private BufferedReader reader;
	private BufferedWriter writer;
	MyMap<String, Integer> frqMap;
	String word = null;
	CircList cList;
	int window = 0;

	public FrequencyProcessor(String f){
		file = new File(".");
		this.filename = f;
		window = 1;
		word = "";  //fake instantiation to match window > 1 
		try {
			reader = new BufferedReader(new FileReader(file.getCanonicalPath() + "\\" + f + ".lyr"));
			writer = new BufferedWriter(new FileWriter(file.getCanonicalPath() + "\\" + f + window + ".json"));
			frqMap = new MyMap<String, Integer>();
		} catch (Exception e) {
			System.err.println("File unable to be processed.");
			reader = null;
		}
	}
	
	public FrequencyProcessor(String f, int length){
		file = new File(".");
		this.filename = f;
		this.window = length;
		word = "";
		cList = new CircList(length);
		try {
			reader = new BufferedReader(new FileReader(file.getCanonicalPath() + "\\" + f + ".lyr"));
			writer = new BufferedWriter(new FileWriter(file.getCanonicalPath() + "\\" + f + window + ".json"));
			frqMap = new MyMap<String, Integer>();
		} catch (Exception e) {
			System.err.println("File unable to be processed.");
			reader = null;
		}
	}
	
	//Build out to only extract useful parts of words
	static String processWord(String s){
		return s.toLowerCase();
	}

	public String readWords(){
		try {
			return reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("EXCEPTION while reading file");
			return "";
		}
	}

	@Override
	public String getCurrentPath() {
		// TODO Auto-generated method stub
		try {
			return file.getCanonicalPath()+ "\\" + filename;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "NOT FOUND";
		}
	}

	public boolean processLine(){
		String line = readWords();
		if(line == null) return false;
		else{
			String[] words = line.split(" ");
			String word;
			String phrase; //for if we're using a CircList
			for(int i = 0; i < words.length; i++){
				word = FrequencyProcessor.processWord(words[i]);
				if(window > 1){ //then we're using a CircList
					cList.insert(word);
					phrase = cList.getPhrase();
					Integer freq = frqMap.get(phrase);
					frqMap.put(phrase, (freq == null) ? 1 : freq + 1);
				}
				else{
					Integer freq = frqMap.get(word);
					frqMap.put(word, (freq == null) ? 1 : freq + 1);
				}
			}
			return true;
		}
	}
	
	@Override //TODO: DEPRECATED, implementing in processLine() for now
	public void processLines() {
		// TODO Auto-generated method stub
		int lin = 0;
		while( processLine() ){
			lin++;
		}
		System.out.println(lin + " lines processed" );
	}
	
	
	
	public void exportJSON(){
		PrintWriter pw = new PrintWriter(writer);
		pw.println(frqMap.toJSON(window));
		pw.close();	
	}
	
	public void close(){
		
		try {
			reader.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("ERR on close");
		}
	}

}
