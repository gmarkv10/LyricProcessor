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

//GABE WAS HERE
public class FrequencyProcessor extends LyricProcessor {

	MyMap<String, Integer> frqMap = null;
	String word = null;
	CircList cList;
	int window = 0;
	String lyric;
	String[] words;

	public FrequencyProcessor(){
		window = 1;
			//System.out.println(new File(file.getCanonicalPath() + "\\Lyrics\\" + f + ".lyr").exists());
			//reader = new BufferedReader(new FileReader(file.getCanonicalPath() + "\\Lyrics\\" + f + ".lyr"));
			//writer = new BufferedWriter(new FileWriter(file.getCanonicalPath() + "\\Data\\" + f + window + ".json"));

	}
	
	public FrequencyProcessor(int length){
		window = length;
		cList = new CircList(length);
	}
	
	@Override
	public void resetLyric(String l){
		lyric = l;
		frqMap = new MyMap<String, Integer>();
	}

	@Override
	public int processLyric(){
		if(lyric == null) return -1;
		else{
			words = lyric.split(" ");
			String word;
			String phrase; //for if we're using a CircList
			for(int i = 0; i < words.length; i++){
				word = this.processWord(words[i]);
				if(window > 1){ //then we're using a CircList
					cList.insert(word);
					if(!cList.isFull) continue;
					phrase = cList.getPhrase();
					Integer freq = frqMap.get(phrase);
					frqMap.put(phrase, (freq == null) ? 1 : freq + 1);
				}
				else{
					Integer freq = frqMap.get(word);
					frqMap.put(word, (freq == null) ? 1 : freq + 1);
				}
			}
			return words.length;
		}
	}
	

	
	public String[] getTop10(){
		String[] ret = new String[10];
		frqMap.KList.resetPtr();
		for(int i = 0; i < 10; i++){
			ret[i] = frqMap.KList.getPtr();
			frqMap.KList.advancePtr();
		}
		return ret;
	}
	
	public double distinctToTotalRatio(){
		return (double) frqMap.KList.size / words.length;
	}
	
	public MyMap getMap(){
		return frqMap;
	}
	
	public String toJSon(){
		return frqMap.toJSON();
	}
	
//	public void exportJSON(){
//		PrintWriter pw = new PrintWriter(writer);
//		pw.println(frqMap.toJSON());
//		pw.close();	
//	}
	

}
