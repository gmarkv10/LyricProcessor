import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* This class will attempt to take a song's lyrics as input and predict when the song was popular
 * It will do this by taking our data and seeing which words (lyrics) appear in each week's popular songs
 * It will compare the input song's lyrics and hopefully find words that appear only in specific time frames
 * 
 * For example, if our data shows that no one used the word "swag" before 2005, and swag appears in our input song,
 * it would help narrow down the probable time frame of our input song.
 */


public class timePrediction {
	private queries q = new queries();
	
	//contains distinct lyrics and an array list of weeks that lyric occurs
	public HashMap<String, ArrayList<String>> lyricWeeks = new HashMap<String, ArrayList<String>>();
	public HashMap<String, ArrayList<weekCount>> processedLyricWeeks = new HashMap<String, ArrayList<weekCount>>();
	
	//tests data from our db, averaging within 7 years correctness, 65% of data correctly classified within 5 years 73% within 10
	public void testAllData() throws ClassNotFoundException{
		String[][] allSongBestData = q.songANDartistANDbestweekANDbestrank();
		ArrayList<songPredictedActual> experiment = new ArrayList<songPredictedActual>();
		int lowWordCountSongs =0;
		for (int i=0;i<allSongBestData.length;i++){
			String lyrics = q.getLyrics(allSongBestData[i][0], allSongBestData[i][1]);
			int predictedYear = predictYear(lyrics);
			int actualYear = Integer.parseInt(allSongBestData[i][2].substring(0, 4));
			if (findUniqueWords(lyrics).size()>5 && actualYear >1990){
				songPredictedActual spa = new songPredictedActual(allSongBestData[i][0],allSongBestData[i][1], actualYear, predictedYear);
				experiment.add(spa);
				System.out.println("processed: "+i+" / "+allSongBestData.length);
			}
			else{
				lowWordCountSongs++;
			}
		}
		System.out.println("filtered "+lowWordCountSongs+" songs with low word counts");
		int difference = 0;
		int within5 = 0;
		int within10 = 0;
		for (int i=0;i<experiment.size();i++){
			int dif =  Math.abs(experiment.get(i).actualYear-experiment.get(i).predictedYear);
			if (dif <= 5){
				within5++;
			}
			if (dif <= 10){
				within10++;
			}
			difference += dif;
		}
		int meanDif = difference/experiment.size();
		float pctWithin5years = ((float)within5)/((float)experiment.size())*100;
		float pctWithin10years = ((float)within10)/((float)experiment.size())*100;
		System.out.println("Fit "+experiment.size()+" Songs with mean error of "+meanDif+" years");
		System.out.println("Fit "+pctWithin5years+" % of songs within 5 years");
		System.out.println("Fit "+pctWithin10years+" % of songs within 10 years");
		
	}
	
