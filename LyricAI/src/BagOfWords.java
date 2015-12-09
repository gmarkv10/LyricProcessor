import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;



public class BagOfWords{
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	Set<String> sortedKeys =  new TreeSet<String>();
	String line = null;
	
	BufferedReader reader; 
	BufferedWriter writer;
	String name;
	
	queries q;
	String[][] SONGINFO;
	String lyric;

	
	public BagOfWords(String n) throws Exception{
		// TODO Auto-generated method stub
		name = n;
		reader =  new BufferedReader(new FileReader("Data/globalFreq_StdDev.csv"));
		writer =  new BufferedWriter(new FileWriter("Data/bigheader.arff"));
		String[] data = null; String word, freq;
		//constructing map
		System.out.println("constructing map");
		while((line =reader.readLine()) != null){
			data = line.split(",");
			word = data[0];
			map.put(word, 0);
			sortedKeys.add(word);
		}
		System.out.println("making query");
		q = new queries();
		SONGINFO = q.songANDartistANDbestweekANDbestrank();
		System.out.println("done");
	}
	
	void resetMap(){
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
		    entry.setValue(0);
		}
	}
	
	public void playSong(int i) throws ClassNotFoundException, IOException{
		//lyric = q.getLyrics(SONGINFO[i][0], SONGINFO[i][1]);
		if(i == 1) lyric  = "0, 0z 0";
		if(i ==2) lyric = "0 1 0";
		String[] words = lyric.split(" ");
		
		for(String 	w : words){
			String word = processWord(w);
			
			Integer freq =  map.get(word);
			if(word.equals("0")){
				System.out.println("word: " + word + " freq: "+ freq);	
			}
			
			//map.put(word, (freq == null ? 1 : freq++));
			map.put(word, freq++);
		}
		Iterator it = sortedKeys.iterator();
		System.out.println(map.get(it.next()) + "," + map.get(it.next()) + "," + map.get(it.next()) + "," + map.get(it.next()));
//		while(it.hasNext()){
//			Integer f = map.get(it.next());
//			if(f == null){
//				f = 0;
//			}
//			writer.write(f +",");
//			
//			writer.write(year);
//			writer.newLine();
//		}
		
	}
	
//0 0z 1
	
	
	
	void writeaarfheader() throws IOException{
		if(writer == null){
			System.out.print("run init.");
		}
		else{
			
			writer.write("@relation " + name); writer.newLine();
			writer.newLine();
			int i = 1;
			Iterator it = map.keySet().iterator();
			
			while(it.hasNext()){
				writer.write("@attribute " + it.next() + i++); writer.newLine();
			}
			
			writer.write("@attribute timespan {early1980s,mid1980s,late1980s,early1990s,mid1990s,late1990s,early2000s,mid2000s,late2000s,early2010s,mid2010s}" );
			writer.newLine();
			writer.newLine();
			writer.write("@data"); writer.newLine();
		}
		
		
	}
	
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

}
