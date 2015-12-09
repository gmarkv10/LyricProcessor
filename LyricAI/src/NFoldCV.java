import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;



public class NFoldCV {
	
	//For each fold:
	//  +make filter words from the TRAINing songs
	//    -assign a weight to the year that is a function of stdDec and freq
	//    -if that weight is above a certain threshold:
	//      put lyric in a map <word, year with weight>
	//  +TEST the rest of the data
	//    -assign a year by adding the weights of any words that made
	//    -didn't get pruned to an array with an entry for every year
	//    -and finding the max
	//    -compare that year to the actual year.  

	int folds = 0;
	int foldSize = 0;
	int permutation = 0;
	String[][] allData;
	String[][] test;
	String[][] train;
	queries  q;
	BufferedReader reader;
	HashMap hMap = new HashMap<String, YearWeight>();
	
	public HashMap<String, ArrayList<String>> lyricWeeks = new HashMap<String, ArrayList<String>>();
	public HashMap<String, ArrayList<weekCount>> processedLyricWeeks = new HashMap<String, ArrayList<weekCount>>();
	
	public NFoldCV(int folds) throws ClassNotFoundException{
		this.folds = folds;
		q = new queries();
		allData = q.songANDartistANDbestweekANDbestrank();
		System.out.print("DB Query Completed Successfully.");
		foldSize = allData.length/folds;
		test = new String[foldSize][4];
		train =  new String[allData.length - foldSize][4];
		
		File file = new File("."); 
		File dataFile;
		try {
			dataFile = new File(file.getCanonicalPath()+"/Data/globalFreq_StdDev.csv");
			reader = new BufferedReader(new FileReader(dataFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void arrangeData(){
		
	}
	public void writeDataFile(int fold) throws ClassNotFoundException, IOException{
		File file = new File("."); 
		String filePath = file.getCanonicalPath()+"/Data/timeData"+fold+".txt";
		File outputFile = new File(filePath);
		PrintWriter pw = new PrintWriter(outputFile);
		
		//load and process the data
		populateMap();
		processWeeklyCount();
		
		//write it to a file
		Iterator<Entry<String, ArrayList<weekCount>>> it = processedLyricWeeks.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, ArrayList<weekCount>> pair = (Map.Entry<String, ArrayList<weekCount>>)it.next();
	        pw.write(pair.getKey());
	        for (int i=0;i<pair.getValue().size();i++){
	        	pw.write("," + pair.getValue().get(i).week+","+pair.getValue().get(i).count);
	        }
	        pw.write("\n");
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		pw.close();
	}
	
		public Set<String> findUniqueWords(String lyrics){
			Set<String> uniques = new HashSet<String>();
			//removes non-alphanumeric characters, judgement call
			lyrics = lyrics.replaceAll("[^A-Za-z0-9 ]", "");
			String[] words = lyrics.split(" ");
			for (String word : words){
				word = word.toLowerCase();
				uniques.add(word);
			}
			return uniques;
		}
	public void processWeeklyCount(){
		Iterator<Entry<String, ArrayList<String>>> it = lyricWeeks.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, ArrayList<String>> pair = (Map.Entry<String, ArrayList<String>>)it.next();
	        ArrayList<String> weeks = pair.getValue();
	        ArrayList<weekCount> convertedWeeks = convertWeekList(weeks);
	        processedLyricWeeks.put(pair.getKey(), convertedWeeks);
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	public ArrayList<weekCount> convertWeekList(ArrayList<String> weeklyList){
		ArrayList<weekCount> result = new ArrayList<weekCount>();
		Collections.sort(weeklyList);
		String currentWeek = "";
		String previousWeek = null;
		int count=0;
		for (int i=0;i<weeklyList.size();i++){
			currentWeek = weeklyList.get(i);
			if (currentWeek.equals(previousWeek)){
				count++;
			}
			else{
				if (previousWeek!=null){
					result.add(new weekCount(previousWeek, count));
				}
				count = 1;
				previousWeek = currentWeek;
			}
		}
		result.add(new weekCount(currentWeek, count));
		System.out.println("--- converted ---");
		for (int i =0;i<result.size();i++){
			System.out.println(result.get(i).week+" | "+result.get(i).count);
		}
		return result;
	}
	
	//for adding a lyric found in a given song to our hashmap based on the weeks that song was popular
		public void addLyricNWeek(String lyric, ArrayList<String> weeks){
			if (lyricWeeks.get(lyric)==null){
				lyricWeeks.put(lyric, new ArrayList<String>());
			}
			for (int i=0;i<weeks.size();i++){
				lyricWeeks.get(lyric).add(weeks.get(i));
			}
		}
		
	public void permuteTestTrain(int perm) throws ClassNotFoundException, IOException{
		//re initialize structures
		lyricWeeks = new HashMap<String, ArrayList<String>>();
		processedLyricWeeks = new HashMap<String, ArrayList<weekCount>>();
		System.out.println("Starting permution " + perm);
		int trainIdx = 0;
		int testIdxSTART = perm*foldSize;
		int testIdxEND   = testIdxSTART + foldSize;
		
		for(int i = 0; i < allData.length; i++){
			if(i >= testIdxSTART && i < testIdxEND){
				test[i - testIdxSTART] = allData[i];
			}
			else{
				train[trainIdx] = allData[i];
				trainIdx++;
			}
		}
		//populate process write
		writeDataFile(perm);
	}
	
//initial population
	public void populateMap() throws ClassNotFoundException{
		//String[][] sNa = q.songsANDartists();
		int totalSongs = train.length;
		int count = 0;
		
		for (int i = 0;i<train.length;i++){
			String lyrics = q.getLyrics(train[i][0], train[i][1]);
			ArrayList<String> weeks = q.allWeeks(train[i][0], train[i][1]);
			Set<String> uniques = findUniqueWords(lyrics);
			for (String word : uniques){
				addLyricNWeek(word, weeks);
			}
			count++;
			System.out.println("Pop: "+count+" / "+totalSongs);
		}
	}
	
	public void crossValidate() throws ClassNotFoundException, IOException{
		for(int i  = 0; i < folds; i++){
			permuteTestTrain(i);
		}
	}
	
	public double getWeight(double stdDev, int freq){
		return freq/stdDev;
	}
	
	public void train() throws ClassNotFoundException{
		
		for (int i=0;i<train.length;i++){
			String[] words = q.getLyrics(train[i][0], train[i][1]).split(" ");
		}
		
	}
	
	public void test() throws ClassNotFoundException{
		for (int i=0;i<test.length;i++){
			String[] words = q.getLyrics(train[i][0], train[i][1]).split(" ");
		}
		
	}
	
	class YearWeight{
		int year;
		double weight;
		
		public YearWeight(int y, double w){
			year = y;
			weight = w;
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