	public void init() throws IOException{
		loadData();
		filterData();
		filterData2();
	}
	//loads all data from timeData.txt
	public void loadData() throws IOException{
		System.out.println("Loading data...");
		processedLyricWeeks = new HashMap<String, ArrayList<weekCount>>();
		File file = new File("."); 
		File dataFile = new File(file.getCanonicalPath()+"/Data/timeData.txt");
		BufferedReader br = new BufferedReader(new FileReader(dataFile));
		String sCurrentLine;
		String curLyric;
		int count = 0;
		while ((sCurrentLine = br.readLine()) != null) {
			//System.out.println(sCurrentLine);
			String[] words = sCurrentLine.split(",");
			ArrayList<weekCount> curList = new ArrayList<weekCount>();
			curLyric = words[0];
			for (int i=1;i<words.length-1;i+=2){
				curList.add(new weekCount(words[i], Integer.parseInt(words[i+1])));
			}
			processedLyricWeeks.put(curLyric, curList);
		}
		br.close();
		System.out.println("Sucessfully Loaded "+processedLyricWeeks.size()+" distinct lyric data!");
	}
	//trys to remove data lyrics that span over 15 years
	public void filterData2(){
		ArrayList<String> lyricsToRemove = new ArrayList<String>();
		int startSize = processedLyricWeeks.size();
		int[] yearScore = new int[36]; //from 1980 at index 0 to 2015 @ index 34
		
		Iterator<Entry<String, ArrayList<weekCount>>> it = processedLyricWeeks.entrySet().iterator();
	    while (it.hasNext()) {
	    	for (int i =0;i<yearScore.length;i++){
				yearScore[i]=0;
			}
	        Map.Entry<String, ArrayList<weekCount>> pair = (Map.Entry<String, ArrayList<weekCount>>)it.next();
	        String lrc = pair.getKey();
	        ArrayList<weekCount> wCs = pair.getValue();
	        for (int i=0;i<wCs.size();i++){
	        	int year = Integer.parseInt(wCs.get(i).week.substring(0,4));
				int index = year-1980;
				yearScore[index] += wCs.get(i).count;
	        }
	      //  it.remove(); // avoids a ConcurrentModificationException
	        boolean shouldFilter = true;
	        int minYear =10000;
	        int maxYear =-1;
		    for (int j=0;j<yearScore.length;j++){
		    	if (yearScore[j]>0){
		    		maxYear = 1980+j;
		    		if ((1980+j)<minYear){
		    			minYear = 1980+j;
		    		}
		    	}
		    }
		    if ((maxYear-minYear) <= 20){
		    	shouldFilter = false;
		    }
		    if (shouldFilter){
		    	lyricsToRemove.add(lrc);
		    }
	    }
	    for (int i=0;i<lyricsToRemove.size();i++){
	    	processedLyricWeeks.remove(lyricsToRemove.get(i));
	    }
	    int endSize = processedLyricWeeks.size();
	    
	    System.out.println("Filtered: "+(startSize-endSize)+" spread out lyrics");
	}
	//filters lyrics that occur in every year (common words)
	public void filterData(){
		ArrayList<String> lyricsToRemove = new ArrayList<String>();
		int startSize = processedLyricWeeks.size();
		int[] yearScore = new int[36]; //from 1980 at index 0 to 2015 @ index 34
		
		Iterator<Entry<String, ArrayList<weekCount>>> it = processedLyricWeeks.entrySet().iterator();
	    while (it.hasNext()) {
	    	for (int i =0;i<yearScore.length;i++){
				yearScore[i]=0;
			}
	        Map.Entry<String, ArrayList<weekCount>> pair = (Map.Entry<String, ArrayList<weekCount>>)it.next();
	        String lrc = pair.getKey();
	        ArrayList<weekCount> wCs = pair.getValue();
	        for (int i=0;i<wCs.size();i++){
	        	int year = Integer.parseInt(wCs.get(i).week.substring(0,4));
				int index = year-1980;
				yearScore[index] += wCs.get(i).count;
	        }
	      //  it.remove(); // avoids a ConcurrentModificationException
	        boolean shouldFilter = true;
		    for (int j=0;j<yearScore.length;j++){
		    	if (yearScore[j]<1){
		    		shouldFilter = false;
		    	}
		    }
		    if (shouldFilter){
		    	lyricsToRemove.add(lrc);
		    }
	    }
	    for (int i=0;i<lyricsToRemove.size();i++){
	    	processedLyricWeeks.remove(lyricsToRemove.get(i));
	    }
	    int endSize = processedLyricWeeks.size();
	    
	    System.out.println("Filtered: "+(startSize-endSize)+" Commonly occuring lyrics");
	}
	public int predictYear(String lyrics) throws ClassNotFoundException{
		int resultYear = -1;
		int[] yearScore = new int[36]; //from 1980 at index 0 to 2015 @ index 34
		for (int i =0;i<yearScore.length;i++){
			yearScore[i]=0;
		}
		//HashMap score = q.everyWeek();
		Set<String> uniqueLyrics = findUniqueWords(lyrics);
		
		for (String word : uniqueLyrics){
			if (processedLyricWeeks.get(word)!=null){
				for (weekCount wC : processedLyricWeeks.get(word)){
					int year = Integer.parseInt(wC.week.substring(0,4));
					int index = year-1980;
					yearScore[index] += wC.count;
				}
			}
		}
		/*for (int j=0;j<yearScore.length;j++){
			System.out.println((1980+j)+": "+yearScore[j]);
		}*/
		
		int max = 0;
		int maxYear = 0;
		
		for (int i=0;i<yearScore.length;i++){
			if (yearScore[i]>=max){
				max = yearScore[i];
				maxYear = 1980+i;
			}
		}
		resultYear = maxYear;
		return resultYear;
	}
	
	//writes a data file containing all lyric words and weeks which they occur combined with how many songs contained that lyric for that week
	public void writeDataFile() throws ClassNotFoundException, IOException{
		File file = new File("."); 
		File outputFile = new File(file.getCanonicalPath()+"/Data/timeData.txt");
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
	
	
	//initial population
	public void populateMap() throws ClassNotFoundException{
		String[][] sNa = q.songsANDartists();
		int totalSongs = sNa.length;
		int count = 0;
		for (int i = 0;i<sNa.length;i++){
			String lyrics = q.getLyrics(sNa[i][0], sNa[i][1]);
			ArrayList<String> weeks = q.allWeeks(sNa[i][0], sNa[i][1]);
			Set<String> uniques = findUniqueWords(lyrics);
			for (String word : uniques){
				addLyricNWeek(word, weeks);
			}
			count++;
			System.out.println("Pop: "+count+" / "+totalSongs);
		}
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
	
	//find all unique words in a song source:
	//http://stackoverflow.com/questions/15522321/find-the-unique-words-in-a-text-file
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
	
	private class weekCount{
		public String week;
		public int count;
		
		public weekCount(String week, int count){
			this.week = week;
			this.count = count;
		}
		
	}
	private class songPredictedActual{
		public String song;
		public String artist;
		public int actualYear;
		public int predictedYear;
		
		public songPredictedActual(String song, String artist, int actualYear, int predictedYear){
			this.song = song;
			this.artist = artist;
			this.actualYear = actualYear;
			this.predictedYear = predictedYear;
		}
		
	}
}
